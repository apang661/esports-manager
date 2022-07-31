package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// TODO: Add functionality to set click action on tabs to switch to their respective panels

// A screen GUI template that facilitates the addition of tabs and tab content
public class AbstractScreen extends JPanel {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final Color TAB_COLOR = new Color(70, 70, 70);

    JPanel tabBar;

    JPanel tab;
    JPanel contentPanel;

    public AbstractScreen() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(HomeScreen.BACKGROUND_COLOR);
        setLayout(new BorderLayout());
        add(setupTabBar(), BorderLayout.LINE_START);
        add(setupContentPanel(), BorderLayout.CENTER);
    }

    private JPanel setupContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(SCREEN_WIDTH * 3 / 4, SCREEN_HEIGHT));
        contentPanel.setBackground(new Color(200, 200, 200));
        return contentPanel;
    }

    private JPanel setupTabBar() {
        JPanel sideBar = new JPanel(new GridBagLayout());
        sideBar.setPreferredSize(new Dimension(SCREEN_WIDTH / 4, SCREEN_HEIGHT));
        sideBar.setBackground(TAB_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();

        tabBar = new JPanel();
        tabBar.setLayout(new GridBagLayout());
        tabBar.setPreferredSize(new Dimension(SCREEN_WIDTH / 4, SCREEN_HEIGHT));
        tabBar.setBackground(TAB_COLOR);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        sideBar.add(tabBar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        JPanel panel = new JPanel();
        sideBar.add(panel, gbc);
        panel.setBackground(TAB_COLOR);
        return sideBar;
    }

    protected void addTab(String tabName, JPanel tabPanel) {
        tab = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        JLabel text = new JLabel(tabName);
        text.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, 14));
        text.setForeground(Color.WHITE);
        tab.setBackground(TAB_COLOR);
        tab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("cool");
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
        tabBar.add(tab, gbc);

        System.out.println(tab.getPreferredSize());
//        HomeScreen.createBorder(tab, Color.GRAY);
        contentPanel.add(tabPanel);
    }
}
