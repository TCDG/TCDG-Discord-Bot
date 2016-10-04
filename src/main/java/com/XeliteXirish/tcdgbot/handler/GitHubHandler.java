package com.XeliteXirish.tcdgbot.handler;

import com.XeliteXirish.tcdgbot.GithubRepo;
import com.XeliteXirish.tcdgbot.utils.Constants;
import com.XeliteXirish.tcdgbot.utils.JsonReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GitHubHandler {

    private static ArrayList<GithubRepo> orgRepos = new ArrayList<>();

    public static void fillRepos(){
        JsonReader jsonReader = new JsonReader();
        JSONObject jsonObject = jsonReader.makeHttpRequest(Constants.GITHUB_ORG_REPOS, "GET", new HashMap<String, String>());

        JSONArray jsonArray = jsonObject.getJSONArray("");
    }
}
