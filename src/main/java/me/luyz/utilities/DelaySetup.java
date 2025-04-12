package me.luyz.utilities;

import me.luyz.servicies.impl.HitDelayService;
import org.bukkit.entity.*;
import org.bukkit.attribute.*;

public class DelaySetup {

    public static void setUp(final Player p) {
        p.setMaximumNoDamageTicks(Integer.parseInt(HitDelayService.HIT_DELAY));
        p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16.0);
    }

}
