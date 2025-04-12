package me.luyz.providers.scoreboard;

import org.bukkit.entity.*;
import java.util.*;

public interface ScoreboardAdapter {

    String getTitle(final Player p0);

    List<String> getLines(final Player p0);

    ScoreboardStyle getBoardStyle(final Player p0);

}
