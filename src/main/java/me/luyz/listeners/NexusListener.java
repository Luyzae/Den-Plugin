package me.luyz.listeners;

import me.luyz.commons.team.Team;
import me.luyz.commons.team.TeamManager;
import me.luyz.commons.nexus.NexusManager;
import me.luyz.module.ModuleService;
import me.luyz.utilities.ChatUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class NexusListener implements Listener {
    private final TeamManager teamManager;
    private final NexusManager nexusManager;

    public NexusListener() {
        this.teamManager = ModuleService.getManagerModule().getTeamManager();
        this.nexusManager = ModuleService.getManagerModule().getNexusManager();
    }

    @EventHandler
    public void onNexusPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = event.getItemInHand();
        Location location = event.getBlock().getLocation();

        if (itemInHand.getType() == Material.ENDER_STONE && itemInHand.hasItemMeta()) {
            ItemMeta meta = itemInHand.getItemMeta();
            if (meta != null && meta.hasDisplayName()) {
                String displayName = ChatColor.stripColor(meta.getDisplayName());

                if (displayName.startsWith("Nexus Block (")) {
                    String teamName = displayName.substring(13, displayName.length() - 1);
                    Team team = teamManager.getTeam(teamName);

                    if (team == null) {
                        ChatUtil.sendMessage(player, ChatColor.RED + "This team does not exist.");
                        event.setCancelled(true);
                        return;
                    }

                    nexusManager.assignNexus(teamName, location);
                    ChatUtil.sendMessage(player, ChatColor.GREEN + "Nexus location set for team " + team.getColor() + teamName + ChatColor.GREEN + ".");
                }
            }
        }
    }
}
