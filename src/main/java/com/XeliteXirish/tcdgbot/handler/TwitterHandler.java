package com.XeliteXirish.tcdgbot.handler;

import com.XeliteXirish.tcdgbot.utils.BotLogger;
import com.XeliteXirish.tcdgbot.utils.MessageUtils;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterHandler {

    private static Twitter twitter;

    public static void init(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {

        ConfigurationBuilder config = new ConfigurationBuilder();
        config.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret).setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessTokenSecret);

        TwitterFactory twitterFactory = new TwitterFactory(config.build());
        twitter = twitterFactory.getInstance();

    }

    public static void sendTweet(User sender, TextChannel textChannel, String tweet) {
        if (PrivHandler.isUserTCDGOwner(sender)) {
            try {
                Status status = twitter.updateStatus(tweet);
                textChannel.sendMessage(sender.getUsername() + " tweeted: " + status.getText());

                BotLogger.twitterLog(sender.getUsername() + "tweeted: " + status.getText());
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }else {
            MessageUtils.sendNoPermissionMessage(sender, textChannel);
        }
    }
}
