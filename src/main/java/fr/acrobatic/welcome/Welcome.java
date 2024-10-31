package fr.acrobatic.welcome;

import fr.acrobatic.welcome.commands.WelcomeCommand;
import fr.acrobatic.welcome.configurations.Messages;
import fr.acrobatic.welcome.hooks.VaultHook;
import fr.acrobatic.welcome.configurations.Settings;
import fr.acrobatic.welcome.listeners.PlayerJoinListeners;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class Welcome extends JavaPlugin {

    private static Welcome instance;
    private static BukkitAudiences audience;

    private Map<UUID, List<UUID>> playerCache;

    @Override
    public void onEnable() {

        getLogger().info(">> Initializing config...");
        instance = this;
        saveDefaultConfig();
        loadConfigurations();

        audience = BukkitAudiences.create(this);
        playerCache = new HashMap<>();

        getLogger().info(">> Intializing hooks...");
        if(!VaultHook.setupPermissions()) return;

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListeners(playerCache), this);

        getCommand("welcome").setExecutor(new WelcomeCommand(playerCache, this));
        getLogger().info("Enabled Welcome!");
    }



    private void saveDefaultLanguageFile() {
        File langFile = new File(getDataFolder(), "lang.yml");
        if (!langFile.exists()) {
            saveResource("lang.yml", false);
        }
    }

    public void loadConfigurations() {
        reloadConfig();
        FileConfiguration config = getConfig();
        Settings.loadSettings(config);
        loadLanguageFile();
    }

    private void loadLanguageFile() {
        File langFile = new File(getDataFolder(), "lang.yml");
        if (!langFile.exists()) {
            saveDefaultLanguageFile();
        }
        FileConfiguration langConfig = YamlConfiguration.loadConfiguration(langFile);
        Messages.loadMessages(langConfig);
    }

    public void reloadAllConfigurations() {
        loadConfigurations();
    }

    public static Welcome getInstance() {
        return instance;
    }

    public static BukkitAudiences getAudience() {
        return audience;
    }

    public List<String> getWelcomeFormats() {
        return Messages.welcomeFormats;
    }

    public List<String> getCommandRewards() {
        return Settings.commandsRewards;
    }
}
