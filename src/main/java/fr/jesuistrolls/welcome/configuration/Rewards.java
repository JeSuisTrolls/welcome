package fr.jesuistrolls.welcome.configuration;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Rewards {
    public static boolean enabled;
    public static List<String> commands;

    public static void loadRewards(FileConfiguration config) {
        enabled = config.getBoolean("rewards.enabled");
        commands = config.getStringList("rewards.commands");
    }
}
