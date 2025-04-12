package me.luyz.module.impl;

import me.luyz.Den;
import me.luyz.commons.CombatManager;
import me.luyz.commons.game.GameManager;
import me.luyz.commons.minerals.MineralManager;
import me.luyz.commons.nexus.NexusManager;
import me.luyz.commons.setup.SetupManager;
import me.luyz.commons.team.TeamManager;
import me.luyz.commons.timer.TimerManager;
import me.luyz.module.Module;
import me.luyz.providers.ScoreboardProvider;
import me.luyz.providers.scoreboard.ScoreboardD;
import me.luyz.servicies.impl.ConfigService;
import me.luyz.servicies.impl.ScoreboardService;
import me.luyz.user.UserManager;
import me.luyz.utilities.command.CommandManager;

public class ManagerModule extends Module {

    private CombatManager combatManager;
    private CommandManager commandManager;
    private TimerManager timerManager;
    private TeamManager teamManager;
    private NexusManager nexusManager;
    private UserManager userManager;
    private MineralManager mineralManager;
    private SetupManager setupManager;
    private GameManager gameManager;

    @Override
    public String getName() {
        return "Manager";
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public void onEnable(final Den plugin) {

        this.combatManager = new CombatManager();
        this.commandManager = new CommandManager(plugin, ConfigService.DISABLE_COMMANDS);
        this.timerManager = new TimerManager();
        this.teamManager = new TeamManager();
        this.nexusManager = new NexusManager();
        this.userManager = new UserManager();
        this.mineralManager = new MineralManager();
        this.setupManager = new SetupManager();
        this.gameManager = new GameManager(plugin);



        if (ScoreboardService.ENABLED) {
            final ScoreboardD scoreboard = new ScoreboardD(plugin, new ScoreboardProvider());
            scoreboard.setTicks(2L);
        }
    }

    public void reload() {

    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public CombatManager getCombatManager() {
        return this.combatManager;
    }

    public TimerManager getTimerManager() {
        return this.timerManager;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public NexusManager getNexusManager() {
        return nexusManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public MineralManager getMineralManager() {return mineralManager;}

    public SetupManager getSetupManager() {return setupManager;}

    public GameManager getGameManager() {
        return gameManager;
    }


}
