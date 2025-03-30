package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import backend.services.InventoryService;
import backend.services.LoginServices;
import client.components.Cart;
import client.components.Dashboard;
import client.components.DisplayProductPanel;
import client.components.InformationAccount;
import client.components.Login;
import client.components.ManageEmp;
import client.components.ManageProduct;
import client.components.POSDateTimeFrame;
import resources.SetPreferences;

public class MainFrame extends JFrame {
    private String[] accountData = new LoginServices().getDataToken();
    private Cart cart = new Cart(this);
    private JPanel topBar = TopBar();
    private JPanel sideBar = SideBar();
    private JPanel outerBody = OuterBody();
    private JPanel dashboard = new Dashboard(this, 0);
    private JPanel manageEmp = new ManageEmp(this, 0);
    private JPanel manageUser = new ManageUser(this, 0);
    private JPanel manageProduct = new ManageProduct(this, 0);
    private JPanel mainPanel = new JPanel(new CardLayout());
    private Container container = getContentPane();
    private JButton accountName;
    private JScrollPane allProductPanel;
    private JScrollPane productFoodPanel;
    private JScrollPane productElectronicsPanel;
    private JScrollPane productFashionPanel;
    private JScrollPane productCosmeticsPanel;
    private JScrollPane productHouseholdPanel;
    private JScrollPane productToolsPanel;
    private JScrollPane productSportPanel;
    private JScrollPane productToyPanel;
    private int amountProductFirst = new InventoryService().getProductDataNow().length;

    public MainFrame() {
        CreateGui();
        SetupWindow();

        if (accountData.length == 0) {
            new Login(this);

        }
    }

    public void setAccountData(String[] acc) {
        this.accountData = acc;
    }

    private void SetupWindow() {
        setTitle("POS");
        ImageIcon icon = new ImageIcon(getClass().getResource("/backend/data/images/logo.png"));
        setIconImage(icon.getImage());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(1600, 900);
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
        mainPanel.add(manageEmp, "ManageEmp");
        mainPanel.add(manageUser, "ManageUser");
        mainPanel.add(manageProduct, "ManageProduct");
        mainPanel.add(dashboard, "Dashboard");
        container.add(mainPanel, BorderLayout.CENTER);

        DisplayProductPanel displayPanel = new DisplayProductPanel();
        int panelWidth = this.getWidth() - sideBar.getWidth() - cart.getWidth();

        allProductPanel = displayPanel.getPanel(cart, "all", panelWidth);
        productFoodPanel = displayPanel.getPanel(cart, "2", panelWidth);
        productElectronicsPanel = displayPanel.getPanel(cart, "1", panelWidth);
        productFashionPanel = displayPanel.getPanel(cart, "3", panelWidth);
        productCosmeticsPanel = displayPanel.getPanel(cart, "4", panelWidth);
        productHouseholdPanel = displayPanel.getPanel(cart, "5", panelWidth);
        productToolsPanel = displayPanel.getPanel(cart, "6", panelWidth);
        productSportPanel = displayPanel.getPanel(cart, "7", panelWidth);
        productToyPanel = displayPanel.getPanel(cart, "8", panelWidth);

        outerBody.add(allProductPanel, BorderLayout.CENTER);

        container.add(cart, BorderLayout.EAST);
    }

    private JPanel TopBar() {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel areaInner1 = new JPanel(new GridLayout(1, 1));
        JPanel areaInner3 = new JPanel(new GridLayout(1, 1));
        JPanel areaInner4 = new JPanel(new GridLayout(1, 1));
        JPanel areaAccount = new JPanel(new GridLayout(1, 1));

        if (accountData.length != 0) {
            accountName = new JButton(accountData[1]);
        } else {
            accountName = new JButton("No account");
        }
        JLabel ApplicationName = new JLabel("  Point of sale  ");
        JLabel time = new POSDateTimeFrame();

        accountName.setFont(new SetPreferences().getFont(24));
        accountName.setBackground(null);
        accountName.setBorder(null);
        accountName.setForeground(Color.white);
        accountName.addActionListener(e -> {
            if (accountData.length != 0) {
                new InformationAccount(this, this.accountData);
            }
        });

        ApplicationName.setFont(new SetPreferences().getFont(36));
        ApplicationName.setForeground(Color.white);
        ApplicationName.setPreferredSize(new Dimension(300, 50));

        time.setForeground(Color.white);
        time.setFont(new SetPreferences().getFont(16));

        areaInner1.add(ApplicationName);
        areaInner1.setBackground(null);
        areaInner1.setPreferredSize(new Dimension(300, 45));

        areaInner3.add(time);
        areaInner3.setBackground(null);
        areaInner3.setPreferredSize(new Dimension(200, 40));

        areaAccount.add(accountName);
        areaAccount.setBackground(null);
        areaAccount.setPreferredSize(new Dimension(150, 40));

        areaInner4.setPreferredSize(new Dimension(850, 35));
        areaInner4.setBackground(null);
        area.add(areaInner1);
        area.add(areaInner4);
        area.add(areaInner3);
        area.add(areaAccount);
        area.setPreferredSize(new Dimension(1000, 50));
        float[] hsbValues = Color.RGBtoHSB(3, 153, 254, null);
        area.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
        return area;
    }

