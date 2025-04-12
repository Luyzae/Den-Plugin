package me.luyz.commands.team.subcommands;

import me.luyz.commons.team.Team;
import me.luyz.commons.team.TeamManager;
import me.luyz.module.ModuleService;
import me.luyz.utilities.ChatUtil;
import me.luyz.utilities.command.BaseCommand;
import me.luyz.utilities.command.CommandArgs;
import me.luyz.utilities.command.CommandMeta;
import org.bukkit.ChatColor;


public class TeamListCommand extends BaseCommand {
    private final TeamManager teamManager;

    public TeamListCommand() {
        this.teamManager = ModuleService.getManagerModule().getTeamManager();
    }

    @CommandMeta(name = "team.list", permission = "den.command.team")
    @Override
    public void onCommand(CommandArgs command) {
        ChatUtil.sendMessage(command.getSender(), ChatColor.YELLOW + "Existing teams:");

        for (String teamName : teamManager.getTeams()) {
            Team team = teamManager.getTeam(teamName);
            ChatUtil.sendMessage(command.getSender(), team.getColor() + "- " + team.getName());
        }
    }
}
