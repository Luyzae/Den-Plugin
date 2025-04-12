package me.luyz;

import me.luyz.module.ModuleService;
import org.bukkit.plugin.java.JavaPlugin;

public class Den extends JavaPlugin {

    @Override
    public void onEnable() {

        ModuleService.start(this);
    }

    @Override
    public void onDisable() {

        ModuleService.stop();
    }

    public static Den get() {
        return (Den) getPlugin(Den.class);
    }
}