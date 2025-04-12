package me.luyz.servicies.impl;

import me.luyz.servicies.Service;
import me.luyz.module.ModuleService;
import me.luyz.utilities.file.FileConfig;

public class HitDelayService extends Service {

    public static String HIT_DELAY;

    @Override
    public void initialize() {
        final FileConfig hitDelayFile = ModuleService.getFileModule().getFile("hitDelay");
        HitDelayService.HIT_DELAY = hitDelayFile.getString("hit-delay");
    }
}
