import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Random;

public class BattlePart extends JFrame implements ActionListener {
    JPanel panel;
    ImageIcon ice, fire, earth;
    Enemy enemy;
    Game game;
    JPanel heroPanel, enemyPanel, options;
    JLabel statsLabel, enemyStatsLabel;
    JButton specialAttack;
    BattlePart(CharacterCls character) {
        game = Game.getInstance(character);

        ice = new ImageIcon("icicle.png");
        fire = new ImageIcon("spell.png");
        earth = new ImageIcon("earth.png");

        enemy = new Enemy();
        Random rand = new Random();
        enemy.addPower(rand.nextInt(3, 6));

        panel = new JPanel(new GridLayout(1, 3));
//        hero part (left)
        heroPanel = new JPanel(new GridLayout(2, 1));
        JLabel heroLabel= new JLabel();
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/hero.png")));
        Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        heroLabel.setIcon(new ImageIcon(scaledImage));

//        heroLabel.setIcon(new ImageIcon(new ImageIcon("../PROIECT/hero.png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
        heroPanel.add(heroLabel);
        statsLabel = new JLabel("life: " + character.life + " aura: " + character.aura);
        heroPanel.add(statsLabel);
        heroPanel.setPreferredSize(new Dimension(100, 100));
        panel.add(heroPanel, BorderLayout.SOUTH);
//        buttons part(center)
        options = new JPanel(new GridLayout(2, 1));
        specialAttack = new JButton("Special Power");
//        if the hero doesn't have any spells
        if (game.myCharacter.spells.isEmpty()){
            specialAttack.setEnabled(false);
        }
//        if the hero doesn't have enough aura for one of his spells
        int count = 0;
        for (Spell s : game.myCharacter.spells) {
            if (s.cost > game.myCharacter.aura){
                count++;
            }
        }
        if (count == game.myCharacter.spells.size()){
            specialAttack.setEnabled(false);
        }
        specialAttack.addActionListener(this);
        options.add(specialAttack);
        JButton normalAttack = new JButton("Normal Attack");
        normalAttack.addActionListener(this);
        options.add(normalAttack);
        panel.add(options);
        options.setSize(100, 100);
//        enemy part(right)
        enemyPanel = new JPanel(new GridLayout(2, 1));
        JLabel enemyLabel = new JLabel();
//        enemyLabel.setIcon(new ImageIcon(new ImageIcon("ghost.png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)))
        ImageIcon iconEnemy = new ImageIcon(Objects.requireNonNull(getClass().getResource("/ghost.png")));
        Image scaledImageEnemy = iconEnemy.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        enemyLabel.setIcon(new ImageIcon(scaledImageEnemy));

        enemyPanel.add(enemyLabel);
        enemyStatsLabel = new JLabel("life: " + enemy.life + " aura: " + enemy.aura);
        enemyPanel.add(enemyStatsLabel);
        enemyPanel.setPreferredSize(new Dimension(100, 100));

        panel.add(enemyPanel, BorderLayout.SOUTH);

        setSize(500, 500);
        setTitle("Choose how to attack!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Special Power")) {
            setVisible(false);
            ChooseSpell chooseSpell = new ChooseSpell(game.myCharacter, enemy);
            chooseSpell.setBattlePart(this);
            chooseSpell.setLocationRelativeTo(null);
            chooseSpell.setVisible(true);
        }
        if (command.equals("Normal Attack")) {
            enemy.receiveDamage(game.myCharacter.getDamage());
        }
//            if the enemy died
        if (enemy.life <= 0){
            game.myCharacter.enemiesDefeated++;
            if (game.myCharacter.life * 2 < game.myCharacter.maxLife)
                game.myCharacter.life *= 2;
            else
                game.myCharacter.life = game.myCharacter.maxLife;
            if (game.myCharacter.aura * 2 < game.myCharacter.aura)
                game.myCharacter.aura *= 2;
            else
                game.myCharacter.aura = game.myCharacter.maxAura;
            Random rand = new Random();
            game.myCharacter.xp += rand.nextInt(5, 10);
            Game.grid.matrix.get(Grid.y).get(Grid.x).state = CellEntityType.PLAYER;
            Game.grid.matrix.get(Grid.y).get(Grid.x).visited = true;
            Game.grid.matrix.get(Grid.y).get(Grid.x).contains.replace(0, 1, "P");

            dispose();
            game.displayGrid.GridPanel.removeAll();
            for (int i = 0; i < Grid.height; i++){
                for (int j = 0; j < Grid.length; j++){
                    JLabel label = new JLabel(Game.grid.matrix.get(i).get(j).toString());
                    label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    label.setHorizontalAlignment(JLabel.CENTER);
                    game.displayGrid.GridPanel.add(label);
                }

            }
            game.displayGrid.revalidate();
            game.displayGrid.repaint();

            game.displayGrid.lifeLabel.setText("life: rev" + game.myCharacter.life);
            game.displayGrid.auraLabel.setText("aura:  rev" + game.myCharacter.aura);
            game.displayGrid.enemiesKilled.setText("enemies killed: rev" + game.myCharacter.enemiesDefeated);
            game.displayGrid.infoPanel.revalidate();
            game.displayGrid.infoPanel.repaint();
            game.displayGrid.middlePanel.revalidate();
            game.displayGrid.middlePanel.repaint();
            game.displayGrid.rightPanel.revalidate();
            game.displayGrid.rightPanel.repaint();
            game.displayGrid.setVisible(true);
            game.displayGrid.updateGrid();
        }

//        enemy attacks as well
        Random rand = new Random();
        int enemyChoice = rand.nextInt(2);
        if (enemyChoice == 0){
//            normal attack
            game.myCharacter.receiveDamage(enemy.getDamage());
        }
        if (enemyChoice == 1){
//               spell attack
            if (enemy.spells.isEmpty()){
//                   normal attack
                game.myCharacter.receiveDamage(enemy.getDamage());
            } else {
                int pos = rand.nextInt(enemy.spells.size());
                Spell chosenSpell = enemy.spells.get(pos);
                enemy.spells.remove(pos);
                enemy.aura -= chosenSpell.cost;
                game.myCharacter.accept(chosenSpell);
//                enemy.attack(chosenSpell, game.myCharacter);
            }
        }

        if (game.myCharacter.life <= 0){
            game.myCharacter.life = 0;
//          i lost
            dispose();
            EndGame endGame = new EndGame(game.myCharacter);
            endGame.setLocationRelativeTo(null);
            endGame.setVisible(true);
        }

        heroPanel.remove(statsLabel);
        statsLabel = new JLabel("life: " + game.myCharacter.life + " aura: " + game.myCharacter.aura);
        heroPanel.add(statsLabel);

        heroPanel.revalidate();
        heroPanel.repaint();

        if (game.myCharacter.spells.isEmpty()){
            specialAttack.setEnabled(false);
        }
        options.revalidate();
        options.repaint();

        enemyPanel.remove(enemyStatsLabel);
        enemyStatsLabel = new JLabel("life: " + enemy.life + " aura: " + enemy.aura);
        enemyPanel.add(enemyStatsLabel);

        enemyPanel.revalidate();
        enemyPanel.repaint();
    }
}
