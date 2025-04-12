package me.luyz.servicies.impl;

import me.luyz.module.ModuleService;
import me.luyz.servicies.Service;
import me.luyz.utilities.file.FileConfig;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class MineralService extends Service {

    private static final Map<Material, Integer> regenerationTimes = new HashMap<>();
    private static final Map<Material, Integer> xpValues = new HashMap<>();

    @Override
    public void initialize() {
        final FileConfig mineralFile = ModuleService.getFileModule().getFile("minerals");

        regenerationTimes.clear();
        xpValues.clear();

        if (mineralFile.getConfiguration().getConfigurationSection("minerals") != null) {
            for (String key : mineralFile.getConfiguration().getConfigurationSection("minerals").getKeys(false)) {
                try {
                    Material material = Material.valueOf(key.toUpperCase());

                    Integer regenTime = mineralFile.getConfiguration().contains("minerals." + key + ".regen-time")
                            ? mineralFile.getConfiguration().getInt("minerals." + key + ".regen-time")
                            : 10;

                    Integer xp = mineralFile.getConfiguration().contains("minerals." + key + ".xp")
                            ? mineralFile.getConfiguration().getInt("minerals." + key + ".xp")
                            : 5;

                    regenerationTimes.put(material, regenTime);
                    xpValues.put(material, xp);
                } catch (IllegalArgumentException e) {
                    System.out.println("[Den] Warning: Invalid material in minerals.yml: " + key);
                }
            }
        }
    }

    public static int getRegenerationTime(Material material) {
        return regenerationTimes.getOrDefault(material, 10);
    }

    public static int getXPValue(Material material) {
        return xpValues.getOrDefault(material, 5);
    }

    public static boolean isRegisteredMineral(Material material) {
        return regenerationTimes.containsKey(material);
    }

}
