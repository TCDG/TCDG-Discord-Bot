package com.XeliteXirish.tcdgbot.commands;

import com.XeliteXirish.tcdgbot.handler.TwitterHandler;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class TwitterCommand implements ICommand{

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if(args.length == 0){
            event.getTextChannel().sendMessage(help());

        }else {
            StringBuilder builder = new StringBuilder();

            for(int x = 0; x < args.length; x++){
                builder.append(args[x] + " ");
            }

            TwitterHandler.sendTweet(event.getAuthor(), event.getTextChannel(), builder.toString());
        }
    }

    @Override
    public String help() {
        return "Tweets from the TCDG Twitter account";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getTag() {
        return "tweet";
    }
}
