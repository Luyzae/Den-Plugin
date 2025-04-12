package me.luyz.utilities.command;

import org.bukkit.command.*;
import org.bukkit.entity.*;

public class CommandArgs
{
    private final CommandSender sender;
    private final Command command;
    private final String label;
    private final String[] args;

    protected CommandArgs(final CommandSender sender, final Command command, final String label, final String[] args, final int subCommand) {
        final String[] modArgs = new String[args.length - subCommand];
        if (args.length - subCommand >= 0) {
            System.arraycopy(args, subCommand, modArgs, 0, args.length - subCommand);
        }
        final StringBuilder buffer = new StringBuilder();
        buffer.append(label);
        for (int x = 0; x < subCommand; ++x) {
            buffer.append(".").append(args[x]);
        }
        final String cmdLabel = buffer.toString();
        this.sender = sender;
        this.command = command;
        this.label = cmdLabel;
        this.args = modArgs;
    }


    public String getArgs(final int index) {
        return this.args[index];
    }

    public int length() {
        return this.args.length;
    }

    public boolean isPlayer() {
        return this.sender instanceof Player;
    }

    public Player getPlayer() {
        if (this.sender instanceof Player) {
            return (Player)this.sender;
        }
        return null;
    }

    public CommandSender getSender() {
        return this.sender;
    }

    public Command getCommand() {
        return this.command;
    }

    public String getLabel() {
        return this.label;
    }

    public String[] getArgs() {
        return this.args;
    }
}