package me.uwu.dsc.log.struct;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Message {
    private final long id;
    private final boolean tts;
    private final long timestamp, referencedMessage, nonce;
    private final MessageReference messageReference;
    private final Mention[] mentions;
    private final long[] mentionRoles;
    private final boolean mentionEveryone;
    private final int type;
    private final long flags;
    private final JsonObject[] embeds;
    private final long editedTimestamp;
    private final String content;
    private final JsonObject[] components;
    private final long channelId;
    private final Author author;
    private final Attachment[] attachments;
    private final boolean deleted;
    private final String oldContents;
    private final String oldAttachments;
    private final long deletedTimestamp;

    public String getUrl() {
        return "https://discord.com/channels/@me/" + channelId + "/" + id;
    }
}
