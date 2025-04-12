package me.luyz.listeners;

import me.luyz.module.ModuleService;
import me.luyz.utilities.file.FileConfig;
import me.luyz.utilities.cuboid.Cuboid;
import me.luyz.utilities.cuboid.CuboidUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class PreLobbyRestrictionsListener implements Listener {
    private final Plugin plugin;
    private Cuboid preLobbyRegion;
    private final FileConfig config;

    public PreLobbyRestrictionsListener(Plugin plugin) {
        this.plugin = plugin;
        this.config = ModuleService.getFileModule().getFile("config");
        loadPreLobbyRegion();
    }

    private void loadPreLobbyRegion() {
        String serializedCuboid = config.getConfiguration().getString("prelobby.region");

        if (serializedCuboid != null && !serializedCuboid.isEmpty()) {
            this.preLobbyRegion = CuboidUtil.deserializeCuboid(serializedCuboid);
        }
    }

    public void reloadPreLobbyRegion() {
        loadPreLobbyRegion();
    }

    private boolean isInPreLobby(Location location) {
        return preLobbyRegion != null && preLobbyRegion.contains(location);
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (isInPreLobby(player.getLocation())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (isInPreLobby(player.getLocation())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (isInPreLobby(player.getLocation())) {
            if (event.getSlotType() != InventoryType.SlotType.OUTSIDE) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (isInPreLobby(player.getLocation())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (isInPreLobby(player.getLocation())) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                event.setCancelled(true);
            }
        }
    }
}
