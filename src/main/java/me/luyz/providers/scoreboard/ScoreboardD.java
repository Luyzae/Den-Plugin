package me.luyz.providers.scoreboard;

import org.bukkit.plugin.java.*;
import java.util.concurrent.*;
import org.bukkit.plugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import me.luyz.providers.scoreboard.events.*;
import java.util.*;
import org.bukkit.event.*;

public class ScoreboardD {

    private JavaPlugin plugin;
    private ScoreboardAdapter adapter;
    private Map<UUID, ScoreboardDManager> boards;
    private ScoreboardThread thread;
    private ScoreboardListener listeners;
    private long ticks;
    private boolean hook;
    private ScoreboardStyle assembleStyle;

    public ScoreboardD(final JavaPlugin plugin, final ScoreboardAdapter adapter) {
        this.ticks = 2L;
        this.hook = false;
        this.assembleStyle = ScoreboardStyle.MODERN;
        if (plugin == null) {
            throw new RuntimeException("Scoreboard can not be instantiated without a plugin instance!");
        }
        this.plugin = plugin;
        this.adapter = adapter;
        this.boards = new ConcurrentHashMap<UUID, ScoreboardDManager>();
        this.setup();
    }

    public void setup() {
        this.listeners = new ScoreboardListener(this);
        this.plugin.getServer().getPluginManager().registerEvents((Listener)this.listeners, (Plugin)this.plugin);
        if (this.thread != null) {
            this.thread.stop();
            this.thread = null;
        }
        for (final Player player : Bukkit.getServer().getOnlinePlayers()) {
            final ScoreboardCreateEvent createEvent = new ScoreboardCreateEvent(player);
            Bukkit.getPluginManager().callEvent((Event)createEvent);
            if (createEvent.isCancelled()) {
                return;
            }
            this.boards.put(player.getUniqueId(), new ScoreboardDManager(player, this));
        }
        this.thread = new ScoreboardThread(this);
        ScoreboardAnimated.init();
    }

    public void cleanup() {
        if (this.thread != null) {
            this.thread.stop();
            this.thread = null;
        }
        if (this.listeners != null) {
            HandlerList.unregisterAll((Listener)this.listeners);
            this.listeners = null;
        }
        for (final UUID uuid : this.getBoards().keySet()) {
            final Player player = Bukkit.getPlayer(uuid);
            this.getBoards().remove(uuid);
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    public ScoreboardAdapter getAdapter() {
        return this.adapter;
    }

    public Map<UUID, ScoreboardDManager> getBoards() {
        return this.boards;
    }

    public ScoreboardThread getThread() {
        return this.thread;
    }

    public ScoreboardListener getListeners() {
        return this.listeners;
    }

    public long getTicks() {
        return this.ticks;
    }

    public boolean isHook() {
        return this.hook;
    }

    public ScoreboardStyle getAssembleStyle() {
        return this.assembleStyle;
    }

    public void setPlugin(final JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void setAdapter(final ScoreboardAdapter adapter) {
        this.adapter = adapter;
    }

    public void setBoards(final Map<UUID, ScoreboardDManager> boards) {
        this.boards = boards;
    }

    public void setThread(final ScoreboardThread thread) {
        this.thread = thread;
    }

    public void setListeners(final ScoreboardListener listeners) {
        this.listeners = listeners;
    }

    public void setTicks(final long ticks) {
        this.ticks = ticks;
    }

    public void setHook(final boolean hook) {
        this.hook = hook;
    }

    public void setAssembleStyle(final ScoreboardStyle assembleStyle) {
        this.assembleStyle = assembleStyle;
    }

}
