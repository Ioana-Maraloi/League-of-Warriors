import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Sanctuary extends JFrame {
    JPanel panel;
    Sanctuary(CharacterCls character){
        Game game = Game.getInstance(character);
        game.sanctuary();

        panel = new JPanel(new GridLayout(3,1));
        JLabel pictureLabel = new JLabel();
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/safe-zone.png")));
        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        pictureLabel.setIcon(new ImageIcon(scaledImage));

//        pictureLabel.setIcon(new ImageIcon(new ImageIcon("safe-zone.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
        pictureLabel.setHorizontalAlignment(SwingConstants.CENTER);


        JLabel label = new JLabel("You arrived at a sanctuary");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel life = new JLabel("life: " + game.myCharacter.life +  " and aura: " + game.myCharacter.aura);
        life.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
        panel.add(pictureLabel);
        panel.add(life);
        add(panel);
//        setLocationRelativeTo(null);

        setSize(300, 300);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Sanctuary");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
    }
}
