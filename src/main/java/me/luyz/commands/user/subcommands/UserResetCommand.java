package me.luyz.commands.user.subcommands;

import me.luyz.module.ModuleService;
import me.luyz.user.User;
import me.luyz.user.UserManager;
import me.luyz.utilities.ChatUtil;
import me.luyz.utilities.command.BaseCommand;
import me.luyz.utilities.command.CommandArgs;
import me.luyz.utilities.command.CommandMeta;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;


public class UserResetCommand extends BaseCommand {
    private final UserManager userManager;

    public UserResetCommand() {
        this.userManager = ModuleService.getManagerModule().getUserManager();
    }

    @CommandMeta(name = "user.reset", permission = "den.command.user")
    @Override
    public void onCommand(CommandArgs command) {
        String[] args = command.getArgs();
        if (args.length < 1) {
            ChatUtil.sendMessage(command.getSender(), ChatColor.RED + "Usage: /user reset <player>");
            return;
        }

        UUID uuid = Bukkit.getOfflinePlayer(args[0]).getUniqueId();
        if (uuid == null) {
            ChatUtil.sendMessage(command.getSender(), ChatColor.RED + "User data not found.");
            return;
        }

        User user = userManager.getUser(uuid);
        if (user == null) {
            user = new User(uuid, args[0]);
            userManager.addUser(user);
        }

        // Resetear estadísticas del usuario
        userManager.resetUserStats(uuid);
        ChatUtil.sendMessage(command.getSender(), ChatColor.GREEN + "User " + args[0] + "'s stats have been reset.");
    }
}

