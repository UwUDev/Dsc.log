package me.uwu.dsc.log;

import me.uwu.dsc.log.config.ConfigManager;
import me.uwu.dsc.log.network.DiscordWSS;
import me.uwu.dsc.log.setting.SettingsManager;
import me.uwu.dsc.log.utils.ConsoleUtils;
import me.uwu.dsc.log.utils.DiscordUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Main {
    public static boolean debug = false;
    public static void main(String[] args) throws ClassNotFoundException {
        try {
            //noinspection ResultOfMethodCallIgnored
            new File("dump/").mkdirs();
            SettingsManager.genSettingsFiles();
            SettingsManager.loadSettingsFile();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Missing permissions :c");
            System.exit(420);
        }

        Class.forName("org.sqlite.JDBC");

        Scanner sc = new Scanner(System.in);
        System.out.print("Token: ");
        String token = sc.nextLine();
        System.out.print("\rLogging in...");

        if (!DiscordUtils.isValidToken(token)){
            System.out.println("Invalid token !");
            System.exit(69);
        }

        sc = new Scanner(System.in);

        String line = "";
        while (!line.equals("0")) {
            line = sc.nextLine();
            if(line.equals("debug"))
                debug = !debug;
            if(line.equals("1"))
                SettingsManager.logMessages = !SettingsManager.logMessages;
            if(line.equals("2"))
                SettingsManager.logFiles = !SettingsManager.logFiles;


            if(line.equals("s"))
                SettingsManager.openSettings();
            if(line.equals("c"))
                ConfigManager.openConfigMenu();

            ConsoleUtils.drawMainMenu();
        }
        ConsoleUtils.clearConsole();
        ConsoleUtils.drawLogo();
        newConnection(token);
    }

    public static void newConnection(String token){
        try {
            new DiscordWSS(token).connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
