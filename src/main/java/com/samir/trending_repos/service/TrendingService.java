package com.samir.trending_repos.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.samir.trending_repos.constant.Constant;
import com.samir.trending_repos.model.Language;
import com.samir.trending_repos.model.Repository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import static com.samir.trending_repos.constant.Constant.*;

@Service
public class TrendingService {

    Map<String, Language> languageListMap;

    public Map<String, Language> getTrendingRepos() throws IOException {
        languageListMap = new HashMap<>();
        HttpURLConnection connection = getConnection();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String data;
        while ((data = bufferedReader.readLine()) != null) {
            addToMap(data);
        }
        connection.disconnect();
        return languageListMap;
    }

    private HttpURLConnection getConnection() throws IOException {
        URL url = new URL(Constant.getUrl());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setConnectTimeout(1000);
        return connection;
    }

    private void addToMap(String jsonData) {
        JsonArray itemsList = getItemsList(jsonData);
        for (int i = 0; i < itemsList.size(); i++) {
            String languageName = getLanguage(itemsList.get(i).getAsJsonObject());
            if (languageName != null) {
                Repository repository = getRepository(itemsList.get(i).getAsJsonObject());
                if (languageListMap.containsKey(languageName)) {
                    Language languageModel = languageListMap.get(languageName);
                    languageModel.getRepositoryList().add(repository);
                    languageModel.setNumberOfRepos(languageModel.getNumberOfRepos() + 1);
                    languageListMap.replace(languageName, languageModel);
                } else {

                    languageListMap.put(languageName, new Language(1, new ArrayList<>(Collections.singleton(repository))));
                }
            }
        }
    }

    private JsonArray getItemsList(String jsonData) {
        JsonObject dataObject = JsonParser.parseString(jsonData).getAsJsonObject();
        return dataObject.get("items").getAsJsonArray();
    }

    private Repository getRepository(JsonObject itemRow) {
        return new Repository(itemRow.get(REPO_FULL_NAME_PARAMETER).getAsString(), itemRow.get(REPO_URL_PARAMETER).getAsString());
    }

    private String getLanguage(JsonObject itemRow) {
        if (itemRow.get(LANGUAGE_PARAMETER).isJsonNull())
            return null;
        return itemRow.get(LANGUAGE_PARAMETER).getAsString();
    }
}
