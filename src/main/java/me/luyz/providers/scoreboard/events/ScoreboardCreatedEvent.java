package me.luyz.providers.scoreboard.events;

import org.bukkit.event.*;
import me.luyz.providers.scoreboard.*;

public class ScoreboardCreatedEvent extends Event {

    public static HandlerList handlerList;
    private boolean cancelled;
    private ScoreboardDManager board;

    public ScoreboardCreatedEvent(final ScoreboardDManager board) {
        this.cancelled = false;
        this.board = board;
    }

    public HandlerList getHandlers() {
        return ScoreboardCreatedEvent.handlerList;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public ScoreboardDManager getBoard() {
        return this.board;
    }

    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void setBoard(final ScoreboardDManager board) {
        this.board = board;
    }

    public static HandlerList getHandlerList() {
        return ScoreboardCreatedEvent.handlerList;
    }

    static {
        ScoreboardCreatedEvent.handlerList = new HandlerList();
    }
}
