package me.luyz.hooks.placeholder;

import me.luyz.*;
import org.bukkit.*;

public final class PlaceholderHook {

    private static boolean enabled;

    public static void initialize(final Den plugin) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            final PlaceholderExpansion papi = new PlaceholderExpansion(plugin);
            if (!papi.isRegistered()) {
                papi.register();
            }
            PlaceholderHook.enabled = true;
        }
    }

    private PlaceholderHook() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static boolean isEnabled() {
        return PlaceholderHook.enabled;
    }


}