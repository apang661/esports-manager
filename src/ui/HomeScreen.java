package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HomeScreen extends JFrame {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final Color BACKGROUND_COLOR = new Color(30, 30, 30);
    public static final String DEFAULT_FONT_NAME = "Helvetica";
    private JPanel userSelect;

    public HomeScreen() {
        super("eSports App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        userSelect = createUserSelect();
        add(userSelect, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    private JPanel createUserSelect() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 30, 30, 30);

        panel.add(createButtonPanel(), gbc);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 0, 20));
        panel.setBackground(BACKGROUND_COLOR);

        JLabel instructionText = new JLabel("Please select a login type:");
        instructionText.setForeground(Color.white);
        instructionText.setFont(new Font(DEFAULT_FONT_NAME, Font.PLAIN, 18));

        JButton viewerButton = getUserSelectButton("Viewer", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUserScreen("viewer");
            }
        });
        JButton analystButton = getUserSelectButton("Analyst", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUserScreen("analyst");
            }
        });
        JButton employeeButton = getUserSelectButton("Employee", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUserScreen("employee");
            }
        });

        panel.add(instructionText);
        panel.add(viewerButton);
        panel.add(analystButton);
        panel.add(employeeButton);

        return panel;
    }

    private void createUserScreen(String user) {
        JPanel mainScreen;

        if (user.equals("viewer")) {
            String inputValue = JOptionPane.showInputDialog("Please enter your viewer ID:", JOptionPane.QUESTION_MESSAGE);


            while (true) {
                try {
                    int viewerID = Integer.parseInt(inputValue);
                    mainScreen = new ViewerScreen(viewerID);
                    break;
                } catch (NumberFormatException e) {
                    inputValue = JOptionPane.showInputDialog("Viewer ID must be an integer. Please try again:", JOptionPane.QUESTION_MESSAGE);
                }
            }
        } else if (user.equals("analyst")) {
            mainScreen = new AnalystScreen();
        } else {
            mainScreen = new EmployeeScreen();
        }

        add(mainScreen);
        remove(userSelect);
        pack();
    }

    private JButton getUserSelectButton(String label, Action action) {
        JButton button = new JButton(action);
        button.setText(label);
        button.setMargin(new Insets(10, 0, 10, 0));
        button.setBackground(new Color(60, 60, 60));
        button.setForeground(Color.white);
        button.setFont(new Font(DEFAULT_FONT_NAME, Font.PLAIN, 18));
        return button;
    }

    // Helper function for finding the borders of a component
    private void createBorder(JComponent comp) {
        comp.setBorder(BorderFactory.createLineBorder(Color.white));

    }
}
