package fr.jesuistrolls.welcome.configuration;

import fr.jesuistrolls.welcome.Welcome;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Messages {
    public static String noPlayer;
    public static String newplayer;
    public static String invalidPlayer;
    public static String alreadySend;
    public static String welcomeSend;
    public static String welcomeFormat;

    public static String noPermission;
    public static String reload;

    public static void loadMessages(FileConfiguration config) {
        noPlayer = config.getString("messages.no-player");
        noPermission = config.getString("messages.no-permission");
        newplayer = config.getString("messages.new-player");
        invalidPlayer = config.getString("messages.invalid-player");
        alreadySend = config.getString("messages.already-send");
        welcomeSend = config.getString("messages.welcome-send");
        welcomeFormat = config.getString("messages.welcome-format");
        reload = config.getString("messages.reload");


    }

    public static void send(UUID uuid, String message) {
        Welcome.getAudience().player(uuid).sendMessage(MiniMessage.miniMessage().deserialize(message));
    }

    public static void send(CommandSender sender, String message) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            Welcome.getAudience().player(player).sendMessage(MiniMessage.miniMessage().deserialize(message));
        } else {
            Welcome.getAudience().console().sendMessage(MiniMessage.miniMessage().deserialize(message));
        }
    }

    public static void sendActionBar(UUID uuid, String message) {
        Welcome.getAudience().player(uuid).sendActionBar(MiniMessage.miniMessage().deserialize(message));
    }

    public static void sendConsole(String message) {
        Welcome.getAudience().console().sendMessage(MiniMessage.miniMessage().deserialize(message));
    }
}
