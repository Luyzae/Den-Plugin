package me.luyz.utilities.event;

import org.bukkit.event.*;

public class CustomEvent extends Event
{
    private static final HandlerList handlers;

    public static HandlerList getHandlerList() {
        return CustomEvent.handlers;
    }

    public HandlerList getHandlers() {
        return CustomEvent.handlers;
    }

    static {
        handlers = new HandlerList();
    }
}
