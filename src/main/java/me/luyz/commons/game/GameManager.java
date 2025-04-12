package me.luyz.commons.game;

import me.luyz.Den;
import me.luyz.commons.team.TeamManager;
import me.luyz.module.ModuleService;
import me.luyz.servicies.impl.ConfigService;
import me.luyz.utilities.ChatUtil;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class GameManager {
    private final Den plugin;
    private GameState gameState;
    private GameTask gameTask;
    private final Set<Player> playersInPreLobby;
    private final int minPlayersToStart;

    public GameManager(Den plugin) {
        this.plugin = plugin;
        this.gameState = GameState.WAITING;
        this.playersInPreLobby = new HashSet<>();
        this.minPlayersToStart = ConfigService.PLAYERS_START_GAME;
    }


    public void addPlayerToPreLobby(Player player) {
        if (gameState != GameState.WAITING) return;

        playersInPreLobby.add(player);
        ChatUtil.broadcast("&a" + player.getName() + " has joined (&b" + playersInPreLobby.size() + "/" + ConfigService.PLAYERS_JOIN_GAME + "&a)");

        if (playersInPreLobby.size() >= minPlayersToStart) {
            startCountdown();
        }
    }

    public void removePlayerFromPreLobby(Player player) {
        playersInPreLobby.remove(player);

        if (gameState == GameState.STARTING && playersInPreLobby.size() < minPlayersToStart) {
            cancelCountdown();
        }
    }

    public void startGame() {
        if (gameState != GameState.STARTING) return;

        gameState = GameState.IN_PROGRESS;
        ChatUtil.broadcast("&aThe game has started!");

    }


    public void startCountdown() {
        if (gameState != GameState.WAITING) return;

        gameState = GameState.STARTING;
        ChatUtil.broadcast("&eThe game will start soon!");


        gameTask = new GameTask(this);
        gameTask.runTaskTimer(plugin, 0L, 20L);
    }

    public void cancelCountdown() {
        if (gameTask != null) {
            gameTask.cancel();
            gameTask = null;
        }
        gameState = GameState.WAITING;
        ChatUtil.broadcast("&cNot enough players! Countdown cancelled.");
    }

    public void removePlayer(Player player) {
        playersInPreLobby.remove(player);

        if (gameState == GameState.STARTING && playersInPreLobby.size() < minPlayersToStart) {
            setGameState(GameState.WAITING);
            cancelCountdown();
            ChatUtil.broadcast("&cNot enough players! Countdown restarted.");
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public boolean isGameInProgress() {
        return gameState == GameState.IN_PROGRESS;
    }

    public TeamManager getTeamManager() {
        return ModuleService.getManagerModule().getTeamManager();
    }

    public Set<Player> getPlayersInPreLobby() {
        return playersInPreLobby;
    }

    public int getMinPlayersToStart() {
        return minPlayersToStart;
    }


    public void setGameState(GameState newState) {
        this.gameState = newState;
    }


    public int getCountdown() {
        return gameTask != null ? gameTask.getCountdown() : 0;
    }


}
