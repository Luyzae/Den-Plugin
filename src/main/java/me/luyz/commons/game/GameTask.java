package me.luyz.commons.game;

import me.luyz.utilities.ChatUtil;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {
    private final GameManager gameManager;
    private int countdown = 30;

    public GameTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        if (gameManager.getPlayersInPreLobby().size() < gameManager.getMinPlayersToStart()) {
            gameManager.cancelCountdown(); // Cancela la cuenta regresiva si no hay jugadores suficientes
            cancel();
            return;
        }

        if (countdown <= 0) {
            cancel();
            System.out.println("La partida empezó");
            gameManager.startGame();
            return;
        }

        if (countdown == 30 || countdown == 15 || countdown <= 5) {
            ChatUtil.broadcast("&eThe game starts in &c" + countdown + "&e seconds!");
        }

        countdown--;
    }

    public int getCountdown() {
        return countdown;
    }
}
