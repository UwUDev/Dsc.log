package me.uwu.dsc.log.logs;

import com.google.gson.Gson;
import me.uwu.dsc.log.Main;
import me.uwu.dsc.log.art.PrintableMessage;
import me.uwu.dsc.log.cache.Cache;
import me.uwu.dsc.log.database.DBManager;
import me.uwu.dsc.log.dump.Dumper;
import me.uwu.dsc.log.event.Event;
import me.uwu.dsc.log.event.EventType;
import me.uwu.dsc.log.event.impl.MessageReceived;
import me.uwu.dsc.log.setting.SettingsManager;
import me.uwu.dsc.log.stats.Stats;
import me.uwu.dsc.log.struct.Attachment;
import me.uwu.dsc.log.utils.ConsoleUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Logger {
    public static void logEvent(Event e) {
        if (e.getEventType() == EventType.MESSAGE_RECEIVED) {
            addMessage(new Gson().fromJson(e.getData(), MessageReceived.class));
            ConsoleUtils.refresh();
        }
        if (e.getEventType() == EventType.MESSAGE_DELETE) {
            deleteMessage(e.getData().get("id").getAsLong());
            ConsoleUtils.refresh();
        }

    }

    private static void addMessage(MessageReceived message) {
        if (SettingsManager.ignoreBots && message.getAuthor().isBot()) return;

        Stats.messageReceived++;
        if (SettingsManager.logMessages) {
            PrintableMessage printableMessage = new PrintableMessage(message.getId(), message.getAuthor().getUsername(), message.getContent());
            Cache.add(printableMessage);
        }
        if (SettingsManager.logFiles)
            for (Attachment attachment : message.getAttachments()) {
                if (!isBlacklisted(attachment.getUrl())) {
                    Dumper.dumpUrl(attachment.getUrl());
                    PrintableMessage printableMessage = new PrintableMessage(message.getId(), message.getAuthor().getUsername(), attachment.getUrl());
                    Cache.add(printableMessage);
                } else if (Main.debug)
                    System.out.println("\u001B[31mBlacklisted file:" + attachment.getUrl());
            }

        if (SettingsManager.logMessages) {
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            try {
                Date date = df2.parse(message.getTimestamp().split(Pattern.quote("000+"))[0]);
                DBManager.addNewMessage(message, date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private static void deleteMessage(long id) {
        if (!SettingsManager.logMessages) return;
        DBManager.deleteMessage(id);
        Cache.setDeleted(id);
    }

    private static boolean isBlacklisted(String mediaUrl) {
        String[] mediaSplits =  mediaUrl.split("/");
        String media = mediaSplits[mediaSplits.length - 1];
        for (String extension : SettingsManager.listExtensions) {
            try {
                if (media.toLowerCase().endsWith(extension.toLowerCase()))
                    return true;
            } catch (Exception ignored) {}
        }
        for (String name : SettingsManager.listName) {
            try {
                if (media.split(Pattern.quote("."))[0].equalsIgnoreCase(name))
                    return true;
            } catch (Exception ignored) {}
        }
        for (String reg : SettingsManager.listRegex) {
            try {
                if (media.matches(reg))
                    return true;
            } catch (Exception ignored) {}
        }
        return false;
    }
}
