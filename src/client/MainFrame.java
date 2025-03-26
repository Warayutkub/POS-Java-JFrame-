package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import backend.services.LoginServices;
import client.components.Cart;
import client.components.DisplayProductPanel;
import client.components.Login;
import client.components.ManageEmp;
import client.components.POSDateTimeFrame;
import resources.SetPreferences;

public class MainFrame extends JFrame {
    private String[] accountData = new LoginServices().getDataToken();
    private Cart cart = new Cart(this);
    private JPanel topBar = TopBar();
    private JPanel sideBar = SideBar();
    private JPanel outerBody = OuterBody();
    private JPanel dashboard = new Dashboard(this);
    private JPanel manageEmp = new ManageEmp(this);
    private JPanel mainPanel = new JPanel(new CardLayout());
    private JPanel productPanel = new JPanel(new CardLayout());
    private Container container = getContentPane();
    private JButton accountName;
    
    


    // Display Products panel Obj
    private JScrollPane All = new DisplayProductPanel().getPanel(cart, "all");
    private JScrollPane Electronics = new DisplayProductPanel().getPanel(cart, "1");
    private JScrollPane Foods = new DisplayProductPanel().getPanel(cart, "2");
    private JScrollPane Fashions = new DisplayProductPanel().getPanel(cart, "3");
    private JScrollPane Cosmetics = new DisplayProductPanel().getPanel(cart, "4");
    private JScrollPane Households = new DisplayProductPanel().getPanel(cart, "5");
    private JScrollPane Tools = new DisplayProductPanel().getPanel(cart, "6");
    private JScrollPane Sports = new DisplayProductPanel().getPanel(cart, "7");
    private JScrollPane Toys = new DisplayProductPanel().getPanel(cart, "8");
    private JScrollPane Search = new DisplayProductPanel().getSearchPanel(cart, "");

    public MainFrame() {
        CreateGui();
        SetupWindow();

        if(accountData.length==0){
            new Login(this);
        }
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
        mainPanel.add(dashboard, "Dashboard");

        // Type Btn
        All.setName("All");
        Electronics.setName("Electronics");
        Foods.setName("Foods");
        Fashions.setName("Fashions");
        Cosmetics.setName("Cosmetics");
        Households.setName("Households");
        Tools.setName("Tools");
        Sports.setName("Sports");
        Toys.setName("Toys");
        Search.setName("Search");

        outerBody.add(productPanel, BorderLayout.CENTER);
        productPanel.add(All, "All");
        productPanel.add(Electronics, "Electronics");
        productPanel.add(Foods, "Foods");
        productPanel.add(Fashions, "Fashions");
        productPanel.add(Cosmetics, "Cosmetics");
        productPanel.add(Households, "Households");
        productPanel.add(Tools, "Tools");
        productPanel.add(Sports, "Sports");
        productPanel.add(Toys, "Toys");
        productPanel.add(Search, "Search");

        container.add(mainPanel, BorderLayout.CENTER);
        container.add(cart, BorderLayout.EAST);
    }

    private JPanel TopBar() {
        JPopupMenu popupProfile = new JPopupMenu();
        JMenuItem signOutItem = new JMenuItem("SignOut");
        JTextField searchArea = new JTextField();
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> {
            String searchData = searchArea.getText();
            if (!searchData.isEmpty()) {
                productPanel.remove(Search);
                this.Search = new DisplayProductPanel().getSearchPanel(cart, searchData);
                this.Search.setName("Search");
                productPanel.add(Search, "Search");
                CardLayout pr = (CardLayout) productPanel.getLayout();
                productPanel.revalidate();
                productPanel.repaint();
                pr.show(productPanel, "Search");
                searchArea.setText("");
            }
        });

        searchArea.addActionListener(e -> {
            String searchData = searchArea.getText();
            if (!searchData.isEmpty()) {
                productPanel.remove(Search);
                this.Search = new DisplayProductPanel().getSearchPanel(cart, searchData);
                this.Search.setName("Search");
                productPanel.add(Search, "Search");
                CardLayout pr = (CardLayout) productPanel.getLayout();
                productPanel.revalidate();
                productPanel.repaint();
                pr.show(productPanel, "Search");
                searchArea.setText("");
            }
        });

        searchBtn.setBackground(Color.white);
        searchBtn.setPreferredSize(new Dimension(80, 25));
        searchArea.setBorder(null);

        JPanel area = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel areaInner1 = new JPanel(new GridLayout(1, 1));
        JPanel areaInner2 = new JPanel(new GridLayout(1, 2));
        JPanel areaInner3 = new JPanel(new GridLayout(1, 1));
        JPanel areaAccount = new JPanel(new GridLayout(1, 1));
        JPanel areaAroundSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        if (accountData.length != 0) {
            accountName = new JButton(accountData[1]);
        } else {
            accountName = new JButton("No account");
        }
        JLabel ApplicationName = new JLabel("  Point of sale  ");
        JLabel time = new POSDateTimeFrame();

