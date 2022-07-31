package model;

public class Ticket {
    private final int ticketNum;
    private final int vID;
    private final int gID;
    private final int aID;
    private final int seatNum;

    public Ticket(int ticketNum, int vID, int gID, int aID, int seatNum) {
        this.ticketNum = ticketNum;
        this.vID = vID;
        this.gID = gID;
        this.aID = aID;
        this.seatNum = seatNum;
    }

    public int getTicketNum() {
        return ticketNum;
    }

    public int getvID() {
        return vID;
    }

    public int getgID() {
        return gID;
    }

    public int getaID() {
        return aID;
    }

    public int getSeatNum() {
        return seatNum;
    }
}
