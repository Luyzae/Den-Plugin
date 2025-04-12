package me.luyz.module.impl;

import me.luyz.Den;
import me.luyz.commands.DenCommand;
import me.luyz.commands.essentials.PingCommand;
import me.luyz.commands.essentials.admin.GameModeCommand;
import me.luyz.commands.hitdelay.HitDelayCommand;
import me.luyz.commands.setup.SetupCommand;
import me.luyz.commands.team.TeamCommand;
import me.luyz.commands.timer.TimerCommand;
import me.luyz.commands.user.UserCommand;
import me.luyz.module.Module;

public class CommandModule extends Module {

    @Override
    public String getName() {
        return "Command";
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public void onEnable(final Den plugin) {
        new DenCommand();
        new PingCommand();
        new GameModeCommand();
        new TimerCommand();
        new HitDelayCommand();
        new TeamCommand();
        new UserCommand();
        new SetupCommand();

    }
}
