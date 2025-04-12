package me.luyz.commands.user.subcommands;

import me.luyz.module.ModuleService;
import me.luyz.user.UserManager;
import me.luyz.utilities.ChatUtil;
import me.luyz.utilities.command.BaseCommand;
import me.luyz.utilities.command.CommandArgs;
import me.luyz.utilities.command.CommandMeta;
import org.bukkit.ChatColor;



public class UserResetAllCommand extends BaseCommand {
    private final UserManager userManager;

    public UserResetAllCommand() {
        this.userManager = ModuleService.getManagerModule().getUserManager();
    }

    @CommandMeta(name = "user.resetAll", permission = "den.command.user")
    @Override
    public void onCommand(CommandArgs command) {
        userManager.resetAllUsers();
        ChatUtil.sendMessage(command.getSender(), ChatColor.GREEN + "All user statistics have been reset.");
    }
}

