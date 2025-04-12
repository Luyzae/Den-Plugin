package me.luyz.commands.essentials.admin;


import me.luyz.utilities.command.BaseCommand;
import me.luyz.utilities.command.CommandMeta;
import me.luyz.utilities.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.GameMode;
import me.luyz.utilities.ChatUtil;
import me.luyz.servicies.impl.*;

public class GameModeCommand extends BaseCommand{

    @CommandMeta(name = "gamemode", permission = "den.command.gamemode", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 1) {
            ChatUtil.sendMessage(sender, LanguageService.GAMEMODE_USAGE);
            return;
        }

        GameMode mode = parseGameMode(args[0]);
        if (mode == null) {
            ChatUtil.sendMessage(sender, LanguageService.GAMEMODE_INVALID_MODE);
            return;
        }

        Player target;
        if (args.length == 1) {
            if (!(sender instanceof Player)) {
                ChatUtil.sendMessage(sender, LanguageService.GAMEMODE_CONSOLE_NEEDS_PLAYER);
                return;
            }
            target = (Player) sender;
        } else {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                ChatUtil.sendMessage(sender, LanguageService.GAMEMODE_PLAYER_NOT_FOUND.replace("<player>", args[1]));
                return;
            }
        }

        target.setGameMode(mode);

        ChatUtil.sendMessage(target, LanguageService.GAMEMODE_CHANGED_SELF
                .replace("<gamemode>", mode.name()));

        if (!target.equals(sender)) {
            ChatUtil.sendMessage(sender, LanguageService.GAMEMODE_CHANGED_OTHER
                    .replace("<gamemode>", mode.name())
                    .replace("<player>", target.getName()));
        }
    }

    private GameMode parseGameMode(String input) {
        switch (input.toLowerCase()) {
            case "survival":
            case "0":
                return GameMode.SURVIVAL;
            case "creative":
            case "1":
                return GameMode.CREATIVE;
            case "adventure":
            case "2":
                return GameMode.ADVENTURE;
            case "spectator":
            case "3":
                return GameMode.SPECTATOR;
            default:
                return null;
        }
    }


}
