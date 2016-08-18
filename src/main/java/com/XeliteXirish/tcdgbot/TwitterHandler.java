package com.XeliteXirish.tcdgbot;

import net.dv8tion.jda.entities.Guild;
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

    public static void sendTweet(Guild guild, String tweet) {
        try {
            Status status = twitter.updateStatus(tweet);


        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
