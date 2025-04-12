package me.luyz.module.impl;

import me.luyz.module.*;
import me.luyz.*;
import me.luyz.servicies.impl.*;
import me.luyz.servicies.*;

public class ServiceModule extends Module {

    @Override
    public String getName() {
        return "Service";
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public void onEnable(final Den plugin) {
        new ConfigService().initialize();
        new LanguageService().initialize();
        new ScoreboardService().initialize();
        new HitDelayService().initialize();
        new MineralService().initialize();
    }

    public void reload() {
        for (final Service service : Service.getServices()) {
            service.initialize();
        }
    }
}
