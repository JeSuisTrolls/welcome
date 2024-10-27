package fr.acrobatic.welcome.configurations;

import fr.acrobatic.welcome.Welcome;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class Messages {

    public static List<String> welcomeFormats;
    public static String noPlayer;
    public static String newPlayer;
    public static String invalidPlayer;
    public static String alreadySend;
    public static String welcomeSend;
    public static String noPermission;
    public static String reload;

    public static void loadMessages(FileConfiguration config) {

        welcomeFormats = config.getStringList("messages.welcome-formats");
        noPlayer = config.getString("messages.no-player");
        noPermission = config.getString("messages.no-permission");
        newPlayer = config.getString("messages.new-player");
        invalidPlayer = config.getString("messages.invalid-player");
        alreadySend = config.getString("messages.already-send");
        welcomeSend = config.getString("messages.welcome-send");
        reload = config.getString("messages.reload");
    }

    private static String parsePlaceholders(Player player, String message) {
        return PlaceholderAPI.setPlaceholders(player, message);
    }

    public static void send(CommandSender sender, String message) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            message = parsePlaceholders(player, message);
            Welcome.getAudience().player(player).sendMessage(MiniMessage.miniMessage().deserialize(message));
        } else {
            Welcome.getAudience().console().sendMessage(MiniMessage.miniMessage().deserialize(message));
        }
    }

    public static void send(UUID uuid, String message) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null && player.isOnline()) {
            send(player, message);
        }
    }
}
