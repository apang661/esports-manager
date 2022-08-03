package tabs;

import model.Ticket;
import popUps.BuyTicket;
import ui.AbstractScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.AbstractScreen.SCREEN_WIDTH;

public class ViewerTicketsPanel extends Panel {

    private Ticket selectedTicket;

    public ViewerTicketsPanel(AbstractScreen parent) {
        super(parent);
        // TODO: display tickets with arena/team names/seat number
        // make selectable
        addToolbar();
    }

    private void addToolbar() {
        JPanel toolbar = new JPanel(new GridLayout(0, 1));
        AbstractScreen.setColors(toolbar, "s");
        JButton buyTicketButton = new JButton("Buy Ticket");
        buyTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyTicket();
            }
        });
        JButton refundTicketButton = new JButton("Refund Ticket");
        refundTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refundTicket();
            }
        });
        buyTicketButton.setPreferredSize(new Dimension(SCREEN_WIDTH * 3/4, 50));
        AbstractScreen.setColors(buyTicketButton, "s");
        refundTicketButton.setPreferredSize(new Dimension(SCREEN_WIDTH * 3/4, 50));
        AbstractScreen.setColors(refundTicketButton, "s");
        toolbar.add(buyTicketButton);
        toolbar.add(refundTicketButton);
        panel.add(toolbar, BorderLayout.SOUTH);
    }

    public void buyTicket() {
        new BuyTicket(this);
    }

    public void refundTicket() {
        parent.getDbHandler().refundTicket(selectedTicket.getTicketNum());
    }

}
