package ui;

import javax.swing.*;

public class ViewerScreen extends AbstractScreen {
    public ViewerScreen(int viewerID) {
        super();
        addTab("Tickets", setupTicketsPanel());
    }

    private JPanel setupTicketsPanel() {
        return new JPanel();
    }
}
