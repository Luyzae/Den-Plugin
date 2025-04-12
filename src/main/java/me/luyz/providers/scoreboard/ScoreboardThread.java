package me.luyz.providers.scoreboard;

import org.bukkit.entity.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.scoreboard.*;

public class ScoreboardThread extends Thread {

    private final ScoreboardD scoreboard;


    ScoreboardThread(final ScoreboardD scoreboard) {
        this.scoreboard = scoreboard;
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.tick();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(this.scoreboard.getTicks() * 50L);
            }
            catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
    }

    private void tick(){
        for (final Player player : Bukkit.getServer().getOnlinePlayers()){
            try {
                final ScoreboardDManager board = this.scoreboard.getBoards().get(player.getUniqueId());
                if (board == null) {
                    continue;
                }
                final org.bukkit.scoreboard.Scoreboard scoreboard = board.getScoreboard();
                final Objective objective = board.getObjective();
                if (scoreboard == null || objective == null) {
                    continue;
                }
                final String title = ChatColor.translateAlternateColorCodes('&', this.scoreboard.getAdapter().getTitle(player));
                if (title.length() > 32) {
                    objective.setDisplayName(title.substring(0, 32));
                } else {
                    objective.setDisplayName(title);
                }

                if (!objective.getDisplayName().equals(title)) {
                    objective.setDisplayName(title);
                }
                List<String> newLines = this.scoreboard.getAdapter().getLines(player);
                if (newLines == null || newLines.isEmpty()) {
                    for (final ScoreboardEntry entry : board.getEntries()) {
                        entry.remove();
                    }
                    board.getEntries().clear();
                }
                else {
                    if (this.scoreboard.getAdapter().getLines(player).size() > 15) {
                        newLines = this.scoreboard.getAdapter().getLines(player).subList(0, 15);
                    }
                    if (!this.scoreboard.getAssembleStyle().isDescending()) {
                        Collections.reverse(newLines);
                    }
                    if (board.getEntries().size() > newLines.size()) {
                        for (int i = newLines.size(); i < board.getEntries().size(); ++i) {
                            final ScoreboardEntry entry = board.getEntryAtPosition(i);
                            if (entry != null) {
                                entry.remove();
                            }
                        }
                    }
                    int cache = this.scoreboard.getAssembleStyle().getStartNumber();
                    for (int j = 0; j < newLines.size(); ++j) {
                        ScoreboardEntry entry2 = board.getEntryAtPosition(j);
                        final String line = ChatColor.translateAlternateColorCodes('&', (String)newLines.get(j));
                        if (entry2 == null) {
                            entry2 = new ScoreboardEntry(board, line, j);
                        }
                        entry2.setText(line);
                        entry2.setup();
                        entry2.send(this.scoreboard.getAssembleStyle().isDescending() ? cache-- : cache++);
                    }
                }
                if (player.getScoreboard() == scoreboard || this.scoreboard.isHook()) {
                    continue;
                }
                player.setScoreboard(scoreboard);


            }catch (Exception ex) {}
        }
    }
}
