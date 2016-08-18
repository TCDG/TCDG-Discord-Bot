package com.XeliteXirish.tcdgbot.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class BotLogger {

    private static Logger generalLogger = Logger.getLogger("generalLog.txt");
    private static Logger twitterLogger = Logger.getLogger("twitterLog.txt");

    private static FileHandler generalFileHandler;
    private static FileHandler twitterFileHandler;

    public static void initLoggers(){

        try {
            generalFileHandler = new FileHandler("generalLog.log");
            twitterFileHandler = new FileHandler("twitterLog.log");

            generalLogger.addHandler(generalFileHandler);
            twitterLogger.addHandler(twitterFileHandler);

            SimpleFormatter formatter = new SimpleFormatter();
            generalFileHandler.setFormatter(formatter);
            twitterFileHandler.setFormatter(formatter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generalLog(String heading, String message){
        generalLogger.info("[" + heading.toUpperCase() + "]: " + message);
    }

    public static void twitterLog(String message){
        twitterLogger.info("[TWITTER]: " + message);
    }
}
