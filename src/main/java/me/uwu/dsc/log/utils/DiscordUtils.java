package me.uwu.dsc.log.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiscordUtils {
    public static boolean isValidToken(String token) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://discord.com/api/v9/users/@me/inventory")
                .get()
                .addHeader("authorization", token)
                .addHeader("x-super-properties", "eyJvcyI6IkFuZHJvaWQiLCJicm93c2VyIjoiRGlzY29yZCBBbmRyb2lkIiwiZGV2aWNlIjoiYmx1ZWpheSIsInN5c3RlbV9sb2NhbGUiOiJmci1GUiIsImNsaWVudF92ZXJzaW9uIjoiMjA2LjE2IC0gcm4iLCJyZWxlYXNlX2NoYW5uZWwiOiJnb29nbGVSZWxlYXNlIiwiZGV2aWNlX3ZlbmRvcl9pZCI6IjhkZGU4M2IzLTUzOGEtNDJkMi04MzExLTM1YmFlY2M2YmJiOCIsImJyb3dzZXJfdXNlcl9hZ2VudCI6IiIsImJyb3dzZXJfdmVyc2lvbiI6IiIsIm9zX3ZlcnNpb24iOiIzMyIsImNsaWVudF9idWlsZF9udW1iZXIiOjIwNjAxNjAwMTM4MTA0LCJjbGllbnRfZXZlbnRfc291cmNlIjpudWxsLCJkZXNpZ25faWQiOjB9")
                .addHeader("accept-language", "fr-FR")
                .addHeader("x-discord-locale", "fr")
                .addHeader("x-discord-timezone", "Europe/Paris")
                .addHeader("x-debug-options", "bugReporterEnabled")
                .addHeader("user-agent", "Discord-Android/206016;RNA")
                .addHeader("host", "discord.com")
                .addHeader("connection", "Keep-Alive")
                .addHeader("accept-encoding", "gzip")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                response.close();
                return false;
            }
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static List<Long> getGuilds(String token) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://canary.discord.com/api/v9/users/@me/guilds")
                .get()
                .addHeader("authorization", token)
                .addHeader("x-super-properties", "eyJvcyI6IkFuZHJvaWQiLCJicm93c2VyIjoiRGlzY29yZCBBbmRyb2lkIiwiZGV2aWNlIjoiYmx1ZWpheSIsInN5c3RlbV9sb2NhbGUiOiJmci1GUiIsImNsaWVudF92ZXJzaW9uIjoiMjA2LjE2IC0gcm4iLCJyZWxlYXNlX2NoYW5uZWwiOiJnb29nbGVSZWxlYXNlIiwiZGV2aWNlX3ZlbmRvcl9pZCI6IjhkZGU4M2IzLTUzOGEtNDJkMi04MzExLTM1YmFlY2M2YmJiOCIsImJyb3dzZXJfdXNlcl9hZ2VudCI6IiIsImJyb3dzZXJfdmVyc2lvbiI6IiIsIm9zX3ZlcnNpb24iOiIzMyIsImNsaWVudF9idWlsZF9udW1iZXIiOjIwNjAxNjAwMTM4MTA0LCJjbGllbnRfZXZlbnRfc291cmNlIjpudWxsLCJkZXNpZ25faWQiOjB9")
                .addHeader("accept-language", "fr-FR")
                .addHeader("x-discord-locale", "fr")
                .addHeader("x-discord-timezone", "Europe/Paris")
                .addHeader("x-debug-options", "bugReporterEnabled")
                .addHeader("user-agent", "Discord-Android/206016;RNA")
                .addHeader("host", "discord.com")
                .addHeader("connection", "Keep-Alive")
                .addHeader("accept-encoding", "document")
                .build();

        try {
            Response response = client.newCall(request).execute();
            Gson gson = new Gson();
            List<Long> guilds = new ArrayList<>();
            if (response.isSuccessful()) {
                JsonArray array = gson.fromJson(response.body().string(), JsonArray.class);
                array.forEach(e -> guilds.add(e.getAsJsonObject().get("id").getAsLong()));
            }
            response.close();

            return guilds;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
