package me.luyz.commands.hitdelay;

import me.luyz.utilities.DelaySetup;
import me.luyz.utilities.command.BaseCommand;
import me.luyz.utilities.command.CommandArgs;
import me.luyz.utilities.command.CommandMeta;
import me.luyz.servicies.impl.HitDelayService;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;


public class HitDelayCommand extends BaseCommand {

    @CommandMeta(name = "sethitdelay", permission = "hitdelay.set")
    @Override
    public void onCommand(final CommandArgs command) {

        final CommandSender sender = command.getSender();

        final String[] args = command.getArgs();


        if (args.length != 1) {
            sender.sendMessage("§cUsage: /sethitdelay <ticks>");
            return;
        }
        try {
            int delay = Integer.parseInt(args[0]);

            if (delay < 0) {
                sender.sendMessage("§cHit delay must be a positive number!");
                return;
            }

            // Cambiar el valor global
            HitDelayService.HIT_DELAY = String.valueOf(delay);

            // Aplicar a todos los jugadores conectados
            Bukkit.getOnlinePlayers().forEach(DelaySetup::setUp);

            sender.sendMessage("§aHit delay has been set to " + delay + " ticks.");

        } catch (NumberFormatException e) {
            sender.sendMessage("§cInvalid number. Please enter a valid integer.");
        }
    }

}
