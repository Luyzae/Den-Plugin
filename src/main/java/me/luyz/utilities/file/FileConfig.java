package me.luyz.utilities.file;

import org.bukkit.plugin.java.*;
import java.io.*;
import org.bukkit.configuration.file.*;
import java.util.*;
import org.bukkit.*;

public class FileConfig {

    private final File file;
    private FileConfiguration configuration;

    public FileConfig(final JavaPlugin plugin, final String fileName) {
        this.file = new File(plugin.getDataFolder(), fileName);
        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs();
            if (plugin.getResource(fileName) == null) {
                try {
                    this.file.createNewFile();
                }
                catch (IOException ex) {
                    plugin.getLogger().severe("Failed to create new file " + fileName);
                }
            }
            else {
                plugin.saveResource(fileName, false);
            }
        }
        this.configuration = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
    }

    public double getDouble(final String path) {
        if (this.configuration.contains(path)) {
            return this.configuration.getDouble(path);
        }
        return 0.0;
    }

    public int getInt(final String path) {
        if (this.configuration.contains(path)) {
            return this.configuration.getInt(path);
        }
        return 0;
    }

    public boolean getBoolean(final String path) {
        return this.configuration.contains(path) && this.configuration.getBoolean(path);
    }

    public long getLong(final String path) {
        if (this.configuration.contains(path)) {
            return this.configuration.getLong(path);
        }
        return 0L;
    }

    public String getString(final String path) {
        if (this.configuration.contains(path)) {
            return ChatColor.translateAlternateColorCodes('&', this.configuration.getString(path));
        }
        return null;
    }

    public List<String> getStringList(final String path) {
        if (this.configuration.contains(path)) {
            final ArrayList<String> strings = new ArrayList<String>();
            for (final String string : this.configuration.getStringList(path)) {
                strings.add(ChatColor.translateAlternateColorCodes('&', string));
            }
            return strings;
        }
        return Collections.singletonList("ERROR: STRING LIST NOT FOUND!");
    }

    public void save() {
        try {
            this.configuration.save(this.file);
        }
        catch (IOException ex) {
            Bukkit.getLogger().severe("Could not save config file " + this.file.toString());
            ex.printStackTrace();
        }
    }

    public void reload() {
        this.configuration = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
    }

    public File getFile() {
        return this.file;
    }

    public FileConfiguration getConfiguration() {
        return this.configuration;
    }


}
