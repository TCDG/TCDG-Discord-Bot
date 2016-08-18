package com.XeliteXirish.tcdgbot;

import com.XeliteXirish.tcdgbot.commands.ICommand;
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

    public static void main(String[] args){

        if(args.length == 1){
            DISCORD_TOKEN = args[0];
        }else {
            System.out.println("Please enter a valid Discord Bot Token!");
            return;
        }

        try {
            jda = new JDABuilder().setBotToken(DISCORD_TOKEN).setAudioEnabled(true).addListener(new BotListener()).setAudioEnabled(true).buildBlocking();
        }catch (Exception e){
            e.printStackTrace();
        }
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
