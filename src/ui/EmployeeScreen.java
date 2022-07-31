package ui;

import javax.swing.*;

public class EmployeeScreen extends AbstractScreen {
    public EmployeeScreen() {
        super();
        addTab("Games", setupGamesPanel());
        addTab("Teams", setupTeamsPanel());
    }


    private JPanel setupGamesPanel() {
        return new JPanel();
    }

    private JPanel setupTeamsPanel() {
        return new JPanel();
    }
}
