package me.luyz.listeners;

import me.luyz.commons.nexus.Nexus;
import me.luyz.commons.nexus.NexusManager;
import me.luyz.commons.team.TeamManager;
import me.luyz.module.ModuleService;
import me.luyz.servicies.impl.LanguageService;
import me.luyz.utilities.ChatUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class NexusDamageListener implements Listener {
    private final TeamManager teamManager;
    private final NexusManager nexusManager;

    public NexusDamageListener() {
        this.teamManager = ModuleService.getManagerModule().getTeamManager();
        this.nexusManager = ModuleService.getManagerModule().getNexusManager();
    }

    @EventHandler
    public void onNexusDamage(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location location = block.getLocation();

        for (String teamName : teamManager.getTeams()) {
            Nexus nexus = nexusManager.getNexus(teamName);

            if (nexus != null && nexus.getLocation().equals(location)) {
                event.setCancelled(true);

                String playerTeamName = teamManager.getPlayerTeam(player.getName());
                if (playerTeamName != null && playerTeamName.equalsIgnoreCase(teamName)) {
                    ChatUtil.sendMessage(player, LanguageService.NEXUS_CANNOT_DAMAGE_OWN);
                    return;
                }

                boolean stillAlive = nexusManager.damageNexus(teamName, 1);
                int updatedHealth = nexus.getHealth();

                if (stillAlive) {
                    ChatUtil.sendMessage(player, LanguageService.NEXUS_DAMAGED.replace("<health>", String.valueOf(updatedHealth)));
                } else {
                    ChatUtil.broadcast(LanguageService.NEXUS_DESTROYED.replace("<team>", teamName));
                    block.setType(Material.BEDROCK);
                }
            }
        }
    }
}