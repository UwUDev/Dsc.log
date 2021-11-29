package me.uwu.dsc.log.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cache {
    public static int size = 32;
    private static List<String> messages = new ArrayList<>();

    public static void add(String message) {
        messages.addAll(Arrays.asList(message.split("\n")));
        resizeCache();
    }

    public static String getRawMessages() {
        StringBuilder sb = new StringBuilder();
        messages.forEach(m -> sb.append(m).append("\n"));
        return sb.toString();
    }

    private static void resizeCache() {
        if (size < messages.size()) {
            while (size < messages.size()) {
                messages.remove(0);
            }
        }
    }
}
