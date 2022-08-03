package tabs;

import model.Player;
import model.Team;
import ui.AbstractScreen;
import ui.HomeScreen;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import static ui.AbstractScreen.SCREEN_HEIGHT;
import static ui.AbstractScreen.SCREEN_WIDTH;

public class ViewerTeamPanel extends Panel {
    ArrayList<JPanel> teamPanels;
    JPanel teamBar;
    int visibleTabIndex;
    JPanel contentPanel;
    Player selectedPlayer = null;
    private DefaultListModel<String> plistModel;
    private HashMap<String, Player> plist;

    public ViewerTeamPanel(AbstractScreen parent) {
        super(parent);
        teamPanels = new ArrayList<>();
        panel.add(setupTeamBar(), BorderLayout.LINE_START);
        panel.add(setupContentPanel(), BorderLayout.CENTER);
        addAllTeams();
    }

    private void addAllTeams() {
        ArrayList<Team> teamNames =  parent.getDbHandler().getTeams();
        for (Team team : teamNames) {
            addTab(team.getName(), setupTeamMemberPanel(team));
        }
    }

    private JPanel setupTeamMemberPanel(Team team) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        JLabel test = new JLabel(team.getName());
        panel.add(test);
        return panel;
    }

    private JPanel setupContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new OverlayLayout(contentPanel));
        contentPanel.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH * 3 / 4, AbstractScreen.SCREEN_HEIGHT));
        contentPanel.setBackground(new Color(150, 150, 150));
        contentPanel.setAlignmentX(Box.RIGHT_ALIGNMENT);
        return contentPanel;
    }

    private JPanel setupTeamBar() {
        JPanel sideBar = new JPanel(new GridBagLayout());
        sideBar.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 4, AbstractScreen.SCREEN_HEIGHT));
        sideBar.setBackground(AbstractScreen.TAB_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();

        teamBar = new JPanel();
        teamBar.setLayout(new GridBagLayout());
        teamBar.setPreferredSize(new Dimension( AbstractScreen.SCREEN_WIDTH / 4, AbstractScreen.SCREEN_HEIGHT));
        teamBar.setBackground(AbstractScreen.TAB_COLOR);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        sideBar.add(teamBar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        JPanel panel = new JPanel();
        sideBar.add(panel, gbc);
        panel.setBackground(AbstractScreen.TAB_COLOR);
        return sideBar;
    }

    protected void addTab(String tabName, JPanel tabPanel) {
        JPanel tab = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel text = new JLabel(tabName);
        text.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, 14));
        text.setForeground(Color.WHITE);
        tab.setBackground(AbstractScreen.TAB_COLOR);

        int currentSize = teamPanels.size();
        tab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayTab(currentSize);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(20, 20, 20, 0);
        tab.add(text, gbc);

        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        teamBar.add(tab, gbc);

//        HomeScreen.createBorder(tabPanel, Color.RED);
        if (teamPanels.size() != 0) {
            tabPanel.setVisible(false);
        }

        teamPanels.add(tabPanel);
        contentPanel.add(tabPanel);
    }

    protected void displayTab(int index) {
        if (visibleTabIndex == -1) {
            teamPanels.get(index).setVisible(true);
            teamBar.getComponent(index).setBackground(AbstractScreen.TAB_HIGHLIGHTED);
            visibleTabIndex = index;
        } else if (visibleTabIndex != index) {
            teamPanels.get(visibleTabIndex).setVisible(false);
            teamBar.getComponent(visibleTabIndex).setBackground(AbstractScreen.TAB_COLOR);
            teamPanels.get(index).setVisible(true);
            teamBar.getComponent(index).setBackground(AbstractScreen.TAB_HIGHLIGHTED);
            visibleTabIndex = index;
        }
    }

    private JScrollPane displayPlayers(Team team) {
        JList rlist = new JList(getPlayers(team));
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
                selectedPlayer = plist.get(selection);
            }
        });
        return listScrollPane;
    }

    public DefaultListModel<String> getPlayers(Team team) {
        plistModel.clear();
        ArrayList<Player> players = parent.getDbHandler().getPlayers(team);
        for (Player p : players) {

        }

        return plistModel;
    }
}
