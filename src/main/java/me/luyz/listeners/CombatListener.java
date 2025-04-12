package me.luyz.listeners;

import me.luyz.commons.CombatManager;
import me.luyz.module.ModuleService;
import me.luyz.servicies.impl.ConfigService;
import me.luyz.servicies.impl.LanguageService;
import me.luyz.utilities.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class CombatListener implements Listener {

    private final CombatManager combatManager;

    public CombatListener() {
        this.combatManager = ModuleService.getManagerModule().getCombatManager();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onCombat(final EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            final Player damager = (Player)event.getDamager();
            this.combatManager.setCombat((Player)event.getEntity(), damager);
        }
    }

    @EventHandler
    private void onDeath(final PlayerDeathEvent event) {
        if (event.getEntity() != null) {
            final Player player = event.getEntity();
            if (this.combatManager.hasCooldown(player)) {
                this.combatManager.removeCooldown(player);
            }
        }
    }

    @EventHandler
    private void onRespawn(final PlayerRespawnEvent event) {
        final Player player = event.getPlayer();
        if (this.combatManager.hasCooldown(player)) {
            this.combatManager.removeCooldown(player);
        }
    }

    @EventHandler
    private void onDisconnect(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        if (this.combatManager.hasCooldown(player)) {
            this.combatManager.removeCooldown(player);
        }
    }

    @EventHandler
    private void onCombatCommand(final PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        if (this.combatManager.hasCooldown(player)) {
            final String command = event.getMessage().toLowerCase().replace("/", "");
            for (final String disableCommand : ConfigService.COMBAT_SYSTEM_DISABLE_COMMANDS) {
                if (command.startsWith(disableCommand)) {
                    event.setCancelled(true);
                    ChatUtil.sendMessage((CommandSender)player, LanguageService.COMBAT_MESSAGE_DISABLE_COMMAND);
                    break;
                }
            }
        }
    }
}
