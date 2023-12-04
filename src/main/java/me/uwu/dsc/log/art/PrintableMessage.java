package me.uwu.dsc.log.art;

import lombok.Data;

public @Data class PrintableMessage {
    private final long id;
    private final String author;
    private final String content;
    private boolean deleted = false;

    public String toPrintable() {
        return (deleted ? "\u001B[31m" : "\u001B[32m") + author + "\u001B[0m: " + content;
    }
}
