package model;

public class Staff {
    private final int teamMemberID;
    private final String role;

    public Staff(int teamMemberID, String role) {
        this.teamMemberID = teamMemberID;
        this.role = role;
    }

    public int getTeamMemberID() {
        return teamMemberID;
    }

    public String getRole() {
        return role;
    }
}
