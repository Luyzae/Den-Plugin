package me.luyz.hooks.placeholder;

import me.luyz.Den;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.*;

public class PlaceholderExpansion extends me.clip.placeholderapi.expansion.PlaceholderExpansion{

    private final Den plugin;

    public PlaceholderExpansion(final Den plugin) {
        this.plugin = plugin;
    }


    public boolean persist() {
        return true;
    }

    public boolean canRegister() {
        return true;
    }

    @NotNull
    public String getAuthor() {
        return "Luyz";
    }

    @NotNull
    public String getIdentifier() {
        return "Den";
    }

    @NotNull
    public String getVersion() {
        return this.plugin.getDescription().getVersion();
    }

    public String onPlaceholderRequest(final Player player, @NotNull final String identifier){

        if (player == null) {
            return "";
        }


        return null;
    }


}