package fr.jesuistrolls.welcome.commands;

import fr.jesuistrolls.welcome.configuration.Messages;
import fr.jesuistrolls.welcome.configuration.Rewards;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WelcomeCommand implements CommandExecutor {

    private final Map<UUID, List<UUID>> playerCache;
    private final Messages messages;

    private final  List<String> commandRewards;

    public WelcomeCommand(Map<UUID, List<UUID>> playerCache, List<String> commandRewards, Messages messages) {
        this.messages = messages;
        this.playerCache  = playerCache;
        this.commandRewards = commandRewards;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player player){
            if(player.hasPermission("welcome.use")){

                if (playerCache.isEmpty()) {
                    Messages.send(player.getUniqueId(), Messages.noPlayer.replaceAll("%player_name%", player.getName()));
                    return false;
                }
                for (UUID newPlayer : playerCache.keySet()) {
                    if (player.getUniqueId() == newPlayer){
                        Messages.send(player.getUniqueId(), Messages.invalidPlayer);
                        continue;
                    }

                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(newPlayer);
                    if (playerCache.get(newPlayer).contains(player.getUniqueId())) {
                        Messages.send(player.getUniqueId(), Messages.alreadySend.replaceAll("%player_name%", player.getName()));
                        continue;
                    }

                    player.sendMessage(Messages.welcomeSend.replaceAll("%player_name%", player.getName()));
                    playerCache.get(newPlayer).add(player.getUniqueId());
                    if (offlinePlayer instanceof Player playeronline){
                        playeronline.sendMessage(Messages.welcomeFormat.replaceAll("%player_name%", player.getName()));
                    }
                    if(Rewards.enabled) {
                        for (String rewardCommand : commandRewards) {
                            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), rewardCommand.replace("%player%", player.getName()));
                        }
                    } return false;

                }
            }else{
                Messages.send(player.getUniqueId(), Messages.noPermission);
            }
        }
        return false;
    }
}

