package me.luyz.commons.setup;

import me.luyz.module.ModuleService;
import me.luyz.utilities.cuboid.Cuboid;
import me.luyz.utilities.cuboid.CuboidUtil;
import me.luyz.utilities.file.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class SetupSelection {
    private Location location1;
    private Location location2;
    private final SetupType setupType;

    private static Cuboid preLobbyRegion;


    public SetupSelection(SetupType setupType) {
        this.setupType = setupType;
    }

    public static SetupSelection createOrGetSelection(Plugin plugin, Player player, SetupType setupType) {
        if (player.hasMetadata("setup-selection")) {
            return (SetupSelection) player.getMetadata("setup-selection").get(0).value();
        }
        SetupSelection selection = new SetupSelection(setupType);
        player.setMetadata("setup-selection", new FixedMetadataValue(plugin, selection));
        return selection;
    }

    public boolean isFullSelected() {
        return location1 != null && location2 != null;
    }

    public Cuboid getCuboid() {
        return isFullSelected() ? new Cuboid(location1, location2) : null;
    }

    public void clear(Player player) {
        this.location1 = null;
        this.location2 = null;
        player.removeMetadata("setup-selection", player.getServer().getPluginManager().getPlugin("Den"));
    }

    public Location getLocation1() {
        return location1;
    }

    public Location getLocation2() {
        return location2;
    }

    public void setLocation1(Location location) {
        this.location1 = location;
    }

    public void setLocation2(Location location) {
        this.location2 = location;
    }

    public SetupType getSetupType() {
        return setupType;
    }

    private boolean notified;

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public static void setPreLobbyRegion(Location loc1, Location loc2) {
        preLobbyRegion = new Cuboid(loc1, loc2);
    }

    public static Cuboid getPreLobbyRegion() {
        if (preLobbyRegion == null) {
            loadPreLobbyRegion();
        }
        return preLobbyRegion;
    }

    public static void loadPreLobbyRegion() {
        FileConfig config = ModuleService.getFileModule().getFile("config");
        String serializedCuboid = config.getConfiguration().getString("prelobby.region");

        if (serializedCuboid != null) {
            preLobbyRegion = CuboidUtil.deserializeCuboid(serializedCuboid);
        }
    }



}
