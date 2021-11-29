package me.uwu.dsc.log.utils;

import lombok.SneakyThrows;
import me.uwu.dsc.log.Main;
import me.uwu.dsc.log.cache.Cache;
import me.uwu.dsc.log.config.ConfigManager;
import me.uwu.dsc.log.setting.SettingsManager;
import me.uwu.dsc.log.stats.Stats;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class ConsoleUtils {
    private static final String os = System.getProperty("os.name");
    public static String word1 = "blacklist";
    public static String word2 = "Blacklisted";

    public static void clearConsole() {
        //intellij console moment
        //for (int i = 0; i < 50; ++i) System.out.println();
        try {
            if (os.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else Runtime.getRuntime().exec("clear").waitFor();
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static void drawLogo() {
        System.out.println(
                "\u001B[0m /#######                          /##                    \n" +
                        "| ##__  ##                        | ##                    \n" +
                        "| ##  \\ ##  /#######  /#######    | ##  /######   /###### \n" +
                        "| ##  | ## /##_____/ /##_____/    | ## /##__  ## /##__  ##\n" +
                        "| ##  | ##|  ###### | ##          | ##| ##  \\ ##| ##  \\ ##\n" +
                        "| ##  | ## \\____  ##| ##          | ##| ##  | ##| ##  | ##\n" +
                        "| #######/ /#######/|  ####### /##| ##|  ######/|  #######\n" +
                        "|_______/ |_______/  \\_______/|__/|__/ \\______/  \\____  ##\n" +
                        "                                                 /##  \\ ##\n" +
                        "                                                |  ######/\n" +
                        "                                                 \\______/ \n" +
                        "---------------------------------------------------------"
        );
    }

    public static void drawMainMenu() {
        clearConsole();
        drawLogo();
        if (Main.debug)
            System.out.println("\u001B[31m/\u001B[33m!\u001B[31m\\ DEBUGGER IS ENABLED \u001B[31m/\u001B[33m!\u001B[31m\\");
        System.out.println("\u001B[34m0) Start\n");
        if (SettingsManager.logMessages)
            System.out.println("\u001B[32m1) Log messages");
        else System.out.println("\u001B[31m1) Log messages");

        if (SettingsManager.logFiles)
            System.out.println("\u001B[32m2) Log all files");
        else System.out.println("\u001B[31m2) Log all files");

        System.out.println("\n\u001B[0mt) Tools");
        System.out.println("\u001B[0ms) Settings");
        System.out.println("\u001B[0mc) Configs");
    }

    public static void drawSettingsMenu() {
        clearConsole();
        drawLogo();
        System.out.println("\u001B[34mb) Back\n");
        System.out.println("\u001B[0ms) Switch blacklist/whitelist mode      (" + word1 + ")");
        System.out.println("\u001B[0m0) Edit " + word1 + " files by extensions   (" + SettingsManager.listExtensions.size() + ")");
        System.out.println("\u001B[0m1) Edit " + word1 + " files by names        (" + SettingsManager.listName.size() + ")");
        System.out.println("\u001B[0m2) Edit " + word1 + " files by regex        (" + SettingsManager.listRegex.size() + ")");
    }

    public static void drawToolsMenu() {
        clearConsole();
        drawLogo();
        System.out.println("\u001B[34mb) Back\n");
        System.out.println("\u001B[0m0) Get dumped file infos");
    }

    public static void drawConfigsMenu() {
        clearConsole();
        drawLogo();
        System.out.println("You have " + ConfigManager.configs.size() + " configs:");
        ConfigManager.configs.forEach(c -> System.out.println("\u001B[35mName: \u001B[36m" +
                c.getName() +
                " \t\u001B[35mDescription: \u001B[36m"
                + c.getDescription() +
                " \t\u001B[35mID: \u001B[33m"
                + c.getId()
                + "\u001B[0m"));

        System.out.println("\n\u001B[34mb) Back\n");
        System.out.println("\u001B[0mn) Create new config");
        System.out.println("\u001B[0ml) Load a config. ex: (l ffffffff-ffff-ffff-ffff-ffffffffffff)");
        System.out.println("\u001B[0ms) Save current setting into a config. ex: (s ffffffff-ffff-ffff-ffff-ffffffffffff)");
        System.out.println("\u001B[0md) Delete a config. ex: (d ffffffff-ffff-ffff-ffff-ffffffffffff)");
        System.out.println("\u001B[0mr) Rename a config. ex: (r ffffffff-ffff-ffff-ffff-ffffffffffff cool config owo)");
        System.out.println("\u001B[0me) Edit config description a config. ex: (d ffffffff-ffff-ffff-ffff-ffffffffffff new cool description)");
    }

    public static void drawExtensionsMenu() {
        clearConsole();
        drawLogo();
        System.out.println(word2 + " extensions:");
        SettingsManager.listExtensions.forEach(System.out::println);

        System.out.println("\n\u001B[34mb)         Back");
        System.out.println("\u001B[34ma)         Add extension to " + word1 + " ex: (a .mp4)");
        System.out.println("\u001B[34mr)         Remove extension to " + word1 + " ex: (r .mp4)");
        System.out.println("\u001B[34mclear all) Remove all extension in the " + word1);
    }

    public static void drawNameMenu() {
        clearConsole();
        drawLogo();
        System.out.println(word2 + " file names:");
        SettingsManager.listName.forEach(name -> System.out.println(name + ".*"));

        System.out.println("\n\u001B[34mb)         Back");
        if (SettingsManager.whiteListMode)
            System.out.println("\u001B[34ma)         Add file name to " + word1 + " ex: (a card) will keep mee6 wellcome files named card.png and card.gif");
        else System.out.println("\u001B[34ma)         Add file name to " + word1 + " ex: (a card) will ignore mee6 wellcome files named card.png and card.gif");
        System.out.println("\u001B[34mr)         Remove file name to " + word1 + " ex: (r card)");
        System.out.println("\u001B[34mclear all) Remove all file names in the " + word1);
    }

    public static void drawRegexMenu() {
        clearConsole();
        drawLogo();
        System.out.println(word2 + " file regexes:");
        SettingsManager.listRegex.forEach(System.out::println);

        System.out.println("\n\u001B[34mb)         Back");
        if (SettingsManager.whiteListMode) System.out.println("\u001B[34ma)         Add file regex to " + word1 + " ex: (a ^Screenshot.*jpg$) will only keep screenshots");
        else System.out.println("\u001B[34ma)         Add file regex to " + word1 + " ex: (a ^Screenshot.*jpg$) will ignore screenshots");
        System.out.println("\u001B[34mr)         Remove file regex to " + word1 + " ex: (r ^Screenshot.*jpg$)");
        System.out.println("\u001B[34mclear all) Remove all file regex in the " + word1);
    }

    @SneakyThrows
    public static void refresh() {
        clearConsole();
        OutputStream out = new BufferedOutputStream( System.out );
        out.write(Stats.graphic.getBytes(StandardCharsets.UTF_8));
        out.write(Cache.getRawMessages().getBytes(StandardCharsets.UTF_8));
        out.flush();
    }
}
