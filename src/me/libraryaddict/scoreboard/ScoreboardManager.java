package me.libraryaddict.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardManager {
    private static FakeScoreboard mainScoreboard = new FakeScoreboard();

    public static void addToTeam(OfflinePlayer player, String teamName, String teamPrefix, boolean seeFriendlyInvis) {
        removeFromTeam(player);
        FakeTeam team = mainScoreboard.getFakeTeam(teamName);
        if (team == null) {
            team = mainScoreboard.createFakeTeam(teamName);
            team.setPrefix(teamPrefix);
            team.setSeeInvisiblePlayers(seeFriendlyInvis);
        }
        team.addPlayer(player);
        for (Player p : Bukkit.getOnlinePlayers()) {
            addToTeam(p, player, teamName, teamPrefix, seeFriendlyInvis);
        }
    }

    public static void addToTeam(Player observer, OfflinePlayer player, String teamName, String teamPrefix,
            boolean seeFriendlyInvis) {
        removeFromTeam(observer, player);
        Scoreboard board = observer.getScoreboard();
        if (board.getTeam(teamName) == null) {
            Team newTeam = board.registerNewTeam(teamName);
            newTeam.setCanSeeFriendlyInvisibles(seeFriendlyInvis);
            newTeam.setPrefix(teamPrefix);
        }
        board.getTeam(teamName).addPlayer(player);
    }

    public static void removeFromTeam(OfflinePlayer player) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Scoreboard board = p.getScoreboard();
            for (Team team : board.getTeams()) {
                if (team.hasPlayer(player)) {
                    team.removePlayer(player);
                }
            }
        }
    }

    public static void removeFromTeam(Player observer, OfflinePlayer player) {
        Scoreboard board = observer.getScoreboard();
        for (Team team : board.getTeams()) {
            if (team.hasPlayer(player)) {
                team.removePlayer(player);
            }
        }
    }

    private static Objective getObjective(Scoreboard board, DisplaySlot slot) {
        if (board.getObjective(slot.name()) == null) {
            Objective obj = board.registerNewObjective(slot.name(), slot.name());
            obj.setDisplaySlot(slot);
        }
        return board.getObjective(slot.name());
    }

    public static void hideScore(DisplaySlot slot, String name) {
        mainScoreboard.hideScore(slot, name);
        for (Player player : Bukkit.getOnlinePlayers()) {
            hideScore(player, slot, name);
        }
    }

    public static void hideScore(Player player, DisplaySlot slot, String name) {
        if (name.length() > 16)
            name = name.substring(0, 16);
        player.getScoreboard().resetScores(Bukkit.getOfflinePlayer(name));
    }

    public static void makeScore(DisplaySlot slot, String name, int score) {
        mainScoreboard.makeScore(slot, name, score);
        for (Player player : Bukkit.getOnlinePlayers()) {
            makeScore(player, slot, name, score);
        }
    }

    public static void makeScore(Player player, DisplaySlot slot, String name, int score) {
        if (name.length() > 16) {
            name = name.substring(0, 16);
        }
        getObjective(player.getScoreboard(), slot).getScore(Bukkit.getOfflinePlayer(name)).setScore(score);
    }

    public static void registerScoreboard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        player.setScoreboard(board);
        mainScoreboard.setupObjectives(player);
    }

    public static void removeFromTeam(OfflinePlayer player, String teamName) {
        FakeTeam team = mainScoreboard.getFakeTeam(teamName);
        if (team != null)
            team.removePlayer(player);
        for (Player p : Bukkit.getOnlinePlayers()) {
            Scoreboard board = p.getScoreboard();
            if (board.getTeam(teamName) != null && board.getTeam(teamName).hasPlayer(player))
                board.getTeam(teamName).removePlayer(player);
        }
    }

    public static void setDisplayName(DisplaySlot slot, String string) {
        mainScoreboard.setDisplayName(slot, string);
        for (Player player : Bukkit.getOnlinePlayers())
            setDisplayName(player, slot, string);
    }

    public static void setDisplayName(Player player, DisplaySlot slot, String string) {
        getObjective(player.getScoreboard(), slot).setDisplayName(string);
    }

}