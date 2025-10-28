import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class EndGame extends JFrame implements ActionListener {
    JPanel panel;
    EndGame(CharacterCls character) {
        panel = new JPanel(new GridLayout(3, 1));

        JLabel heroLabel= new JLabel();

        ImageIcon icon = new ImageIcon(
                Objects.requireNonNull(getClass().getResource("/hero.png"))
        );
        Image scaledImage = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        heroLabel.setIcon(new ImageIcon(scaledImage));

//        heroLabel.setIcon(new ImageIcon(new ImageIcon("hero.png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
        heroLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel statsPanel = new JPanel(new GridLayout(3, 1));
        JLabel stats = new JLabel("stats");
        stats.setHorizontalAlignment(SwingConstants.CENTER);
        statsPanel.setPreferredSize(new Dimension(100, 100));
        panel.add(heroLabel);
        JLabel enemiesKilled = new JLabel("enemies killed: " + character.enemiesDefeated);
        JLabel xp = new JLabel("xp: " + character.xp);
        JLabel name = new JLabel("Name: " + character.name);
        enemiesKilled.setHorizontalAlignment(SwingConstants.CENTER);
        xp.setHorizontalAlignment(SwingConstants.CENTER);
        name.setHorizontalAlignment(SwingConstants.CENTER);

        statsPanel.add(name);
        statsPanel.add(enemiesKilled);
        statsPanel.add(xp);

        panel.add(statsPanel, BorderLayout.SOUTH);
        add(panel, BorderLayout.CENTER);
        setTitle("End Game");


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        JButton startButton = new JButton("play again");
        startButton.addActionListener(this);
        buttonPanel.add(startButton);
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("Exit")) {
            dispose();
        }
        if (s.equals("play again")) {
            dispose();
            ChooseCharacter chooseCharacter = new ChooseCharacter(Game.getMyAccount());
            Game.newGrid = true;
            chooseCharacter.setLocationRelativeTo(null);
            chooseCharacter.setVisible(true);
        }
    }
}
