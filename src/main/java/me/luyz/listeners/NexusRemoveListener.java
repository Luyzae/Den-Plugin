package me.luyz.listeners;

import me.luyz.commons.nexus.NexusManager;
import me.luyz.commons.team.Team;
import me.luyz.commons.team.TeamManager;
import me.luyz.module.ModuleService;
import me.luyz.utilities.ChatUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NexusRemoveListener implements Listener {
    private final TeamManager teamManager;
    private final NexusManager nexusManager;

    public NexusRemoveListener() {
        this.teamManager = ModuleService.getManagerModule().getTeamManager();
        this.nexusManager = ModuleService.getManagerModule().getNexusManager();
    }

    @EventHandler
    public void onNexusRemove(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getClickedBlock();

        if (block == null) {
            return;
        }

        final ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getType() != Material.STICK || !item.hasItemMeta()) {
            return;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName() || !meta.getDisplayName().equals(ChatColor.RED + "Nexus Remover")) {
            return;
        }

        if (block.getType() != Material.ENDER_STONE && block.getType() != Material.BEDROCK) {
            event.setCancelled(true);
            return;
        }

        boolean isNexus = false;
        for (String teamName : teamManager.getTeams()) {
            if (nexusManager.getNexusLocation(teamName) != null && nexusManager.getNexusLocation(teamName).equals(block.getLocation())) {
                isNexus = true;

                block.setType(Material.AIR);
                nexusManager.removeNexus(block.getLocation());
                ChatUtil.sendMessage(player, ChatColor.GREEN + "You have removed the Nexus of team: " + teamName);
                ChatUtil.sendMessage(player,ChatColor.GREEN + "The Nexus of team " + teamName + " has been permanently removed!");
                return;
            }
        }

        if (!isNexus) {
            event.setCancelled(true);
            ChatUtil.sendMessage(player, ChatColor.RED + "This is not a valid Nexus.");
        }
    }


}
