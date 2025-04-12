package me.luyz.commands.team.subcommands;

import me.luyz.commons.team.TeamManager;
import me.luyz.module.ModuleService;
import me.luyz.utilities.ChatUtil;
import me.luyz.utilities.command.BaseCommand;
import me.luyz.utilities.command.CommandArgs;
import me.luyz.utilities.command.CommandMeta;
import org.bukkit.ChatColor;


public class TeamResetCommand extends BaseCommand {
    private final TeamManager teamManager;

    public TeamResetCommand() {
        this.teamManager = ModuleService.getManagerModule().getTeamManager();
    }

    @CommandMeta(name = "reset", permission = "den.command.team")
    @Override
    public void onCommand(CommandArgs command) {
        teamManager.resetTeams();
        ChatUtil.sendMessage(command.getSender(), ChatColor.GREEN + "All teams have been reset.");
    }
}
