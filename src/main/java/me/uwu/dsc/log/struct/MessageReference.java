package me.uwu.dsc.log.struct;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageReference {
    @SerializedName("message_id")
    private final long messageId;
    @SerializedName("channel_id")
    private final long channelId;
}
