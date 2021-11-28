package me.uwu.dsc.log.event;

import com.google.gson.Gson;
import me.uwu.dsc.log.logs.Logger;
import me.uwu.dsc.log.struct.ReceivedEventPayload;

public class EventManager {
    public static void onMessageReceived(String message) {
        Event event = new Gson().fromJson(message, ReceivedEventPayload.class).getEvent();
        Logger.logEvent(event);
    }
}
