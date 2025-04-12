package me.luyz.commands.setup;

import me.luyz.commands.setup.subcommands.SetupSetPrelobbyCommand;
import me.luyz.utilities.ChatUtil;
import me.luyz.utilities.command.BaseCommand;
import me.luyz.utilities.command.CommandArgs;
import me.luyz.utilities.command.CommandMeta;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetupCommand extends BaseCommand {

    public SetupCommand() {
        new SetupSetPrelobbyCommand();

    }

    @CommandMeta(name = "setup", permission = "den.command.setup", inGameOnly = false)
    @Override
    public void onCommand(final CommandArgs command) {

        final Player player = command.getPlayer();
        final String label = command.getLabel();

        ChatUtil.sendMessage((CommandSender)player, new String[] {

                ChatUtil.NORMAL_LINE,
                "&3&lSetup Commands",
                "",
                " &f<> &7= &fRequired &7| &f[] &7= &fOptional",
                "",
                " &7▶ &b/" + label + " setPrelobby - &fCreate a pre-lobby.",
                ChatUtil.NORMAL_LINE });
    }


}
