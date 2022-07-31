package ui;

import javax.swing.*;
import java.awt.*;

public class EmployeeScreen extends AbstractScreen {
    public EmployeeScreen() {
        super();
        addTab("Games", setupGamesPanel());
        addTab("Teams", setupTeamsPanel());
    }


    private JPanel setupGamesPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.RED);
        JLabel test = new JLabel("test");
        panel.add(test);
        return panel;
    }

    private JPanel setupTeamsPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLUE);
        JLabel test = new JLabel("test");
        panel.add(test);
        return panel;
    }
}
