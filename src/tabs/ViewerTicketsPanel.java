package tabs;

import ui.AbstractScreen;

public class ViewerTicketsPanel extends Panel {
    public ViewerTicketsPanel(AbstractScreen parent) {
        super(parent);
    }

//    private void addBottom() {
//        JPanel bottom = new JPanel(new BorderLayout());
//        AbstractScreen.setColors(bottom, "s");
//        JPanel tools = new JPanel(new GridLayout(0, 1));
//        AbstractScreen.setColors(tools, "s");
//        JButton deleteGame = new JButton("Delete Game");
//        deleteGame.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                deleteGame();
//            }
//        });
//        deleteGame.setPreferredSize(new Dimension(SCREEN_WIDTH * 3/4, 50));
//        AbstractScreen.setColors(deleteGame, "s");
//        JButton addGame = new JButton("Add Game");
//        addGame.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                addGame();
//            }
//        });
//        addGame.setPreferredSize(new Dimension(SCREEN_WIDTH * 3/4, 50));
//        AbstractScreen.setColors(addGame, "s");
//        bottom.add(displayGames(), BorderLayout.NORTH);
//        tools.add(deleteGame);
//        tools.add(addGame);
//        bottom.add(tools, BorderLayout.SOUTH);
//        panel.add(bottom, BorderLayout.SOUTH);
//    }
}
