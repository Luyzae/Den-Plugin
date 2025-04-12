package me.luyz.commons.timer;

import org.bukkit.*;
import me.luyz.*;
import org.bukkit.plugin.*;
import me.luyz.module.*;

public class TimerTask implements Runnable
{
    private final Timer timer;
    private final int id;

    public TimerTask(final Timer timer) {
        this.timer = timer;
        this.id = Bukkit.getScheduler().runTaskTimerAsynchronously((Plugin)Den.get(), (Runnable)this, 0L, 20L).getTaskId();
    }

    @Override
    public void run() {
        if (this.timer == null) {
            this.cancel();
            return;
        }
        if (this.timer.getEndMillis() < System.currentTimeMillis()) {
            this.cancel();
            return;
        }
        if (!this.timer.isRunning()) {
            this.cancel();
        }
    }

    private void cancel() {
        ModuleService.getManagerModule().getTimerManager().deleteTimer(this.timer);
        Bukkit.getScheduler().cancelTask(this.id);
    }

    public Timer getTimer() {
        return this.timer;
    }

    public int getId() {
        return this.id;
    }
}