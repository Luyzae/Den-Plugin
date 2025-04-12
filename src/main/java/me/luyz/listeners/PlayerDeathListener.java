package me.luyz.listeners;

import me.luyz.module.ModuleService;
import me.luyz.user.User;
import me.luyz.user.UserManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    private final UserManager userManager;

    public PlayerDeathListener() {
        this.userManager = ModuleService.getManagerModule().getUserManager();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player deadPlayer = event.getEntity();
        Player killer = deadPlayer.getKiller();

        User deadUser = userManager.getUser(deadPlayer.getUniqueId());
        if (deadUser != null) {
            deadUser.addDeath();
            userManager.saveUsers();
        }

        if (killer != null) {
            User killerUser = userManager.getUser(killer.getUniqueId());
            if (killerUser != null) {
                killerUser.addKill();
                userManager.saveUsers();
                killer.sendMessage(ChatColor.GREEN + "You have killed " + ChatColor.RED + deadPlayer.getName());
            }
        }
    }
}
