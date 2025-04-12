package me.luyz.commands;

import me.luyz.utilities.*;
import me.luyz.module.*;
import me.luyz.utilities.command.CommandMeta;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.command.*;
import me.luyz.utilities.command.*;


public class DenCommand extends BaseCommand {

    @CommandMeta(name = "den", aliases = { "denA" }, permission = "den.command.admin", inGameOnly = false)
    @Override
    public void onCommand(final CommandArgs command) {
        final CommandSender sender = command.getSender();
        final String[] args = command.getArgs();
        final String label = command.getLabel();
        if (args.length == 0) {
            ChatUtil.sendMessage(sender, "&cUsage: /" + label + " <reload|reset>");
            return;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            ModuleService.reload();
            ChatUtil.sendMessage(sender, "&aDen has been reloaded!");
        }
        else if (args[0].equalsIgnoreCase("reset")) {
            if (sender instanceof Player) {
                ChatUtil.sendMessage(sender, "&cYou can't use this command in-game!");
                return;
            }
        }
    }

}
