package me.luyz.providers.scoreboard.events;

import org.bukkit.event.*;
import org.bukkit.entity.*;

public class ScoreboardDestroyEvent extends Event implements Cancellable {

    public static HandlerList handlerList;
    private final Player player;
    private boolean cancelled;

    public ScoreboardDestroyEvent(final Player player) {
        this.cancelled = false;
        this.player = player;
    }

    public void setCancelled(final boolean b) {
        this.cancelled = b;
    }

    public HandlerList getHandlers() {
        return ScoreboardDestroyEvent.handlerList;
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public static HandlerList getHandlerList() {
        return ScoreboardDestroyEvent.handlerList;
    }

    static {
        ScoreboardDestroyEvent.handlerList = new HandlerList();
    }
}
