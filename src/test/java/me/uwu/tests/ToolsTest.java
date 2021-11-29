package me.uwu.tests;

import com.google.gson.Gson;
import me.uwu.dsc.log.art.Ascii;
import me.uwu.dsc.log.struct.Message;
import me.uwu.dsc.log.tools.FilesTool;
import me.uwu.dsc.log.tools.Tools;

import java.io.File;

public class ToolsTest {
    public static void main(String[] args) {
        String media = "914630438172131368.png";
        Message message = FilesTool.getFileMessage(media);
        System.out.println(new Gson().toJson(message));
        System.out.println(message.getUrl());
        File file = new File("dump/png/" + media);
        System.out.println(Ascii.getFloppy(file, System.currentTimeMillis()));
        Tools.getFileInfosMenu();
    }
}
