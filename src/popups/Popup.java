package popups;

import tabs.Panel;
import ui.AbstractScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// Represent frames who edit a restaurant in a MainFrame
public abstract class Popup extends JFrame implements ActionListener {
    protected static final int POPUP_WIDTH = 250;
    protected static final int POPUP_HEIGHT = 200;
    protected tabs.Panel editor;
    protected JPanel main;
    protected int sequence;

    // REQUIRES: editor.getSelectedR != null
    // EFFECTS: constructs a RestaurantEditor gui with a given MainFrame editor that operates on editor's mainList
    public Popup(Panel e, String name) {
        super(name);
        this.editor = e;
        sequence = 0;
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: initializes the gui
    protected void initializeGraphics() {
        main = new JPanel();
        add(main, BorderLayout.CENTER);
        AbstractScreen.setColors(main, "m");
        setSize(new Dimension(POPUP_WIDTH, POPUP_HEIGHT));
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(Popup.DISPOSE_ON_CLOSE);
        initializePrompts();
    }

    // MODIFIES: this
    // EFFECTS: increases sequence by one and returns it
    protected int advanceSequence(String text) {
        return sequence++;
    }

    protected abstract void initializePrompts();
}
