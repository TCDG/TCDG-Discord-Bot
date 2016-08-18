package com.XeliteXirish.tcdgbot.listeners;

import com.XeliteXirish.tcdgbot.commands.ChatMessageListeners;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

public class BotListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        ChatMessageListeners.onMessageRecieved(event);
    }
}
