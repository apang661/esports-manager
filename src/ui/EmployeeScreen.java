package ui;

import tabs.EmployeeGamePanel;

import javax.swing.*;
import java.awt.*;

public class EmployeeScreen extends AbstractScreen {
    public static final Color MAIN_COLOR = new Color(47,49,54);
    public static final Color SECOND_COLOR = new Color(55,57,63);
    public static final Color TEXT_COLOR = new Color(231, 231, 199);

    public EmployeeScreen() {
        super();

        addTab("Games", setupGamesPanel());
        addTab("Teams", setupTeamsPanel());
    }


    private JPanel setupGamesPanel() {
        EmployeeGamePanel panel = new EmployeeGamePanel(this);
        return panel.getPanel();
    }





    private JPanel setupTeamsPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLUE);
        JLabel test = new JLabel("test");
        panel.add(test);
        return panel;
    }
}
