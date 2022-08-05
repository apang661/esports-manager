package tabs;

import database.DatabaseConnectionHandler;

import javax.swing.*;

public class AnalystTeamsPanel extends JPanel {
    private DatabaseConnectionHandler dbHandler;
    public AnalystTeamsPanel(DatabaseConnectionHandler dbHandler) {
        this.dbHandler = dbHandler;

    }
}
