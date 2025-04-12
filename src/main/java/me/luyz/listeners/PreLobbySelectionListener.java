package me.luyz.listeners;

import me.luyz.commons.setup.SetupSelection;
import me.luyz.commons.setup.SetupType;
import me.luyz.utilities.ChatUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PreLobbySelectionListener implements Listener {

    @EventHandler
    public void onPreLobbySelection(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType() != Material.DIAMOND_HOE || !item.getItemMeta().hasDisplayName()) {
            return;
        }

        String itemName = ChatColor.stripColor(item.getItemMeta().getDisplayName());
        if (!itemName.equals("Pre-Lobby Region Selector")) {
            return;
        }

        event.setCancelled(true);

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        SetupSelection selection = SetupSelection.createOrGetSelection(
                player.getServer().getPluginManager().getPlugin("Den"), player, SetupType.PRELOBBY);
        Location clickedLocation = event.getClickedBlock() != null ? event.getClickedBlock().getLocation() : null;

        if (clickedLocation == null) {
            return;
        }

        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (selection.getLocation1() == null || !selection.getLocation1().equals(clickedLocation)) {
                selection.setLocation1(clickedLocation);
                ChatUtil.sendMessage(player, ChatColor.GREEN + "First position set for Pre-Lobby.");
                selection.setNotified(false);
            }
        }

        else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (selection.getLocation2() == null || !selection.getLocation2().equals(clickedLocation)) {
                selection.setLocation2(clickedLocation);
                ChatUtil.sendMessage(player, ChatColor.GREEN + "Second position set for Pre-Lobby.");
                selection.setNotified(false);
            }
        }

        if (selection.isFullSelected() && !selection.isNotified()) {
            selection.setNotified(true);
            ChatUtil.sendMessage(player, ChatColor.AQUA + "Pre-Lobby region successfully selected!");
        }
    }
}
