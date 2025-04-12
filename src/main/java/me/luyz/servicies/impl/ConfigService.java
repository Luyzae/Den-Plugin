package me.luyz.servicies.impl;

import me.luyz.module.ModuleService;
import me.luyz.servicies.Service;
import me.luyz.utilities.file.FileConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigService extends Service {

    public static String TIME_ZONE;
    public static String TIME_DATE;
    public static String TIME_HOUR;
    public static List<String> DISABLE_COMMANDS;
    public static int COMBAT_SYSTEM_COOLDOWN;
    public static boolean COMBAT_SYSTEM_KILL_ON_QUIT_ENABLED;
    public static String COMBAT_SYSTEM_KILL_ON_QUIT_MESSAGE;
    public static List<String> COMBAT_SYSTEM_DISABLE_COMMANDS;

    public static int NEXUS_MAX_HEALTH;

    public static String PRE_LOBBY_LOCATION;
    public static List<String> TEAM_SPAWNS;

    public static int PLAYERS_JOIN_GAME;
    public static int PLAYERS_START_GAME;




    @Override
    public void initialize() {

        final FileConfig configFile = ModuleService.getFileModule().getFile("config");

        ConfigService.TIME_ZONE = configFile.getString("time.zone");
        ConfigService.TIME_DATE = configFile.getString("time.date");
        ConfigService.TIME_HOUR = configFile.getString("time.hour");
        ConfigService.DISABLE_COMMANDS = configFile.getStringList("disable-commands");
        ConfigService.COMBAT_SYSTEM_COOLDOWN = configFile.getInt("combat-system.cooldown");
        ConfigService.COMBAT_SYSTEM_KILL_ON_QUIT_ENABLED = configFile.getBoolean("combat-system.kill-on-quit.enabled");
        ConfigService.COMBAT_SYSTEM_KILL_ON_QUIT_MESSAGE = configFile.getString("combat-system.kill-on-quit.message");
        ConfigService.COMBAT_SYSTEM_DISABLE_COMMANDS = configFile.getStringList("combat-system.disable-commands");

        ConfigService.NEXUS_MAX_HEALTH = configFile.getInt("nexus.max-health");

        ConfigService.PRE_LOBBY_LOCATION = configFile.getString("prelobby.location");

        ConfigService.TEAM_SPAWNS = new ArrayList<>(configFile.getStringList("teams.spawns"));

        ConfigService.PLAYERS_JOIN_GAME = configFile.getInt("game-settings.players-join-game");
        ConfigService.PLAYERS_START_GAME = configFile.getInt("game-settings.players-start-game");


    }




}
