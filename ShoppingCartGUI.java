import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ShoppingCartGUI extends JFrame {
    private HomePageGUI homePage;
    private DefaultListModel<String> cartListModel;
    private JList<String> cartList;
    private JButton updateButton;
    private JButton checkoutButton;
    private JButton backButton;

    public ShoppingCartGUI(HomePageGUI homePage) {
        this.homePage = homePage;

        setTitle("Shopping Cart");
        setSize(2200, 830);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 229, 255));

        //adj img
        ImageIcon logoIcon = new ImageIcon("pic/meow.png"); 
        Image img = logoIcon.getImage();
        Image resizedImg = img.getScaledInstance(600, 200, Image.SCALE_SMOOTH); 
        ImageIcon resizedLogoIcon = new ImageIcon(resizedImg);
        JLabel logoLabel = new JLabel(resizedLogoIcon);
        headerPanel.add(logoLabel, BorderLayout.NORTH);

        add(headerPanel, BorderLayout.NORTH);

        //bck color
        getContentPane().setBackground(new Color(204, 229, 255));

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder("Shopping Cart"));
        cartPanel.setBackground(new Color(204, 229, 255)); // Set panel background color
        cartListModel = new DefaultListModel<>();
        cartList = new JList<>(cartListModel);
        JScrollPane cartScrollPane = new JScrollPane(cartList);
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);
        mainPanel.add(cartPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(204, 229, 255)); // Set panel background color
        updateButton = new JButton("Update Item");
        checkoutButton = new JButton("Checkout");
        backButton = new JButton("Back to Home");

        buttonPanel.add(updateButton);
        buttonPanel.add(checkoutButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateItem();
            }
        });

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkout();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToHomePage();
            }
        });

        add(mainPanel);
        setLocationRelativeTo(null);
    }

    private void updateItem() {
        int selectedIndex = cartList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedItem = cartListModel.getElementAt(selectedIndex);
            String quantityStr = JOptionPane.showInputDialog(this, "Adjust quantity for " + selectedItem + " (use negative value to remove items):");
            if (quantityStr != null) {
                int quantityChange = Integer.parseInt(quantityStr);
                String[] parts = selectedItem.split(" - \\$");
                String productPart = parts[0];
                int currentQuantity = Integer.parseInt(productPart.split("\\(Qty: ")[1].split("\\)")[0]);
                int newQuantity = currentQuantity + quantityChange;
                if (newQuantity <= 0) {
                    cartListModel.remove(selectedIndex);
                } else {
                    double pricePerItem = Double.parseDouble(parts[1]) / currentQuantity;
                    double newPrice = pricePerItem * newQuantity;
                    cartListModel.set(selectedIndex, productPart.split("\\(Qty: ")[0] + "(Qty: " + newQuantity + ") - $" + String.format("%.2f", newPrice));
                }
            }
        }
    }

    private void checkout() {
        if (cartListModel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty. Add items to checkout.");
        } else {
            StringBuilder receipt = new StringBuilder("Receipt:\n");
            for (int i = 0; i < cartListModel.size(); i++) {
                receipt.append(cartListModel.get(i)).append("\n");
            }
            JOptionPane.showMessageDialog(this, receipt.toString());
        }
    }

    private void backToHomePage() {
        setVisible(false);
        homePage.setVisible(true);
    }

    public void addItemToCart(String item) {
        boolean itemExists = false;
        for (int i = 0; i < cartListModel.size(); i++) {
            String existingItem = cartListModel.getElementAt(i);
            if (existingItem.startsWith(item.split(" \\(Qty:")[0])) {
                int currentQuantity = Integer.parseInt(existingItem.split("\\(Qty: ")[1].split("\\)")[0]);
                int additionalQuantity = Integer.parseInt(item.split("\\(Qty: ")[1].split("\\)")[0]);
                double pricePerItem = Double.parseDouble(existingItem.split(" - \\$")[1]) / currentQuantity;
                int newQuantity = currentQuantity + additionalQuantity;
                double newPrice = pricePerItem * newQuantity;
                cartListModel.set(i, existingItem.split("\\(Qty: ")[0] + "(Qty: " + newQuantity + ") - $" + String.format("%.2f", newPrice));
                itemExists = true;
                break;
            }
        }
        if (!itemExists) {
            cartListModel.addElement(item);
        }
    }
}
