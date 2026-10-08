import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    JTextField username;
    JPasswordField password;

    Login() {

        setTitle("Airport Baggage Handling System");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("AIRPORT BAGGAGE SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(100, 30, 280, 30);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(60, 100, 100, 25);

        username = new JTextField();
        username.setBounds(160, 100, 200, 30);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(60, 150, 100, 25);

        password = new JPasswordField();
        password.setBounds(160, 150, 200, 30);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setBounds(160, 210, 100, 35);

        loginButton.addActionListener(e -> login());

        panel.add(title);
        panel.add(userLabel);
        panel.add(username);
        panel.add(passLabel);
        panel.add(password);
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    void login() {

        String user = username.getText();
        String pass = new String(password.getPassword());

        if (user.equals("admin") && pass.equals("1234")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login Successful!"
            );

            dispose();
            new Dashboard(new BaggageManager());

            // Dashboard will be opened here later

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Username or Password!"
            );
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}