package fr.jesuistrolls.welcome.commands;

import fr.jesuistrolls.welcome.configuration.Messages;
import fr.jesuistrolls.welcome.configuration.Rewards;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class WelcomeCommand implements TabExecutor {

    private final Map<UUID, List<UUID>> playerCache;
    private final Messages messages;

    private final Plugin plugin;

    private final  List<String> commandRewards;

    public WelcomeCommand(Map<UUID, List<UUID>> playerCache, List<String> commandRewards, Messages messages, Plugin plugin) {
        this.messages = messages;
        this.playerCache  = playerCache;
        this.commandRewards = commandRewards;
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(commandSender instanceof Player player){
            if (args.length >= 1) {
                if (args[0].equals("reload")) {
                    if (commandSender.hasPermission("welcome.admin")) {
                        plugin.reloadConfig();
                        Messages.loadMessages(plugin.getConfig());
                        Rewards.loadRewards(plugin.getConfig());
                        Messages.send(player.getUniqueId(), Messages.reload);
                    } else {
                        Messages.send(player.getUniqueId(), Messages.noPermission);
                        return false;
                    }
                }
            } else {
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
                            Messages.send(player.getUniqueId(), Messages.alreadySend.replaceAll("%player_name%", Objects.requireNonNull(offlinePlayer.getName())));
                            continue;
                        }
                        Messages.send(player, Messages.welcomeSend.replaceAll("%player_name%", Objects.requireNonNull(offlinePlayer.getName())));
                        playerCache.get(newPlayer).add(player.getUniqueId());
                        if (offlinePlayer instanceof Player playeronline){
                            Messages.send(playeronline, Messages.welcomeFormat.replaceAll("%player_name%", player.getName()));
                        }
                        if(Rewards.enabled) {
                            for (String rewardCommand : commandRewards) {
                                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), rewardCommand.replace("%player_name%", player.getName()));
                            }
                        } return false;

                    }
                }else{
                    Messages.send(player.getUniqueId(), Messages.noPermission);
                }
            }
        }
        return false;
    }
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        List<String> tempArgs = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("welcome.admin")) tempArgs.add("reload");
            return tempArgs;
        }
        return new ArrayList<>();
    }
}

