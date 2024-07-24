import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Register extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton backButton;
    private JLabel backgroundLabel;

    public Register() {
        setTitle("User Registration");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //bck color
        getContentPane().setBackground(new Color(204, 229, 255));

        //header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 229, 255));

        //adj img
        ImageIcon logoIcon = new ImageIcon("pic/meow.png"); // Replace with your logo path
        Image img = logoIcon.getImage();
        Image resizedImg = img.getScaledInstance(600, 200, Image.SCALE_SMOOTH); // Adjust size as needed
        ImageIcon resizedLogoIcon = new ImageIcon(resizedImg);
        JLabel logoLabel = new JLabel(resizedLogoIcon);
        headerPanel.add(logoLabel, BorderLayout.NORTH);

        add(headerPanel, BorderLayout.NORTH);

        // Center Panel with registration form
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

        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Serif", Font.BOLD, 15));
        registerButton.setBackground(new Color(102, 178, 255));
        registerButton.setForeground(Color.white);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(registerButton, gbc);

        backButton = new JButton("Back to Login");
        backButton.setFont(new Font("Serif", Font.BOLD, 15));
        backButton.setBackground(Color.white);
        backButton.setForeground(new Color(102, 178, 255));
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(backButton, gbc);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Login();
                dispose();
            }
        });

        getContentPane().add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username or Password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(username + "," + password);
            writer.newLine();
            JOptionPane.showMessageDialog(this, "User registered successfully");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving user", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Register();
            }
        });
    }
}
