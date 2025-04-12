package me.luyz.commands.timer;

import me.luyz.commands.timer.subcommand.*;
import me.luyz.utilities.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.luyz.utilities.command.*;

public class TimerCommand extends BaseCommand
{
    public TimerCommand() {
        new TimerStartCommand();
        new TimerStopCommand();
        new TimerListCommand();
    }

    @CommandMeta(name = "timer", aliases = { "customt", "ctimer", "ct" }, permission = "den.command.timer", inGameOnly = false)
    @Override
    public void onCommand(final CommandArgs command) {
        final Player player = command.getPlayer();
        final String label = command.getLabel();
        ChatUtil.sendMessage((CommandSender)player, new String[] {

                ChatUtil.NORMAL_LINE, "&3&lTimer Commands",
                "",
                " &f<> &7= &fRequired &7| &f[] &7= &fOptional",
                "",
                " &7▶ &b/" + label + " start <timer> <time> <prefix> &7- &fStart a timer.",
                " &7▶ &b/" + label + " stop <timer> &7- &fStop a timer.",
                " &7▶ &b/" + label + " list &7- &fSee all timers.",

                ChatUtil.NORMAL_LINE });
    }
}