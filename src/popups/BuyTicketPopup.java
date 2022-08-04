package popups;

import model.Game;
import tabs.ViewerGamePanel;
import utils.CustomButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class BuyTicketPopup extends Popup {
    private Game game;
    private int viewerID;
    JComboBox ticketsCB;
    private ArrayList<Integer> ticketNums;

    public BuyTicketPopup(ViewerGamePanel viewerGamePanel, Game game, int viewerID) {
        super(viewerGamePanel, "Buy Ticket");
        this.game = game;
        this.viewerID = viewerID;
        // query for all tickets with gid = gameID and vID = NULL into a list
        // display seatnum and price
        // purchase button assigns this vID to the ticket
    }

    @Override
    protected void initializePrompts() {
        main.setLayout(new BorderLayout());
        ArrayList<String> ticketTexts = editor.getParent().getDbHandler().getAvailTickets(game.getgID(), viewerID);
        ticketNums = editor.getParent().getDbHandler().getTicketNums(game.getgID(), viewerID);
        ticketsCB = new JComboBox(ticketTexts.toArray());
        main.add(ticketsCB, BorderLayout.LINE_END);
        JButton purchaseButton = new CustomButton("Purchase Ticket", "s");
        purchaseButton.addActionListener(this);
        main.add(purchaseButton, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedTicket = ticketNums.get(ticketsCB.getSelectedIndex());
        editor.getParent().getDbHandler().bookTicket(selectedTicket, viewerID);
        dispose();
    }
}
