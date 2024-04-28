package fr.jesuistrolls.welcome.listeners;

import fr.jesuistrolls.welcome.Welcome;
import fr.jesuistrolls.welcome.configuration.Messages;
import fr.jesuistrolls.welcome.configuration.Rewards;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static fr.jesuistrolls.welcome.configuration.Rewards.welcomeDelay;

public class PlayerJoinListeners implements Listener {

    private final Map<UUID, List<UUID>> playerCache;
    public static int welcomeDelaySecond;


    public PlayerJoinListeners(Map<UUID, List<UUID>> playerCache, Messages messages, int welcomeDelay){
        this.playerCache = playerCache;
    }

    @EventHandler
    public void joinPlayer(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        welcomeDelaySecond = welcomeDelay*20;

        if (!player.hasPlayedBefore()){
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if(player.getUniqueId() == onlinePlayer.getUniqueId()) continue;
                Messages.send(onlinePlayer, Messages.newplayer.replaceAll("%player_name%", player.getName()));
            }
            playerCache.put(player.getUniqueId(), new ArrayList<>());
            Bukkit.getScheduler().runTaskLaterAsynchronously(Welcome.getPlugin(Welcome.class), () -> {
                playerCache.remove(player.getUniqueId());
            }, welcomeDelaySecond);
        }

    }

}


