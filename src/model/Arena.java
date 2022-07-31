package model;

public class Arena {
    private final int aID;
    private final String name;
    private final String city;

    public Arena(int aID, String name, String city) {
        this.aID = aID;
        this.name = name;
        this.city = city;
    }

    public int getaID() {
        return aID;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }
}
