package me.luyz.commands.timer.subcommand;

import me.luyz.commons.timer.Timer;
import me.luyz.module.*;
import me.luyz.utilities.*;
import me.luyz.commons.timer.*;
import org.bukkit.command.*;
import java.util.*;
import me.luyz.utilities.command.*;

public class TimerListCommand extends BaseCommand {


    private final TimerManager timerManager;

    public TimerListCommand() {
        this.timerManager = ModuleService.getManagerModule().getTimerManager();
    }

    @CommandMeta(name = "timer.list", aliases = { "customt.list", "ctimer.list", "ct.list" }, permission = "den.command.timer.list", inGameOnly = false)
    @Override
    public void onCommand(final CommandArgs command) {
        final CommandSender sender = command.getSender();
        ChatUtil.sendMessage(sender, "&7&m--------->&r &3&lTimers List &7&m<---------");
        if (this.timerManager.getTimers().isEmpty()) {
            ChatUtil.sendMessage(sender, "&cThere are no timers.");
        }
        else {
            for (final Timer timer : this.timerManager.getTimers().values()) {
                ChatUtil.sendMessage(sender, " &7\u25b6 &b" + timer.getName() + " Timer");
            }
        }
        ChatUtil.sendMessage(sender, ChatUtil.NORMAL_LINE);
    }

}
