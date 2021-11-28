package me.uwu.dsc.log.config;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Config {
    public Config(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    private final String id;
    private String name;
    private String description;
    private final List<String> listExtensions = new ArrayList<>(), listName = new ArrayList<>(), listRegex = new ArrayList<>();
    private boolean whitelistMode, logMessages, logFiles, logDMs, logGroups, logGuilds;
}
