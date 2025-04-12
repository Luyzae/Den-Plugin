package me.luyz.commands.team.subcommands;

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


public class TeamGiveRemoveNexoCommand extends BaseCommand {

    public TeamGiveRemoveNexoCommand() {}

    @CommandMeta(name = "team.giveRemoveNexo", permission = "den.command.team")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        ItemStack removeStick = new ItemBuilder(Material.STICK)
                .setName(ChatColor.RED + "Nexus Remover")
                .setLore(Collections.singletonList(ChatColor.GRAY + "Use this stick to remove a Nexus."))
                .setEnchant(true)
                .build();

        player.getInventory().addItem(removeStick);
        ChatUtil.sendMessage(player, ChatColor.GREEN + "You have received the Nexus Remover!");
    }
}
