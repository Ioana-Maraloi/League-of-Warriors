import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWin extends JFrame implements ActionListener {
    JPanel panel;
    Game game;
    GameWin(CharacterCls character) {
        this.game = Game.getInstance(character);
        panel = new JPanel(new GridLayout(3, 1));
        JLabel congratulationsLabel = new JLabel("Congratulations! You got to the portal!");
        congratulationsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(congratulationsLabel);

        JLabel options = new JLabel("Do you want to start a new game?");
        options.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(options);

        JPanel optionsLabel = new JPanel(new GridLayout(2, 1));
        JButton nextLevel = new JButton("Next Game");
        nextLevel.addActionListener(this);
        optionsLabel.add(nextLevel);
        JButton chooseADifferentCharacter = new JButton("choose a different character");
        optionsLabel.add(chooseADifferentCharacter);
        chooseADifferentCharacter.addActionListener(this);
        panel.add(optionsLabel);
        add(panel, BorderLayout.CENTER);
        setSize(400, 400);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
     String value = e.getActionCommand();
     if (value.equals("Next Game")){
         dispose();
         Game.newGrid();
         game.run(game.myCharacter);
     }
     if (value.equals("choose a different character")) {
         dispose();
         Game.newGrid = true;
         ChooseCharacter chooseCharacter = new ChooseCharacter(Game.getMyAccount());
         chooseCharacter.setLocationRelativeTo(null);
         chooseCharacter.setVisible(true);
     }
    }
}
