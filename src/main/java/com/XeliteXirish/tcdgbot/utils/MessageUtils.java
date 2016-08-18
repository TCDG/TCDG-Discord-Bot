package com.XeliteXirish.tcdgbot.utils;

import net.dv8tion.jda.MessageBuilder;
import net.dv8tion.jda.Permission;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;

public class MessageUtils {

    public static String wrapStringInCodeBlock(String message) {
        String newMessage = "```" + message + "```";
        return newMessage;
    }

    public static Message appendEveryone(String message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.appendEveryoneMention();
        messageBuilder.appendString(" " + message);
        return messageBuilder.build();
    }

    public static void sendNoPermissionMessage(User author, TextChannel channel) {
        String message = "Sorry but you don't have the required permission to use this command.";
        channel.sendMessage(author.getAsMention() + MessageUtils.wrapStringInCodeBlock(message));
    }
}
