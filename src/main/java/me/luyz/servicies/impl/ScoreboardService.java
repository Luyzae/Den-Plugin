package me.luyz.servicies.impl;

import me.luyz.servicies.*;
import java.util.*;
import me.luyz.module.*;
import me.luyz.utilities.file.*;

public class ScoreboardService extends Service
{
    public static boolean ENABLED;
    public static boolean TITLE_ANIMATED_ENABLED;
    public static double TITLE_ANIMATED_INTERVAL;
    public static List<String> TITLE_ANIMATED_TITLE;
    public static boolean FOOTER_ANIMATED_ENABLED;
    public static double FOOTER_ANIMATED_INTERVAL;
    public static List<String> FOOTER_ANIMATED_FOOTER;
    public static List<String> PRINCIPAL;
    public static List<String> TIMERS;
    public static List<String> WAITING;


    @Override
    public void initialize() {
        final FileConfig scoreboardFile = ModuleService.getFileModule().getFile("scoreboard");
        ScoreboardService.ENABLED = scoreboardFile.getBoolean("scoreboard.enabled");
        ScoreboardService.TITLE_ANIMATED_ENABLED = scoreboardFile.getBoolean("scoreboard.title-animated.enabled");
        ScoreboardService.TITLE_ANIMATED_INTERVAL = scoreboardFile.getDouble("scoreboard.title-animated.interval");
        ScoreboardService.TITLE_ANIMATED_TITLE = scoreboardFile.getStringList("scoreboard.title-animated.title");
        ScoreboardService.FOOTER_ANIMATED_ENABLED = scoreboardFile.getBoolean("scoreboard.footer-animated.enabled");
        ScoreboardService.FOOTER_ANIMATED_INTERVAL = scoreboardFile.getDouble("scoreboard.footer-animated.interval");
        ScoreboardService.FOOTER_ANIMATED_FOOTER = scoreboardFile.getStringList("scoreboard.footer-animated.footer");
        ScoreboardService.PRINCIPAL = scoreboardFile.getStringList("scoreboard.principal");
        ScoreboardService.TIMERS = scoreboardFile.getStringList("scoreboard.timers");
        ScoreboardService.WAITING = scoreboardFile.getStringList("scoreboard.waiting");

    }
}