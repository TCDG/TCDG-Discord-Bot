package com.XeliteXirish.tcdgbot.commands;

import com.XeliteXirish.tcdgbot.Main;
import com.XeliteXirish.tcdgbot.handler.PrivHandler;
import com.XeliteXirish.tcdgbot.utils.Constants;
import com.XeliteXirish.tcdgbot.utils.MessageUtils;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdminCommand implements ICommand {

    private static String[] adminCommands = {"reload", "clear", "status", "add", "list"};
    private static String[] adminCommandHelp = {"Reloads the data from the online git source, removing any offline edits",
            "Clears recent bot messages, Usage 'clear <number>'",
            "Sets the bot status to the specified text",
            "Adds a user to the TCDG owners <needs a commit to be permanent>",
            "List the current TCDG Owners that have permission to use the bot"};

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if (args.length == 0) {
            sendAdminHelpMessage(event);
        } else {
            if (PrivHandler.isUserServerStaff(event.getGuild(), event.getAuthor()) || PrivHandler.isUserTCDGOwner(event.getAuthor())) {
                if (args[0].equalsIgnoreCase(adminCommands[0])) {
                    event.getTextChannel().sendMessage(MessageUtils.wrapStringInCodeBlock("Reloading users from github..."));
                    PrivHandler.reloadFromGit();
                } else if (args[0].equalsIgnoreCase(adminCommands[1])) {
                    MessageChannel messageChannel = event.getChannel();
                    int clearedMessages = 0;
                    Collection<Message> messageCollection = new ArrayList<>();

                    if (args.length == 2) {
                        int clearMessages = Integer.parseInt(args[1]);
                        List<Message> recentMessages = messageChannel.getHistory().retrieve(clearMessages);

                        for (Message message : recentMessages) {
                            if (message.getAuthor().getId().equals(Main.jda.getSelfInfo().getId())) {
                                messageCollection.add(message);
                                clearedMessages++;
                            }
                        }
                        event.getTextChannel().deleteMessages(messageCollection);

                    } else {
                        List<Message> recentMessages = messageChannel.getHistory().retrieve(10);

                        for (Message message : recentMessages) {
                            if (message.getAuthor().getId().equals(Main.jda.getSelfInfo().getId())) {
                                message.deleteMessage();
                                clearedMessages++;
                            }
                        }
                    }
                    event.getTextChannel().sendMessage(MessageUtils.wrapStringInCodeBlock("Cleared " + clearedMessages + " messages from chat."));

                } else if (args[0].equalsIgnoreCase(adminCommands[2])) {
                    if (args.length >= 2) {
                        StringBuilder builder = new StringBuilder();

                        for (int x = 1; x < args.length; x++) {
                            builder.append(args[x] + " ");
                        }
                        event.getJDA().getAccountManager().setGame(builder.toString());
                        event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + " has changed the status to: " + builder.toString());
                    } else {
                        event.getTextChannel().sendMessage(MessageUtils.wrapStringInCodeBlock("Use '!dev admin playing <title>'"));
                    }
                }else if(args[0].equalsIgnoreCase(adminCommands[4])){
                    StringBuilder builder = new StringBuilder();
                    builder.append("The current TCDG Owners are: \n");
                    for(User user : PrivHandler.getTCDGOwners()){
                        builder.append(user.getUsername() + " - " + user.getId() + "\n");
                    }
                    event.getTextChannel().sendMessage(MessageUtils.wrapStringInCodeBlock(builder.toString()));
                }
            } else if (PrivHandler.isUserTCDGOwner(event.getAuthor())) {
                if (args[0].equalsIgnoreCase(adminCommands[3])) {
                    if (PrivHandler.isUserTCDGOwner(event.getAuthor())) {
                        for (User user : event.getMessage().getMentionedUsers()) {
                            PrivHandler.addUserToTcdgOwners(event.getAuthor(), user);
                        }
                    } else {
                        MessageUtils.sendNoPermissionMessage(event.getAuthor(), event.getTextChannel());
                    }
                }
            } else {
                MessageUtils.sendNoPermissionMessage(event.getAuthor(), event.getTextChannel());
            }
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

    private static void sendAdminHelpMessage(MessageReceivedEvent event) {
        StringBuilder builder = new StringBuilder();
        builder.append("The following admin commands can be used by the bot: \n");
        for (int x = 0; x < adminCommands.length; x++) {
            builder.append(adminCommands[x] + ": " + adminCommandHelp[x] + "\n");
        }
        builder.append("\nTo use an admin command do '" + Constants.COMMAND_PREFIX + " admin <sub_command>'");
        event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + "\n" + MessageUtils.wrapStringInCodeBlock(builder.toString()));
    }
}
