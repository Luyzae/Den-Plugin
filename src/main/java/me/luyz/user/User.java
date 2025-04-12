package me.luyz.user;

import java.util.UUID;


public class User {
    private final UUID uuid;
    private String name;
    private String team;
    private String kit;
    private int kills;
    private int deaths;
    private int wins;
    private boolean inPreLobby;

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.team = null;
        this.kit = null;
        this.kills = 0;
        this.deaths = 0;
        this.wins = 0;
        this.inPreLobby = true;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
        this.inPreLobby = (team == null);
    }

    public String getKit() {
        return kit;
    }

    public void setKit(String kit) {
        this.kit = kit;
    }

    public int getKills() {
        return kills;
    }

    public void addKill() {
        this.kills++;
    }

    public int getDeaths() {
        return deaths;
    }

    public void addDeath() {
        this.deaths++;
    }

    public int getWins() {
        return wins;
    }

    public void addWin() {
        this.wins++;
    }

    public boolean isInPreLobby() {
        return inPreLobby;
    }

    public void setInPreLobby(boolean inPreLobby) {
        this.inPreLobby = inPreLobby;
    }


    public void resetStats() {
        this.kills = 0;
        this.deaths = 0;
        this.wins = 0;
    }


}
