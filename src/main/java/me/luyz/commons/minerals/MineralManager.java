package me.luyz.commons.minerals;

import me.luyz.module.ModuleService;
import me.luyz.utilities.file.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class MineralManager {
    private final FileConfig mineralConfig;
    private final Map<Material, Integer> mineralRegenerationTimes;
    private final Map<Material, Integer> mineralXPValues;

    public MineralManager() {
        this.mineralConfig = ModuleService.getFileModule().getFile("minerals");
        this.mineralRegenerationTimes = new HashMap<>();
        this.mineralXPValues = new HashMap<>();
        loadMinerals();
    }

    private void loadMinerals() {
        FileConfiguration config = mineralConfig.getConfiguration();
        if (config.getConfigurationSection("minerals") == null) return;

        for (String key : config.getConfigurationSection("minerals").getKeys(false)) {
            try {
                Material material = Material.valueOf(key.toUpperCase());
                int regenTime = config.getInt("minerals." + key + ".regen-time", 10);
                int xp = config.getInt("minerals." + key + ".xp", 5);

                mineralRegenerationTimes.put(material, regenTime);
                mineralXPValues.put(material, xp);
            } catch (IllegalArgumentException e) {
                Bukkit.getLogger().warning("[Den] Invalid material in minerals.yml: " + key);
            }
        }
    }

    public int getRegenerationTime(Material material) {
        return mineralRegenerationTimes.getOrDefault(material, 10);
    }

    public int getXPValue(Material material) {
        return mineralXPValues.getOrDefault(material, 5);
    }

    public boolean isRegisteredMineral(Material material) {
        return mineralRegenerationTimes.containsKey(material);
    }

    public void startRegeneration(Location location, Material originalMaterial, Runnable onRegenerationComplete) {
        int regenTime = getRegenerationTime(originalMaterial);
        new BukkitRunnable() {
            @Override
            public void run() {
                location.getBlock().setType(originalMaterial);
                onRegenerationComplete.run();
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugin("Den"), regenTime * 20L);
    }

}
