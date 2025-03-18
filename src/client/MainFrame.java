package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.services.InventoryService;
import client.components.Cart;
import client.components.ProductCard;
import resources.SetPreferences;

public class MainFrame extends JFrame {
    private Cart cart = new Cart(this);
    private String[][] inventoryProduct = new InventoryService().getAllProductData();
    private JPanel topBar = TopBar();
    private JPanel sideBar = SideBar();
    private JPanel body = Body();
    private JPanel dashboard = new Dashboard();
    private JPanel mainPanel = new JPanel(new CardLayout()); // Use CardLayout
    private Container container = getContentPane();

    public MainFrame() {
        CreateGui();
        SetupWindow();
    }

    private void SetupWindow() {
        setTitle("POS");
        ImageIcon icon = new ImageIcon(getClass().getResource("/backend/data/images/logo.png"));
        setIconImage(icon.getImage());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void CreateGui() {
        container.setLayout(new BorderLayout());
        container.add(topBar, BorderLayout.NORTH);
        container.add(sideBar, BorderLayout.WEST);

        // Add body and dashboard to the mainPanel
        mainPanel.add(body, "Body");
        mainPanel.add(dashboard, "Dashboard");

        container.add(mainPanel, BorderLayout.CENTER);
        container.add(cart, BorderLayout.EAST);
    }

    private JPanel TopBar() {
        JPanel area = new JPanel(new FlowLayout());
        area.setPreferredSize(new Dimension(1000, 50));
        area.setBackground(Color.BLUE);
        return area;
    }

    private JPanel SideBar() {
        int width = 150;
        int height = 500;
        JPanel area = new JPanel(new FlowLayout());
        area.setPreferredSize(new Dimension(width, height));
        area.add(LogoArea(width, height));
        area.setBackground(Color.WHITE);

        area.add(ButtonToHome());
        area.add(ButtonToManageProduct());
        area.add(ButtonToManageUser());
        area.add(ButtonToDashBoard());
        return area;
    }

    private JPanel Body() {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.LEADING));
        for (String[] product : inventoryProduct) {
            area.add(new ProductCard(product[0], cart));
        }
        return area;
    }

    private JPanel LogoArea(int width, int height) {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 25));
        JLabel img = new JLabel();
        area.setPreferredSize(new Dimension(width, 150));
        ImageIcon icon = new ImageIcon(getClass().getResource("/backend/data/images/logo.png"));
        img.setIcon(new ImageIcon(icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        area.setBackground(Color.WHITE);
        area.add(img);
        return area;
    }

    private JButton ButtonToHome() {
        JButton Home = new JButton("Home");
        Home.setBackground(null);
        Home.setBorder(null);
        Home.setPreferredSize(new Dimension(120, 40));
        Home.setFont(new SetPreferences().getFont(14));
        Home.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "Body"); // Show the body panel
        });

        Home.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Home.setText("<html><u>Home</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Home.setText("Home");
            }
        });

        return Home;
    }

    private JButton ButtonToManageProduct() {
        JButton Btn = new JButton("Manage Product");
        Btn.setPreferredSize(new Dimension(120, 30));
        Btn.setFont(new SetPreferences().getFont(14));
        Btn.setBackground(null);
        Btn.setBorder(null);

        Btn.addActionListener(e -> {
        });

        Btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Btn.setText("<html><u>Manage Product</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Btn.setText("Manage Product");
            }
        });
        return Btn;
    }

    private JButton ButtonToManageUser() {
        JButton Btn = new JButton("Manage User");
        Btn.setPreferredSize(new Dimension(120, 30));
        Btn.setFont(new SetPreferences().getFont(14));
        Btn.setBackground(null);
        Btn.setBorder(null);

        Btn.addActionListener(e -> {
        });

        Btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Btn.setText("<html><u>Manage User</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Btn.setText("Manage User");
            }
        });
        return Btn;
    }

    private JButton ButtonToDashBoard() {
        JButton Btn = new JButton("History");
        Btn.setPreferredSize(new Dimension(120, 30));
        Btn.setFont(new SetPreferences().getFont(14));
        Btn.setBackground(null);
        Btn.setBorder(null);

        Btn.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            mainPanel.remove(dashboard);
            mainPanel.add(new Dashboard(),"Dashboard");
            cl.show(mainPanel, "Dashboard");
            cart.setVisible(true);
        });

        Btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Btn.setText("<html><u>History</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Btn.setText("History");
            }
        });
        return Btn;
    }

}
