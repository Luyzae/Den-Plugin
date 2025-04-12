package me.luyz.commands.setup.subcommands;

import me.luyz.commons.setup.SetupSelection;
import me.luyz.commons.setup.SetupType;
import me.luyz.module.ModuleService;
import me.luyz.servicies.impl.ConfigService;
import me.luyz.utilities.ChatUtil;
import me.luyz.utilities.command.BaseCommand;
import me.luyz.utilities.command.CommandArgs;
import me.luyz.utilities.command.CommandMeta;
import me.luyz.utilities.file.FileConfig;
import me.luyz.utilities.item.ItemBuilder;
import me.luyz.utilities.cuboid.Cuboid;
import me.luyz.utilities.cuboid.CuboidUtil;
import me.luyz.listeners.PreLobbyRestrictionsListener;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class SetupSetPrelobbyCommand extends BaseCommand {
    private final FileConfig config;
    private static final String PRE_LOBBY_TOOL_NAME = ChatColor.AQUA + "Pre-Lobby Region Selector";
    private static final String PRE_LOBBY_SELECTION_KEY = "prelobby.region-selected";

    public SetupSetPrelobbyCommand() {
        this.config = ModuleService.getFileModule().getFile("config");
    }

    @CommandMeta(name = "setup.setPrelobby", permission = "den.command.setup")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        SetupSelection selection = SetupSelection.createOrGetSelection( player.getServer().getPluginManager().getPlugin("Den"), player, SetupType.PRELOBBY);        boolean regionSelected = config.getConfiguration().getBoolean(PRE_LOBBY_SELECTION_KEY, false);

        if (!regionSelected) {
            ItemStack selectionTool = new ItemBuilder(Material.DIAMOND_HOE)
                    .setName(PRE_LOBBY_TOOL_NAME)
                    .setLore(Collections.singletonList(ChatColor.GRAY + "Use this to define the pre-lobby area."))
                    .build();

            player.getInventory().addItem(selectionTool);
            ChatUtil.sendMessage(player, ChatColor.GREEN + "You have received the pre-lobby selection tool.");

            config.getConfiguration().set(PRE_LOBBY_SELECTION_KEY, true);
            config.save();
            return;
        }

        if (!selection.isFullSelected()) {
            ChatUtil.sendMessage(player, ChatColor.RED + "Region not selected yet.");
            return;
        }

        Location loc = player.getLocation();
        ConfigService.PRE_LOBBY_LOCATION = serializeLocation(loc);

        Cuboid preLobbyRegion = selection.getCuboid();
        String serializedCuboid = CuboidUtil.serializeCuboid(preLobbyRegion);
        config.getConfiguration().set("prelobby.region", serializedCuboid);
        config.getConfiguration().set("prelobby.location", ConfigService.PRE_LOBBY_LOCATION);
        config.save();

        player.getInventory().removeItem(new ItemStack(Material.DIAMOND_HOE));

        PreLobbyRestrictionsListener listener = ModuleService.getListenerModule().getPreLobbyRestrictionsListener();
        if (listener != null) {
            listener.reloadPreLobbyRegion();
        }

        ChatUtil.sendMessage(player, ChatColor.GREEN + "Pre-lobby location and region set successfully!");
    }

    private String serializeLocation(Location loc) {
        return loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + "," + loc.getPitch();
    }
}
