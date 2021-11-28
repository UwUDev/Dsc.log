package me.uwu.dsc.log.struct;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Author {
    private final String username;
    @SerializedName("public_flags")
    private final long publicFlags;
    private final long id;
    private final String discriminator;
    private final String avatar;

    public String blankUsername() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < username.length(); i++)
            sb.append(" ");
        return sb.toString();
    }
}
