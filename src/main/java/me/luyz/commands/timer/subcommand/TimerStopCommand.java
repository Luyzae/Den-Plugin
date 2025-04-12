package me.luyz.commands.timer.subcommand;

import me.luyz.module.*;
import me.luyz.utilities.*;
import org.bukkit.command.*;
import me.luyz.commons.timer.*;
import me.luyz.utilities.command.*;

public class TimerStopCommand extends BaseCommand
{
    private final TimerManager timerManager;

    public TimerStopCommand() {
        this.timerManager = ModuleService.getManagerModule().getTimerManager();
    }

    @CommandMeta(name = "timer.stop", aliases = { "customt.stop", "ctimer.stop", "ct.stop" }, permission = "den.command.timer.stop", inGameOnly = false)
    @Override
    public void onCommand(final CommandArgs command) {
        final CommandSender sender = command.getSender();
        final String[] args = command.getArgs();
        final String label = command.getLabel().replace(".stop", "");
        if (args.length < 1) {
            ChatUtil.sendMessage(sender, "&cUsage: /" + label + " stop <timer>");
            return;
        }
        final String timerName = ChatUtil.capitalize(args[0]);
        if (!this.timerManager.existsTimer(timerName)) {
            ChatUtil.sendMessage(sender, "&cTimer '" + timerName + "' is not activated.");
            return;
        }
        final Timer timer = this.timerManager.getTimer(timerName);
        timer.setRunning(false);
        ChatUtil.sendMessage(sender, "&cCustom Timer '&f" + timerName + "&c' has been stopped.");
    }
}