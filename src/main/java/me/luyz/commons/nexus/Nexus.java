package me.luyz.commons.nexus;

import me.luyz.servicies.impl.ConfigService;
import org.bukkit.Location;

public class Nexus {
    private final String teamName;
    private final Location location;
    private int health;

    public Nexus(String teamName, Location location, int health) {
        this.teamName = teamName;
        this.location = location;
        this.health = health;
    }

    public Nexus(String teamName, Location location) {
        this.teamName = teamName;
        this.location = location;
        this.health = ConfigService.NEXUS_MAX_HEALTH;
    }

    public String getTeamName() {
        return teamName;
    }

    public Location getLocation() {
        return location;
    }

    public int getHealth() {
        return health;
    }

    public void reduceHealth(int amount) {
        this.health = Math.max(0, this.health - amount);
    }
}
