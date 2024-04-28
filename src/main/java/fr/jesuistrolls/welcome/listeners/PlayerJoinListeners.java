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

public class PlayerJoinListeners implements Listener {

    private final Map<UUID, List<UUID>> playerCache;


    public PlayerJoinListeners(Map<UUID, List<UUID>> playerCache, Messages messages, int timeRewards){
        this.playerCache = playerCache;
    }

    @EventHandler
    public void joinPlayer(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()){
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if(player.getUniqueId() == onlinePlayer.getUniqueId()) continue;
                onlinePlayer.sendMessage(Messages.newplayer.replace("%player_name%", player.getName()));
            }
            playerCache.put(player.getUniqueId(), new ArrayList<>());
            Bukkit.getScheduler().runTaskLaterAsynchronously(Welcome.getPlugin(Welcome.class), () -> {
                playerCache.remove(player.getUniqueId());
            }, Rewards.time);
        }

    }

}


