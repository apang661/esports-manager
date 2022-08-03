package popUps;

import model.Game;
import tabs.Panel;
import ui.AbstractScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

public class AddGame extends Popup{
    private JTextField[] fields;
    public AddGame(Panel e) {
        super(e, "Add Game");
    }

    @Override
    protected void initializePrompts() {
        String[] labels = {"Date: ", "Blue Team ID: ", "Red Team ID: ", "Arena ID: ", "Season: "};
        int numPairs = labels.length;

        fields = new JTextField[numPairs];
        SpringLayout layout = new SpringLayout();
        main.setLayout(layout);
        JComponent aboveLabel = null;
        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            main.add(l);
            AbstractScreen.setColors(l, "s");
            JTextField textField = new JTextField(10);
            l.setLabelFor(textField);
            AbstractScreen.setColors(textField, "s");
            main.add(textField);
            fields[i] = textField;
            if (i == 0) {
                layout.putConstraint(SpringLayout.NORTH, l, 10, SpringLayout.NORTH, main);
                layout.putConstraint(SpringLayout.NORTH, textField, -3, SpringLayout.NORTH, l);
                layout.putConstraint(SpringLayout.WEST, l, 10, SpringLayout.WEST, main);
                layout.putConstraint(SpringLayout.WEST, textField, 6, SpringLayout.EAST, l);
            } else {
                layout.putConstraint(SpringLayout.NORTH, l, 10, SpringLayout.SOUTH, aboveLabel);
                layout.putConstraint(SpringLayout.NORTH, textField, -3, SpringLayout.NORTH, l);
                layout.putConstraint(SpringLayout.WEST, l, 10, SpringLayout.WEST, main);
                layout.putConstraint(SpringLayout.WEST, textField, 6, SpringLayout.EAST, l);

            }
            aboveLabel = l;

        }
        JButton create = new JButton("Create Game");
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game = new Game(editor.updateMaxKey("gID", "Game") + 1,Integer.parseInt(fields[1].getText()),
                        Integer.parseInt(fields[2].getText()), Date.valueOf(fields[0].getText()),Integer.parseInt(fields[3].getText()), fields[4].getText(),Integer.parseInt(fields[0].getText().substring(0, 2)));
                editor.getParent().getDbHandler().insertGame(game);
            }
        });
        AbstractScreen.setColors(create, "s");
        main.add(create);
        layout.putConstraint(SpringLayout.NORTH, create, 10, SpringLayout.SOUTH, aboveLabel);
        layout.putConstraint(SpringLayout.WEST, create, 10, SpringLayout.WEST, main);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }

}
