package me.luyz.commands.timer.subcommand;

import me.luyz.module.*;
import me.luyz.utilities.*;
import org.apache.commons.lang.*;
import me.luyz.commons.timer.*;
import org.bukkit.command.*;
import me.luyz.utilities.command.*;

public class TimerStartCommand extends BaseCommand
{
    private final TimerManager timerManager;

    public TimerStartCommand() {
        this.timerManager = ModuleService.getManagerModule().getTimerManager();
    }

    @CommandMeta(name = "timer.start", aliases = { "customt.start", "ctimer.start", "ct.start" }, permission = "den.command.timer.start", inGameOnly = false)
    @Override
    public void onCommand(final CommandArgs command) {
        final CommandSender sender = command.getSender();
        final String[] args = command.getArgs();
        final String label = command.getLabel().replace(".start", "");
        if (args.length < 3) {
            ChatUtil.sendMessage(sender, "&cUsage: /" + label + " start <timer> <time> <prefix>");
            return;
        }
        final String timerName = ChatUtil.capitalize(args[0]);
        if (this.timerManager.existsTimer(timerName)) {
            ChatUtil.sendMessage(sender, "&cTimer '" + timerName + "' already exist.");
            return;
        }
        final long duration = JavaUtil.formatLong(args[1]);
        if (duration == -1L) {
            ChatUtil.sendMessage(sender, "&c" + args[1] + " is an invalid duration.");
            return;
        }
        if (duration < 1000L) {
            ChatUtil.sendMessage(sender, "&cTimer must last for at least 20 ticks.");
            return;
        }
        final String prefix = StringUtils.join((Object[])args, ' ', 2, args.length);
        final Timer timer = new Timer(timerName, System.currentTimeMillis(), System.currentTimeMillis() + duration, prefix);
        this.timerManager.createTimer(timer);
        ChatUtil.sendMessage(sender, "&aCustom Timer '&f" + timerName + "&a' has been created.");
    }
}