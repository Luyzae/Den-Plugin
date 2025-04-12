package me.luyz.module.impl;



import me.luyz.listeners.*;
import me.luyz.providers.ScoreboardProvider;
import me.luyz.providers.scoreboard.ScoreboardListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.*;
import me.luyz.Den;

import me.luyz.module.Module;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenerModule extends Module {

    private PreLobbyRestrictionsListener preLobbyRestrictionsListener;




    @Override
    public String getName() {
        return "Listener";
    }

    @Override
    public int getPriority() {
        return 4;
    }

    @Override
    public void onEnable(final Den plugin) {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        this.preLobbyRestrictionsListener = new PreLobbyRestrictionsListener(plugin);

        pluginManager.registerEvents((Listener) new PlayerJoinListener(), (Plugin) plugin);

        pluginManager.registerEvents((Listener) new NexusListener(), (Plugin) plugin);

        pluginManager.registerEvents((Listener) new NexusDamageListener(), (Plugin) plugin);

        pluginManager.registerEvents((Listener) new NexusRemoveListener(), (Plugin) plugin);

        pluginManager.registerEvents((Listener) new PlayerDeathListener(), (Plugin) plugin);

        pluginManager.registerEvents((Listener) new PreLobbySelectionListener(), (Plugin) plugin);

        pluginManager.registerEvents(new PreLobbyHotbarListener(plugin), plugin);

        pluginManager.registerEvents(preLobbyRestrictionsListener, plugin);

        pluginManager.registerEvents((Listener) new MineralListener(), (Plugin) plugin);

    }

    public PreLobbyRestrictionsListener getPreLobbyRestrictionsListener() {
        return preLobbyRestrictionsListener;
    }

}
