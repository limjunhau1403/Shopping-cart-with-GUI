import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HomePageGUI extends JFrame {
    private JPanel productPanel;
    private DefaultListModel<String> productListModel;
    private JList<String> productList;
    private JSpinner quantitySpinner;
    private JButton addButton;
    private JButton viewCartButton;

    private JLabel productImageLabel;
    private JTextArea productDetailsArea;

    private Map<String, Product> products = new HashMap<>();
    private Map<String, List<Product>> categoryProducts = new HashMap<>();
    private ShoppingCartGUI shoppingCartGUI;

    public HomePageGUI() {
        setTitle("Product Selection");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // bck color
        getContentPane().setBackground(new Color(204, 229, 255));

        initializeDummyData();

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


        // navigation
        JPanel navigationPanel = new JPanel(new GridLayout(0, 1, 0, 20));
        navigationPanel.setBackground(new Color(204, 229, 255));
        navigationPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel navigationLabel = new JLabel("Categories", JLabel.CENTER);
        navigationLabel.setFont(new Font("Serif", Font.BOLD, 15));
        navigationPanel.add(navigationLabel);

        String[] categories = {"Cat Food", "Cat Litter", "Cat Treats","Cat Toys"};

        for (String category : categories) {
            JButton categoryButton = new JButton(category);
            categoryButton.setFont(new Font("Serif", Font.BOLD, 15));
            categoryButton.setBackground(new Color(153, 204, 255));
            categoryButton.setForeground(Color.WHITE); // Set font color
            categoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateProductList(category);
                }
            });
            navigationPanel.add(categoryButton);
        }

        add(navigationPanel, BorderLayout.WEST);

        //product list
        JPanel productListPanel = new JPanel(new BorderLayout());
        productListPanel.setBorder(BorderFactory.createTitledBorder("Products"));
        productListPanel.setBackground(new Color(204, 229, 255));
        productListModel = new DefaultListModel<>();
        productList = new JList<>(productListModel);
        productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productList.setFont(new Font("Serif", Font.PLAIN, 15));
        JScrollPane productScrollPane = new JScrollPane(productList);
        productListPanel.add(productScrollPane, BorderLayout.CENTER);
        add(productListPanel, BorderLayout.CENTER);

        //details
        JPanel productDetailsPanel = new JPanel(new BorderLayout());
        productDetailsPanel.setBorder(BorderFactory.createTitledBorder("Product Details"));
        productDetailsPanel.setPreferredSize(new Dimension(800, 0));
        productDetailsPanel.setBackground(new Color(204, 229, 255));

        productImageLabel = new JLabel();
        productImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        productDetailsPanel.add(productImageLabel, BorderLayout.NORTH);

        productDetailsArea = new JTextArea();
        productDetailsArea.setLineWrap(true);
        productDetailsArea.setWrapStyleWord(true);
        productDetailsArea.setEditable(false);
        productDetailsArea.setFont(new Font("Serif", Font.PLAIN, 15));
        JScrollPane detailsScrollPane = new JScrollPane(productDetailsArea);
        productDetailsPanel.add(detailsScrollPane, BorderLayout.CENTER);

        add(productDetailsPanel, BorderLayout.EAST);

        // control button & quantity
        JPanel controlPanel = new JPanel(new GridLayout(0, 1));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Controls"));
        controlPanel.setBackground(new Color(204, 229, 255));

        JPanel quantityPanel = new JPanel(new FlowLayout());
        quantityPanel.setBackground(new Color(204, 229, 255));
        quantityPanel.add(new JLabel("Quantity:"));
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        quantitySpinner.setFont(new Font("Serif", Font.PLAIN, 14));
        quantityPanel.add(quantitySpinner);
        controlPanel.add(quantityPanel);

        addButton = new JButton("Add Item");
        addButton.setFont(new Font("Serif", Font.BOLD, 14));
        controlPanel.add(addButton);

        viewCartButton = new JButton("View Shopping Cart");
        viewCartButton.setFont(new Font("Serif", Font.BOLD, 14));
        controlPanel.add(viewCartButton);

        add(controlPanel, BorderLayout.SOUTH);

        for (String category : categories) {
            updateProductList(category);
        }

        productList.addListSelectionListener(e -> displayProductDetails());

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSelectedItem();
            }
        });

        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewShoppingCart();
            }
        });

        setVisible(true);
    }

    //insert
    private void initializeDummyData() {
        Product product1 = new Product("Wild Rabbits", "Cat Food", "Rabbit meat has a high protein content of 21.5 per cent, which meets the high protein needs of cats,and is rich in niacin and lecithin to help cats with hair and skin care.", "pic/food1.jpg", 50.0);
        Product product2 = new Product("Hunting Feast Turkey", "Cat Food", "Turkey is a high-protein, low-fat, low-calorie and low-cholesterol ingredient, which is good for cats to build up muscle and grow strong. It is also rich in vitamins and nutrients, which can supplement the cat's nutrition and strengthen its body.", "pic/food2.jpg", 50.0);
        Product product3 = new Product("Hunting Bird Squab", "Cat Food", "Two squabs are a very high quality source of protein and are rich in amino acids and unsaturated fatty acids. Pigeon meat is also easier to digest and absorb, suitable for cats who want to grow meat and hair cheeks!", "pic/food3.jpg", 50.0);
        Product product4 = new Product("Baby Powder Flavour Litter", "Cat Litter", "Clumps quickly and super absorption with ★ Triple Action Formula ★\n 70% Tofu Litter + 28% Crushed Bentonite. Along with 2% natural deodorant particle firmly locks the odour. The bentonite litter smarty fills up the gaps between tofu litter, it instantly boost up the power of clumping effects and odour control. This litter is naturally scented, keeps both you and your cats a comfortable air environment, happy, and healthy.", "pic/litter1.jpg", 17.0);
        Product product5 = new Product("Strawberry Flavour Litter", "Cat Litter", "Clumps quickly and super absorption with ★ Triple Action Formula ★\n 70% Tofu Litter + 28% Crushed Bentonite. Along with 2% natural deodorant particle firmly locks the odour. The bentonite litter smarty fills up the gaps between tofu litter, it instantly boost up the power of clumping effects and odour control. This litter is naturally scented, keeps both you and your cats a comfortable air environment, happy, and healthy.", "pic/litter2.jpg", 17.0);
        Product product6 = new Product("Apple Flavour", "Cat Litter", "Clumps quickly and super absorption with ★ Triple Action Formula ★\n 70% Tofu Litter + 28% Crushed Bentonite. Along with 2% natural deodorant particle firmly locks the odour. The bentonite litter smarty fills up the gaps between tofu litter, it instantly boost up the power of clumping effects and odour control. This litter is naturally scented, keeps both you and your cats a comfortable air environment, happy, and healthy.", "pic/litter3.jpg", 17.0);
        Product product7 = new Product("Lavender Flavour", "Cat Litter", "Clumps quickly and super absorption with ★ Triple Action Formula ★\n 70% Tofu Litter + 28% Crushed Bentonite. Along with 2% natural deodorant particle firmly locks the odour. The bentonite litter smarty fills up the gaps between tofu litter, it instantly boost up the power of clumping effects and odour control. This litter is naturally scented, keeps both you and your cats a comfortable air environment, happy, and healthy.", "pic/litter4.jpg", 17.0);
        Product product8 = new Product("Tuna Chicken", "Cat Treats", "All natural highly nutritions fish & chicken, grain-free, vet formulated\nIncluded 3px", "pic/treats1.jpg", 1.50);
        Product product9 = new Product("Tuna Shirmp", "Cat Treats", "All natural highly nutritions fish & chicken, grain-free, vet formulated\nIncluded 3px", "pic/treats2.jpg", 1.50);
        Product product10 = new Product("Salmon Shirmp", "Cat Treats", "All natural highly nutritions fish & chicken, grain-free, vet formulated\nIncluded 3px", "pic/treats3.jpg", 1.50);
        Product product11 = new Product("Cat Mint", "Cat Treats", "Catnip contains thujaplicin lactone (nepetalactone), which can make cats become excited or relaxed and is often used to encourage play and relieve stress. This product is not harmful to cats", "pic/treats4.jpg", 8.00);
        Product product12 = new Product("Cat Tower Ball Pet Four Layers", "Cat Toys", "Features:\n▶️ Suitable for all cats\n▶️ Lovely Toys to entertain your furkids\n▶️ Great resting place for your pet, but also a pet toy for claw grinding\n▶️Well designed according to cat's habits, it is practical, creative, eco- friendly, room-saving and multifunctional\n▶️ Small and compact without occupying too much space, easy to clean\n▶️ With the help of it, your cat can grind its claws conveniently, so as to prevent the cat from scratching your floor, sofa, pillow and furniture", "pic/toys1.jpg", 8.00);
        Product product13 = new Product("Cat teasing stick", "Cat Toys", "100% brand new and high quality", "pic/toys2.jpg", 2.00);
        Product product14 = new Product("Catnip Fish Plush", "Cat Toys", "100% brand new and high quality\nMaterial: PP cotton filling, catnip, HempFabric", "pic/toys3.jpg", 6.00);


        products.put(product1.getName(), product1);
        products.put(product2.getName(), product2);
        products.put(product3.getName(), product3);
        products.put(product4.getName(), product4);
        products.put(product5.getName(), product5);
        products.put(product6.getName(), product6);
        products.put(product7.getName(), product7);
        products.put(product8.getName(), product8);
        products.put(product9.getName(), product9);
        products.put(product10.getName(), product10);
        products.put(product11.getName(), product11);
        products.put(product12.getName(), product12);
        products.put(product13.getName(), product13);
        products.put(product14.getName(), product14);





        categoryProducts.put("Cat Food", products.values().stream().filter(p -> p.getCategory().equals("Cat Food")).collect(Collectors.toList()));
        categoryProducts.put("Cat Litter", products.values().stream().filter(p -> p.getCategory().equals("Cat Litter")).collect(Collectors.toList()));
        categoryProducts.put("Cat Treats", products.values().stream().filter(p -> p.getCategory().equals("Cat Treats")).collect(Collectors.toList()));
        categoryProducts.put("Cat Toys", products.values().stream().filter(p -> p.getCategory().equals("Cat Toys")).collect(Collectors.toList()));
    }

    private void updateProductList(String category) {
        List<Product> productsForCategory = categoryProducts.get(category);

        productListModel.clear();
        for (Product product : productsForCategory) {
            productListModel.addElement(product.getName());
        }
    }

    private void displayProductDetails() {
        String selectedProduct = productList.getSelectedValue();
        if (selectedProduct != null) {
            Product product = products.get(selectedProduct);

            String productInfo = "Product Name: " + product.getName() + "\n"
                    + "Category: " + product.getCategory() + "\n"
                    + "Description: \n" + product.getDescription() + "\n"
                    + "Price: $" + product.getPrice();
            productDetailsArea.setText(productInfo);

            ImageIcon imageIcon = new ImageIcon(product.getImagePath());
            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
            productImageLabel.setIcon(scaledImageIcon);
        }
    }

    private void addSelectedItem() {
        String product = productList.getSelectedValue();
        int quantity = (int) quantitySpinner.getValue();

        Product selectedProduct = products.get(product);
        if (selectedProduct != null) {
            double price = selectedProduct.getPrice() * quantity;
            if (shoppingCartGUI == null) {
                shoppingCartGUI = new ShoppingCartGUI(this);
            }
            shoppingCartGUI.addItemToCart(product + " (Qty: " + quantity + ") - $" + String.format("%.2f", price));
            JOptionPane.showMessageDialog(this, "Item added to cart successfully!");
        }else{
            JOptionPane.showMessageDialog(this, "Shopping cart is not initialized");
        }
    }

    private void viewShoppingCart() {
        if (shoppingCartGUI == null) {
            shoppingCartGUI = new ShoppingCartGUI(this);
        }
        shoppingCartGUI.setVisible(true);
        setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HomePageGUI();
            }
        });
    }
}

