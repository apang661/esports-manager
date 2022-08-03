package tabs;

import model.Game;
import model.Player;
import popUps.AddGame;
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

import static ui.AbstractScreen.SCREEN_HEIGHT;
import static ui.AbstractScreen.SCREEN_WIDTH;

public class EmployeeGamePanel extends Panel {
    public static final int DEF_ITEMS = 10;
    private DefaultListModel<String> glistModel;
    private HashMap<String, Game> glist;
    private JTextArea main;

    private Game selectedG;






    private String filter;
    public EmployeeGamePanel(EmployeeScreen parent) {
        super(parent);
        selectedG = null;
        glistModel = new DefaultListModel<>();
        glist = new HashMap<>();
        updateMaxKey("gID", "Game");
        addSearchBar();
        addMainPanel();
        addBottom();
    }



    private void addBottom() {
        JPanel bottom = new JPanel(new BorderLayout());
        AbstractScreen.setColors(bottom, "s");
        JPanel tools = new JPanel(new GridLayout(0, 1));
        AbstractScreen.setColors(tools, "s");
        JButton deleteGame = new JButton("Delete Game");
        deleteGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteGame();
            }
        });
        deleteGame.setPreferredSize(new Dimension(SCREEN_WIDTH * 3/4, 50));
        AbstractScreen.setColors(deleteGame, "s");
        JButton addGame = new JButton("Add Game");
        addGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGame();
            }
        });
        addGame.setPreferredSize(new Dimension(SCREEN_WIDTH * 3/4, 50));
        AbstractScreen.setColors(addGame, "s");
        bottom.add(displayGames(), BorderLayout.NORTH);
        tools.add(deleteGame);
        tools.add(addGame);
        bottom.add(tools, BorderLayout.SOUTH);
        panel.add(bottom, BorderLayout.SOUTH);
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

    private JScrollPane displayGames() {
        JList rlist = new JList(getGames("",""));
        rlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rlist.setSelectedIndex(0);
        rlist.setVisibleRowCount(5);
        AbstractScreen.setColors(rlist, "s");
        JScrollPane listScrollPane = new JScrollPane(rlist);
        AbstractScreen.setColors(listScrollPane, "m");
        listScrollPane.setPreferredSize(new Dimension( SCREEN_WIDTH * 3/4, SCREEN_HEIGHT/4));
        rlist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String selection = (String) rlist.getSelectedValue();
                setGame(glist.get(selection));
            }
        });
        return listScrollPane;
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
            System.out.println(g.getgID());
            if (!glist.containsKey(g.getDescription())) {
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

    public void deleteGame() {
        parent.getDbHandler().deleteGame(selectedG.getgID());
        printGame();
    }

    public void addGame() {
        new AddGame(this);
    }
}
