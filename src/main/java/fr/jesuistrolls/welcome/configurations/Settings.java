package fr.jesuistrolls.welcome.configurations;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Settings {

    public static List<String> welcomeCommands;
    public static List<String> commandsRewards;
    public static String welcomeMessageType;
    public static int welcomeDelay;
    public static boolean enabled;

    public static void loadSettings(FileConfiguration config) {
        welcomeCommands = config.getStringList("settings.welcome-commands");
        welcomeDelay = config.getInt("settings.welcome-delay") * 20;
        welcomeMessageType = config.getString("settings.welcome-message-type");
        commandsRewards = config.getStringList("settings.rewards.commands");
        enabled = config.getBoolean("settings.rewards.enabled");
    }
}