package me.luyz.commands.user;

import me.luyz.commands.user.subcommands.*;
import me.luyz.utilities.ChatUtil;
import me.luyz.utilities.command.BaseCommand;
import me.luyz.utilities.command.CommandArgs;
import me.luyz.utilities.command.CommandMeta;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class UserCommand extends BaseCommand {

    public UserCommand() {
        new UserResetCommand();
        new UserResetAllCommand();
    }

    @CommandMeta(name = "user", permission = "den.command.user", inGameOnly = false)
    @Override
    public void onCommand(final CommandArgs command) {

        final Player player = command.getPlayer();
        final String label = command.getLabel();

        ChatUtil.sendMessage((CommandSender)player, new String[] {
                ChatUtil.NORMAL_LINE,
                "&3&lUser Commands",
                "",
                " &f<> &7= &fRequired &7| &f[] &7= &fOptional",
                "",
                " &7▶ &b/" + label + " reset <player> &7- &fReset a user's stats.",
                " &7▶ &b/" + label + " resetAll &7- &fReset all users' stats.",
                ChatUtil.NORMAL_LINE });
    }
}
