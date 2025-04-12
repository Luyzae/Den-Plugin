package me.luyz.providers.scoreboard;

import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.scoreboard.*;
import me.luyz.providers.scoreboard.events.*;
import org.bukkit.event.*;
import org.bukkit.*;

public class ScoreboardDManager {

    private final List<ScoreboardEntry> entries;
    private final List<String> identifiers;
    private final UUID uuid;
    private Scoreboard scoreboard;
    private Objective objective;
    private me.luyz.providers.scoreboard.ScoreboardD assemble;

    public ScoreboardDManager(final Player player, final me.luyz.providers.scoreboard.ScoreboardD assemble) {
        this.entries = new ArrayList<ScoreboardEntry>();
        this.identifiers = new ArrayList<String>();
        this.uuid = player.getUniqueId();
        this.assemble = assemble;
        this.setup(player);
        this.assemble = assemble;
    }

    public Scoreboard getScoreboard() {
        final Player player = Bukkit.getPlayer(this.getUuid());
        if (this.getAssemble().isHook() || player.getScoreboard() != Bukkit.getScoreboardManager().getMainScoreboard()) {
            return player.getScoreboard();
        }
        return Bukkit.getScoreboardManager().getNewScoreboard();
    }

    public Objective getObjective() {
        final Scoreboard scoreboard = this.getScoreboard();
        if (scoreboard.getObjective("Scoreboard") == null) {
            final Objective objective = scoreboard.registerNewObjective("Scoreboard", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(this.getAssemble().getAdapter().getTitle(Bukkit.getPlayer(this.getUuid())));
            return objective;
        }
        return scoreboard.getObjective("Scoreboard");
    }

    private void setup(final Player player) {
        final Scoreboard scoreboard = this.getScoreboard();
        player.setScoreboard(scoreboard);
        this.getObjective();
        final ScoreboardCreatedEvent createdEvent = new ScoreboardCreatedEvent(this);
        Bukkit.getPluginManager().callEvent((Event)createdEvent);
    }

    public ScoreboardEntry getEntryAtPosition(final int pos) {
        if (pos >= this.entries.size()) {
            return null;
        }
        return this.entries.get(pos);
    }

    public String getUniqueIdentifier(final int position) {
        String identifier;
        for (identifier = getRandomChatColor(position) + ChatColor.WHITE; this.identifiers.contains(identifier); identifier = identifier + getRandomChatColor(position) + ChatColor.WHITE) {}
        if (identifier.length() > 16) {
            return this.getUniqueIdentifier(position);
        }
        this.identifiers.add(identifier);
        return identifier;
    }

    private static String getRandomChatColor(final int position) {
        return ChatColor.values()[position].toString();
    }

    public List<ScoreboardEntry> getEntries() {
        return this.entries;
    }

    public List<String> getIdentifiers() {
        return this.identifiers;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public me.luyz.providers.scoreboard.ScoreboardD getAssemble() {
        return this.assemble;
    }

}
