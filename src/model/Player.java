package model;

public class Player {
    private final int teamMemberID;
    private final String position;
    private final String alias;

    public Player(int teamMemberID, String position, String alias) {
        this.teamMemberID = teamMemberID;
        this.position = position;
        this.alias = alias;
    }

    public int getTeamMemberID() {
        return teamMemberID;
    }

    public String getPosition() {
        return position;
    }

    public String getAlias() {
        return alias;
    }
}
