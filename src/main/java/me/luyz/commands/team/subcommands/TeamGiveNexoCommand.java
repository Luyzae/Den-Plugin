package me.luyz.commands.team.subcommands;

import me.luyz.commons.nexus.NexusManager;
import me.luyz.commons.team.Team;
import me.luyz.commons.team.TeamManager;
import me.luyz.module.ModuleService;
import me.luyz.utilities.ChatUtil;
import me.luyz.utilities.command.BaseCommand;
import me.luyz.utilities.command.CommandArgs;
import me.luyz.utilities.command.CommandMeta;
import me.luyz.utilities.item.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;


public class TeamGiveNexoCommand extends BaseCommand {
    private final TeamManager teamManager;
    private final NexusManager nexusManager;

    public TeamGiveNexoCommand() {
        this.teamManager = ModuleService.getManagerModule().getTeamManager();
        this.nexusManager = ModuleService.getManagerModule().getNexusManager();
    }

    @CommandMeta(name = "team.giveNexo", permission = "den.command.team")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            ChatUtil.sendMessage(command.getSender(), ChatColor.RED + "Usage: /team giveNexo <team>");
            return;
        }

        String teamName = args[0];
        Team team = teamManager.getTeam(teamName);

        if (team == null) {
            ChatUtil.sendMessage(command.getSender(), ChatColor.RED + "The team does not exist.");
            return;
        }

        ItemStack nexusBlock = new ItemBuilder(Material.ENDER_STONE)
                .setName(ChatColor.AQUA + "Nexus Block (" + team.getColor() + teamName + ChatColor.AQUA + ")")
                .setLore(Collections.singletonList(ChatColor.GRAY + "Place this block to set the team's nexus."))
                .setEnchant(true)
                .build();

        player.getInventory().addItem(nexusBlock);
        ChatUtil.sendMessage(command.getSender(), ChatColor.GREEN + "You have received the Nexus Block for team " + team.getColor() + teamName + ChatColor.GREEN + ".");
    }

}
