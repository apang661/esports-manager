package popups;

import tabs.ViewerGamePanel;
import utils.CustomButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        JPanel buttons = new JPanel(new GridLayout(0, 1));
        if (ticketNums.isEmpty()) {
            JLabel soldOutLabel = new JLabel("All sold out!");
            soldOutLabel.setForeground(Color.white);
            main.add(soldOutLabel, BorderLayout.CENTER);
        } else {
            ticketsCB = new JComboBox(ticketTexts.toArray());
            main.add(ticketsCB, BorderLayout.CENTER);
            JButton purchaseButton = new CustomButton("Purchase Ticket", "s");
            purchaseButton.addActionListener(this);
            buttons.add(purchaseButton);
        }
        JButton cancelButton = new CustomButton("Cancel", "s");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttons.add(cancelButton);
        main.add(buttons, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedTicketNum = ticketNums.get(ticketsCB.getSelectedIndex());
        editor.getParent().getDbHandler().bookTicket(selectedTicketNum, viewerID);
        dispose();
    }
}
