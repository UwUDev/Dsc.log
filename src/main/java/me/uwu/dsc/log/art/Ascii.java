package me.uwu.dsc.log.art;

import me.uwu.dsc.log.tools.FilesTool;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ascii {
    public static String getFloppy(File file, long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy,,HH:mm:ss");
        Date resultdate = new Date(date);
        String dateString[] = sdf.format(resultdate).split(",,");

        StringBuilder floppy = new StringBuilder();
        String size = FilesTool.getSize(file.length());

        StringBuilder sizeLine = new StringBuilder();
        sizeLine.append("|  |_").append(size);
        for (int i = 0; i < 14-size.length(); i++)
            sizeLine.append("_");
        sizeLine.append("|  |\n");

        StringBuilder dateLine = new StringBuilder();
        dateLine.append("|  |_").append(dateString[0]);
        for (int i = 0; i < 14-dateString[0].length(); i++)
            dateLine.append("_");
        dateLine.append("|  |\n");

        StringBuilder hourLine = new StringBuilder();
        hourLine.append("|__|_").append(dateString[1]);
        for (int i = 0; i < 14-dateString[1].length(); i++)
            hourLine.append("_");
        hourLine.append("|__|\n");

        floppy.append(" \u001B[0m___,___,_______,____\n" +
                "|  :::|///./||'||    \\\n" +
                "|  :::|//.//|| ||     |\n" +
                "|  :::|/.///|!!!|     |\n" +
                "|   _______________   |\n" +
                "|  |:::::::::::::::|  |\n" +
                "|  |_______________|  |\n" +
                "|  |_______________|  |\n" +
                sizeLine +
                dateLine +
                hourLine +
                "|__|_______________|__|");
        return floppy.toString();
    }
}
