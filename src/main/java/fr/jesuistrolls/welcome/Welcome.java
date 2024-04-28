package fr.jesuistrolls.welcome;

import fr.jesuistrolls.welcome.commands.WelcomeCommand;
import fr.jesuistrolls.welcome.configuration.Messages;
import fr.jesuistrolls.welcome.configuration.Rewards;
import fr.jesuistrolls.welcome.listeners.PlayerJoinListeners;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public final class Welcome extends JavaPlugin {

    private static Welcome instance;
    private static Messages messages;

    private static BukkitAudiences audience;

    private Map<UUID, List<UUID>> playerCache;
    private List<String> commandRewards;

    private Plugin plugin;

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


        getCommand("bvn").setExecutor(new WelcomeCommand(playerCache, commandRewards, messages, this));
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
