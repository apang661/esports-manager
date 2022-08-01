package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeScreen extends AbstractScreen {
    public static final Color MAIN_COLOR = new Color(47,49,54);
    public static final Color SECOND_COLOR = new Color(55,57,63);
    public static final Color TEXT_COLOR = new Color(231, 231, 199);
    private DefaultListModel<String> glistModel;
    private HashMap<Integer, Game> glist;
    public EmployeeScreen() {
        super();
        glistModel = new DefaultListModel<>();
        addTab("Games", setupGamesPanel());
        addTab("Teams", setupTeamsPanel());
    }


    private JPanel setupGamesPanel() {
        JPanel panel = new JPanel();
        displayGames(panel);
        return panel;
    }

    private void displayGames(JPanel panel) {
        JList rlist = new JList(getGames(5));
        rlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rlist.setSelectedIndex(0);
        rlist.setVisibleRowCount(5);
        rlist.setBackground(SECOND_COLOR);
        rlist.setForeground(TEXT_COLOR);
        JScrollPane listScrollPane = new JScrollPane(rlist);
        listScrollPane.setBackground(MAIN_COLOR);
        panel.add(listScrollPane, BorderLayout.SOUTH);
        listScrollPane.setPreferredSize(new Dimension(listScrollPane.getWidth(), 150));
//        rlist.addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                String selection = (String) rlist.getSelectedValue();
//                setRestaurant(slist.get(selection));
//            }
//        }
    }

    public ListModel getGames(int items) {
        ArrayList<Game> games = dbHandler.getGames(items);
        System.out.println(games.size());
        glistModel.addElement("poop");
        for (Game g : games) {

            if (glist.get(g.getgID()) == null) {
                glistModel.addElement(g.getDescription());
                glist.put(g.getgID(),g);
            }
        }
        return glistModel;
    }


    private JPanel setupTeamsPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLUE);
        JLabel test = new JLabel("test");
        panel.add(test);
        return panel;
    }
}
