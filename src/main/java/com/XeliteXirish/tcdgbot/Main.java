package com.XeliteXirish.tcdgbot;

import com.XeliteXirish.tcdgbot.commands.AdminCommand;
import com.XeliteXirish.tcdgbot.commands.HelpCommand;
import com.XeliteXirish.tcdgbot.commands.ICommand;
import com.XeliteXirish.tcdgbot.commands.TwitterCommand;
import com.XeliteXirish.tcdgbot.handler.PrivHandler;
import com.XeliteXirish.tcdgbot.handler.TwitterHandler;
import com.XeliteXirish.tcdgbot.listeners.BotListener;
import com.XeliteXirish.tcdgbot.utils.CommandParser;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.JDABuilder;

import java.util.HashMap;

public class Main {

    public static JDA jda;
    public static final CommandParser parser = new CommandParser();
    public static HashMap<String, ICommand> commands = new HashMap<>();

    public static String DISCORD_TOKEN;
    private static String CONSUMER_KEY;
    private static String CONSUMER_SECRET;
    private static String ACCESS_TOKEN;
    private static String ACCESS_TOKEN_SECRET;

    public static void main(String[] args){

        if(args.length == 5){
            DISCORD_TOKEN = args[0];
            CONSUMER_KEY = args[1];
            CONSUMER_SECRET = args[2];
            ACCESS_TOKEN = args[3];
            ACCESS_TOKEN_SECRET = args[4];
        }else {
            System.out.println("Please check to make sure you entered the correct arguments: <Discord Token, Consumer Key, Consumer Secret, Access Token, Access Token Secret>");
            return;
        }

        try {
            jda = new JDABuilder().setBotToken(DISCORD_TOKEN).setAudioEnabled(true).addListener(new BotListener()).setAudioEnabled(true).buildBlocking();
        }catch (Exception e){
            e.printStackTrace();
        }

        registerCommands();
        PrivHandler.setupUsers();
        TwitterHandler.init(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
    }

    private static void registerCommands(){
        commands.put("help", new HelpCommand());
        commands.put("tweet", new TwitterCommand());
        commands.put("admin", new AdminCommand());
    }

    public static void handleCommand(CommandParser.CommandContainer cmd){
        if(commands.containsKey(cmd.invoke)){
            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);

            if(safe){
                commands.get(cmd.invoke).action(cmd.args, cmd.event);
                commands.get(cmd.invoke).executed(safe, cmd.event);
            }else {
                commands.get(cmd.invoke).executed(safe, cmd.event);
            }
        }
    }
}
