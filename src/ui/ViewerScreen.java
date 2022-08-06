package ui;

import tabs.ViewerGamePanel;
import tabs.ViewerTeamPanel;
import tabs.ViewerTicketsPanel;

import javax.swing.*;
import java.awt.*;

public class ViewerScreen extends AbstractScreen {
    private int viewerID;
    private ViewerTicketsPanel ticketsPanel;

    public ViewerScreen(int viewerID) {
        super();
        this.viewerID = viewerID;
        addTab("Tickets", setupTicketsPanel());
        addTab("Schedule", setupSchedulePanel());
        addTab("Games", setupGamePanel());
        addTab("Teams", setupTeamPanel());
        displayTab(0);
    }

    private JPanel setupTicketsPanel() {
        ViewerTicketsPanel panel = new ViewerTicketsPanel(this, viewerID);
        ticketsPanel = panel;
        return panel.getPanel();
    }

    public ViewerTicketsPanel getTicketsPanel() {
        return ticketsPanel;
    }

    private JPanel setupSchedulePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.RED);
        JLabel test = new JLabel("test");
        panel.add(test);
        return panel;
    }

    private JPanel setupGamePanel() {
        ViewerGamePanel panel = new ViewerGamePanel(this, viewerID);
        return panel.getPanel();
    }

    private JPanel setupTeamPanel() {
        ViewerTeamPanel panel = new ViewerTeamPanel(this);
        return panel.getPanel();
    }
}
