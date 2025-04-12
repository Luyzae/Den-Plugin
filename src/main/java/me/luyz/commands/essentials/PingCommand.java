package me.luyz.commands.essentials;

import me.luyz.utilities.command.CommandMeta;
import org.bukkit.entity.*;
import me.luyz.servicies.impl.*;
import me.luyz.utilities.*;
import org.bukkit.command.*;
import org.bukkit.*;
import me.luyz.utilities.command.*;

public class PingCommand extends BaseCommand{

    @CommandMeta(name = "ping", permission = "den.command.ping", inGameOnly = false)
    @Override
    public void onCommand(final CommandArgs command) {
        final CommandSender sender = command.getSender();
        final String[] args = command.getArgs();
        if (args.length == 0) {
            if (sender instanceof Player) {
                final Player player = (Player)sender;
                ChatUtil.sendMessage((CommandSender)player, LanguageService.PING_MESSAGE_PLAYER.replace("<ping>", String.valueOf(PlayerUtil.getPing(player))));
            }
            else {
                ChatUtil.sendMessage(sender, "&cUsage: /" + command.getLabel() + " <player>");
            }
            return;
        }
        final Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            ChatUtil.sendMessage(sender, "&cPlayer '" + args[0] + "' not found.");
            return;
        }
        ChatUtil.sendMessage(sender, LanguageService.PING_MESSAGE_TARGET.replace("<ping>", String.valueOf(PlayerUtil.getPing(target))).replace("<target>", target.getName()));
    }
}
