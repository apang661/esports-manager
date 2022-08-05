package ui;

import tabs.EmployeeGamePanel;
import tabs.EmployeeTeamPanel;

import javax.swing.*;
import java.awt.*;

public class EmployeeScreen extends AbstractScreen {


    public EmployeeScreen() {
        super();

        addTab("Games", setupGamesPanel());
        addTab("Teams", setupTeamsPanel());
        addTab("TeamMember", setupTeamMemberPanel());
        addTab("Arena", setupArenasPanel());
        addTab("Viewers", setupViewersPanel());
        displayTab(0);
    }

    private JPanel setupViewersPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.RED);
        JLabel test = new JLabel("test");
        panel.add(test);
        return panel;
    }

    private JPanel setupArenasPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.YELLOW);
        JLabel test = new JLabel("test");
        panel.add(test);
        return panel;
    }

    private JPanel setupTeamMemberPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        JLabel test = new JLabel("test");
        panel.add(test);
        return panel;
    }


    private JPanel setupGamesPanel() {
        EmployeeGamePanel panel = new EmployeeGamePanel(this);
        return panel.getPanel();
    }





    private JPanel setupTeamsPanel() {
        EmployeeTeamPanel panel = new EmployeeTeamPanel(this);
        return panel.getPanel();
    }
}
