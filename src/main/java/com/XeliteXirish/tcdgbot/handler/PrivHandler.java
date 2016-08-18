package com.XeliteXirish.tcdgbot.handler;

import com.XeliteXirish.tcdgbot.Main;
import com.XeliteXirish.tcdgbot.utils.Constants;
import com.XeliteXirish.tcdgbot.utils.JsonReader;
import com.XeliteXirish.tcdgbot.utils.MessageUtils;
import net.dv8tion.jda.Permission;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.Role;
import net.dv8tion.jda.entities.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PrivHandler {

    private static ArrayList<User> tcdgOwners = new ArrayList<>();
    private static File tcdgUserOwners = new File("tcdg_user_owners.json");

    public static void setupUsers() {
        addDefaultTCDGOwners();
    }

    public static boolean isUserTCDGOwner(User user) {
        if (tcdgOwners.contains(user)) {
            return true;
        }
        return false;
    }

    public static boolean isUserServerStaff(Guild guild, User user) {
        for (Role role : guild.getRolesForUser(user)) {
            return role.hasPermission(Permission.MESSAGE_MANAGE);
        }
        return false;
    }

    private static void addDefaultTCDGOwners() {

        try {
            JSONObject jsonObject = JsonReader.readJsonFromUrl(Constants.TCDG_OWNERS_URL);
            JSONArray jsonArray = jsonObject.getJSONArray("arrayTcdgOwners");

            if (jsonArray != null) {
                for (int x = 0; x < jsonArray.length(); x++) {
                    JSONObject jsonItem = jsonArray.getJSONObject(x);

                    String[] userSplit = String.valueOf(jsonItem.get("userSplit")).split("-");
                    String userId = userSplit[1];

                    User user = Main.jda.getUserById(userId);
                    if (user != null) {
                        tcdgOwners.add(user);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addUserToTcdgOwners(User author, User user){
        if(isUserTCDGOwner(author)){
            tcdgOwners.add(user);

            writeOwnersList();
        }else {
            user.getPrivateChannel().sendMessage(MessageUtils.wrapStringInCodeBlock("Sorry but you do not have the required permission to execute that command."));
        }
    }

    public static void reloadFromGit() {
        tcdgOwners.clear();
        addDefaultTCDGOwners();

    }

    private static void writeOwnersList(){
        try {
            if(!tcdgUserOwners.exists()){
                tcdgUserOwners.createNewFile();
            }

            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArrayUsers = new JSONArray();
            jsonObject.put("arrayTcdgOwners", jsonArrayUsers);

            for (int x = 0; x < tcdgOwners.size(); x++){
                String userId = tcdgOwners.get(x).getId();
                String username = tcdgOwners.get(x).getUsername();

                jsonArrayUsers.put(username + ":" + userId);
            }

            FileWriter fileWriter = new FileWriter(tcdgUserOwners);
            fileWriter.write(jsonObject.toString());
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
