package com.XeliteXirish.tcdgbot.commands;

import com.XeliteXirish.tcdgbot.Main;
import com.XeliteXirish.tcdgbot.utils.Constants;
import com.XeliteXirish.tcdgbot.utils.MessageUtils;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.Iterator;
import java.util.Map;

public class HelpCommand implements ICommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length == 0) {
            sendCommandHelpMessage(event);
        } else {
            String helpCommand = args[0];
            ICommand command = getCommandFromString(helpCommand);

            sendCommandHelpMessage(event, command);
        }
    }

    @Override
    public String help() {
        return "View the available commands for this bot.";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getTag() {
        return "help";
    }

    private static void sendCommandHelpMessage(MessageReceivedEvent event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hey im TCDG Bot, my master is XeliteXirish and Elite Dev Bot is my bestie!\n");
        stringBuilder.append("You can use the following commands with this bot: ");

        Iterator commandEntries = Main.commands.entrySet().iterator();
        while (commandEntries.hasNext()) {
            Map.Entry pair = (Map.Entry) commandEntries.next();
            ICommand command = (ICommand) pair.getValue();
            stringBuilder.append(command.getTag() + ": " + command.help() + "\n");
        }
        stringBuilder.append("\nThe bot prefix is: " + Constants.COMMAND_PREFIX + "\n");
        event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + "\n" + MessageUtils.wrapStringInCodeBlock(stringBuilder.toString()));
    }

    private static ICommand getCommandFromString(String commandName) {
        if (Main.commands.containsKey(commandName)) {
            return Main.commands.get(commandName);
        }
        return null;
    }

    private static void sendCommandHelpMessage(MessageReceivedEvent event, ICommand command) {
        if (command.help() != null) {
            event.getTextChannel().sendMessage(command.help());
        } else {
            event.getTextChannel().sendMessage("Sorry there is no info available for this command, please contact a bot admin.");
        }
    }
}
