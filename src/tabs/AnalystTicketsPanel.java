package tabs;

import database.DatabaseConnectionHandler;
import model.SalesStruct;
import ui.AbstractScreen;
import ui.HomeScreen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class AnalystTicketsPanel extends JPanel {

    public static final int NO_SETTING = 0;
    public static final int GAME_SETTING = 1;
    public static final int TEAM_SETTING = 2;
    public static final int ARENA_SETTING = 3;
    private DatabaseConnectionHandler dbHandler;
    private int currentView;
    private JPanel listPanelContent;
    public AnalystTicketsPanel(DatabaseConnectionHandler dbHandler) {
        this.dbHandler = dbHandler;
        setLayout(new BorderLayout());
        add(setupViewSelection(), BorderLayout.PAGE_START);
        add(setupListPanel(), BorderLayout.CENTER);
    }

    private JPanel setupListPanel() {

        JPanel listPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        listPanel.setForeground(AbstractScreen.TEXT_COLOR);


        listPanelContent = new JPanel(new GridBagLayout());
        listPanelContent.setBackground(AbstractScreen.SECOND_COLOR);
        listPanelContent.setForeground(AbstractScreen.TEXT_COLOR);
//        HomeScreen.createBorder(listPanelContent, Color.GREEN);

        switchView(GAME_SETTING);

        JScrollPane listPanelScroller = new JScrollPane(listPanelContent);
        listPanelScroller.setBorder(new EmptyBorder(0, 0, 0, 0));

        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        listPanel.add(listPanelScroller, gbc);

        JPanel listPanelFiller = new JPanel();
        listPanelFiller.setBackground(AbstractScreen.SECOND_COLOR);
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        listPanel.add(listPanelFiller, gbc);

        return listPanel;
    }

    private void setupListPanelHeader(int setting) {


        JLabel firstLabel = new JLabel("Game: ");
        JLabel secondLabel = new JLabel("Date: ");

        if (setting == GAME_SETTING) {
            firstLabel = new JLabel("Game: ");
            secondLabel = new JLabel("Date: ");
        } else if (setting == TEAM_SETTING) {
            firstLabel = new JLabel("Team: ");
            secondLabel = new JLabel("Total Games: ");
        } else {

        }

        GridBagConstraints gbc = new GridBagConstraints();
        AbstractScreen.setBoldFont(firstLabel, 13);
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 0.8;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(15, 15, 5, 5);
        listPanelContent.add(firstLabel, gbc);


        AbstractScreen.setBoldFont(secondLabel, 13);
        gbc.gridx = 1;
        gbc.weightx = 0.2;
        gbc.insets = new Insets(15, 5, 5, 5);
        listPanelContent.add(secondLabel, gbc);

        JLabel totalViewers = new JLabel("Viewers: ");
        AbstractScreen.setBoldFont(totalViewers, 13);
        gbc.gridx = 2;
        listPanelContent.add(totalViewers, gbc);

        JLabel totalSales = new JLabel("Sales: ");
        AbstractScreen.setBoldFont(totalSales, 13);
        gbc.gridx = 3;
        gbc.insets = new Insets(15, 5, 5, 15);
        listPanelContent.add(totalSales, gbc);

        JSeparator sep = new JSeparator();
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        listPanelContent.add(sep, gbc);
    }

    private JPanel setupViewSelection() {
        JPanel viewBar = new JPanel(new FlowLayout());
        viewBar.setBackground(new Color(200,200,200));

        JLabel viewText = new JLabel("View By:");
        viewText.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, 14));
        viewText.setForeground(Color.BLACK);
        viewBar.add(viewText);

        String[] views = {"Game", "Team", "Arena"};
        JComboBox<String> viewSelect = new JComboBox<>(views);
        viewSelect.setEditable(false);
        viewSelect.setForeground(Color.BLACK);
        viewSelect.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, 12));
        viewSelect.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String view = (String) e.getItemSelectable().getSelectedObjects()[0];
                if (view.equals("Game")) {
                    switchView(GAME_SETTING);
                } else if (view.equals("Team")) {
                    switchView(TEAM_SETTING);
                } else {
                    switchView(ARENA_SETTING);
                }
            }
        });

        viewBar.add(viewSelect);

        return viewBar;
    }

    private void switchView(int view) {
        if (currentView != view) {
            ArrayList<SalesStruct> salesList;
            listPanelContent.removeAll();
            if (view == GAME_SETTING) {
                salesList = dbHandler.getGameSales();
                GridBagConstraints gbc = new GridBagConstraints();
                setupListPanelHeader(GAME_SETTING);
                for (int i = 0; i < salesList.size(); i++) {
                    GameSalesStruct gameSale = (GameSalesStruct) salesList.get(i);

                    JLabel gameName = new JLabel(gameSale.getGameName());
                    AbstractScreen.setDefaultFont(gameName, 13);
                    gbc.gridy = i + 2;
                    gbc.gridx = 0;
                    gbc.gridwidth = 1;
                    gbc.gridheight = 1;
                    gbc.weightx = 0.8;
                    gbc.anchor = GridBagConstraints.PAGE_START;
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.insets = new Insets(10, 15, 10, 5);
                    listPanelContent.add(gameName, gbc);

                    JLabel day = new JLabel(gameSale.getDay().toString());
                    AbstractScreen.setDefaultFont(day, 13);
                    gbc.gridx = 1;
                    gbc.weightx = 0.2;
                    gbc.insets = new Insets(5, 5, 5, 5);
                    listPanelContent.add(day, gbc);
                }
            } else if (view == TEAM_SETTING) {
                salesList = dbHandler.getTeamSales();
                GridBagConstraints gbc = new GridBagConstraints();
                setupListPanelHeader(TEAM_SETTING);
                for (int i = 0; i < salesList.size(); i++) {
                    TeamSalesStruct gameSale = (TeamSalesStruct) salesList.get(i);

                    JLabel teamName = new JLabel(gameSale.getTeamName());
                    AbstractScreen.setDefaultFont(teamName, 13);
                    gbc.gridy = i + 2;
                    gbc.gridx = 0;
                    gbc.gridwidth = 1;
                    gbc.gridheight = 1;
                    gbc.weightx = 0.8;
                    gbc.anchor = GridBagConstraints.PAGE_START;
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.insets = new Insets(10, 15, 10, 5);
                    listPanelContent.add(teamName, gbc);

                    JLabel totalGames = new JLabel(String.valueOf(gameSale.getTotalGames()));
                    AbstractScreen.setDefaultFont(totalGames, 13);
                    gbc.gridx = 1;
                    gbc.weightx = 0.2;
                    gbc.insets = new Insets(5, 5, 5, 5);
                    listPanelContent.add(totalGames, gbc);
                }
            } else {
                salesList = dbHandler.getGameSales();
            }

            for (int i = 0; i < salesList.size(); i++) {
                SalesStruct sale = salesList.get(i);
                GridBagConstraints gbc = new GridBagConstraints();

                JLabel totalViewers = new JLabel(String.valueOf(sale.getTotalViewers()));
                AbstractScreen.setDefaultFont(totalViewers, 13);
                gbc.gridy = i + 2;
                gbc.gridx = 2;
                gbc.weightx = 0.2;
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.fill = GridBagConstraints.BOTH;
                listPanelContent.add(totalViewers, gbc);

                DecimalFormat format = new DecimalFormat("#.00");
                String salesString = "$" + format.format(sale.getTotalSales());
                JLabel totalSales = new JLabel(salesString);
                AbstractScreen.setDefaultFont(totalSales, 13);

                gbc.gridx = 3;
                gbc.insets = new Insets(5, 5, 5, 15);
                listPanelContent.add(totalSales, gbc);
            }

            listPanelContent.repaint();
            listPanelContent.revalidate();
        }
    }


    // Class for converting SQL query result to gameName, totalViewers, totalSales
    public static class GameSalesStruct extends SalesStruct {
        private final String gameName;
        private final Date day;

        public GameSalesStruct(String gameName, Date day, int totalViewers, float totalSales) {
            super(totalViewers, totalSales);
            this.gameName = gameName;
            this.day = day;
        }

        public String getGameName() {
            return gameName;
        }

        public Date getDay() {
            return day;
        }
    }

    // Class for converting SQL query result to teamName, totalGames, totalViewers, totalSales
    public static class TeamSalesStruct extends SalesStruct {
        private final String teamName;
        private final int totalGames;

        public TeamSalesStruct(String gameName, int totalGames, int totalViewers, float totalSales) {
            super(totalViewers, totalSales);
            this.teamName = gameName;
            this.totalGames = totalGames;
        }

        public String getTeamName() {
            return teamName;
        }

        public int getTotalGames() {
            return totalGames;
        }
    }
}
