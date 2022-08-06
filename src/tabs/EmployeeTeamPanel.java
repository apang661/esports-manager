package tabs;

import model.*;
import ui.AbstractScreen;
import ui.HomeScreen;
import utils.CustomButton;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class EmployeeTeamPanel extends Panel {
    ArrayList<JPanel> teamPanels;
    JList teamBar;

    DefaultListModel<String> teamNames;
    int visibleTabIndex;
    JPanel contentPanel;

    public EmployeeTeamPanel(AbstractScreen parent) {
        super(parent);
        teamPanels = new ArrayList<>();
        panel.add(setupTeamBar(), BorderLayout.LINE_START);
        panel.add(setupContentPanel(), BorderLayout.CENTER);
        addAllTeams();
    }

    public DefaultListModel<String> getTeamNames() {
        return teamNames;
    }

    private void addAllTeams() {
        ArrayList<Team> teamNames =  parent.getDbHandler().getTeams();
        for (Team team : teamNames) {
            addTab(team.getName(), setupTeamPanel(team));
            System.out.println(team.getTeamID());
        }
    }

    private JPanel setupTeamPanel(Team team) {
        JPanel panel = new JPanel(new BorderLayout());
        AbstractScreen.setColors(panel, "m");
        JPanel titleContainer = new JPanel(new BorderLayout());
        AbstractScreen.setColors(titleContainer, "m");
        JLabel title = new JLabel(team.getName());
        title.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 2 + 30, 80));
        title.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        AbstractScreen.setColors(title, "m");
        AbstractScreen.setColors(panel, "m");
        JPanel ownerContainer = new JPanel(new BorderLayout());
        ownerContainer.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 2 + 30, 50));
        JLabel owner = new JLabel("Owner:");
        owner.setHorizontalAlignment(SwingConstants.RIGHT);
        owner.setPreferredSize(new Dimension(150, 50));
        owner.setForeground(AbstractScreen.TEXT_COLOR);
        AbstractScreen.setColors(ownerContainer, "m");
        JTextField ownerName = new JTextField(team.getOwner());
        ownerName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    parent.getDbHandler().updateTeamOwner(ownerName.getText(), team.getTeamID());
                    team.setOwner(ownerName.getText());
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        AbstractScreen.setColors(ownerName, "s");
        ownerContainer.add(owner, BorderLayout.LINE_START);
        ownerContainer.add(ownerName, BorderLayout.CENTER);
        titleContainer.add(title, BorderLayout.NORTH);
        titleContainer.add(ownerContainer, BorderLayout.SOUTH);

        panel.add(titleContainer, BorderLayout.NORTH);
        ArrayList<Roster> rosters = parent.getDbHandler().getRosters(team.getTeamID());
        JPanel mainInfo = new JPanel(new BorderLayout());
        ArrayList<Achievement> tempA = parent.getDbHandler().getTeamAchievements(team.getTeamID());
        DefaultListModel<JPanel> achievements = new DefaultListModel<>();
        for (Achievement a : tempA) {
            JPanel temp = new JPanel(new BorderLayout());
            AbstractScreen.setColors(temp, "s");
            JPanel tempT = new JPanel();
            JLabel season = new JLabel(a.getSeason() + " " + a.getYear());
            season.setHorizontalAlignment(SwingConstants.CENTER);
            season.setForeground(AbstractScreen.TEXT_COLOR);
            tempT.add(season);
            AbstractScreen.setColors(tempT, "m");
            temp.add(tempT, BorderLayout.NORTH);
            JLabel placement = new JLabel("Placement: " + String.valueOf(a.getPlacement()));
            placement.setHorizontalAlignment(SwingConstants.CENTER);
            placement.setVerticalAlignment(SwingConstants.CENTER);
            placement.setForeground(AbstractScreen.TEXT_COLOR);
            temp.add(placement, BorderLayout.SOUTH);
            achievements.addElement(temp);
        }
        JList<JPanel> tempList = new JList(achievements);
        tempList.setFixedCellHeight(80);
        tempList.setFixedCellWidth(100);
        tempList.setSelectedIndex(-1);
        tempList.setLayoutOrientation(JList.VERTICAL_WRAP);
        tempList.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 2 + 30, 80));
        tempList.setCellRenderer(new ListCellRenderer<JPanel>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends JPanel> list, JPanel value, int index, boolean isSelected, boolean cellHasFocus) {
                return value;
            }
        });
        JScrollPane achievementList = new JScrollPane(tempList, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER , ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tempList.setBackground(new Color(52, 52, 52));
        achievementList.setBorder(new LineBorder(AbstractScreen.MAIN_COLOR));
        mainInfo.add(achievementList, BorderLayout.NORTH);
        panel.add(mainInfo, BorderLayout.CENTER);
        addRosters(mainInfo, rosters);
        return panel;
    }

    private void addRosters(JPanel panel, ArrayList<Roster> rosters) {
        JPanel rosterContainer = new JPanel(new GridLayout(0,1));
        rosterContainer.setBorder(new LineBorder(AbstractScreen.MAIN_COLOR, 10));
        for (Roster r: rosters) {
            JPanel rosterPanel = new JPanel(new BorderLayout());
            JLabel title = new JLabel(r.getSeason() + " " + r.getYear());
            title.setHorizontalAlignment(SwingConstants.CENTER);
            rosterContainer.add(rosterPanel);
            JLabel wins = new JLabel("Wins: " + r.getWins());
            AbstractScreen.setColors(wins, "s");
            JPanel winsContainer = new JPanel();
            winsContainer.add(wins);
            winsContainer.setBackground(new Color(0, 105, 0));
            JLabel losses = new JLabel("Losses: " + r.getLosses());
            AbstractScreen.setColors(losses, "s");
            JPanel lossContainer = new JPanel();
            lossContainer.add(losses);
            lossContainer.setBackground(new Color(134, 8, 8));
            JPanel wl = new JPanel(new GridLayout(0, 2));
            wl.add(winsContainer);
            wl.add(lossContainer);
            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.add(title, BorderLayout.NORTH);
            topPanel.add(wl, BorderLayout.SOUTH);
            rosterPanel.add(topPanel, BorderLayout.NORTH);

            JPanel memberPanel = new JPanel(new GridLayout(0, 3));
            String[] labels = {"ID", "Name/Alias", "Role"};
            for (String l : labels) {
                JLabel temp = new JLabel(l);
                memberPanel.add(temp);
            }
            ArrayList<Player> players = parent.getDbHandler().getRosterPlayers(r.getTeamID(), r.getSeason(), r.getYear());
            for (Player p : players) {
                JLabel tempI = new JLabel(String.valueOf(p.getTeamMemberID()));
                memberPanel.add(tempI);
                JLabel tempA = new JLabel(p.getAlias());
                memberPanel.add(tempA);
                JLabel tempP = new JLabel(p.getPosition());
                memberPanel.add(tempP);
            }
            ArrayList<Staff> staff = parent.getDbHandler().getRosterStaff(r.getTeamID(), r.getSeason(), r.getYear());
            for (Staff s : staff) {
                JLabel tempI = new JLabel(String.valueOf(s.getTeamMemberID()));
                memberPanel.add(tempI);
                JLabel tempN = new JLabel(s.getName());
                memberPanel.add(tempN);
                JLabel tempR = new JLabel(s.getRole());
                memberPanel.add(tempR);
            }
            rosterPanel.add(memberPanel, BorderLayout.CENTER);
        }
        panel.add(rosterContainer, BorderLayout.CENTER);
    }

    private JPanel setupContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 2 + 30, AbstractScreen.SCREEN_HEIGHT));
        AbstractScreen.setColors(contentPanel, "m");
        return contentPanel;
    }

    private JPanel setupTeamBar() {
        JPanel sideBarParent = new JPanel(new BorderLayout());
        initTeamBar();
        panel.setBackground(AbstractScreen.TAB_COLOR);
        JScrollPane scroll = new JScrollPane(teamBar, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder( new LineBorder(AbstractScreen.MAIN_COLOR) );
        scroll.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 4 - 30, AbstractScreen.SCREEN_HEIGHT - 50));
        JPanel filler = new JPanel();
        AbstractScreen.setColors(filler, "m");
        filler.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 4 - 30, 9));
        sideBarParent.add(filler, BorderLayout.NORTH);
        sideBarParent.add(scroll, BorderLayout.LINE_START);
        JButton addTeam = new CustomButton("Add");
        AbstractScreen.setColors(addTeam, "m");
        addTeam.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 4 - 30, 50));
        sideBarParent.add(addTeam, BorderLayout.SOUTH);
        AbstractScreen.setColors(sideBarParent, "m");
        return sideBarParent;
    }

    private void initTeamBar() {
        teamNames = new DefaultListModel<>();
        teamBar = new JList(teamNames);
        teamBar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        teamBar.setSelectedIndex(0);
        teamBar.setVisibleRowCount(10);
        teamBar.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, 14));
        teamBar.setFixedCellHeight(55);
        teamBar.setSelectionBackground(AbstractScreen.TAB_HIGHLIGHTED);
        AbstractScreen.setColors(teamBar, "m");
        teamBar.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Integer selection = teamBar.getSelectedIndex();
                displayTab(selection);
            }
        });
    }

    protected void addTab(String tabName, JPanel tabPanel) {
        teamNames.addElement(tabName);
        GridBagConstraints gbc = new GridBagConstraints();

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
            visibleTabIndex = index;
        } else if (visibleTabIndex != index) {
            teamPanels.get(visibleTabIndex).setVisible(false);
            teamPanels.get(index).setVisible(true);
            visibleTabIndex = index;
        }
    }
}
