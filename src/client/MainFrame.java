package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
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
import client.components.Cart;
import client.components.DisplayProductPanel;
import resources.SetPreferences;

public class MainFrame extends JFrame {
    private Cart cart = new Cart(this);
    private JPanel topBar = TopBar();
    private JPanel sideBar = SideBar();
    private JPanel outerBody = OuterBody();
    private JPanel dashboard = new Dashboard();
    private JPanel mainPanel = new JPanel(new CardLayout());
    private JPanel productPanel = new JPanel(new CardLayout());
    private Container container = getContentPane();

    // Display Products panel Obj
    private JPanel All = new DisplayProductPanel().getPanel(cart, "all");
    private JPanel Electronics = new DisplayProductPanel().getPanel(cart, "1");
    private JPanel Foods = new DisplayProductPanel().getPanel(cart, "2");
    private JPanel Fashions = new DisplayProductPanel().getPanel(cart, "3");
    private JPanel Cosmetics = new DisplayProductPanel().getPanel(cart, "4");
    private JPanel Households = new DisplayProductPanel().getPanel(cart, "5");
    private JPanel Tools = new DisplayProductPanel().getPanel(cart, "6");
    private JPanel Sports = new DisplayProductPanel().getPanel(cart, "7");
    private JPanel Toys = new DisplayProductPanel().getPanel(cart, "8");

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

        // SideBar button
        mainPanel.add(outerBody, "Body");
        mainPanel.add(dashboard, "Dashboard");

        // Type Btn
        outerBody.add(productPanel, BorderLayout.CENTER);
        productPanel.add(All, "All");
        productPanel.add(Foods, "Foods");
        productPanel.add(Electronics, "Electronics");
        productPanel.add(Fashions, "Fashions");
        productPanel.add(Cosmetics, "Cosmetics");
        productPanel.add(Households, "Households");
        productPanel.add(Tools, "Tools");
        productPanel.add(Sports, "Sports");
        productPanel.add(Toys, "Toys");

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

    private JPanel OuterBody() {
        JPanel area = new JPanel(new BorderLayout());
        area.add(typeProductBtn(), BorderLayout.NORTH);
        return area;
    }

    private JPanel typeProductBtn() {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        area.setPreferredSize(new Dimension(1100, 150));
        JButton ElectronicsBtn = new JButton("Electronics");
        JButton FoodsBtn = new JButton("Food");
        JButton FashionsBtn = new JButton("Fashion");
        JButton CosmeticsBtn = new JButton("Electronics");
        JButton HouseholdsBtn = new JButton("Household");
        JButton ToolsBtn = new JButton("Tools");
        JButton SportsBtn = new JButton("Sport");
        JButton ToysBtn = new JButton("Toy");

        JButton[] buttons = { ElectronicsBtn, FoodsBtn, FashionsBtn, CosmeticsBtn, HouseholdsBtn, ToolsBtn, SportsBtn,
                ToysBtn };
        for (JButton button : buttons) {
            button.setBackground(Color.WHITE);
            button.setPreferredSize(new Dimension(200, 50));
            button.setPreferredSize(new Dimension(200, 50));
            button.setFont(new SetPreferences().getFont(20));

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setText("<html><u>" + button.getText() + "</u></html>");
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setText(button.getText().replace("<html><u>", "").replace("</u></html>", ""));
                }
            });

            FoodsBtn.addActionListener(e -> {
                CardLayout cl = (CardLayout) productPanel.getLayout();
                productPanel.remove(Foods);
                Foods = new DisplayProductPanel().getPanel(cart, "2");
                productPanel.add(Foods, "Foods");
                cl.show(productPanel, "Foods");
            });
            ElectronicsBtn.addActionListener(e -> {
                CardLayout cl = (CardLayout) productPanel.getLayout();
                productPanel.remove(Electronics);
                Electronics = new DisplayProductPanel().getPanel(cart, "1");
                productPanel.add(Electronics, "Electronics");
                cl.show(productPanel, "Electronics");
            });
            FashionsBtn.addActionListener(e -> {
                CardLayout cl = (CardLayout) productPanel.getLayout();
                productPanel.remove(Fashions);
                Fashions = new DisplayProductPanel().getPanel(cart, "3");
                productPanel.add(Fashions, "Fashions");
                cl.show(productPanel, "Fashions");
            });
            CosmeticsBtn.addActionListener(e -> {
                CardLayout cl = (CardLayout) productPanel.getLayout();
                productPanel.remove(Cosmetics);
                Cosmetics = new DisplayProductPanel().getPanel(cart, "4");
                productPanel.add(Cosmetics, "Cosmetics");
                cl.show(productPanel, "Cosmetics");
            });
            HouseholdsBtn.addActionListener(e -> {
                CardLayout cl = (CardLayout) productPanel.getLayout();
                productPanel.remove(Households);
                Households = new DisplayProductPanel().getPanel(cart, "5");
                productPanel.add(Households, "Households");
                cl.show(productPanel, "Households");
            });
            ToolsBtn.addActionListener(e -> {
                CardLayout cl = (CardLayout) productPanel.getLayout();
                productPanel.remove(Tools);
                Tools = new DisplayProductPanel().getPanel(cart, "6");
                productPanel.add(Tools, "Tools");
                cl.show(productPanel, "Tools");
            });
            SportsBtn.addActionListener(e -> {
                CardLayout cl = (CardLayout) productPanel.getLayout();
                productPanel.remove(Sports);
                Sports = new DisplayProductPanel().getPanel(cart, "7");
                productPanel.add(Sports, "Sports");
                cl.show(productPanel, "Sports");
            });
            ToysBtn.addActionListener(e -> {
                CardLayout cl = (CardLayout) productPanel.getLayout();
                productPanel.remove(Toys);
                Toys = new DisplayProductPanel().getPanel(cart, "8");
                productPanel.add(Toys, "Toys");
                cl.show(productPanel, "Toys");
            });

            area.add(button);
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

    public void resetPage() {
        CardLayout pr = (CardLayout) productPanel.getLayout();
        String page = "";
        for (Component comp : productPanel.getComponents()) {
            if (comp.isVisible()) {
                page = comp.getName();
            }
        }
        All = new DisplayProductPanel().getPanel(cart, "all");
        Electronics = new DisplayProductPanel().getPanel(cart, "1");
        Foods = new DisplayProductPanel().getPanel(cart, "2");
        Fashions = new DisplayProductPanel().getPanel(cart, "3");
        Cosmetics = new DisplayProductPanel().getPanel(cart, "4");
        Households = new DisplayProductPanel().getPanel(cart, "5");
        Tools = new DisplayProductPanel().getPanel(cart, "6");
        Sports = new DisplayProductPanel().getPanel(cart, "7");
        Toys = new DisplayProductPanel().getPanel(cart, "8");
        
        productPanel.removeAll();;
        productPanel.add(All, "All");
        productPanel.add(Foods, "Foods");
        productPanel.add(Electronics, "Electronics");
        productPanel.add(Fashions, "Fashions");
        productPanel.add(Cosmetics, "Cosmetics");
        productPanel.add(Households, "Households");
        productPanel.add(Tools, "Tools");
        productPanel.add(Sports, "Sports");
        productPanel.add(Toys, "Toys");
        pr.show(productPanel, page);
    }
    
    private JButton ButtonToHome() {
        JButton Home = new JButton("Home");
        Home.setBackground(null);
        Home.setBorder(null);
        Home.setPreferredSize(new Dimension(120, 40));
        Home.setFont(new SetPreferences().getFont(14));
        Home.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "Body");
            resetPage();
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
            mainPanel.add(new Dashboard(), "Dashboard");
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
