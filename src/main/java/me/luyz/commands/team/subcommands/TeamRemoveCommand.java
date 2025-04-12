package me.luyz.commands.team.subcommands;

import me.luyz.commons.team.TeamManager;
import me.luyz.module.ModuleService;
import me.luyz.utilities.ChatUtil;
import me.luyz.utilities.command.BaseCommand;
import me.luyz.utilities.command.CommandArgs;
import me.luyz.utilities.command.CommandMeta;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class TeamRemoveCommand extends BaseCommand {
    private final TeamManager teamManager;

    public TeamRemoveCommand() {
        this.teamManager = ModuleService.getManagerModule().getTeamManager();
    }

    @CommandMeta(name = "team.remove", permission = "den.command.team")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            ChatUtil.sendMessage(command.getSender(), ChatColor.RED + "Usage: /team remove <name>");
            return;
        }

        if (teamManager.removeTeam(args[0])) {
            ChatUtil.sendMessage(command.getSender(), ChatColor.GREEN + "Team successfully removed.");
        } else {
            ChatUtil.sendMessage(command.getSender(), ChatColor.RED + "The team does not exist.");
        }
    }
}
