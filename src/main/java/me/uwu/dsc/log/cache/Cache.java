package me.uwu.dsc.log.cache;

import me.uwu.dsc.log.art.PrintableMessage;

import java.util.ArrayList;
import java.util.List;

public class Cache {
    public static int size = 32;
    private static final List<PrintableMessage> messages = new ArrayList<>();

    public static void add(PrintableMessage message) {
        messages.add(message);
        resizeCache();
    }

    public static void setDeleted(long id) {
        for (PrintableMessage message : messages) {
            if (message.getId() == id) {
                message.setDeleted(true);
            }
        }
    }

    public static String getRawMessages() {
        StringBuilder sb = new StringBuilder();
        messages.forEach(m -> sb.append(m.toPrintable()).append("\n"));
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
