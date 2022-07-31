package model;

public class Caster {
    private final int cID;
    private final String name;

    public Caster(int cID, String name) {
        this.cID = cID;
        this.name = name;
    }

    public int getCID() {
        return cID;
    }

    public String getName() {
        return name;
    }
}
