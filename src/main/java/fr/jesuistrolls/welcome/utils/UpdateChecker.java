package fr.jesuistrolls.welcome.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.jesuistrolls.welcome.configurations.Messages;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker {

    private final JavaPlugin plugin;
    private final int resourceId;

    public UpdateChecker(JavaPlugin plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void checkForUpdate() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL("https://api.spiget.org/v2/resources/" + resourceId + "/versions/latest").openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                connection.connect();

                if (connection.getResponseCode() != 200) {
                    return;
                }

                JsonObject json = JsonParser.parseReader(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
                String latestVersion = json.get("name").getAsString();
                String currentVersion = plugin.getDescription().getVersion();
                Bukkit.getLogger().info("[Welcome] Current version: " + currentVersion + ", Latest version: " + latestVersion);
                if (!currentVersion.equalsIgnoreCase(latestVersion) && Messages.updateMessage) {
                    Bukkit.getLogger().info( "[Welcome] A new version of " + plugin.getName() + " is available: " + latestVersion + " (current: " + currentVersion + ")");
                    Bukkit.getLogger().info( "[Welcome] Download it here: https://www.spigotmc.org/resources/" + resourceId);
                }
            } catch (Exception e) {
                Bukkit.getLogger().info("Error while checking for updates: " + e);
            }
        });
    }
}
