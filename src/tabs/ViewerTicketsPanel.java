package tabs;

import model.Ticket;
import popUps.buyTicket;
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
    }

    private void addToolbar() {
        JPanel tools = new JPanel(new GridLayout(0, 1));
        AbstractScreen.setColors(tools, "s");
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
        tools.add(buyTicketButton);
        tools.add(refundTicketButton);
        panel.add(tools, BorderLayout.SOUTH);
    }

    public void buyTicket() {
        new buyTicket(this);
    }

    public void refundTicket() {
        parent.getDbHandler().refundTicket(selectedTicket.getTicketNum());
    }

}
