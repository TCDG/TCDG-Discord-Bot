package com.XeliteXirish.tcdgbot.commands;

import com.XeliteXirish.tcdgbot.handler.PrivHandler;
import com.XeliteXirish.tcdgbot.utils.Constants;
import com.XeliteXirish.tcdgbot.utils.MessageUtils;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.List;

public class AdminCommand implements ICommand{

    private static String[] adminCommands = {"reload", "add"};
    private static String[] adminCommandHelp = {"Reloads the data from the online git source, removing any offline edits", "Adds a user to the TCDG owners <needs a commit to be permanent>"};
    
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if(PrivHandler.isUserServerStaff(event.getAuthor())){
            if(args.length == 0){
                sendAdminHelpMessage(event);
            }else{
                if(args[0].equalsIgnoreCase(adminCommands[0])){
                    event.getTextChannel().sendMessage(MessageUtils.wrapStringInCodeBlock("Reloading users from github..."));
                    PrivHandler.reloadFromGit();

                }else if (args[0].equalsIgnoreCase(adminCommands[1])){
                    if(PrivHandler.isUserTCDGOwner(event.getAuthor())){
                        for(User user : event.getMessage().getMentionedUsers()) {
                            PrivHandler.addUserToTcdgOwners(event.getAuthor(), user);
                        }
                    }else {
                        MessageUtils.sendNoPermissionMessage(event.getAuthor(), event.getGuild());
                    }
                }
            }
        }else {
            MessageUtils.sendNoPermissionMessage(event.getAuthor(), event.getGuild());
        }
    }

    @Override
    public String help() {
        return "Administrative commands that must be preformed only by a server administrator";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getTag() {
        return "admin";
    }

    private static void sendAdminHelpMessage(MessageReceivedEvent event){
        StringBuilder builder = new StringBuilder();
        builder.append("The following admin commands can be used by the bot: \n");
        for(int x = 0; x < adminCommands.length; x++){
            builder.append(adminCommands[x] + ": " + adminCommandHelp[x] + "\n");
        }
        builder.append("\nTo use an admin command do '" + Constants.COMMAND_PREFIX + " admin <sub_command>'");
        event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + "\n" + MessageUtils.wrapStringInCodeBlock(builder.toString()));
    }
}
