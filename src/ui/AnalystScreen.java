package ui;

import javax.swing.*;

public class AnalystScreen extends AbstractScreen {
    public AnalystScreen() {
        super();
        addTab("Viewers", setupViewersPanel());
        addTab("Teams", setupTeamsPanel());
    }



    private JPanel setupViewersPanel() {
        return new JPanel();
    }

    private JPanel setupTeamsPanel() {
        return new JPanel();
    }

}
