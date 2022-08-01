package tabs;

import model.Game;
import model.Player;
import ui.EmployeeScreen;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import static com.sun.javafx.fxml.expression.Expression.add;
import static java.awt.Component.BOTTOM_ALIGNMENT;
import static ui.AbstractScreen.SCREEN_HEIGHT;
import static ui.AbstractScreen.SCREEN_WIDTH;

public class EmployeeGamePanel {
    public static final Color MAIN_COLOR = new Color(47,49,54);
    public static final Color SECOND_COLOR = new Color(55,57,63);
    public static final Color TEXT_COLOR = new Color(231, 231, 199);
    private ArrayList<String> glistModel;
    private HashMap<String, Game> glist;
    private JTextArea main;

    private Game selectedG;

    private  JPanel panel;

    private EmployeeScreen parent;
    public EmployeeGamePanel(EmployeeScreen parent) {
        this.parent = parent;
        selectedG = null;
        panel = new JPanel(new BorderLayout());
        panel.setBackground(MAIN_COLOR);
        glistModel = new ArrayList<>();
        glist = new HashMap<>();
        addMainPanel();
        displayGames(panel);
    }


    public JPanel getPanel() {
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
        listScrollPane.setPreferredSize(new Dimension( SCREEN_WIDTH * 3/4, SCREEN_HEIGHT/2));
        listScrollPane.setAlignmentY(BOTTOM_ALIGNMENT);
        rlist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String selection = (String) rlist.getSelectedValue();
                setGame(glist.get(selection));
            }
        });
    }
    private void addMainPanel() {
        initializeMain();
        JScrollPane p = new JScrollPane(main);
        p.setBackground(MAIN_COLOR);
        printGame();
        add(panel, BorderLayout.CENTER);
        panel.add(p, BorderLayout.CENTER);
    }

    private void initializeMain() {
        main = new JTextArea();
        main.setEditable(false);
        main.setBackground(SECOND_COLOR);
        main.setForeground(TEXT_COLOR);
    }

    public String[] getGames(int items) {
        ArrayList<Game> games = parent.getDbHandler().getGames(items);
        for (Game g : games) {
            if (!glist.containsKey(g.getgID())) {
                glistModel.add(g.getDescription());
                glist.put(g.getDescription(),g);
            }
        }
        String[] arr = new String[glistModel.size()];
        arr = glistModel.toArray(arr);

        return arr;
    }

    private void printGame() {
        main.setText(null);
        if (selectedG == null) {
            main.append("No Game selected :(");
        } else {
            main.append("GameID: " + selectedG.getgID());
            main.append("\nArena:" + parent.getDbHandler().getArena(selectedG.getaID()).getName());
            main.append("\nRed Team: " + parent.getDbHandler().getTeam(selectedG.getRtID()).getName());
            ArrayList<Player> rtPlayers = parent.getDbHandler().getRosterPlayers(selectedG.getRtID(), selectedG.getSeason(),selectedG.getDay().getYear());
            printPlayers(rtPlayers);
            main.append("\nBlue Team: " + parent.getDbHandler().getTeam(selectedG.getBtID()).getName());

            ArrayList<Player> btPlayers = parent.getDbHandler().getRosterPlayers(selectedG.getBtID(), selectedG.getSeason(),selectedG.getDay().getYear());
            printPlayers(btPlayers);
        }
    }

    private void printPlayers(ArrayList<Player> btPlayers) {
        for (Player p : btPlayers) {
            main.append("\n"+ p.getPosition() + ": " + p.getAlias());
        }
    }

    public void setGame(Game g) {
        selectedG = g;
        printGame();
    }
}
