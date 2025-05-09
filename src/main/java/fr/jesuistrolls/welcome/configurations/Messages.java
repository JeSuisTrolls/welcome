package fr.jesuistrolls.welcome.configurations;

import fr.jesuistrolls.welcome.Welcome;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public static String applyPlaceholders(Player target, String message) {
        return message != null ? PlaceholderAPI.setPlaceholders(target, message) : null;
    }

    public static List<String> applyPlaceholders(Player target, List<String> messages) {
        return messages.stream()
                .map(message -> applyPlaceholders(target, message))
                .collect(Collectors.toList());
    }

    public static void send(CommandSender sender, String message, Player placeholderTarget) {
        if (message == null) return;
        message = applyPlaceholders(placeholderTarget, message);
        Welcome.getAudience().sender(sender).sendMessage(MiniMessage.miniMessage().deserialize(message));
    }

    public static void send(Player player, String message) {
        send(player, message, player);
    }

    public static void send(CommandSender sender, String message) {
        if (message == null) return;
        Welcome.getAudience().sender(sender).sendMessage(MiniMessage.miniMessage().deserialize(message));
    }

    public static void send(UUID uuid, String message) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null && player.isOnline()) {
            send(player, message, player);
        }
    }
}
