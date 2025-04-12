package me.luyz.utilities.command;


import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class BukkitCommand extends Command {

    private final Plugin ownerPlugin;
    private final CommandExecutor executor;

    protected BukkitCommand(final String label, final CommandExecutor executor, final Plugin owner) {
        super(label);
        this.executor = executor;
        this.ownerPlugin = owner;
        this.usageMessage = "";
    }

    public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
        if (!this.ownerPlugin.isEnabled()) {
            return false;
        }
        if (!this.testPermission(sender)) {
            return true;
        }
        boolean success;
        try {
            success = this.executor.onCommand(sender, (Command)this, commandLabel, args);
        }
        catch (Throwable ex) {
            throw new CommandException("Unhandled exception executing command '" + commandLabel + "' in plugin " + this.ownerPlugin.getDescription().getFullName(), ex);
        }
        if (!success && this.usageMessage.length() > 0) {
            for (final String line : this.usageMessage.replace("<command>", commandLabel).split("\n")) {
                sender.sendMessage(line);
            }
        }
        return success;
    }





}
