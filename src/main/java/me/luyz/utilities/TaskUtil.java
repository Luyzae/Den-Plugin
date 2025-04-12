package me.luyz.utilities;

import me.luyz.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import org.bukkit.scheduler.*;

public final class TaskUtil {

    private static final Den plugin;

    public static void run(final Runnable runnable) {
        Bukkit.getServer().getScheduler().runTask((Plugin)TaskUtil.plugin, runnable);
    }

    public static void runTimer(final Runnable runnable, final long delay, final long timer) {
        Bukkit.getServer().getScheduler().runTaskTimer((Plugin)TaskUtil.plugin, runnable, delay, timer);
    }

    public static void runTimer(final BukkitRunnable runnable, final long delay, final long timer) {
        runnable.runTaskTimer((Plugin)TaskUtil.plugin, delay, timer);
    }

    public static void runTimerAsync(final Runnable runnable, final long delay, final long timer) {
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously((Plugin)TaskUtil.plugin, runnable, delay, timer);
    }

    public static void runTimerAsync(final BukkitRunnable runnable, final long delay, final long timer) {
        runnable.runTaskTimerAsynchronously((Plugin)TaskUtil.plugin, delay, timer);
    }

    public static void runLater(final Runnable runnable, final long delay) {
        Bukkit.getServer().getScheduler().runTaskLater((Plugin)TaskUtil.plugin, runnable, delay);
    }

    public static void runLaterAsync(final Runnable runnable, final long delay) {
        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously((Plugin)TaskUtil.plugin, runnable, delay);
    }

    public static void runTaskTimer(final Runnable runnable, final long delay, final long timer) {
        Bukkit.getServer().getScheduler().runTaskTimer((Plugin)TaskUtil.plugin, runnable, 20L * delay, 20L * timer);
    }

    public static void runTaskTimerAsynchronously(final Runnable runnable, final int delay) {
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously((Plugin)TaskUtil.plugin, runnable, 20L * delay, 20L * delay);
    }

    public static void runAsync(final Runnable runnable) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously((Plugin)TaskUtil.plugin, runnable);
    }

    public static void scheduleSyncDelayedTask(final Runnable runnable) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)TaskUtil.plugin, runnable);
    }

    public static void cancelTask(final int id) {
        Bukkit.getServer().getScheduler().cancelTask(id);
    }

    private TaskUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static {
        plugin = Den.get();
    }

}
