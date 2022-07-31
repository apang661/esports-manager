package model;

public class Arena {
    private final int arenaID;
    private final String name;
    private final String city;

    public Arena(int arenaID, String name, String city) {
        this.arenaID = arenaID;
        this.name = name;
        this.city = city;
    }

    public int getArenaID() {
        return arenaID;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }
}
