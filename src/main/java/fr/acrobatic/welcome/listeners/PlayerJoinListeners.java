package fr.acrobatic.welcome.listeners;

import fr.acrobatic.welcome.Welcome;
import fr.acrobatic.welcome.configurations.Messages;
import fr.acrobatic.welcome.configurations.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.*;
import java.util.function.Supplier;

import static fr.acrobatic.welcome.hooks.VaultHook.perms;

public class PlayerJoinListeners implements Listener {

    private final Map<UUID, List<UUID>> playerCache;
    private final int welcomeDelaySeconds;

    public PlayerJoinListeners(Map<UUID, List<UUID>> playerCache) {
        this.playerCache = playerCache;
        this.welcomeDelaySeconds = Settings.welcomeDelay;
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


            perms.playerAdd(null, player, "welcome.join");
            Bukkit.getScheduler().runTaskLaterAsynchronously(Welcome.getPlugin(Welcome.class), () -> {
                playerCache.remove(player.getUniqueId());
            }, welcomeDelaySeconds);
        }
    }
}
