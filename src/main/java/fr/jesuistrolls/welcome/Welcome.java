package fr.jesuistrolls.welcome;

import fr.jesuistrolls.welcome.commands.WelcomeCommand;
import fr.jesuistrolls.welcome.configuration.Messages;
import fr.jesuistrolls.welcome.configuration.Rewards;
import fr.jesuistrolls.welcome.listeners.PlayerJoinListeners;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;

import java.util.*;


public final class Welcome extends JavaPlugin {

    private static Welcome instance;
    private static Messages messages;

    private static BukkitAudiences audience;

    private Map<UUID, List<UUID>> playerCache;
    private List<String> commandRewards;

    @Override
    public void onEnable() {

        instance = this;
        saveDefaultConfig();
        loadConfig();
        audience = BukkitAudiences.create((Plugin)this);

        playerCache = new HashMap<>();
        commandRewards = Rewards.commands;

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListeners(playerCache, messages), this);


        getCommand("bvn").setExecutor(new WelcomeCommand(playerCache, commandRewards, messages));
    }

    public void loadConfig() {
        reloadConfig();
        FileConfiguration config = getConfig();
        Messages.loadMessages(config);
        Rewards.loadRewards(config);
    }

    public static BukkitAudiences getAudience() {
        return audience;
    }

}
