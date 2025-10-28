import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Random;

public class ChooseSpell extends JFrame implements ActionListener {
    JPanel panel;
    Game game;
    Enemy myenemy;
    BattlePart myBattlepart;
    public void setBattlePart(BattlePart battlepart) {
        myBattlepart = battlepart;
    }
    ChooseSpell(CharacterCls character, Enemy enemy) {
        myenemy = enemy;
        game = Game.getInstance(character);
        panel = new JPanel(new GridLayout(1, character.spells.size()));
        int i = 0;
        for ( Spell spell : character.spells ) {
            JPanel spelli = new JPanel(new GridLayout(3, 1));
            spelli.setPreferredSize(new Dimension(800 / character.spells.size(), 800 ));
            JLabel pictureLabel = new JLabel();
            if (spell.ice){
                ImageIcon iconIce = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icicle.png")));
                Image scaledImageIce = iconIce.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                pictureLabel.setIcon(new ImageIcon(scaledImageIce));

//                pictureLabel.setIcon(new ImageIcon(new ImageIcon("icicle.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
            }
            if (spell.earth){
                ImageIcon iconEarth = new ImageIcon(Objects.requireNonNull(getClass().getResource("/earth.png")));
                Image scaledImageEarth = iconEarth.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                pictureLabel.setIcon(new ImageIcon(scaledImageEarth));

//                pictureLabel.setIcon(new ImageIcon(new ImageIcon("earth.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
            }
            if (spell.fire){

                ImageIcon iconFire = new ImageIcon(Objects.requireNonNull(getClass().getResource("/spell.png")));
                Image scaledImageFire = iconFire.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                pictureLabel.setIcon(new ImageIcon(scaledImageFire));

//                pictureLabel.setIcon(new ImageIcon(new ImageIcon("Spell.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
            }
            pictureLabel.setHorizontalAlignment(SwingConstants.CENTER);
            spelli.add(pictureLabel);

            JLabel aura = new JLabel("aura cost: " + spell.cost);
            JLabel damage = new JLabel(" damage: " + spell.damage);
            aura.setHorizontalAlignment(SwingConstants.CENTER);
            damage.setHorizontalAlignment(SwingConstants.CENTER);
            JPanel stats = new JPanel(new GridLayout(2, 1));
            stats.setPreferredSize(new Dimension(800 / character.spells.size(), 200 ));
            stats.add(aura);
            stats.add(damage);

            spelli.add(stats);

            JButton button = new JButton("choose");
            button.setHorizontalAlignment(SwingConstants.CENTER);
            button.addActionListener(this);
            String s = "";
            s += i;
            button.setActionCommand(s);
            spelli.add(button);
            i++;
            if (spell.cost > character.aura){
                button.setEnabled(false);
            }

            panel.add(spelli);
            setSize(800, 800);
            setTitle("Choose a spell!");

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            add(panel, BorderLayout.CENTER);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        int number = 0;
        if (command.equals("1"))
            number = 1;
        if (command.equals("2"))
            number = 2;
        if (command.equals("3"))
            number = 3;
        if (command.equals("4"))
            number = 4;
        if (command.equals("5"))
            number = 5;
        if (command.equals("6"))
            number = 6;
        Spell spell = game.myCharacter.spells.get(number);
        game.myCharacter.spells.remove(number);
        if (game.myCharacter.aura - spell.cost > 0)
            game.myCharacter.aura -= spell.cost;
        else
            game.myCharacter.aura = 0;
        myenemy.accept(spell);
        if (myenemy.life <= 0){
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

        Random rand = new Random();
        int enemyChoice = rand.nextInt(2);
        if (enemyChoice == 0){
//            normal attack
            game.myCharacter.receiveDamage(myenemy.getDamage());
        }
        if (enemyChoice == 1) {
//               spell attack
            if (myenemy.spells.isEmpty()) {
//                   normal attack
                game.myCharacter.receiveDamage(myenemy.getDamage());
            } else {
                int pos = rand.nextInt(myenemy.spells.size());
                Spell chosenSpell = myenemy.spells.get(pos);
                myenemy.spells.remove(pos);
                myenemy.aura -= chosenSpell.cost;
                game.myCharacter.accept(chosenSpell);
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

        myBattlepart.statsLabel.setText("life: " + game.myCharacter.life + " aura: " + game.myCharacter.aura);
        myBattlepart.heroPanel.repaint();
        myBattlepart.heroPanel.revalidate();
        myBattlepart.enemyStatsLabel.setText("life: " + myenemy.life + " aura: "+ myenemy.aura);
        myBattlepart.enemyPanel.repaint();
        myBattlepart.enemyPanel.revalidate();
        myBattlepart.setVisible(true);

        if (myenemy.fireImmunity && spell.fire){
            EnemyImmune enemyImmuneFire = new EnemyImmune("fire");
            enemyImmuneFire.setVisible(true);
        }
        if (myenemy.earthImmunity && spell.earth){
            EnemyImmune enemyImmuneEarth = new EnemyImmune("earth");
            enemyImmuneEarth.setVisible(true);
        }
        if (myenemy.iceImmunity && spell.ice){
            EnemyImmune enemyImmuneIce = new EnemyImmune("ice");
            enemyImmuneIce.setVisible(true);
        }
        dispose();
    }
}