        popupProfile.add(signOutItem);
        signOutItem.setPreferredSize(new Dimension(150, 40));
        signOutItem.setBackground(null);
        accountName.setFont(new SetPreferences().getFont(24));
        accountName.setBackground(null);
        accountName.setBorder(null);
        accountName.setForeground(Color.white);
        accountName.addActionListener(e -> {
            if (accountData.length!=0){
                popupProfile.show(accountName, 0, accountName.getHeight());
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

        searchArea.setPreferredSize(new Dimension(200, 30));
        areaAroundSearch.add(searchArea);
        areaAroundSearch.add(searchBtn);
        areaInner2.add(areaAroundSearch);
        areaAroundSearch.setBackground(null);
        areaInner2.setPreferredSize(new Dimension(850, 45));
        areaInner2.setBackground(null);

        areaInner3.add(time);
        areaInner3.setBackground(null);
        areaInner3.setPreferredSize(new Dimension(200, 40));

        areaAccount.add(accountName);
        areaAccount.setBackground(null);
        areaAccount.setPreferredSize(new Dimension(150, 40));

        signOutItem.addActionListener(e -> {
            new LoginServices().signOut(this);
            accountName = null;
            accountName = new JButton("No account");
            areaAccount.removeAll();
            areaAccount.add(accountName);
            accountName.addActionListener(event -> {
                new Login(this).setVisible(true);
            });
            areaAccount.revalidate();
            areaAccount.repaint();
            resetSideBar();
        });

        area.add(areaInner1);
        area.add(areaInner2);
        area.add(areaInner3);
        area.add(areaAccount);
        area.setPreferredSize(new Dimension(1000, 50));
        float[] hsbValues = Color.RGBtoHSB(3, 153, 254, null);
        area.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
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
        area.add(ButtonToManageEmp());
        area.add(ButtonToDashBoard());
        area.add(ButtonToLogin());
        return area;
    }

    private void resetSideBar() {
        int width = 150;
        int height = 500;
        this.accountData = new LoginServices().getDataToken();
        sideBar.removeAll();
        sideBar.add(LogoArea(width, height));
        sideBar.add(ButtonToHome());
        sideBar.add(ButtonToManageProduct());
        sideBar.add(ButtonToManageUser());
        sideBar.add(ButtonToDashBoard());
        sideBar.add(ButtonToLogin());
        sideBar.revalidate();
        sideBar.repaint();
    }

    public void resetDashboard() {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        mainPanel.remove(dashboard);
        dashboard = new Dashboard(this);
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
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        area.setPreferredSize(new Dimension(1100, 150));
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
                productPanel.revalidate();
                productPanel.repaint();
                cl.show(productPanel, "Foods");
            });
            ElectronicsBtn.addActionListener(e -> {
                CardLayout cl = (CardLayout) productPanel.getLayout();
                productPanel.revalidate();
                productPanel.repaint();
                cl.show(productPanel, "Electronics");
            });
            FashionsBtn.addActionListener(e -> {
                CardLayout cl = (CardLayout) productPanel.getLayout();
                productPanel.revalidate();
                productPanel.repaint();
                cl.show(productPanel, "Fashions");
            });
            CosmeticsBtn.addActionListener(e -> {
                CardLayout cl = (CardLayout) productPanel.getLayout();
                productPanel.revalidate();
                productPanel.repaint();
                cl.show(productPanel, "Cosmetics");
            });
            HouseholdsBtn.addActionListener(e -> {
                CardLayout cl = (CardLayout) productPanel.getLayout();
                productPanel.revalidate();
                productPanel.repaint();
                cl.show(productPanel, "Households");
            });
            ToolsBtn.addActionListener(e -> {
                CardLayout cl = (CardLayout) productPanel.getLayout();
                productPanel.revalidate();
                productPanel.repaint();
                cl.show(productPanel, "Tools");
            });
            SportsBtn.addActionListener(e -> {
                CardLayout cl = (CardLayout) productPanel.getLayout();
                productPanel.revalidate();
                productPanel.repaint();
                cl.show(productPanel, "Sports");
            });
            ToysBtn.addActionListener(e -> {
                CardLayout cl = (CardLayout) productPanel.getLayout();
                productPanel.revalidate();
                productPanel.repaint();
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
            if (comp.isVisible() && comp.getName() != null) {
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

        All.setName("All");
        Electronics.setName("Electronics");
        Foods.setName("Foods");
        Fashions.setName("Fashions");
        Cosmetics.setName("Cosmetics");
        Households.setName("Households");
        Tools.setName("Tools");
        Sports.setName("Sports");
        Toys.setName("Toys");

        productPanel.removeAll();

        productPanel.add(All, "All");
        productPanel.add(Electronics, "Electronics");
        productPanel.add(Foods, "Foods");
        productPanel.add(Fashions, "Fashions");
        productPanel.add(Cosmetics, "Cosmetics");
        productPanel.add(Households, "Households");
        productPanel.add(Tools, "Tools");
        productPanel.add(Sports, "Sports");
        productPanel.add(Toys, "Toys");

        productPanel.revalidate();
        productPanel.repaint();
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
            CardLayout pr = (CardLayout) productPanel.getLayout();
            cl.show(mainPanel, "Body");
            pr.show(productPanel, "All");
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

        if (accountData.length!=0) {
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
        if (accountData.length!=0) {
            if (!accountData[5].equals("Manager")) {
                Btn.setVisible(false);
            }
        } else {
            Btn.setVisible(false);
        }
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
        if (accountData.length!=0) {
            if (!accountData[5].equals("Manager")) {
                Btn.setVisible(false);
            }
        } else {
            Btn.setVisible(false);
        }
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
            mainPanel.remove(dashboard);
            mainPanel.add(new ManageEmp(this), "ManageEmp");
            cl.show(mainPanel, "ManageEmp");
            cart.setVisible(true);
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
        if (accountData.length!=0) {
            if (!accountData[5].equals("Manager")) {
                Btn.setVisible(false);
            }
        } else {
            Btn.setVisible(false);
        }
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
            mainPanel.add(new Dashboard(this), "Dashboard");
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
        if (accountData.length!=0) {
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
            new Login(this).setVisible(true);;
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
        if (accountData.length==0) {
            Btn.setVisible(true);
        }else{
            Btn.setVisible(false);
        }
        return Btn;
    }

}
