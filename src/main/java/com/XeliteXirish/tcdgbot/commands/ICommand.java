package com.XeliteXirish.tcdgbot.commands;

import net.dv8tion.jda.events.message.MessageReceivedEvent;

public interface ICommand {

    boolean called(String[] args, MessageReceivedEvent event);

    void action(String[] args, MessageReceivedEvent event);

    String help();

    void executed(boolean success, MessageReceivedEvent event);

    String getTag();
}
