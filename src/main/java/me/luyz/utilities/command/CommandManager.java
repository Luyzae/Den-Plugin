package me.luyz.utilities.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;
import me.luyz.utilities.*;
import org.bukkit.entity.Player;
import java.lang.reflect.*;
import org.bukkit.help.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.plugin.*;

public class CommandManager implements CommandExecutor {

    private final Map<String, Map.Entry<Method, Object>> commandMap;
    private final JavaPlugin plugin;
    private final List<String> disableCommands;
    private CommandMap map;

    public CommandManager(final JavaPlugin plugin, final List<String> disableCommands) {
        this.commandMap = new HashMap<String, Map.Entry<Method, Object>>();
        this.plugin = plugin;
        this.disableCommands = disableCommands;
        if (plugin.getServer().getPluginManager() instanceof SimplePluginManager) {
            final SimplePluginManager manager = (SimplePluginManager)plugin.getServer().getPluginManager();
            try {
                final Field field = SimplePluginManager.class.getDeclaredField("commandMap");
                field.setAccessible(true);
                this.map = (CommandMap)field.get(manager);
            }
            catch (IllegalArgumentException | SecurityException | NoSuchFieldException | IllegalAccessException ex2) {
                final Exception ex;
                final Exception e = ex2;
                e.printStackTrace();
            }
        }
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        return this.handleCommand(sender, cmd, label, args);
    }

    public boolean handleCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        int i = args.length;
        while (i >= 0) {
            final StringBuilder buffer = new StringBuilder();
            buffer.append(label.toLowerCase());
            for (int x = 0; x < i; ++x) {
                buffer.append(".").append(args[x].toLowerCase());
            }
            final String cmdLabel = buffer.toString();
            if (this.commandMap.containsKey(cmdLabel)) {
                final Method method = this.commandMap.get(cmdLabel).getKey();
                final Object methodObject = this.commandMap.get(cmdLabel).getValue();
                final me.luyz.utilities.command.CommandMeta command = method.getAnnotation(me.luyz.utilities.command.CommandMeta.class);
                if (!command.permission().equals("") && !sender.hasPermission(command.permission())) {
                    ChatUtil.sendMessage(sender, "&cYou do not have permission to use this command.");
                    return true;
                }
                if (command.inGameOnly() && !(sender instanceof Player)) {
                    ChatUtil.sendMessage(sender, "&cThis command in only executable in game.");
                    return true;
                }
                try {
                    method.invoke(methodObject, new CommandArgs(sender, cmd, label, args, cmdLabel.split("\\.").length - 1));
                }
                catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ex2) {
                    final Exception ex;
                    final Exception e = ex2;
                    e.printStackTrace();
                }
                return true;
            }
            else {
                --i;
            }
        }
        return true;
    }

    public void registerCommands(final Object obj, final List<String> aliases) {
        for (final Method m : obj.getClass().getMethods()) {
            if (m.getAnnotation(me.luyz.utilities.command.CommandMeta.class) != null) {
                final me.luyz.utilities.command.CommandMeta command = m.getAnnotation(me.luyz.utilities.command.CommandMeta.class);
                if (m.getParameterTypes().length > 1 || m.getParameterTypes()[0] != CommandArgs.class) {
                    ChatUtil.logger("Unable to register command " + m.getName() + ". Unexpected method arguments");
                }
                else {
                    if (this.disableCommands.contains(command.name())) {
                        return;
                    }
                    this.registerCommand(command, command.name(), m, obj);
                    for (final String alias : command.aliases()) {
                        this.registerCommand(command, alias, m, obj);
                    }
                    if (aliases != null) {
                        for (final String alias2 : aliases) {
                            this.registerCommand(command, alias2, m, obj);
                        }
                    }
                }
            }
        }
    }

    public void registerCommands(final BaseCommand obj, final List<String> aliases, final String commandName) {
        for (final Method m : obj.getClass().getMethods()) {
            if (m.getAnnotation(me.luyz.utilities.command.CommandMeta.class) != null) {
                final me.luyz.utilities.command.CommandMeta command = m.getAnnotation(me.luyz.utilities.command.CommandMeta.class);
                if (m.getParameterTypes().length > 1 || m.getParameterTypes()[0] != CommandArgs.class) {
                    System.out.println("Unable to register command " + m.getName() + ". Unexpected method arguments");
                }
                else {
                    if (this.disableCommands.contains(command.name())) {
                        return;
                    }
                    this.registerCommand(command, commandName, m, obj);
                    for (final String alias : command.aliases()) {
                        this.registerCommand(command, alias, m, obj);
                    }
                    if (aliases != null) {
                        for (final String alias2 : aliases) {
                            this.registerCommand(command, alias2, m, obj);
                        }
                    }
                }
            }
        }
    }
    public void registerHelp() {
        final Set<HelpTopic> help = new TreeSet<HelpTopic>((Comparator<? super HelpTopic>)HelpTopicComparator.helpTopicComparatorInstance());
        for (final String s : this.commandMap.keySet()) {
            if (!s.contains(".")) {
                final Command cmd = this.map.getCommand(s);
                final HelpTopic topic = (HelpTopic)new GenericCommandHelpTopic(cmd);
                help.add(topic);
            }
        }
        final IndexHelpTopic topic2 = new IndexHelpTopic(this.plugin.getName(), "All commands for " + this.plugin.getName(), (String)null, (Collection)help, "Below is a list of all " + this.plugin.getName() + " commands:");
        Bukkit.getServer().getHelpMap().addTopic((HelpTopic)topic2);
    }

    public void unregisterCommands(final Object obj) {
        for (final Method m : obj.getClass().getMethods()) {
            if (m.getAnnotation(me.luyz.utilities.command.CommandMeta.class) != null) {
                final me.luyz.utilities.command.CommandMeta command = m.getAnnotation(me.luyz.utilities.command.CommandMeta.class);
                this.commandMap.remove(command.name().toLowerCase());
                this.commandMap.remove(this.plugin.getName() + ":" + command.name().toLowerCase());
                this.map.getCommand(command.name().toLowerCase()).unregister(this.map);
            }
        }
    }

    public void registerCommand(final me.luyz.utilities.command.CommandMeta command, final String label, final Method m, final Object obj) {
        this.commandMap.put(label.toLowerCase(), new AbstractMap.SimpleEntry<Method, Object>(m, obj));
        this.commandMap.put(this.plugin.getName() + ':' + label.toLowerCase(), new AbstractMap.SimpleEntry<Method, Object>(m, obj));
        final String cmdLabel = label.replace(".", ",").split(",")[0].toLowerCase();
        if (this.map.getCommand(cmdLabel) == null) {
            final Command cmd = new BukkitCommand(cmdLabel, (CommandExecutor)this, (Plugin)this.plugin);
            this.map.register(this.plugin.getName(), cmd);
        }
        if (!command.description().equalsIgnoreCase("") && cmdLabel.equals(label)) {
            this.map.getCommand(cmdLabel).setDescription(command.description());
        }
        if (!command.usage().equalsIgnoreCase("") && cmdLabel.equals(label)) {
            this.map.getCommand(cmdLabel).setUsage(command.usage());
        }
    }

}
