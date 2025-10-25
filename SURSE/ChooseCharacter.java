import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseCharacter extends JFrame implements ActionListener {
    JPanel panel;
    Account account;
    ChooseCharacter(Account account) {
        this.account = account;
        panel = new JPanel(new GridLayout(2, account.character.size()));
        for (int i = 0; i < account.character.size(); i++) {
            JPanel panel1 = new JPanel(new GridLayout(6, 1));

            JLabel name = new JLabel(account.character.get(i).name);
            JLabel xp = new JLabel("xp: " + account.character.get(i).xp);
            JLabel level = new JLabel("level: " + account.character.get(i).level);
            JLabel life = new JLabel("life: " + account.character.get(i).life);
            JLabel aura = new JLabel("aura: " + account.character.get(i).aura);
            JLabel damage = new JLabel(("damage:" + account.character.get(i).getDamage()));

            name.setHorizontalAlignment(SwingConstants.CENTER);
            xp.setHorizontalAlignment(SwingConstants.CENTER);
            level.setHorizontalAlignment(SwingConstants.CENTER);
            life.setHorizontalAlignment(SwingConstants.CENTER);
            aura.setHorizontalAlignment(SwingConstants.CENTER);
            damage.setHorizontalAlignment(SwingConstants.CENTER);

            panel1.add(name);
            panel1.add(xp);
            panel1.add(level);
            panel1.add(life);
            panel1.add(aura);
            panel1.add(damage);
            add(panel1, BorderLayout.CENTER);

            panel.add(panel1);

        }
        for (int i = 0; i < account.character.size(); i++) {
            JButton button = new JButton("choose " + account.character.get(i).name);
            button.addActionListener(this);
            panel.add(button);
            if (account.character.get(i).life <= 0){
                button.setEnabled(false);
            }

        }
        StringBuffer s = new StringBuffer();
        s.append("Choose a character ").append(account.getName());
        add(panel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(s.toString());
        setSize(600, 500);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
        String s = e.getActionCommand();
        for ( int i = 0; i < account.character.size(); i++) {
            if (s.equals("choose " + account.character.get(i).name)) {
                Game game = Game.getInstance(account.character.get(i));
                if (Game.newGrid) {
                    game.myCharacter = account.character.get(i);
                    game.newGrid();
                }
                game.setAccount(account);
                game.run(game.myCharacter);
            }
        }
    }
}
