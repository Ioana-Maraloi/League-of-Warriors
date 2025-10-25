import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DisplayGrid extends JFrame implements ActionListener {
    JPanel panel;
    Game game;
    JPanel characterPanel, GridPanel, bottomPanel;
    JPanel infoPanel, middlePanel, rightPanel;
    JLabel nameLabel, xpLabel, levelLabel,lifeLabel, auraLabel, damageLabel, enemiesKilled;
    ArrayList<ArrayList<JLabel>> labels = new ArrayList<>();
    public DisplayGrid(CharacterCls character) {
        panel = new JPanel();

        characterPanel = new JPanel(new GridLayout(1, 4));
        characterPanel.setBackground(new Color(76, 133, 116));
        characterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        characterPanel.setPreferredSize(new Dimension(800, 100));
//        adding a picture on the right
        JPanel heroPanel = new JPanel();
        JLabel heroLabel= new JLabel();
        heroLabel.setIcon(new ImageIcon(new ImageIcon("hero.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
        heroLabel.setHorizontalAlignment(SwingConstants.CENTER);
        heroPanel.add(heroLabel);
        characterPanel.add(heroPanel);
//        name, xp and level display
        infoPanel = new JPanel(new GridLayout(3, 1));
        nameLabel = new JLabel(character.name);
        infoPanel.add(nameLabel);
        xpLabel = new JLabel("xp: " + character.xp);
        infoPanel.add(xpLabel);
        levelLabel = new JLabel("level: " + character.level);
        infoPanel.add(levelLabel);
        characterPanel.add(infoPanel);
//        life, aura display
        middlePanel = new JPanel(new GridLayout(2, 1));
        lifeLabel = new JLabel("life: " + character.life);
        middlePanel.add(lifeLabel);
        auraLabel = new JLabel("aura: " + character.aura);
        middlePanel.add(auraLabel);
        characterPanel.add(middlePanel);
//        damage and enemies killed display
        rightPanel = new JPanel(new GridLayout(2, 1));
        damageLabel = new JLabel("damage: " + character.getDamage());
        enemiesKilled = new JLabel("enemies killed: " + character.enemiesDefeated);
        rightPanel.add(damageLabel);
        rightPanel.add(enemiesKilled);
        characterPanel.add(rightPanel);
        panel.add(characterPanel, BorderLayout.NORTH);

        GridPanel = new JPanel(new GridLayout(Grid.height , Grid.length));
        GridPanel.setBackground(new Color(139, 177, 191));
        GridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridPanel.setPreferredSize(new Dimension(800, 500));

        this.game = Game.getInstance(character);

        for (int i = 0; i < Grid.height; i++) {
//            ArrayList<JLabel> row = new ArrayList<>();
            for (int j = 0; j < Grid.length; j++) {
                JLabel label = new JLabel(Game.grid.matrix.get(i).get(j).toString());
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                label.setHorizontalAlignment(JLabel.CENTER);
                GridPanel.add(label);
//                row.add(label);
            }
//            labels.add(row);
        }
        panel.add(GridPanel, BorderLayout.CENTER);

        bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(97, 138,132));
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        bottomPanel.setPreferredSize(new Dimension(800, 100));
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setSize(800, 700);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
        setTitle("Grid");
    }
    public void updateGrid() {
        ArrayList<String> possibleMoves = game.possibleMoves();
        panel.remove(bottomPanel);
        bottomPanel = new JPanel(new GridLayout(1, possibleMoves.size() + 1));
        for (String possibleMove : possibleMoves) {
            JButton button = new JButton(possibleMove);
            button.setBackground(new Color(97, 138,132));
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            button.addActionListener(this);
            bottomPanel.add(button);
        }

        JButton button = new JButton("exit");
        button.setBackground(new Color(97, 138,132));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        button.addActionListener(this);
        bottomPanel.add(button);

        panel.add(bottomPanel, BorderLayout.SOUTH);
        panel.revalidate();
        panel.repaint();
    }
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        Grid grid = Game.grid;
        StringBuffer result = new StringBuffer();
         switch (s) {
            case "Move-up" -> result.append(grid.goNorth());
            case "Move-down" -> result.append(grid.goSouth());
            case "Move-left" -> result.append(grid.goWest());
            case "Move-right" -> result.append(grid.goEast());
            case "exit" -> {
                dispose();
                ChooseCharacter chooseCharacter = new ChooseCharacter(Game.getMyAccount());
                chooseCharacter.setLocationRelativeTo(null);
                Game.newGrid = true;
                chooseCharacter.setVisible(true);
            }
        }
        if (result.toString().equals("sanctuary")) {
            Sanctuary sanctuary = new Sanctuary(game.myCharacter);
            sanctuary.setVisible(true);
            lifeLabel.setText("life:" + game.myCharacter.life);
            auraLabel.setText("aura:" + game.myCharacter.aura);
            characterPanel.revalidate();
            characterPanel.repaint();
        }
        if (result.toString().equals("enemy")){
            setVisible(false);
            BattlePart battlePart = new BattlePart(game.myCharacter);
            battlePart.setLocationRelativeTo(null);
            battlePart.setVisible(true);
        }

        if (result.toString().equals("portal")){
            dispose();
            for (int i = 0; i < Game.getMyAccount().getCharacters(); i++){
                if (Game.getMyAccount().character.get(i).name.equals(game.myCharacter.name)){
                    Game.getMyAccount().character.get(i).xp += Game.getMyAccount().character.get(i).level * 5;
                    Game.getMyAccount().character.get(i).level += 1;
                    Game.getMyAccount().character.get(i).setMaxAura();
                    Game.getMyAccount().character.get(i).setMaxLife();
                    break;
                }
            }
            GameWin gamewin = new GameWin(game.myCharacter);
            gamewin.setLocationRelativeTo(null);
            gamewin.setVisible(true);
        }
        GridPanel.removeAll();
        labels.clear();

        for (int i = 0; i < Grid.height; i++) {
            ArrayList<JLabel> row = new ArrayList<>();
            for (int j = 0; j < Grid.length; j++) {
                JLabel label = new JLabel(Game.grid.matrix.get(i).get(j).toString());
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                label.setHorizontalAlignment(JLabel.CENTER);

                GridPanel.add(label);
                row.add(label);
            }
            labels.add(row);
        }
        GridPanel.revalidate();
        GridPanel.repaint();

        ArrayList <String> possibleMoves = game.possibleMoves();


        panel.remove(bottomPanel);
        bottomPanel = new JPanel(new GridLayout(1, possibleMoves.size() + 1));
        for (String possibleMove : possibleMoves) {
            JButton button = new JButton(possibleMove);
            button.setBackground(new Color(97, 138,132));
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            button.addActionListener(this);
            bottomPanel.add(button);
        }

        JButton button = new JButton("exit");
        button.setBackground(new Color(97, 138,132));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        button.addActionListener(this);
        bottomPanel.add(button);

        panel.add(bottomPanel, BorderLayout.SOUTH);
        panel.revalidate();
        panel.repaint();

        if (game.myCharacter.life < 0){
            dispose();
            Game.newGrid = true;
            ChooseCharacter chooseCharacter = new ChooseCharacter(Game.getMyAccount());
            chooseCharacter.setLocationRelativeTo(null);
            chooseCharacter.setVisible(true);
        }
    }
}
