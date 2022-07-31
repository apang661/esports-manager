package model;

public class Viewer {
    private final int vID;
    private final String name;

    public Viewer(int vID, String name) {
        this.vID = vID;
        this.name = name;
    }

    public int getvID() {
        return vID;
    }

    public String getName() {
        return name;
    }
}
