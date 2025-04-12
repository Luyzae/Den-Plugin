package me.luyz.module.impl;

import java.util.*;
import com.google.common.collect.*;
import me.luyz.module.*;
import me.luyz.utilities.file.*;
import me.luyz.*;
import me.luyz.module.Module;

public class FileModule extends Module {

    private final Map<String, FileConfig> files;

    public FileModule() {
        this.files = Maps.<String, FileConfig>newHashMap();
    }

    @Override
    public String getName() {
        return "File";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public void onEnable(final Den plugin) {

        this.files.put("config", new FileConfig(plugin, "config.yml"));

        this.files.put("scoreboard", new FileConfig(plugin, "provider/scoreboard.yml"));

        this.files.put("language", new FileConfig(plugin, "language.yml"));

        this.files.put("hitDelay", new FileConfig(plugin, "hit-delay.yml"));

        this.files.put("teams", new FileConfig(plugin, "teams.yml"));

        this.files.put("users", new FileConfig(plugin, "data/users.yml"));

        this.files.put("minerals", new FileConfig(plugin, "minerals.yml"));

    }

    public FileConfig getFile(final String name) {
        return this.files.get(name);
    }

    public void reload() {
        for (final FileConfig file : this.files.values()) {
            file.reload();
        }
    }

    public Map<String, FileConfig> getFiles() {
        return this.files;
    }
}
