package tabs;

import model.Roster;
import model.Team;
import ui.AbstractScreen;
import ui.HomeScreen;
import utils.CustomButton;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
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
        JLabel title = new JLabel(team.getName());
        title.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 2 + 30, 80));
        title.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        AbstractScreen.setColors(title, "m");
        AbstractScreen.setColors(panel, "m");
        panel.add(title, BorderLayout.NORTH);
        ArrayList<Roster> rosters = parent.getDbHandler().getRosters(team.getTeamID());
        addRosters(panel, rosters);
        return panel;
    }

    private void addRosters(JPanel panel, ArrayList<Roster> rosters) {
        JPanel rosterContainer = new JPanel(new GridLayout(0,1));
        for (Roster r: rosters) {
            JPanel rosterPanel = new JPanel(new BorderLayout());
            JLabel title = new JLabel(r.getSeason() + " " + r.getYear());
            title.setHorizontalAlignment(SwingConstants.CENTER);
            rosterPanel.add(title, BorderLayout.NORTH);
            rosterContainer.add(rosterPanel);
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
