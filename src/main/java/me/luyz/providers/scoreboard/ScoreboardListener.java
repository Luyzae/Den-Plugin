package me.luyz.providers.scoreboard;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import me.luyz.providers.scoreboard.events.*;

public class ScoreboardListener implements Listener {

    private final ScoreboardD scoreboard;

    public ScoreboardListener(final ScoreboardD scoreboard) {
        this.scoreboard = scoreboard;
    }

    @EventHandler
    public void onPlayerScoreboardJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final ScoreboardCreateEvent createEvent = new ScoreboardCreateEvent(player);
        Bukkit.getPluginManager().callEvent((Event)createEvent);
        if (createEvent.isCancelled()) {
            return;
        }
        this.scoreboard.getBoards().put(player.getUniqueId(), new ScoreboardDManager(player, this.scoreboard));
    }

    @EventHandler
    public void onPlayerScoreboardQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final ScoreboardDestroyEvent destroyEvent = new ScoreboardDestroyEvent(player);
        Bukkit.getPluginManager().callEvent((Event)destroyEvent);
        if (destroyEvent.isCancelled()) {
            return;
        }
        this.scoreboard.getBoards().remove(player.getUniqueId());
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }

    public ScoreboardD getScoreboard() {
        return this.scoreboard;
    }


}
