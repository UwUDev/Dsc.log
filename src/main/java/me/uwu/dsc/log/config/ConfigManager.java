package me.uwu.dsc.log.config;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import me.uwu.dsc.log.setting.SettingsManager;
import me.uwu.dsc.log.utils.ConsoleUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class ConfigManager {
    public final static List<Config> configs = new ArrayList<>();
    private static final Gson gson = new Gson();
    private static final File configDirectory = new File("configs/");
    
    public static void openConfigMenu() {
        updateConfigs();
        ConsoleUtils.drawConfigsMenu();
        Scanner sc = new Scanner(System.in);
        String line = "";
        while (!line.equals("b")) {
            line = sc.nextLine();
            if(line.equals("n"))
                newConfig();
            if(line.startsWith("d ")) {
                Config config = getConfigsById(line.split(" ")[1]);
                if (config != null) {
                    configs.remove(config);
                    new File("configs/" + config.getId() + ".json").delete();
                } else {
                    System.err.println("Unable to find config " + line.split(" ")[1] + " double check name.\n" +
                            "if you just manually added the config type 're' in the console to re-import configs.\n" +
                            "Press enter to continue...");
                    sc.nextLine();
                }
            }
            if(line.startsWith("l ")) {
                Config config = getConfigsById(line.split(" ")[1]);
                if (config != null) {
                    SettingsManager.listExtensions.clear();
                    SettingsManager.listExtensions.addAll(config.getListExtensions());
                    SettingsManager.listName.clear();
                    SettingsManager.listName.addAll(config.getListName());
                    SettingsManager.listRegex.clear();
                    SettingsManager.listRegex.addAll(config.getListRegex());
                    SettingsManager.whiteListMode = config.isWhitelistMode();
                    SettingsManager.logMessages = config.isLogMessages();
                    SettingsManager.logFiles = config.isLogFiles();
                    SettingsManager.logDMs = config.isLogDMs();
                    SettingsManager.logGroups = config.isLogGroups();
                    SettingsManager.logGuilds = config.isLogGuilds();
                    SettingsManager.ignoreBots = config.isIgnoreBots();
                    saveConfig(config);
                } else {
                    System.err.println("Unable to find config " + line.split(" ")[1] + " double check name.\n" +
                            "if you just manually added the config type 're' in the console to re-import configs.\n" +
                            "Press enter to continue...");
                    sc.nextLine();
                }
            }
            if(line.startsWith("s ")) {
                Config config = getConfigsById(line.split(" ")[1]);
                if (config != null) {
                    config.getListExtensions().clear();
                    config.getListExtensions().addAll(SettingsManager.listExtensions);
                    config.getListName().clear();
                    config.getListName().addAll(SettingsManager.listName);
                    config.getListRegex().clear();
                    config.getListRegex().addAll(SettingsManager.listRegex);
                    config.setWhitelistMode(SettingsManager.whiteListMode);
                    config.setLogMessages(SettingsManager.logMessages);
                    config.setLogFiles(SettingsManager.logFiles);
                    config.setLogDMs(SettingsManager.logDMs);
                    config.setLogGroups(SettingsManager.logGroups);
                    config.setLogGuilds(SettingsManager.logGuilds);
                    config.setIgnoreBots(SettingsManager.ignoreBots);
                    saveConfig(config);
                } else {
                    System.err.println("Unable to find config " + line.split(" ")[1] + " double check name.\n" +
                            "if you just manually added the config type 're' in the console to re-import configs.\n" +
                            "Press enter to continue...");
                    sc.nextLine();
                }
            }
            if(line.startsWith("e ")) {
                Config config = getConfigsById(line.split(" ")[1]);
                if (config != null) {
                    String desc = line.replace(config.getId().toLowerCase(), "")
                            .replace(config.getId().toLowerCase(Locale.ROOT), "")
                            .replace("e  ", "");
                    config.setDescription(desc);
                    saveConfig(config);
                } else {
                    System.err.println("Unable to find config " + line.split(" ")[1] + " double check name.\n" +
                            "if you just manually added the config type 're' in the console to re-import configs.\n" +
                            "Press enter to continue...");
                    sc.nextLine();
                }
            }
            if(line.startsWith("r ")) {
                Config config = getConfigsById(line.split(" ")[1]);
                if (config != null) {
                    String name = line.replace(config.getId().toLowerCase(), "")
                            .replace(config.getId().toLowerCase(Locale.ROOT), "")
                            .replace("r  ", "");
                    config.setName(name);
                    saveConfig(config);
                } else {
                    System.err.println("Unable to find config " + line.split(" ")[1] + " double check name.\n" +
                            "if you just manually added the config type 're' in the console to re-import configs.\n" +
                            "Press enter to continue...");
                    sc.nextLine();
                }
            }
            updateConfigs();
            ConsoleUtils.drawConfigsMenu();
        }
    }

    private static void newConfig() {
        Scanner sc = new Scanner(System.in);
        ConsoleUtils.clearConsole();
        ConsoleUtils.drawLogo();
        System.out.print("Config name: ");
        String name = sc.nextLine();
        System.out.print("Config description: ");
        String desc = sc.nextLine();
        Config config = new Config(UUID.randomUUID().toString(), name, desc);
        config.getListExtensions().addAll(SettingsManager.listExtensions);
        config.getListName().addAll(SettingsManager.listName);
        config.getListRegex().addAll(SettingsManager.listRegex);
        config.setWhitelistMode(SettingsManager.whiteListMode);
        config.setLogMessages(SettingsManager.logMessages);
        config.setLogFiles(SettingsManager.logFiles);
        config.setLogDMs(SettingsManager.logDMs);
        config.setLogGroups(SettingsManager.logGroups);
        config.setLogGuilds(SettingsManager.logGuilds);
        config.setIgnoreBots(SettingsManager.ignoreBots);
        configs.add(config);
        saveConfig(config);
    }

    @SneakyThrows
    private static void saveConfig(Config config) {
        File configFile = new File("configs/" + config.getId() + ".json");
        if (!configFile.exists()) configFile.createNewFile();
        try {
            FileWriter myWriter = new FileWriter(configFile);
            myWriter.write(gson.toJson(config));
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateConfigs() {
        configs.clear();
        for (String s : Objects.requireNonNull(configDirectory.list()))
            try { configs.add(gson.fromJson(Files.readAllLines(Paths.get("configs/" + s)).get(0), Config.class));
            } catch (Exception ignored) {}
    }

    public static Config getConfigsById(String id) {
        for (Config config : configs)
            if(config.getId().equalsIgnoreCase(id)) return config;
        return null;
    }
}
