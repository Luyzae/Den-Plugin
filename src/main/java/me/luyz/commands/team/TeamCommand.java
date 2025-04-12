package me.luyz.commands.team;


import me.luyz.commands.team.subcommands.*;
import me.luyz.utilities.ChatUtil;
import me.luyz.utilities.command.BaseCommand;
import me.luyz.utilities.command.CommandArgs;
import me.luyz.utilities.command.CommandMeta;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class TeamCommand extends BaseCommand {


    public TeamCommand() {
        new TeamCreateCommand();
        new TeamRemoveCommand();
        new TeamListCommand();
        new TeamResetCommand();
        new TeamGiveNexoCommand();
        new TeamGiveRemoveNexoCommand();
    }

    @CommandMeta(name = "team", permission = "den.command.team", inGameOnly = false)
    @Override
    public void onCommand(final CommandArgs command) {

        final Player player = command.getPlayer();
        final String label = command.getLabel();

        ChatUtil.sendMessage((CommandSender)player, new String[] {

                ChatUtil.NORMAL_LINE,
                "&3&lTeam Commands",
                "",
                " &f<> &7= &fRequired &7| &f[] &7= &fOptional",
                "",
                " &7▶ &b/" + label + " create <name> <color> &7- &fCreate a team.",
                " &7▶ &b/" + label + " giveNexo <name> &7- &fGive a nexo team.",
                " &7▶ &b/" + label + " remove <name> &7- &fRemove a team.",
                " &7▶ &b/" + label + " giveRemoveNexo &7- &fGive a Stick.",
                " &7▶ &b/" + label + " list &7- &fList all teams.",
                " &7▶ &b/" + label + " reset &7- &fReset all teams.",
                ChatUtil.NORMAL_LINE });
    }
}
