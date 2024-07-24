import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel backgroundLabel;

    public Login() {
        setTitle("User Login");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // bck color
        getContentPane().setBackground(new Color(204, 229, 255));


        //header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 229, 255));

        // adj img
        ImageIcon logoIcon = new ImageIcon("pic/meow.png"); // Replace with your logo path
        Image img = logoIcon.getImage();
        Image resizedImg = img.getScaledInstance(600, 200, Image.SCALE_SMOOTH); // Adjust size as needed
        ImageIcon resizedLogoIcon = new ImageIcon(resizedImg);
        JLabel logoLabel = new JLabel(resizedLogoIcon);
        headerPanel.add(logoLabel, BorderLayout.NORTH);

        add(headerPanel, BorderLayout.NORTH);

        

        // Center Panel with login form
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField(25);
        usernameField.setFont(new Font("Serif", Font.PLAIN, 18));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(25);
        passwordField.setFont(new Font("Serif", Font.PLAIN, 18));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Serif", Font.BOLD, 15));
        loginButton.setBackground(new Color(102, 178, 255));
        loginButton.setForeground(Color.white);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Serif", Font.BOLD, 15));
        registerButton.setBackground(Color.white);
        registerButton.setForeground(new Color(102, 178, 255));
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(registerButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Register();
                dispose();
            }
        });

        getContentPane().add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void loginUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username or Password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean authenticated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials[0].equals(username) && credentials[1].equals(password)) {
                    authenticated = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading user file", "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (authenticated) {
            JOptionPane.showMessageDialog(this, "Login successful");

            // Open homepage after successful login
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new HomePageGUI();
                    dispose(); // Close window
                }
            });

        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Login();
            }
        });
    }
}
