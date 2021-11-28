package me.uwu.dsc.log.struct;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Attachment {
    private final long id;
    private final String filename;
    private final String description;
    @SerializedName("content_type")
    private final String contentType;
    private final long size;
    private final String url;
    private final int height, width;
    private final boolean ephemeral;
}
