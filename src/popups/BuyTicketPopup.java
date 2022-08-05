package popups;

import tabs.ViewerGamePanel;
import utils.CustomButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class BuyTicketPopup extends Popup {
    JComboBox ticketsCB;
    private ArrayList<Integer> ticketNums;

    public BuyTicketPopup(ViewerGamePanel viewerGamePanel, int gameID, int viewerID) {
        super(viewerGamePanel, "Buy Ticket", gameID, viewerID);
    }

    @Override
    protected void initializePrompts() {
        main.setLayout(new BorderLayout());
        ArrayList<String> ticketTexts = editor.getParent().getDbHandler().getAvailTickets(gameID);
        ticketNums = editor.getParent().getDbHandler().getTicketNums(gameID);
        ticketsCB = new JComboBox(ticketTexts.toArray());
        main.add(ticketsCB, BorderLayout.LINE_END);
        JButton purchaseButton = new CustomButton("Purchase Ticket", "s");
        purchaseButton.addActionListener(this);
        main.add(purchaseButton, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedTicketNum = ticketNums.get(ticketsCB.getSelectedIndex());
        editor.getParent().getDbHandler().bookTicket(selectedTicketNum, viewerID);
        dispose();
    }
}
