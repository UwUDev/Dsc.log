package me.uwu.dsc.log.event.impl;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.uwu.dsc.log.struct.Attachment;
import me.uwu.dsc.log.struct.Author;
import me.uwu.dsc.log.struct.Mention;
import me.uwu.dsc.log.struct.MessageReference;

@Getter
@AllArgsConstructor
public class MessageReceived {
    private final int type;
    private final boolean tts;
    private final String timestamp;
    @SerializedName("referenced_message")
    private final MessageReceived referencedMessage;
    private final boolean pinned;
    private final long nonce;
    @SerializedName("message_reference")
    private final MessageReference messageReference;
    private final Mention[] mentions;
    @SerializedName("mention_roles")
    private final long[] mentionRoles;
    @SerializedName("mention_everyone")
    private final boolean mentionEveryone;
    private final long id;
    private final long flags;
    private final JsonObject[] embeds;
    @SerializedName("edited_timestamp")
    private final String editedTimestamp;
    private final String content;
    private final JsonObject[] components;
    @SerializedName("channel_id")
    private final long channelId;
    private final Author author;
    private final Attachment[] attachments;
}
