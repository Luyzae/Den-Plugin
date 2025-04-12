package me.luyz.commons.setup;

import me.luyz.module.ModuleService;
import me.luyz.utilities.file.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class SetupManager {
    private final FileConfig config;
    private Location preLobbyLocation;
    private final Map<String, Location> teamSpawns;

    public SetupManager() {
        this.config = ModuleService.getFileModule().getFile("config");
        this.teamSpawns = new HashMap<>();
        loadConfig();
    }

    private void loadConfig() {
        FileConfiguration fileConfig = config.getConfiguration();

        preLobbyLocation = deserializeLocation(fileConfig.getString("prelobby.location", ""));

        if (fileConfig.contains("teams")) {
            for (String team : fileConfig.getConfigurationSection("teams").getKeys(false)) {
                teamSpawns.put(team, deserializeLocation(fileConfig.getString("teams." + team + ".spawn", "")));
            }
        }
    }

    public void setPreLobbyLocation(Location location) {
        this.preLobbyLocation = location;
        config.getConfiguration().set("prelobby.location", serializeLocation(location));
        config.save();
    }

    public Location getPreLobbyLocation() {
        return preLobbyLocation;
    }

    public void setTeamSpawn(String team, Location location) {
        teamSpawns.put(team, location);
        config.getConfiguration().set("teams." + team + ".spawn", serializeLocation(location));
        config.save();
    }

    public Location getTeamSpawn(String team) {
        return teamSpawns.get(team);
    }

    private String serializeLocation(Location loc) {
        return loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + "," + loc.getPitch();
    }

    private Location deserializeLocation(String locString) {
        if (locString == null || locString.isEmpty()) return null;
        String[] parts = locString.split(",");
        return new Location(Bukkit.getWorld(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2]),
                Double.parseDouble(parts[3]), Float.parseFloat(parts[4]), Float.parseFloat(parts[5]));
    }
}
