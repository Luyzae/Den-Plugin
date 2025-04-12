package me.luyz.servicies.impl;

import me.luyz.module.ModuleService;
import me.luyz.servicies.Service;
import me.luyz.utilities.file.FileConfig;
import org.bukkit.ChatColor;

public class LanguageService extends Service {

    public static String COMBAT_MESSAGE_DISABLE_COMMAND;
    public static String PING_MESSAGE_PLAYER;
    public static String PING_MESSAGE_TARGET;
    public static String GAMEMODE_USAGE;
    public static String GAMEMODE_INVALID_MODE;
    public static String GAMEMODE_CONSOLE_NEEDS_PLAYER;
    public static String GAMEMODE_PLAYER_NOT_FOUND;
    public static String GAMEMODE_CHANGED_SELF;
    public static String GAMEMODE_CHANGED_OTHER;

    public static String NEXUS_CANNOT_DAMAGE_OWN;
    public static String NEXUS_DAMAGED;
    public static String NEXUS_DESTROYED;

    public static String WELCOME_MESSAGE_PRE_LOBBY;

    public static String TEAM_SELECTED_INFO;

    public static String GUI_TITLE;

    public static String DIAMOND_TITLE_TEAM;
    public static String DIAMOND_TEXT_TEAM;
    public static String GUI_JOIN_TEXT;
    public static String ALREADY_SELECTED_THIS_TEAM;
    public static String JOINED_THE_TEAM;






    @Override
    public void initialize() {
        final FileConfig languageFile = ModuleService.getFileModule().getFile("language");
        LanguageService.COMBAT_MESSAGE_DISABLE_COMMAND = languageFile.getString("combat-message.disable-command");
        LanguageService.PING_MESSAGE_PLAYER = languageFile.getString("ping-message.player");
        LanguageService.PING_MESSAGE_TARGET = languageFile.getString("ping-message.target");
        LanguageService.GAMEMODE_USAGE = languageFile.getString("gamemode.usage");
        LanguageService.GAMEMODE_INVALID_MODE = languageFile.getString("gamemode.invalid-mode");
        LanguageService.GAMEMODE_CONSOLE_NEEDS_PLAYER = languageFile.getString("gamemode.console-needs-player");
        LanguageService.GAMEMODE_PLAYER_NOT_FOUND = languageFile.getString("gamemode.player-not-found");
        LanguageService.GAMEMODE_CHANGED_SELF = languageFile.getString("gamemode.changed-self");
        LanguageService.GAMEMODE_CHANGED_OTHER = languageFile.getString("gamemode.changed-other");

        LanguageService.NEXUS_CANNOT_DAMAGE_OWN = languageFile.getString("messages.nexus.cannot-damage-own");
        LanguageService.NEXUS_DAMAGED = languageFile.getString("messages.nexus.damaged");
        LanguageService.NEXUS_DESTROYED = languageFile.getString("messages.nexus.destroyed");
        LanguageService.WELCOME_MESSAGE_PRE_LOBBY = languageFile.getString("pre-lobby.welcome-message-pre-lobby");

        LanguageService.GUI_TITLE = languageFile.getString("pre-lobby.gui-title");

        LanguageService.TEAM_SELECTED_INFO = languageFile.getString("pre-lobby.team-selected-info");

        LanguageService.DIAMOND_TITLE_TEAM = languageFile.getString("pre-lobby.diamond-title-team");

        LanguageService.DIAMOND_TEXT_TEAM = languageFile.getString("pre-lobby.diamond-text-team");

        LanguageService.GUI_JOIN_TEXT = languageFile.getString("pre-lobby.gui-join-text");

        LanguageService.ALREADY_SELECTED_THIS_TEAM = languageFile.getString("pre-lobby.already-selected-this-team");

        LanguageService.JOINED_THE_TEAM = languageFile.getString("pre-lobby.joined-the-team");

    }

}
