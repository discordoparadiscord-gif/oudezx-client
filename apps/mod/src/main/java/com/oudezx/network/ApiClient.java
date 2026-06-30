package com.oudezx.network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;

public class ApiClient {
    private static final String API_URL = "http://localhost:3000/api";
    private final HttpClient httpClient = HttpClients.createDefault();
    private final Gson gson = new Gson();

    public JsonObject getCosmetics(String username) {
        try {
            HttpGet request = new HttpGet(API_URL + "/cosmetics/" + username);
            return httpClient.execute(request, response -> {
                String body = EntityUtils.toString(response.getEntity());
                return gson.fromJson(body, JsonObject.class);
            });
        } catch (IOException e) {
            System.err.println("Failed to fetch cosmetics: " + e.getMessage());
            return new JsonObject();
        }
    }

    public boolean syncCosmetics(String username, JsonObject cosmetics) {
        try {
            HttpPost request = new HttpPost(API_URL + "/cosmetics/sync");
            JsonObject body = new JsonObject();
            body.addProperty("username", username);
            body.add("cosmetics", cosmetics);
            request.setEntity(new StringEntity(gson.toJson(body)));
            request.setHeader("Content-Type", "application/json");

            return httpClient.execute(request, response -> response.getCode() == 200);
        } catch (IOException e) {
            System.err.println("Failed to sync cosmetics: " + e.getMessage());
            return false;
        }
    }
}