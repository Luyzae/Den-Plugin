package me.luyz.commons.team;

import me.luyz.module.ModuleService;
import me.luyz.utilities.file.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class TeamManager {
    private final FileConfig teamConfig;
    private final Map<String, Team> teams;
    private final Map<String, String> playerTeams;

    public TeamManager() {
        this.teamConfig = ModuleService.getFileModule().getFile("teams");
        this.teams = new HashMap<>();
        this.playerTeams = new HashMap<>();
        loadTeams();
        loadPlayerTeams();
    }

    private void loadTeams() {
        FileConfiguration config = teamConfig.getConfiguration();
        if (config.getConfigurationSection("teams") == null) return;

        for (String teamName : config.getConfigurationSection("teams").getKeys(false)) {
            String color = config.getString("teams." + teamName + ".color");
            String nexusLocationStr = config.getString("teams." + teamName + ".nexus");
            Location nexusLocation = nexusLocationStr != null ? deserializeLocation(nexusLocationStr) : null;

            teams.put(teamName, new Team(teamName, ChatColor.valueOf(color), nexusLocation));
        }
    }

    private void loadPlayerTeams() {
        FileConfiguration config = teamConfig.getConfiguration();
        if (config.getConfigurationSection("players") == null) return;

        for (String playerName : config.getConfigurationSection("players").getKeys(false)) {
            String teamName = config.getString("players." + playerName);
            if (teamName != null && teams.containsKey(teamName)) {
                playerTeams.put(playerName, teamName);
            }
        }
    }

    public boolean createTeam(String name, ChatColor color) {
        for (String existingTeam : teams.keySet()) {
            if (existingTeam.equalsIgnoreCase(name)) {
                return false;
            }
        }

        teams.put(name, new Team(name, color, null));
        saveTeam(name);
        return true;
    }

    public boolean removeTeam(String name) {
        if (!teams.containsKey(name)) return false;

        teams.remove(name);
        teamConfig.getConfiguration().set("teams." + name, null);
        teamConfig.save();
        return true;
    }

    public boolean assignNexus(String teamName, Location location) {
        Team team = teams.get(teamName);
        if (team == null) return false;

        team.setNexusLocation(location);
        saveTeam(teamName);
        return true;
    }

    public Team getTeam(String name) {
        return teams.get(name);
    }


    public Set<String> getTeams() {
        return teams.keySet();
    }


    public List<Team> getTeamsList() {
        return new ArrayList<>(teams.values());
    }


    public boolean addPlayerToTeam(String playerName, String teamName) {
        if (!teams.containsKey(teamName)) return false;

        playerTeams.put(playerName, teamName);
        savePlayerTeams();
        return true;
    }


    public String getPlayerTeam(String playerName) {
        return playerTeams.getOrDefault(playerName, null);
    }


    private void savePlayerTeams() {
        for (Map.Entry<String, String> entry : playerTeams.entrySet()) {
            teamConfig.getConfiguration().set("players." + entry.getKey(), entry.getValue());
        }
        teamConfig.save();
    }

    public void resetTeams() {
        teams.clear();
        playerTeams.clear();
        teamConfig.getConfiguration().set("teams", null);
        teamConfig.getConfiguration().set("players", null);
        teamConfig.save();
    }

    private void saveTeam(String name) {
        Team team = teams.get(name);
        teamConfig.getConfiguration().set("teams." + name + ".color", team.getColor().name());
        if (team.getNexusLocation() != null) {
            teamConfig.getConfiguration().set("teams." + name + ".nexus", serializeLocation(team.getNexusLocation()));
        }
        teamConfig.save();
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
