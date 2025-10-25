import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class EnemyImmune extends JFrame {
    JPanel panel;
    EnemyImmune(String s){
        panel = new JPanel(new GridLayout(2, 1));
        JLabel shieldPicture = new JLabel();
//        shieldPicture.setIcon(new ImageIcon(new ImageIcon("shield.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));

        ImageIcon shield = new ImageIcon(Objects.requireNonNull(getClass().getResource("/shield.png")));
        Image scaledShield = shield.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        shieldPicture.setIcon(new ImageIcon(scaledShield));


        shieldPicture.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(shieldPicture);



        JLabel label = new JLabel("Enemy is immune to "  + s);
        panel.add(label);
        add(panel, BorderLayout.NORTH);
        setLocationRelativeTo(null);
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Enemy Immune");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
    }

}
