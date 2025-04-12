package me.luyz.commands.team.subcommands;

import me.luyz.commons.team.TeamManager;
import me.luyz.module.ModuleService;
import me.luyz.utilities.ChatUtil;
import me.luyz.utilities.command.BaseCommand;
import me.luyz.utilities.command.CommandArgs;
import me.luyz.utilities.command.CommandMeta;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TeamCreateCommand extends BaseCommand {
    private final TeamManager teamManager;

    public TeamCreateCommand() {
        this.teamManager = ModuleService.getManagerModule().getTeamManager();
    }

    @CommandMeta(name = "team.create", permission = "den.command.team")
    @Override
    public void onCommand(CommandArgs command) {
        String[] args = command.getArgs();

        if (args.length < 2) {
            ChatUtil.sendMessage(command.getSender(), ChatColor.RED + "Usage: /team create <name> <color>");
            return;
        }

        try {
            ChatColor color = ChatColor.valueOf(args[1].toUpperCase());

            if (teamManager.createTeam(args[0], color)) {
                ChatUtil.sendMessage(command.getSender(), ChatColor.GREEN + "Team " + color + args[0] + ChatColor.GREEN + " successfully created.");
            } else {
                ChatUtil.sendMessage(command.getSender(), ChatColor.RED + "The team already exists.");
            }
        } catch (IllegalArgumentException e) {
            String availableColors = Arrays.stream(ChatColor.values())
                    .filter(c -> c.isColor())
                    .map(c -> c.name().toLowerCase())
                    .collect(Collectors.joining(", "));

            ChatUtil.sendMessage(command.getSender(), ChatColor.RED + "Invalid color. Available colors: " + ChatColor.YELLOW + availableColors);
        }
    }
}
