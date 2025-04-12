package me.luyz.listeners;

import me.luyz.servicies.impl.MineralService;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class MineralListener implements Listener {
    private final Set<Location> unbreakableCobblestone = new HashSet<>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location location = block.getLocation();
        Material originalMaterial = block.getType();

        if (MineralService.isRegisteredMineral(originalMaterial)) {

            if (player.getGameMode() == GameMode.CREATIVE && player.isSneaking()) {
                return;
            }

            int xp = MineralService.getXPValue(originalMaterial);
            player.giveExp(xp);

            ItemStack drop = new ItemStack(getDropMaterial(originalMaterial), 1);
            player.getInventory().addItem(drop);

            event.setCancelled(true);
            block.setType(Material.COBBLESTONE);

            unbreakableCobblestone.add(location);

            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

            startRegeneration(location, originalMaterial);
        }
    }


    private void startRegeneration(Location location, Material originalMaterial) {
        int regenTime = MineralService.getRegenerationTime(originalMaterial);
        new BukkitRunnable() {
            @Override
            public void run() {
                location.getBlock().setType(originalMaterial);
                unbreakableCobblestone.remove(location);
            }
        }.runTaskLater(org.bukkit.Bukkit.getPluginManager().getPlugin("Den"), regenTime * 20L);
    }

    @EventHandler
    public void onBlockBreakCobblestone(BlockBreakEvent event) {
        if (unbreakableCobblestone.contains(event.getBlock().getLocation())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        event.blockList().removeIf(block -> unbreakableCobblestone.contains(block.getLocation()));
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent event) {
        if (unbreakableCobblestone.contains(event.getBlock().getLocation())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {
        event.getBlocks().removeIf(block -> unbreakableCobblestone.contains(block.getLocation()));
    }

    private Material getDropMaterial(Material mineral) {
        switch (mineral) {
            case IRON_ORE:
            case GOLD_ORE:
                return mineral;
            case DIAMOND_ORE:
                return Material.DIAMOND;
            case EMERALD_ORE:
                return Material.EMERALD;
            case LAPIS_ORE:
                return Material.INK_SACK;
            case REDSTONE_ORE:
                return Material.REDSTONE;
            case QUARTZ_ORE:
                return Material.QUARTZ;
            default:
                return mineral;
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Material placedBlock = event.getBlock().getType();

        if (MineralService.isRegisteredMineral(placedBlock) && player.getGameMode() == GameMode.SURVIVAL) {
            event.setCancelled(true);
        }
    }

}