    public void resetTopBar() {
        if (this.accountData.length != 0) {
            this.accountName.setText(accountData[1]);
        } else {
            this.accountName.setText("No account");
        }
        container.removeAll();
        container.add(cart, BorderLayout.EAST);
        container.add(sideBar, BorderLayout.WEST);
        container.add(mainPanel, BorderLayout.CENTER);
        this.topBar = TopBar();
        container.add(topBar, BorderLayout.NORTH);
        topBar.revalidate();
        topBar.repaint();
        container.revalidate();
        container.repaint();
    }

    public void resetAccCart(String[] acc) {
        this.cart.resetAccInner(acc);
    }

    private JPanel SideBar() {
        int width = 150;
        int height = 500;
        JPanel area = new JPanel(new FlowLayout());
        area.setPreferredSize(new Dimension(width, height));
        area.setBackground(Color.WHITE);

        area.add(LogoArea(width, height));
        area.add(ButtonToHome());
        if (accountData.length != 0 && accountData[5].equals("Manager")) {
            area.add(ButtonToManageProduct());
            area.add(ButtonToManageUser());
            area.add(ButtonToManageEmp());
        }
        area.add(ButtonToDashBoard());
        area.add(ButtonToLogin());
        return area;
    }

    public void resetSideBar() {
        int width = 150;
        int height = 500;
        this.accountData = new LoginServices().getDataToken();

        JButton manageEmpReset = ButtonToManageEmp();
        JButton manageUserReset = ButtonToManageUser();
        JButton manageProductReset = ButtonToManageProduct();
        manageEmpReset.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            mainPanel.remove(manageEmp);
            mainPanel.add(new ManageEmp(this, this.getWidth() - cart.getWidth() - sideBar.getWidth()), "ManageEmp");
            cl.show(mainPanel, "ManageEmp");
            cart.setVisible(false);
        });

        manageUserReset.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            mainPanel.remove(manageUser);
            mainPanel.add(new ManageUser(this, this.getWidth() - cart.getWidth() - sideBar.getWidth()), "ManageUser");
            cl.show(mainPanel, "ManageUser");
            cart.setVisible(false);
        });

        manageProductReset.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            mainPanel.remove(manageProduct);
            mainPanel.add(new ManageProduct(this, this.getWidth() - cart.getWidth() - sideBar.getWidth()),
                    "ManageProduct");
            cl.show(mainPanel, "ManageProduct");
            cart.setVisible(false);
        });

        sideBar.removeAll();
        sideBar.add(LogoArea(width, height));
        sideBar.add(ButtonToHome());
        if (accountData.length != 0) {
            if (accountData[5].equals("Manager")) {
                sideBar.add(manageProductReset);
                sideBar.add(manageUserReset);
                sideBar.add(manageEmpReset);
            }
        }
        sideBar.add(ButtonToDashBoard());
        sideBar.add(ButtonToLogin());
        sideBar.revalidate();
        sideBar.repaint();
    }

    public void resetDashboard() {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        mainPanel.remove(dashboard);
        dashboard = new Dashboard(this, this.getWidth() - cart.getWidth() - sideBar.getWidth() - 50);
        dashboard.setName("Dashboard");
        mainPanel.add(dashboard, "Dashboard");
        cl.show(mainPanel, "Dashboard");
    }

    private JPanel OuterBody() {
        JPanel area = new JPanel(new BorderLayout());
        area.add(typeProductBtn(), BorderLayout.NORTH);
        return area;
    }

    private JPanel typeProductBtn() {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        JPanel areaBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        area.setPreferredSize(new Dimension(1100, 150));
        areaBtn.setPreferredSize(new Dimension(1100, 100));

        // Search Area
        JTextField searchArea = new JTextField();
        JButton searchBtn = new JButton("Search");

        searchBtn.addActionListener(e -> {
            String searchData = searchArea.getText();
            if (!searchData.isEmpty()) {
                outerBody.remove(1);
                outerBody.add(new DisplayProductPanel().getSearchPanel(cart, searchData,
                        (this.getWidth() - sideBar.getWidth() - cart.getWidth())), BorderLayout.CENTER);
                outerBody.revalidate();
                searchArea.setText("");
            }
        });

        searchArea.addActionListener(e -> {
            String searchData = searchArea.getText();
            if (!searchData.isEmpty()) {
                outerBody.remove(1);
                outerBody.add(new DisplayProductPanel().getSearchPanel(cart, searchData,
                        (this.getWidth() - sideBar.getWidth() - cart.getWidth())), BorderLayout.CENTER);
                outerBody.revalidate();
                searchArea.setText("");
            }
        });

        searchBtn.setBackground(Color.white);
        searchBtn.setPreferredSize(new Dimension(80, 25));
        searchArea.setPreferredSize(new Dimension(200, 30));
        searchArea.setBorder(new LineBorder(Color.BLACK));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        float[] hsbValues = Color.RGBtoHSB(3, 153, 254, null);
        searchPanel.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
        searchPanel.setPreferredSize(new Dimension(1100, 35));
        searchPanel.add(searchArea);
        searchPanel.add(searchBtn);

        area.add(searchPanel);

        JButton ElectronicsBtn = new JButton("Electronics");
        JButton FoodsBtn = new JButton("Food");
        JButton FashionsBtn = new JButton("Fashion");
        JButton CosmeticsBtn = new JButton("Cosmetics");
        JButton HouseholdsBtn = new JButton("Household");
        JButton ToolsBtn = new JButton("Tools");
        JButton SportsBtn = new JButton("Sport");
        JButton ToysBtn = new JButton("Toy");

        JButton[] buttons = { ElectronicsBtn, FoodsBtn, FashionsBtn, CosmeticsBtn, HouseholdsBtn, ToolsBtn, SportsBtn,
                ToysBtn };
        for (JButton button : buttons) {
            button.setBackground(Color.WHITE);
            button.setPreferredSize(new Dimension(200, 35));
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

            // Add action listeners for each button
            FoodsBtn.addActionListener(e -> {
                outerBody.remove(1);
                outerBody.add(productFoodPanel, BorderLayout.CENTER);
                outerBody.revalidate();
                outerBody.repaint();
            });
            ElectronicsBtn.addActionListener(e -> {
                outerBody.remove(1);
                outerBody.add(productElectronicsPanel, BorderLayout.CENTER);
                outerBody.revalidate();
                outerBody.repaint();
            });
            FashionsBtn.addActionListener(e -> {
                outerBody.remove(1);
                outerBody.add(productFashionPanel, BorderLayout.CENTER);
                outerBody.revalidate();
                outerBody.repaint();
            });
            CosmeticsBtn.addActionListener(e -> {
                outerBody.remove(1);
                outerBody.add(productCosmeticsPanel, BorderLayout.CENTER);
                outerBody.revalidate();
                outerBody.repaint();
            });
            HouseholdsBtn.addActionListener(e -> {
                outerBody.remove(1);
                outerBody.add(productHouseholdPanel, BorderLayout.CENTER);
                outerBody.revalidate();
                outerBody.repaint();
            });
            ToolsBtn.addActionListener(e -> {
                outerBody.remove(1);
                outerBody.add(productToolsPanel, BorderLayout.CENTER);
                outerBody.revalidate();
                outerBody.repaint();
            });
            SportsBtn.addActionListener(e -> {
                outerBody.remove(1);
                outerBody.add(productSportPanel, BorderLayout.CENTER);
                outerBody.revalidate();
                outerBody.repaint();
            });
            ToysBtn.addActionListener(e -> {
                outerBody.remove(1);
                outerBody.add(productToyPanel, BorderLayout.CENTER);
                outerBody.revalidate();
                outerBody.repaint();
            });

            areaBtn.add(button);
        }
        area.add(areaBtn);

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
        int currentProductCount = new InventoryService().getProductDataNow().length;
        if (amountProductFirst != currentProductCount || allProductPanel == null) {
            DisplayProductPanel displayPanel = new DisplayProductPanel();
            int panelWidth = this.getWidth() - sideBar.getWidth() - cart.getWidth();

            allProductPanel = displayPanel.getPanel(cart, "all", panelWidth);
            productFoodPanel = displayPanel.getPanel(cart, "2", panelWidth);
            productElectronicsPanel = displayPanel.getPanel(cart, "1", panelWidth);
            productFashionPanel = displayPanel.getPanel(cart, "3", panelWidth);
            productCosmeticsPanel = displayPanel.getPanel(cart, "4", panelWidth);
            productHouseholdPanel = displayPanel.getPanel(cart, "5", panelWidth);
            productToolsPanel = displayPanel.getPanel(cart, "6", panelWidth);
            productSportPanel = displayPanel.getPanel(cart, "7", panelWidth);
            productToyPanel = displayPanel.getPanel(cart, "8", panelWidth);

            amountProductFirst = currentProductCount;
        }
        outerBody.remove(1);
        outerBody.add(allProductPanel, BorderLayout.CENTER);
        outerBody.revalidate();
        outerBody.repaint();
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void reValuePage() {
        DisplayProductPanel displayPanel = new DisplayProductPanel();
        int panelWidth = this.getWidth() - sideBar.getWidth() - cart.getWidth();

        allProductPanel = displayPanel.getPanel(cart, "all", panelWidth);
        productFoodPanel = displayPanel.getPanel(cart, "2", panelWidth);
        productElectronicsPanel = displayPanel.getPanel(cart, "1", panelWidth);
        productFashionPanel = displayPanel.getPanel(cart, "3", panelWidth);
        productCosmeticsPanel = displayPanel.getPanel(cart, "4", panelWidth);
        productHouseholdPanel = displayPanel.getPanel(cart, "5", panelWidth);
        productToolsPanel = displayPanel.getPanel(cart, "6", panelWidth);
        productSportPanel = displayPanel.getPanel(cart, "7", panelWidth);
        productToyPanel = displayPanel.getPanel(cart, "8", panelWidth);
    }

    private JButton ButtonToHome() {
        JButton Home = new JButton("Home");
        Home.setBackground(null);
        Home.setBorder(null);
        Home.setPreferredSize(new Dimension(120, 40));
        Home.setFont(new SetPreferences().getFont(14));
        Home.addActionListener(e -> {
            outerBody.remove(1);
            outerBody.add(allProductPanel, BorderLayout.CENTER);
            outerBody.revalidate();
            outerBody.repaint();
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "Body");
            cart.setVisible(true);
        });

        Home.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Home.setText("<html><u>Home</u></html>");
                Home.setFont(new SetPreferences().getFont(14));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Home.setText("Home");
                Home.setFont(new SetPreferences().getFont(14));
            }
        });

        if (accountData.length != 0) {
            if (!accountData[5].equals("Manager") && !accountData[5].equals("Employee")) {
                Home.setVisible(false);
            }
        } else {
            Home.setVisible(false);
        }
        return Home;
    }

    private JButton ButtonToManageProduct() {
        JButton Btn = new JButton("Manage Product");
        Btn.setPreferredSize(new Dimension(120, 30));
        Btn.setFont(new SetPreferences().getFont(14));
        Btn.setBackground(null);
        Btn.setBorder(null);
        Btn.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            mainPanel.remove(manageProduct);
            mainPanel.add(new ManageProduct(this, this.getWidth() - cart.getWidth() - sideBar.getWidth()),
                    "ManageProduct");
            cl.show(mainPanel, "ManageProduct");
            cart.setVisible(false);
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
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            mainPanel.remove(manageUser);
            mainPanel.add(new ManageUser(this, this.getWidth() - cart.getWidth() - sideBar.getWidth()), "ManageUser");
            cl.show(mainPanel, "ManageUser");
            cart.setVisible(false);
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

    private JButton ButtonToManageEmp() {
        JButton Btn = new JButton("Manage Emp");
        Btn.setPreferredSize(new Dimension(120, 30));
        Btn.setFont(new SetPreferences().getFont(14));
        Btn.setBackground(null);
        Btn.setBorder(null);

        Btn.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            mainPanel.remove(manageEmp);
            mainPanel.add(new ManageEmp(this, this.getWidth() - sideBar.getWidth()), "ManageEmp");
            cl.show(mainPanel, "ManageEmp");
            cart.setVisible(false);
        });

        Btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Btn.setText("<html><u>Manage Emp</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Btn.setText("Manage Emp");
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
            mainPanel.add(new Dashboard(this, this.getWidth() - cart.getWidth() - sideBar.getWidth() - 50),
                    "Dashboard");
            cl.show(mainPanel, "Dashboard");
            this.cart.setVisible(false);
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
        if (accountData.length != 0) {
            if (!accountData[5].equals("Manager") && !accountData[5].equals("Employee")) {
                Btn.setVisible(false);
            }
        } else {
            Btn.setVisible(false);
        }
        return Btn;
    }

    private JButton ButtonToLogin() {
        JButton Btn = new JButton("Login");
        Btn.setPreferredSize(new Dimension(120, 30));
        Btn.setFont(new SetPreferences().getFont(14));
        Btn.setBackground(null);
        Btn.setBorder(null);

        Btn.addActionListener(e -> {
            new Login(this);
        });

        Btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Btn.setText("<html><u>Login?</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Btn.setText("Login");
            }
        });
        if (accountData.length == 0) {
            Btn.setVisible(true);
        } else {
            Btn.setVisible(false);
        }
        return Btn;
    }

}
