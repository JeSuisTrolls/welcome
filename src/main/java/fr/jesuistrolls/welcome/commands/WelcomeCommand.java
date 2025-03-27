package fr.jesuistrolls.welcome.commands;

import fr.jesuistrolls.welcome.Welcome;
import fr.jesuistrolls.welcome.configurations.Messages;
import fr.jesuistrolls.welcome.configurations.Settings;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class WelcomeCommand implements TabExecutor {

    private final Map<UUID, List<UUID>> playerCache;
    private final Welcome plugin;

    public WelcomeCommand(Map<UUID, List<UUID>> playerCache, Welcome plugin) {
        this.playerCache = playerCache;
        this.plugin = plugin;
    }

    private void reloadConfigValues() {
        plugin.reloadAllConfigurations();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            if (args.length >= 1 && "reload".equalsIgnoreCase(args[0])) {
                reloadConfigValues();
                Messages.send(sender, Messages.reload);
                return true;
            }
            return false;
        }

        if (args.length >= 1 && "reload".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("welcome.admin")) {
                Messages.send(sender, Messages.noPermission);
                return false;
            }
            reloadConfigValues();
            Messages.send(player, Messages.reload);
            return true;
        }

        if (!player.hasPermission("welcome.command.welcome")) {
            Messages.send(player, Messages.noPermission);
            return false;
        }

        if (playerCache.isEmpty()) {
            Messages.send(player, Messages.noPlayer);
            return false;
        }

        for (UUID newPlayerUUID : playerCache.keySet()) {
            if (player.getUniqueId().equals(newPlayerUUID) || playerCache.get(newPlayerUUID).contains(player.getUniqueId())) {
                continue;
            }

            OfflinePlayer newPlayer = Bukkit.getOfflinePlayer(newPlayerUUID);
            Messages.send(player, Messages.welcomeSend.replace("%player_name%", newPlayer.getName()));
            playerCache.get(newPlayerUUID).add(player.getUniqueId());

            if (newPlayer.isOnline()) {
                Player newPlayerOnline = newPlayer.getPlayer();

                if (newPlayerOnline != null) {

                    int randomIndex = new Random().nextInt(Messages.welcomeFormats.size());
                    String randomWelcomeMessage = Messages.welcomeFormats.get(randomIndex);

                    if(Settings.welcomeMessageType.equalsIgnoreCase("global")) {
                        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                            Messages.send(onlinePlayer, randomWelcomeMessage.replace("%player_name%", player.getName()));
                        }
                    } else if (Settings.welcomeMessageType.equalsIgnoreCase("player")) {
                        player.chat(randomWelcomeMessage.replace("%player_name%", newPlayerOnline.getName()));
                    } else {
                        Messages.send(newPlayerOnline, randomWelcomeMessage.replace("%player_name%", player.getName()));
                    }
                }
            }

            if (Settings.enabled) {
                for (String rewardCommand : Settings.commandsRewards) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), rewardCommand.replace("%player_name%", player.getName()));
                }
            }
            return true;
        }

        Messages.send(player, Messages.invalidPlayer);
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1 && sender.hasPermission("welcome.admin")) {
            return Collections.singletonList("reload");
        }
        return Collections.emptyList();
    }
}
