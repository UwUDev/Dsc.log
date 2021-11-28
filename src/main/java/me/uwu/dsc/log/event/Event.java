package me.uwu.dsc.log.event;

import com.google.gson.JsonObject;

public interface Event {
    EventType getEventType();
    int getSessionStatus();
    int getOpCode();
    JsonObject getData();
}
