package ui;

import tabs.AnalystTeamsPanel;
import tabs.AnalystSalesPanel;
import tabs.AnalystViewersPanel;

import javax.swing.*;

public class AnalystScreen extends AbstractScreen {
    public AnalystScreen() {
        super();
        addTab("Sales", setupTicketsPanel());
//        addTab("Team Performances", setupTeamsPanel());
//        addTab("Top Viewers", setupViewersPanel());
    }

    private JPanel setupTicketsPanel() {
        return new AnalystSalesPanel(dbHandler);
    }


    private JPanel setupViewersPanel() {
        return new AnalystViewersPanel();
    }

    private JPanel setupTeamsPanel() {
        return new AnalystTeamsPanel(dbHandler);
    }

}
