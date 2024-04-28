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
        noPlayer = config.getString("no-player.message");
        noPermission = config.getString("no-permission.message");
        newplayer = config.getString("new-player.message");
        invalidPlayer = config.getString("invalid-player.message");
        alreadySend = config.getString("already-send.message");
        welcomeSend = config.getString("welcome-send.message");
        welcomeFormat = config.getString("welcome-format.message");
        reload = config.getString("reload.message");


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
