package me.uwu.dsc.log.struct;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.uwu.dsc.log.event.Event;
import me.uwu.dsc.log.event.EventType;

@Getter
@AllArgsConstructor
public class ReceivedEventPayload {
    @SerializedName("t")
    private final String eventType;

    @SerializedName("s")
    private final int sessionStatus;

    @SerializedName("op")
    private final int opCode;

    @SerializedName("d")
    private final JsonObject data;

    public Event getEvent() {
        EventType evType = EventType.UNKNOWN;
        if (opCode == 1 || opCode == 11)
            evType = EventType.HEARTBEAT;
        else for (EventType eventType : EventType.values())
            if (eventType.getIdentifier().equals(this.getEventType())) {
                evType = eventType;
                break;
            }
        EventType finalEvType = evType;

        return new Event() {
            @Override
            public EventType getEventType() {
                return finalEvType;
            }

            @Override
            public int getSessionStatus() {
                return sessionStatus;
            }

            @Override
            public int getOpCode() {
                return opCode;
            }

            @Override
            public JsonObject getData() {
                return data;
            }
        };
    }
}
