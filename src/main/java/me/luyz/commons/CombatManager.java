package me.luyz.commons;

import org.bukkit.entity.*;
import me.luyz.utilities.*;
import me.luyz.servicies.impl.*;

public class CombatManager {

    private final String cooldownName;

    public CombatManager() {
        this.cooldownName = "combat-timer";
    }

    public boolean hasCooldown(final Player player) {
        return CooldownUtil.hasCooldown(this.cooldownName, player);
    }

    public String getCooldown(final Player player) {
        return JavaUtil.formatLongMin(CooldownUtil.getCooldown(this.cooldownName, player));
    }

    public void setCooldown(final Player player) {
        CooldownUtil.setCooldown(this.cooldownName, player, ConfigService.COMBAT_SYSTEM_COOLDOWN);
    }

    public void removeCooldown(final Player player) {
        CooldownUtil.removeCooldown(this.cooldownName, player);
    }

    public void setCombat(final Player player, final Player attacker) {
        this.setCooldown(player);
        this.setCooldown(attacker);
    }

}
