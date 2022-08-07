package ui;

import tabs.EmployeeAchievementPanel;
import tabs.EmployeeGamePanel;
import tabs.EmployeeTeamPanel;

import javax.swing.*;
import java.awt.*;

public class EmployeeScreen extends AbstractScreen {
    EmployeeTeamPanel teamPanel;


    public EmployeeScreen() {
        super();

        addTab("Games", setupGamesPanel());
        addTab("Teams", setupTeamsPanel());
        addTab("Achievements", setupAchievementsPanel());
        addTab("Arenas", setupArenasPanel());
        displayTab(0);
    }

    private JPanel setupAchievementsPanel() {
        EmployeeAchievementPanel panel = new EmployeeAchievementPanel(this, teamPanel);
        return panel.getPanel();
    }

    private JPanel setupArenasPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.YELLOW);
        JLabel test = new JLabel("test");
        panel.add(test);
        return panel;
    }



    private JPanel setupGamesPanel() {
        EmployeeGamePanel panel = new EmployeeGamePanel(this);
        return panel.getPanel();
    }

    private JPanel setupTeamsPanel() {
        teamPanel = new EmployeeTeamPanel(this);
        return teamPanel.getPanel();
    }
}
