package me.luyz.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.luyz.commons.setup.SetupSelection;
import me.luyz.commons.team.Team;
import me.luyz.commons.team.TeamManager;
import me.luyz.module.ModuleService;
import me.luyz.servicies.impl.LanguageService;
import me.luyz.utilities.ChatUtil;
import me.luyz.utilities.cuboid.Cuboid;
import me.luyz.utilities.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PreLobbyHotbarListener implements Listener {

    private final TeamManager teamManager;

    private final Map<UUID, ItemStack[]> savedInventories = new HashMap<>();


    public PreLobbyHotbarListener(Plugin plugin) {
        this.teamManager = ModuleService.getManagerModule().getTeamManager();

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, PacketType.Play.Client.TELEPORT_ACCEPT) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                updateTeamSelector(player);
            }
        });
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        updateTeamSelector(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        updateTeamSelector(player);
    }

    private void updateTeamSelector(Player player) {
        Cuboid preLobbyRegion = SetupSelection.getPreLobbyRegion();

        if (preLobbyRegion != null) {
            if (preLobbyRegion.contains(player.getLocation())) {
                if (!savedInventories.containsKey(player.getUniqueId())) {
                    savedInventories.put(player.getUniqueId(), player.getInventory().getContents().clone());

                    player.getInventory().clear();
                    player.getInventory().setItem(0, createTeamSelector());
                }
            } else {
                if (savedInventories.containsKey(player.getUniqueId())) {
                    player.getInventory().clear();
                    player.getInventory().setContents(savedInventories.get(player.getUniqueId()));
                    savedInventories.remove(player.getUniqueId());
                }
            }
        }
    }


    private ItemStack createTeamSelector() {
        return new ItemBuilder(Material.DIAMOND)
                .setName(ChatUtil.translate(LanguageService.DIAMOND_TITLE_TEAM))
                .setLore(ChatUtil.translate(LanguageService.DIAMOND_TEXT_TEAM))
                .build();
    }

    private boolean hasTeamSelector(Player player) {
        ItemStack item = player.getInventory().getItem(0);
        return item != null && item.getType() == Material.DIAMOND
                && item.hasItemMeta()
                && item.getItemMeta().hasDisplayName()
                && item.getItemMeta().getDisplayName().equals(ChatUtil.translate(LanguageService.DIAMOND_TITLE_TEAM));
    }


    private void removeTeamSelector(Player player) {
        ItemStack item = player.getInventory().getItem(0);
        if (item != null && item.getType() == Material.DIAMOND
                && item.hasItemMeta()
                && item.getItemMeta().hasDisplayName()
                && item.getItemMeta().getDisplayName().equals(ChatUtil.translate(LanguageService.DIAMOND_TITLE_TEAM))) {
            player.getInventory().setItem(0, null);
        }
    }


    @EventHandler
    public void onTeamSelectorClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
        }

        if (item.getType() == Material.DIAMOND
                && item.getItemMeta().getDisplayName().equals(ChatUtil.translate(LanguageService.DIAMOND_TITLE_TEAM))) {
            openTeamSelectionGUI(player);
        }
    }

    private void openTeamSelectionGUI(Player player) {
        List<Team> teams = teamManager.getTeamsList();
        int size = ((teams.size() / 9) + 1) * 9;
        Inventory gui = Bukkit.createInventory(null, size, LanguageService.GUI_TITLE);

        for (Team team : teams) {
            DyeColor dyeColor = getDyeColorFromChatColor(team.getColor());
            ItemStack teamItem = new ItemStack(Material.WOOL, 1, dyeColor.getWoolData());

            teamItem = new ItemBuilder(teamItem)
                    .setName(team.getColor() + team.getName())
                    .setLore(ChatColor.GRAY + "Click to join this team!")
                    .build();

            gui.addItem(teamItem);
        }

        player.openInventory(gui);
    }

    @EventHandler
    public void onTeamSelect(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(LanguageService.GUI_TITLE)) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem != null && clickedItem.getType() == Material.WOOL) {
                String teamName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());

                String currentTeam = teamManager.getPlayerTeam(player.getName());
                if (currentTeam != null && currentTeam.equalsIgnoreCase(teamName)) {
                    ChatUtil.sendMessage(player, LanguageService.ALREADY_SELECTED_THIS_TEAM);
                    return;
                }

                if (teamManager.addPlayerToTeam(player.getName(), teamName)) {
                    ChatUtil.sendMessage(player, LanguageService.JOINED_THE_TEAM.replace("<team>", teamName));
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    player.closeInventory();
                }
            }
        }
    }

    private DyeColor getDyeColorFromChatColor(ChatColor color) {
        switch (color) {
            case RED: return DyeColor.RED;
            case BLUE: return DyeColor.BLUE;
            case GREEN: return DyeColor.GREEN;
            case YELLOW: return DyeColor.YELLOW;
            case AQUA: return DyeColor.LIGHT_BLUE;
            case GOLD: return DyeColor.ORANGE;
            case DARK_PURPLE: return DyeColor.PURPLE;
            case DARK_GRAY: return DyeColor.GRAY;
            case GRAY: return DyeColor.SILVER;
            case WHITE: return DyeColor.WHITE;
            case BLACK: return DyeColor.BLACK;
            default: return DyeColor.WHITE;
        }
    }
}
