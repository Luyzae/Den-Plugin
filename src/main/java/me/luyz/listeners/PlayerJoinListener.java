package me.luyz.listeners;

import me.luyz.commons.game.GameManager;
import me.luyz.commons.game.GameState;
import me.luyz.module.ModuleService;
import me.luyz.servicies.impl.ConfigService;
import me.luyz.servicies.impl.LanguageService;
import me.luyz.user.User;
import me.luyz.user.UserManager;
import me.luyz.utilities.ChatUtil;
import me.luyz.utilities.DelaySetup;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final UserManager userManager;
    private final GameManager gameManager;

    public PlayerJoinListener() {
        this.userManager = ModuleService.getManagerModule().getUserManager();
        this.gameManager = ModuleService.getManagerModule().getGameManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String name = player.getName();

        if (Bukkit.getOnlinePlayers().size() >= ConfigService.PLAYERS_JOIN_GAME) {
            player.kickPlayer(ChatColor.RED + "The game is full! Maximum players: " + ConfigService.PLAYERS_JOIN_GAME);
            return;
        }

        DelaySetup.setUp(player);

        if (userManager.getUser(uuid) == null) {
            User user = new User(uuid, name);
            userManager.addUser(user);
        }

        User user = userManager.getUser(uuid);
        Location preLobbyLocation = deserializeLocation(ConfigService.PRE_LOBBY_LOCATION);

        if (user.getTeam() == null && preLobbyLocation != null) {
            user.setInPreLobby(true);
            player.teleport(preLobbyLocation);
            ChatUtil.sendMessage(player, LanguageService.WELCOME_MESSAGE_PRE_LOBBY);

            gameManager.addPlayerToPreLobby(player);
        } else {
            user.setInPreLobby(false);
        }
    }

    private Location deserializeLocation(String locString) {
        if (locString == null || locString.isEmpty()) return null;
        String[] parts = locString.split(",");
        return new Location(
                Bukkit.getWorld(parts[0]),
                Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2]),
                Double.parseDouble(parts[3]),
                Float.parseFloat(parts[4]),
                Float.parseFloat(parts[5])
        );
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        event.setQuitMessage(null);

        Player player = event.getPlayer();
        gameManager.removePlayerFromPreLobby(player);
    }
}
