package model;

public class Game {
    private final int gameID;
    private final int redTeamID;
    private final int blueTeamID;
    private final String date;
    private final int arenaID;

    public Game(int gameID, int redTeamID, int blueTeamID, String date, int arenaID) {
        this.gameID = gameID;
        this.redTeamID = redTeamID;
        this.blueTeamID = blueTeamID;
        this.date = date;
        this.arenaID = arenaID;
    }

    public int getGameID() {
        return gameID;
    }

    public int getRedTeamID() {
        return redTeamID;
    }

    public int getBlueTeamID() {
        return blueTeamID;
    }

    public String getDate() {
        return date;
    }

    public int getArenaID() {
        return arenaID;
    }
}
