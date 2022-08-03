package ui;

import tabs.ViewerTicketsPanel;

import javax.swing.*;
import java.awt.*;

public class ViewerScreen extends AbstractScreen {
    public ViewerScreen(int viewerID) {
        super();
        addTab("Account", setupAccountPanel());
        addTab("Tickets", setupTicketsPanel());
        addTab("Schedule", setupSchedulePanel());
        addTab("Standings", setupStandingsPanel());
        addTab("Players", setupPlayersPanel());
        displayTab(0);
    }

    private JPanel setupAccountPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.RED);
        JLabel test = new JLabel("test");
        panel.add(test);
        return panel;
    }

    private JPanel setupTicketsPanel() {
        ViewerTicketsPanel panel = new ViewerTicketsPanel(this);
        return panel.getPanel();
    }

    private JPanel setupSchedulePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.RED);
        JLabel test = new JLabel("test");
        panel.add(test);
        return panel;
    }

    private JPanel setupStandingsPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.RED);
        JLabel test = new JLabel("test");
        panel.add(test);
        return panel;
    }

    private JPanel setupPlayersPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.RED);
        JLabel test = new JLabel("test");
        panel.add(test);
        return panel;
    }
}
