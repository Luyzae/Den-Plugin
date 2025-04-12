package me.luyz.user;

import me.luyz.module.ModuleService;
import me.luyz.utilities.file.FileConfig;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class UserManager {
    private final FileConfig userConfig;
    private final Map<UUID, User> users;

    public UserManager() {
        this.userConfig = ModuleService.getFileModule().getFile("users");
        this.users = new HashMap<>();
        loadUsers();
    }

    private void loadUsers() {
        FileConfiguration config = userConfig.getConfiguration();
        if (config.getConfigurationSection("users") == null) return;

        for (String uuidStr : config.getConfigurationSection("users").getKeys(false)) {
            UUID uuid = UUID.fromString(uuidStr);
            String name = config.getString("users." + uuidStr + ".name");
            String team = config.getString("users." + uuidStr + ".team", null);
            String kit = config.getString("users." + uuidStr + ".kit", null);
            int kills = config.getInt("users." + uuidStr + ".kills", 0);
            int deaths = config.getInt("users." + uuidStr + ".deaths", 0);
            int wins = config.getInt("users." + uuidStr + ".wins", 0);
            boolean inPreLobby = config.getBoolean("users." + uuidStr + ".pre-lobby", true);

            User user = new User(uuid, name);
            user.setTeam(team);
            user.setKit(kit);
            while (kills-- > 0) user.addKill();
            while (deaths-- > 0) user.addDeath();
            while (wins-- > 0) user.addWin();
            user.setInPreLobby(inPreLobby);

            users.put(uuid, user);
        }
    }

    public void saveUsers() {
        FileConfiguration config = userConfig.getConfiguration();

        for (UUID uuid : users.keySet()) {
            User user = users.get(uuid);
            String uuidStr = uuid.toString();

            config.set("users." + uuidStr + ".name", user.getName());
            config.set("users." + uuidStr + ".team", user.getTeam());
            config.set("users." + uuidStr + ".kit", user.getKit());
            config.set("users." + uuidStr + ".kills", user.getKills());
            config.set("users." + uuidStr + ".deaths", user.getDeaths());
            config.set("users." + uuidStr + ".wins", user.getWins());
            config.set("users." + uuidStr + ".pre-lobby", user.isInPreLobby());
        }

        userConfig.save();
    }


    public User getUser(UUID uuid) {
        return users.get(uuid);
    }


    public void addUser(User user) {
        users.putIfAbsent(user.getUuid(), user);
        saveUsers();
    }

    public void resetUserStats(UUID uuid) {
        if (users.containsKey(uuid)) {
            User user = users.get(uuid);
            user.setTeam(null);
            user.setKit(null);
            user.setInPreLobby(true);
            user.resetStats();
            saveUsers();
        }
    }

    public void resetAllUsers() {
        for (User user : users.values()) {
            user.setTeam(null);
            user.setKit(null);
            user.setInPreLobby(true);
            user.resetStats();
        }
        saveUsers();
    }
}
