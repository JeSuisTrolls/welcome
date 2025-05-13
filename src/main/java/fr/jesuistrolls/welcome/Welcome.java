package fr.jesuistrolls.welcome;

import fr.jesuistrolls.welcome.configurations.Messages;
import fr.jesuistrolls.welcome.hooks.Metrics;
import fr.jesuistrolls.welcome.hooks.VaultHook;
import fr.jesuistrolls.welcome.configurations.Settings;
import fr.jesuistrolls.welcome.listeners.PlayerJoinListeners;
import fr.jesuistrolls.welcome.managers.CommandsManager;
import fr.jesuistrolls.welcome.utils.UpdateChecker;
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
    private static CommandsManager commandsManager;
    private static BukkitAudiences audience;
    private static UpdateChecker updateChecker;

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
        Metrics metrics = new Metrics(this, 24494);
        if(!VaultHook.setupPermissions()) return;

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListeners(playerCache), this);

        commandsManager = new CommandsManager(playerCache, this);
        getLogger().info("Enabled Welcome!");

        updateChecker = new UpdateChecker(this, 119888);
        updateChecker.checkForUpdate();
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
