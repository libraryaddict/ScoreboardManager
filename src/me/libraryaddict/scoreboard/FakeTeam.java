package me.libraryaddict.scoreboard;

import java.util.ArrayList;

import org.bukkit.OfflinePlayer;

public class FakeTeam {
    private ArrayList<OfflinePlayer> players = new ArrayList<OfflinePlayer>();
    private String prefix;
    private boolean seeInvisibles;
    private String teamName;

    public FakeTeam(String teamName) {
        this.teamName = teamName;
    }

    public void addPlayer(OfflinePlayer player) {
        players.add(player);
    }

    public boolean canSeeInvisiblePlayers() {
        return seeInvisibles;
    }

    public ArrayList<OfflinePlayer> getPlayers() {
        return players;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getTeamName() {
        return teamName;
    }

    public void removePlayer(OfflinePlayer player) {
        players.remove(player);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSeeInvisiblePlayers(boolean seeInvisibles) {
        this.seeInvisibles = seeInvisibles;
    }

}
