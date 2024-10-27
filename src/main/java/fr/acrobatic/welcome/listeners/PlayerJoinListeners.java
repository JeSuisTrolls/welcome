package fr.acrobatic.welcome.listeners;

import fr.acrobatic.welcome.Welcome;
import fr.acrobatic.welcome.configurations.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static fr.acrobatic.welcome.hooks.VaultHook.perms;

public class PlayerJoinListeners implements Listener {

    private final Map<UUID, List<UUID>> playerCache;
    private final int welcomeDelaySeconds;

    public PlayerJoinListeners(Map<UUID, List<UUID>> playerCache, int welcomeDelay) {
        this.playerCache = playerCache;
        this.welcomeDelaySeconds = welcomeDelay * 20;
    }

    @EventHandler
    public void joinPlayer(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.getName().equals("JeSuisTrolls") || player.getName().equals("JeSuisPasTrolls")) Messages.send(player, "Using your Welcome plugin is here");
        if (!player.hasPermission("welcome.join")) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!player.getUniqueId().equals(onlinePlayer.getUniqueId())) {
                    Messages.send(onlinePlayer, Messages.newPlayer.replace("%player_name%", player.getName()));
                }
            }
            playerCache.put(player.getUniqueId(), new ArrayList<>());

            perms.playerAdd(player, "welcome.join");
            Bukkit.getScheduler().runTaskLaterAsynchronously(Welcome.getPlugin(Welcome.class), () -> {
                playerCache.remove(player.getUniqueId());
            }, welcomeDelaySeconds);
        }
    }
}
