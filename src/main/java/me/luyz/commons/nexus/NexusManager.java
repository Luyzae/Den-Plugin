package me.luyz.commons.nexus;

import me.luyz.commons.team.Team;
import me.luyz.commons.team.TeamManager;
import me.luyz.module.ModuleService;
import me.luyz.servicies.impl.ConfigService;
import me.luyz.utilities.file.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class NexusManager {
    private final FileConfig teamConfig;
    private final TeamManager teamManager;
    private final Map<String, Nexus> nexuses;

    public NexusManager() {
        this.teamConfig = ModuleService.getFileModule().getFile("teams");
        this.teamManager = ModuleService.getManagerModule().getTeamManager();
        this.nexuses = new HashMap<>();
        loadNexuses();
    }

    private void loadNexuses() {
        FileConfiguration config = teamConfig.getConfiguration();
        if (config.getConfigurationSection("teams") == null) return;

        for (String teamName : config.getConfigurationSection("teams").getKeys(false)) {
            String locationStr = config.getString("teams." + teamName + ".nexus");
            int health = config.getInt("teams." + teamName + ".nexus-health", 75);

            if (locationStr != null) {
                Location location = deserializeLocation(locationStr);
                nexuses.put(teamName, new Nexus(teamName, location, health));
            }
        }
    }

    public boolean assignNexus(String teamName, Location location) {
        Team team = teamManager.getTeam(teamName);
        if (team == null) return false;

        int maxHealth = ConfigService.NEXUS_MAX_HEALTH;
        nexuses.put(teamName, new Nexus(teamName, location, maxHealth));
        saveNexus(teamName);
        return true;
    }

    public boolean damageNexus(String teamName, int damage) {
        Nexus nexus = nexuses.get(teamName);
        if (nexus == null) return false;

        nexus.reduceHealth(damage);

        saveNexus(teamName);

        return nexus.getHealth() > 0;
    }

    public Nexus getNexus(String teamName) {
        return nexuses.get(teamName);
    }

    public void removeNexus(Location location) {
        String teamToRemove = null;

        for (String teamName : nexuses.keySet()) {
            if (nexuses.get(teamName).getLocation().equals(location)) {
                teamToRemove = teamName;
                break;
            }
        }

        if (teamToRemove != null) {
            nexuses.remove(teamToRemove);
            teamConfig.getConfiguration().set("teams." + teamToRemove + ".nexus", null);
            teamConfig.getConfiguration().set("teams." + teamToRemove + ".nexus-health", null);
            teamConfig.save();
        }
    }

    private void saveNexus(String teamName) {
        Nexus nexus = nexuses.get(teamName);
        teamConfig.getConfiguration().set("teams." + teamName + ".nexus", serializeLocation(nexus.getLocation()));
        teamConfig.getConfiguration().set("teams." + teamName + ".nexus-health", nexus.getHealth());
        teamConfig.save();
    }

    public Location getNexusLocation(String teamName) {
        return nexuses.containsKey(teamName) ? nexuses.get(teamName).getLocation() : null;
    }

    private String serializeLocation(Location loc) {
        return loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ();
    }

    private Location deserializeLocation(String locString) {
        String[] parts = locString.split(",");
        return new Location(Bukkit.getWorld(parts[0]), Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
    }
}
