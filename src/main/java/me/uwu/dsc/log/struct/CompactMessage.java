package me.uwu.dsc.log.struct;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompactMessage {
    private final String content, username;
}
