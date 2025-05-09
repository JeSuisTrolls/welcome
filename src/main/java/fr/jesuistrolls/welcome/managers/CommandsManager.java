package fr.jesuistrolls.welcome.managers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import fr.jesuistrolls.welcome.Welcome;
import fr.jesuistrolls.welcome.commands.WelcomeCommand;
import fr.jesuistrolls.welcome.configurations.Settings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

public class CommandsManager {
    private CommandMap commandMap;
    private final WelcomeCommand welcomeCommand;

    public CommandsManager(Map<UUID, List<UUID>> playerCache, Welcome plugin) {
        this.welcomeCommand = new WelcomeCommand(playerCache, plugin);
        setupCommandMap();
        createPluginCommand();
    }


    private void createPluginCommand() {
        try {
            Settings.welcomeCommands = (Settings.welcomeCommands == null || Settings.welcomeCommands.isEmpty()) ? Collections.singletonList("welcome") : Settings.welcomeCommands;

            for (String commandName : Settings.welcomeCommands) {
                Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
                constructor.setAccessible(true);
                PluginCommand command = constructor.newInstance(commandName, Welcome.getInstance());

                command.setExecutor(welcomeCommand);

                registerCommand(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setupCommandMap() {
        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            this.commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to setup command map");
        }
    }

    private void registerCommand(Command command) {
        this.commandMap.register(command.getName(), command);
    }
}


