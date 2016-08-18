package com.XeliteXirish.tcdgbot.commands;

import com.XeliteXirish.tcdgbot.Main;
import com.XeliteXirish.tcdgbot.utils.Constants;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class ChatMessageListeners {

    public static void onMessageRecieved(MessageReceivedEvent event){
        if (event.getMessage().getContent().startsWith(Constants.COMMAND_PREFIX) && event.getMessage().getAuthor().getId() != event.getJDA().getSelfInfo().getId()) {
            Main.handleCommand(Main.parser.parse(event.getMessage().getContent().toLowerCase(), event));

        }
    }
}
