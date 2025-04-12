package me.luyz.commons.team;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public class Team {
    private final String name;
    private final ChatColor color;
    private Location nexusLocation;

    public Team(String name, ChatColor color, Location nexusLocation) {
        this.name = name;
        this.color = color;
        this.nexusLocation = nexusLocation;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public Location getNexusLocation() {
        return nexusLocation;
    }

    public void setNexusLocation(Location location) {
        this.nexusLocation = location;
    }
}
