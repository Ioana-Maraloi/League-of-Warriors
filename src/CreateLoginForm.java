import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CreateLoginForm extends JFrame implements ActionListener {
    final JTextField username, password;
    JPanel panel;
    JsonInput input = new JsonInput();
    ArrayList<Account> accounts = input.accounts;
    CreateLoginForm() {
        JLabel label1 = new JLabel("Enter username:");
        JLabel label2 = new JLabel("Enter password:");
        panel = new JPanel(new GridLayout(3, 1));
        username = new JTextField(10);
        password = new JPasswordField(10);
        JButton button = new JButton("Enter");

        panel.add(label1);
        panel.add(username);
        panel.add(label2);
        panel.add(password);
        JLabel message = new JLabel();
        panel.add(message);
        panel.add(button);
        button.addActionListener(this);
        panel.add(button);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setTitle("Login Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel, BorderLayout.CENTER);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String userValue = username.getText();
        String passwordValue = password.getText();
        boolean found = false;
        try {
            for (Account account : accounts) {
                if (account.account(userValue)) {
                    if (account.logIn(passwordValue)) {
                        found = true;
                        dispose();
                        ChooseCharacter chooseCharacter = new ChooseCharacter(account);
                        chooseCharacter.setLocationRelativeTo(null);
                        chooseCharacter.setVisible(true);
                    } else {
                        throw new InvalidCommandException("wrong password!");
                    }
                }
            }
            if (!found)
                throw new InvalidCommandException("no such user!");
        }
        catch (InvalidCommandException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        }
    }