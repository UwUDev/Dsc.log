package me.uwu.dsc.log.struct;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Mention {
    private final String username;
    @SerializedName("public_flags")
    private final long publicFlags;
    private final long id;
    private final String discriminator;
    private final String avatar;

    public String getTag() {
        return username + "#" + discriminator;
    }
}
