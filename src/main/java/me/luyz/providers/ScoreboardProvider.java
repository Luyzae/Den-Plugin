package me.luyz.providers;


import me.luyz.commons.game.GameManager;
import me.luyz.commons.game.GameState;
import me.luyz.commons.setup.SetupSelection;
import me.luyz.commons.timer.Timer;
import me.luyz.module.*;
import me.luyz.utilities.cuboid.Cuboid;
import org.bukkit.entity.*;
import com.google.common.collect.*;
import me.luyz.providers.scoreboard.*;
import me.luyz.commons.timer.*;
import java.util.*;
import me.luyz.servicies.impl.*;
import me.luyz.utilities.*;

import java.util.List;

public class ScoreboardProvider implements ScoreboardAdapter {

    private final TimerManager timerManager;
    private final GameManager gameManager;

    public ScoreboardProvider() {
        this.gameManager = ModuleService.getManagerModule().getGameManager();
        this.timerManager = ModuleService.getManagerModule().getTimerManager();
    }

    @Override
    public String getTitle(final Player player) {
        return ScoreboardAnimated.TITLE;
    }

    @Override
    public List<String> getLines(final Player player) {
        final List<String> lines = Lists.newArrayList();
        GameState gameState = gameManager.getGameState();
        Cuboid preLobbyRegion = SetupSelection.getPreLobbyRegion();

        boolean isInPreLobby = preLobbyRegion != null && preLobbyRegion.contains(player.getLocation());

        if (gameState == GameState.WAITING || gameState == GameState.STARTING) {
            if (isInPreLobby) {
                return ChatUtil.placeholder(player, this.getWaitingScoreboard(player, lines));
            }
        }

        if (gameState == GameState.IN_PROGRESS) {
            return ChatUtil.placeholder(player, this.getGameScoreboard(player, lines));
        }

        return new ArrayList<>();
    }

    @Override
    public ScoreboardStyle getBoardStyle(final Player player) {
        return ScoreboardStyle.KOHI;
    }

    private List<String> getPrincipalScoreboard(final Player player, final List<String> lines) {
        for (final String line : ScoreboardService.PRINCIPAL) {
            if (line.contains("<footer>")) {
                lines.add(ScoreboardAnimated.FOOTER);
            }
            else if (line.contains("<timers>")) {
                for (final Timer timer : this.timerManager.getTimers().values()) {
                    for (final String timerLine : ScoreboardService.TIMERS) {
                        lines.add(timerLine.replace("<timer-name>", timer.getPrefix()).replace("<timer-time>", JavaUtil.formatLongHour(timer.getRemaining())));
                    }
                }
            }
        }
        return lines;
    }

    private List<String> getWaitingScoreboard(final Player player, final List<String> lines) {
        String kit = "None";
        String map = "None";
        String team = gameManager.getTeamManager().getPlayerTeam(player.getName());
        if (team == null) team = "None";

        int countdown = gameManager.getCountdown();
        String date = ServerUtil.getDate();
        String hour = ServerUtil.getHour();

        for (final String line : ScoreboardService.WAITING) {
            if (line.contains("<footer>")) {
                lines.add(ScoreboardAnimated.FOOTER);
            }
            else if (line.contains("<players>")) {
                lines.add(line.replace("<players>", String.valueOf(gameManager.getPlayersInPreLobby().size()))
                        .replace("<max-players>", String.valueOf(ConfigService.PLAYERS_JOIN_GAME)));
            }
            else if (line.contains("<starting-in>")) {
                lines.add(line.replace("<starting-in>", gameManager.getGameState() == GameState.STARTING ? countdown + "s" : "Waiting..."));
            }
            else if (line.contains("<kit>")) {
                lines.add(line.replace("<kit>", kit));
            }
            else if (line.contains("<team>")) {
                lines.add(line.replace("<team>", team));
            }
            else if (line.contains("<map>")) {
                lines.add(line.replace("<map>", map));
            }
            else if (line.contains("<date>") && line.contains("<hour>")) {
                lines.add(line.replace("<date>", date).replace("<hour>", hour));
            }
            else if (line.contains("<date>")) {
                lines.add(line.replace("<date>", date));
            }
            else if (line.contains("<hour>")) {
                lines.add(line.replace("<hour>", hour));
            }
            else if (line.contains("<timers>")) {
                for (final Timer timer : this.timerManager.getTimers().values()) {
                    for (final String timerLine : ScoreboardService.TIMERS) {
                        lines.add(timerLine.replace("<timer-name>", timer.getPrefix()).replace("<timer-time>", JavaUtil.formatLongHour(timer.getRemaining())));
                    }
                }
            }
            else {
                lines.add(line);
            }
        }
        return lines;
    }



    private List<String> getGameScoreboard(final Player player, final List<String> lines) {
        return lines;
    }


}

