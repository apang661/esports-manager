package tabs;

import model.Game;
import model.Player;
import ui.AbstractScreen;
import ui.EmployeeScreen;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import static com.sun.javafx.fxml.expression.Expression.add;
import static java.awt.Component.BOTTOM_ALIGNMENT;
import static ui.AbstractScreen.SCREEN_HEIGHT;
import static ui.AbstractScreen.SCREEN_WIDTH;

public class EmployeeGamePanel {
    public static final int DEF_ITEMS = 10;
    private DefaultListModel<String> glistModel;
    private HashMap<String, Game> glist;
    private JTextArea main;

    private Game selectedG;

    private  JPanel panel;

    private EmployeeScreen parent;
    private String filter;
    public EmployeeGamePanel(EmployeeScreen parent) {
        this.parent = parent;
        selectedG = null;
        panel = new JPanel(new BorderLayout());
        panel.setBackground(AbstractScreen.MAIN_COLOR);
        add(panel, BorderLayout.CENTER);
        glistModel = new DefaultListModel<>();
        glist = new HashMap<>();
        addSearchBar();
        addMainPanel();
        displayGames();
    }

    private void addSearchBar() {
        JPanel searchBar = new JPanel(new BorderLayout());
        AbstractScreen.setColors(searchBar, "m");
        searchBar.setPreferredSize(new Dimension(SCREEN_WIDTH * 3/4, 40));
        JTextField search = new JTextField();
        AbstractScreen.setColors(search, "s");
        search.setText("Search");
        String[] filters = { "game ID", "arena", "team"};
        JPanel leftPanel = new JPanel(new BorderLayout());
        AbstractScreen.setColors(leftPanel, "s");
        JButton all = new JButton("View all");
        all.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                getGames("", "");
            }
        });
        JComboBox filterList = new JComboBox(filters);
        filterList.setSelectedIndex(0);
        filterList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filter = (String) filterList.getSelectedItem();
            }
        });
        leftPanel.add(all, BorderLayout.WEST);
        leftPanel.add(filterList, BorderLayout.EAST);
        searchBar.add(leftPanel, BorderLayout.WEST);
        searchBar.add(search, BorderLayout.CENTER);
        JButton submit = new JButton("Search");
        submit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                searchFilter(search.getText(), filter);
            }
        });
        searchBar.add(submit, BorderLayout.EAST);
        panel.add(searchBar, BorderLayout.NORTH);
    }



    private void searchFilter(String searchTerm, String attribute) {
        switch (attribute) {
            case "arena":
                getGames("aID", searchTerm);
                break;
            case "team":
                getGames("team", searchTerm);
                break;
            case "game ID":
                getGames("gID", searchTerm);
                break;
        }
    }
    public JPanel getPanel() {
        return panel;
    }

    private void displayGames() {
        JList rlist = new JList(getGames("",""));
        rlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rlist.setSelectedIndex(0);
        rlist.setVisibleRowCount(5);
        AbstractScreen.setColors(rlist, "s");
        JScrollPane listScrollPane = new JScrollPane(rlist);
        AbstractScreen.setColors(listScrollPane, "m");
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
        AbstractScreen.setColors(p, "m");
        printGame();
        panel.add(p, BorderLayout.CENTER);
    }

    private void initializeMain() {
        main = new JTextArea();
        main.setEditable(false);
        AbstractScreen.setColors(main, "s");
    }

    public DefaultListModel<String> getGames(String attr, String query) {
        glistModel.clear();
        ArrayList<Game> games = parent.getDbHandler().getGames(DEF_ITEMS, attr, query);
        for (Game g : games) {
            if (!glist.containsKey(g.getgID())) {
                glist.put(g.getDescription(),g);
            }
            glistModel.addElement(g.getDescription());
        }

        return glistModel;
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
