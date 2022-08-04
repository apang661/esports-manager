package tabs;

import model.Game;
import model.Player;
import model.Team;
import popUps.AddGamePopup;
import ui.AbstractScreen;
import ui.EmployeeScreen;
import utils.CustomButton;

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
    private JPanel main;
    JLabel title;
    JPanel redTeam;
    JPanel blueTeam;
    JLabel arena;
    JScrollPane languages;

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
        JButton deleteGame = new CustomButton("Delete Game", "s");
        deleteGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteGame();
            }
        });
        deleteGame.setPreferredSize(new Dimension(SCREEN_WIDTH * 3/4, 50));
        AbstractScreen.setColors(deleteGame, "s");
        JButton addGameButton = new CustomButton("Add Game", "s");
        addGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGame();
            }
        });
        addGameButton.setPreferredSize(new Dimension(SCREEN_WIDTH * 3/4, 50));
        AbstractScreen.setColors(addGameButton, "s");
        bottom.add(displayGames(), BorderLayout.NORTH);
        tools.add(deleteGame);
        tools.add(addGameButton);
        bottom.add(tools, BorderLayout.SOUTH);
        panel.add(bottom, BorderLayout.SOUTH);
    }

    private void addSearchBar() {
        JPanel searchBar = new JPanel(new BorderLayout());
        AbstractScreen.setColors(searchBar, "m");
        searchBar.setPreferredSize(new Dimension(SCREEN_WIDTH * 3/4, 50));
        JTextField search = new JTextField();
        AbstractScreen.setColors(search, "s");
        search.setText("Search");
        String[] filters = { "game ID", "arena", "team"};
        JPanel leftPanel = new JPanel(new BorderLayout());
        AbstractScreen.setColors(leftPanel, "m");
        JButton all = new CustomButton("View all");
        all.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                getGames("", "");
            }
        });
        JComboBox filterList = new JComboBox(filters);

        filterList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filter = (String) filterList.getSelectedItem();
            }
        });
        filterList.setSelectedIndex(0);
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


    private JScrollPane displayGames() {
        JList rlist = new JList(getGames("",""));
        rlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rlist.setSelectedIndex(0);
        rlist.setVisibleRowCount(5);
        AbstractScreen.setColors(rlist, "s");
        JScrollPane listScrollPane = new JScrollPane(rlist);
        AbstractScreen.setColors(listScrollPane, "s");
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
        main = new JPanel(new BorderLayout());
        title = new JLabel("No Game Selected");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        main.add(title, BorderLayout.NORTH);
        JPanel centreScreen = new JPanel(new BorderLayout());
        redTeam = new JPanel(new GridLayout(0, 1));
        blueTeam = new JPanel(new GridLayout(0, 1));
        initTeam(redTeam);
        initTeam(blueTeam);
        centreScreen.add(redTeam, BorderLayout.LINE_START);
        centreScreen.add(blueTeam, BorderLayout.LINE_END);
        JPanel lanes = new JPanel(new GridLayout(0,1));
        String[] lanelist = {"Teams", "Top", "Jg", "Mid", "ADC", "SUP"};
        for (String l : lanelist) {
            JLabel lane = new JLabel(l);
            lane.setHorizontalAlignment(SwingConstants.CENTER);
            lanes.add(lane);
        }
        lanes.setPreferredSize(new Dimension(50, 150));
        lanes.setMaximumSize(new Dimension(50, 150));
        centreScreen.add(lanes, BorderLayout.CENTER);
        main.add(centreScreen, BorderLayout.CENTER);
        JPanel centreLower = new JPanel();
        centreLower.setPreferredSize(new Dimension(SCREEN_WIDTH * 3/4, 50));
        centreLower.setMaximumSize(new Dimension(SCREEN_WIDTH * 3/4, 50));
        arena = new JLabel("Arena: ");
        arena.setPreferredSize(new Dimension(SCREEN_WIDTH / 4, 50));
        arena.setHorizontalAlignment(SwingConstants.LEFT);
        centreLower.add(arena, BorderLayout.LINE_START);
        JPanel languageContainer = new JPanel(new BorderLayout());
        languages = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JLabel languageLabel = new JLabel("Languages:");
        languageLabel.setPreferredSize(new Dimension(SCREEN_WIDTH / 5, 50));
        languageLabel.setMaximumSize(new Dimension(SCREEN_WIDTH / 5, 50));
        languageContainer.add(languageLabel, BorderLayout.LINE_START);
        languageContainer.add(languages, BorderLayout.LINE_END);
        languageContainer.setPreferredSize(new Dimension(SCREEN_WIDTH * 3/8 , 50));
        centreLower.add(languageContainer, BorderLayout.LINE_END);
        main.add(centreLower, BorderLayout.SOUTH);
        AbstractScreen.setColors(lanes, "s");
        AbstractScreen.setColors(redTeam, "s");
        AbstractScreen.setColors(blueTeam, "s");
        AbstractScreen.setColors(lanes, "s");
        AbstractScreen.setColors(main, "s");
        AbstractScreen.setColors(title, "s");
        AbstractScreen.setColors(arena, "s");
        AbstractScreen.setColors(centreLower, "s");
        AbstractScreen.setColors(languageContainer, "s");

    }

    private void initTeam(JPanel team) {
        team.setPreferredSize(new Dimension((SCREEN_WIDTH * 3/4 - 50) / 2, 150 ));
        for (int i = 0; i < 6; i++) {
            JLabel temp = new JLabel();
            team.add(temp);
        }
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
        clearGame();
        if (selectedG == null) {
        } else {
            title.setText("----------|   " + selectedG.getSeason() + " " + selectedG.getDay() + "   |----------");
            Team rtemp = parent.getDbHandler().getTeam(selectedG.getRtID());
            getPlayers(rtemp, redTeam);
            Team btemp = parent.getDbHandler().getTeam(selectedG.getBtID());
            getPlayers(btemp, blueTeam);
            arena.setText("Arena: " + parent.getDbHandler().getArena(selectedG.getaID()).getName());
        }
    }

    private void clearGame() {
        title.setText("No Game Selected :(");
        arena.setText("Arena: ");
        for (int i = 0; i < 6; i++) {
            ((JLabel) blueTeam.getComponent(0)).setText("");
            ((JLabel) redTeam.getComponent(0)).setText("");
        }
    }

    private void getPlayers(Team rtemp, JPanel team) {
        ArrayList<Player> players = parent.getDbHandler().getRosterPlayers(rtemp.getTeamID(), selectedG.getSeason(),selectedG.getDay().getYear());
        ((JLabel) team.getComponent(0)).setText(rtemp.getName());
        for (Player p : players) {
            ((JLabel) team.getComponent(p.getRoleNum() + 1)).setText(p.getAlias());
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
        new AddGamePopup(this);
    }
}
