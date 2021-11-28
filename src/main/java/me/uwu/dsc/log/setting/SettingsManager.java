package me.uwu.dsc.log.setting;

import com.google.gson.Gson;
import me.uwu.dsc.log.utils.ConsoleUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SettingsManager {
    public static boolean whiteListMode = false;
    public static final List<String> listExtensions = new ArrayList<>();
    public static final List<String> listName = new ArrayList<>();
    public static final List<String> listRegex = new ArrayList<>();
    private static final File settingsFile = new File("settings/settings.dat");
    private static final File listExtensionsFile = new File("settings/iExt.json");
    private static final File listNameFile = new File("settings/iName.json");
    private static final File listRegexFile = new File("settings/iReg.json");
    private static final Gson gson = new Gson();

    public static boolean
            logFiles = true,
            logMessages = true,
            logDMs = true,
            logGroups = true,
            logGuilds = true;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void genSettingsFiles() throws IOException {
        new File("settings/").mkdirs();
        new File("configs/").mkdirs();
        if (!settingsFile.exists()) {
            settingsFile.createNewFile();
            saveSettings();
        }
        if (!listExtensionsFile.exists()) {
            listExtensionsFile.createNewFile();
            clearSettingsFile(listExtensionsFile);
        }
        if (!listNameFile.exists()) {
            listNameFile.createNewFile();
            clearSettingsFile(listNameFile);
        }
        if (!listRegexFile.exists()) {
            listRegexFile.createNewFile();
            clearSettingsFile(listRegexFile);
        }
    }

    public static void openSettings() {
        ConsoleUtils.drawSettingsMenu();
        Scanner sc = new Scanner(System.in);

        String line = "";
        while (!line.equals("b")) {
            line = sc.nextLine();
            if(line.equals("s"))
                toggleWhitelist(true);
            if(line.equals("0"))
                openExtensionsEditor();
            if(line.equals("1"))
                openNameEditor();
            if(line.equals("2"))
                openRegexEditor();
            ConsoleUtils.drawSettingsMenu();
        }
    }

    private static void toggleWhitelist(boolean save) {
        whiteListMode =! whiteListMode;
        if (whiteListMode) {
            ConsoleUtils.word1 = "whitelist";
            ConsoleUtils.word2 = "Whitelisted";
        } else {
            ConsoleUtils.word1 = "blacklist";
            ConsoleUtils.word2 = "Blacklisted";
        }
        if (save) saveSettings();
    }

    private static void openExtensionsEditor() {
        ConsoleUtils.drawExtensionsMenu();
        Scanner sc = new Scanner(System.in);

        String line = "";
        while (!line.equals("b")) {
            line = sc.nextLine();
            if(line.startsWith("a ")) {
                String extension = line.replaceFirst("a ", "");
                if (!extension.startsWith(".")) extension = "." + extension;
                listExtensions.add(extension);
            }
            if(line.startsWith("r ")) {
                String extension = line.replaceFirst("r ", "");
                if (!extension.startsWith(".")) extension = "." + extension;
                listExtensions.remove(extension);
            }
            if(line.equalsIgnoreCase("clear all"))
                listExtensions.clear();
            ConsoleUtils.drawExtensionsMenu();
            saveListToFile(listExtensions, listExtensionsFile);
        }
    }

    private static void openNameEditor() {
        ConsoleUtils.drawNameMenu();
        Scanner sc = new Scanner(System.in);

        String line = "";
        while (!line.equals("b")) {
            line = sc.nextLine();
            if(line.startsWith("a "))
                listName.add(line.replaceFirst("a ", "").replace(".", ""));
            if(line.startsWith("r "))
                listName.remove(line.replaceFirst("r ", ""));
            if(line.equalsIgnoreCase("clear all"))
                listName.clear();
            ConsoleUtils.drawNameMenu();
            saveListToFile(listName, listNameFile);
        }
    }


    private static void openRegexEditor() {
        ConsoleUtils.drawRegexMenu();
        Scanner sc = new Scanner(System.in);

        String line = "";
        while (!line.equals("b")) {
            line = sc.nextLine();
            if(line.startsWith("a "))
                listRegex.add(line.replaceFirst("a ", ""));
            if(line.startsWith("r "))
                listRegex.remove(line.replaceFirst("r ", ""));
            if(line.equalsIgnoreCase("clear all"))
                listRegex.clear();
            ConsoleUtils.drawRegexMenu();
            saveListToFile(listRegex, listRegexFile);
        }
    }

    private static void clearSettingsFile(File file) {
        try {
            FileWriter myWriter = new FileWriter(file);
            myWriter.write("[]");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadSettingsFile() throws IOException {
        listExtensions.clear();
        listName.clear();
        listRegex.clear();
        listExtensions.addAll(Arrays.asList(gson.fromJson(Files.readAllLines(listExtensionsFile.toPath()).get(0), String[].class)));
        listName.addAll(Arrays.asList(gson.fromJson(Files.readAllLines(listNameFile.toPath()).get(0), String[].class)));
        listRegex.addAll(Arrays.asList(gson.fromJson(Files.readAllLines(listRegexFile.toPath()).get(0), String[].class)));
        if (Files.readAllLines(settingsFile.toPath()).get(0).equals("true")) toggleWhitelist(false);
    }

    private static void saveListToFile(List<String> list, File file){
        try {
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(gson.toJson(list));
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveSettings() {
        try {
            FileWriter myWriter = new FileWriter(settingsFile);
            myWriter.write(String.valueOf(whiteListMode));
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
