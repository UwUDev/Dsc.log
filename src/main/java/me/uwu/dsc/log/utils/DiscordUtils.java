package me.uwu.dsc.log.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class DiscordUtils {
    public static boolean isValidToken(String token) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://canary.discord.com/api/v9/users/@me/guilds")
                .get()
                .addHeader("Authorization", token)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() != 200)
                return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
