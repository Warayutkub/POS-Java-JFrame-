package client.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import backend.services.AuthService;
import backend.services.DashboardService;
import backend.services.InventoryService;
import resources.SetPreferences;

public class FindBill extends JFrame {
    private Container container = getContentPane();
    private int width = 850;
    private int height = 600;
    // private String

    public FindBill() {
        super("Find Bill");
        CreateGui();
        setupWindow();
    }

    private void setupWindow() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/backend/data/images/logo.png"));
        setIconImage(icon.getImage());
        setSize(width, height);
        setLocationRelativeTo(null);
    }

    private void CreateGui() {
        container.setLayout(new BorderLayout());
        container.add(search(), BorderLayout.NORTH);
        container.add(display(), BorderLayout.CENTER);
    }

    private JPanel search() {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField searchBox = new JTextField();
        JButton searchBtn = new JButton("Search");
        searchBtn.setBackground(Color.WHITE);
        searchBox.setBorder(null);
        searchBtn.addActionListener(e -> {
            String searchKey = searchBox.getText();
            container.removeAll();
            if (!searchKey.isEmpty()) {
                container.add(search(), BorderLayout.NORTH);
                container.add((displaySearch(searchKey)), 1);

            } else {
                container.add(search(), BorderLayout.NORTH);
                container.add(display(), 1);
            }
            container.revalidate();
            container.repaint();
        });

        searchBox.addActionListener(e -> {
            String searchKey = searchBox.getText();
            container.removeAll();
            if (!searchKey.isEmpty()) {
                container.add(search(), BorderLayout.NORTH);
                container.add((displaySearch(searchKey)), 1);
            } else {
                container.add(search(), BorderLayout.NORTH);
                container.add(display(), 1);
            }
            container.revalidate();
            container.repaint();
        });

        searchBox.setPreferredSize(new Dimension(350, 25));

        area.add(searchBox);
        area.add(searchBtn);
        float[] hsbValues = Color.RGBtoHSB(3, 153, 254, null);
        area.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
        return area;
    }

    private JScrollPane display() {
        String[][] billEmp = pullStoreBillEmp();

        JPanel area = new JPanel();
        area.setLayout(new BoxLayout(area, BoxLayout.Y_AXIS));

        for (int c = 0; c < billEmp.length; c++) {
            String BillId = billEmp[c][0];
            String Cname = getCname(billEmp[c][0]);
            String Ename = new AuthService().getDataEmp(billEmp[c][1])[1];
            area.add(rowCard(BillId, Cname, Ename));
        }

        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(width, height));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        area.revalidate();
        area.repaint();
        scrollPane.revalidate();
        scrollPane.repaint();

        return scrollPane;
    }

    private JScrollPane displaySearch(String searchKey) {
        String[][] billEmp = pullStoreBillEmp();
        JPanel area = new JPanel();

        area.setLayout(new BoxLayout(area, BoxLayout.Y_AXIS));
        for (int c = 0; c < billEmp.length; c++) {
            if (billEmp[c][0].toLowerCase().equals(searchKey.toLowerCase())
                    || new AuthService().getDataEmp(billEmp[c][1])[1].toLowerCase().contains(searchKey.toLowerCase())
                    || (getCname(billEmp[c][0])).toLowerCase().contains(searchKey.toLowerCase())) {
                String BillId = billEmp[c][0];
                String Cname = getCname(billEmp[c][0]);
                String Ename = new AuthService().getDataEmp(billEmp[c][1])[1];
                area.add(rowCard(BillId, Cname, Ename));
            }

        }
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(width, height));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        area.revalidate();
        area.repaint();
        scrollPane.revalidate();
        scrollPane.repaint();
        return scrollPane;
    }

    private JPanel rowCard(String BillId, String Cuname, String Eaname) {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel IdLabel = new JLabel("QD : " + BillId);
        JLabel Cname = new JLabel("CN : " + Cuname);
        JLabel Ename = new JLabel("EN : " + Eaname);
        JButton moreBtn = new JButton("See more");

        IdLabel.setPreferredSize(new Dimension(120, 30));
        Cname.setPreferredSize(new Dimension(120, 30));
        Ename.setPreferredSize(new Dimension(120, 30));

        IdLabel.setHorizontalAlignment(JLabel.CENTER);
        Cname.setHorizontalAlignment(JLabel.CENTER);
        Ename.setHorizontalAlignment(JLabel.CENTER);

        float[] hsbValues = Color.RGBtoHSB(3, 153, 254, null);
        moreBtn.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
        moreBtn.setForeground(Color.white);
        moreBtn.addActionListener(e -> {

            if (container.getComponentCount() > 2) {
                container.remove(2);
                container.add(moreInformation(BillId, Eaname), BorderLayout.EAST);
                container.revalidate();
                container.repaint();
            } else {
                container.add(moreInformation(BillId, Eaname), BorderLayout.EAST);
                container.revalidate();
                container.repaint();

            }
        });

        area.add(IdLabel);
        area.add(Cname);
        area.add(Ename);
        area.add(moreBtn);
        area.setPreferredSize(new Dimension(150, 60));
        area.setBackground(Color.WHITE);
        return area;
    }

    private JPanel moreInformation(String BillID, String Eaname) {
        DecimalFormat format = new DecimalFormat("#,###.##");
        JButton refundBtn = new JButton("Refund");
        String[][] products = getProductThisBill(BillID);
        String[] dataHistory = new String[0];
        for (String[] recode : new InventoryService().getAllSalesHistory()) {
            if (recode[0].equals(BillID)) {
                dataHistory = recode;
                break;
            }
        }

        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        JPanel informationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel top = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel idLabel = new JLabel("Bill ID : ");
        JLabel idArea = new JLabel(BillID);
        JLabel dateLabel = new JLabel("Date : ");
        JLabel dateArea = new JLabel(dataHistory[2]);
        JLabel timeLabel = new JLabel("Time : ");
        JLabel timeArea = new JLabel(dataHistory[3]);
        JLabel CustomerLabel = new JLabel("Customer : ");
        JLabel CustomerArea = new JLabel(dataHistory[4]);
        JLabel EmpNameLabel = new JLabel("Employee : ");
        JLabel EmpNameArea = new JLabel(Eaname);
        JLabel TPriceLabel = new JLabel("Total price : ");
        JLabel TPriceArea = new JLabel(format.format(Double.parseDouble(dataHistory[7])));
        JLabel productLabel = new JLabel("Product : ");
        JTextArea productFiled = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(productFiled);
        JButton close = new JButton("X");

        String texts = "";
        for (String[] product : products) {
            texts += product[0] + " : " + product[1] + ", ";
        }

        productFiled.setText(texts);
        productFiled.setEditable(false);
        productFiled.setBackground(Color.WHITE);
        productFiled.setBorder(null);
        productFiled.setWrapStyleWord(true);
        productFiled.setLineWrap(true);
        productFiled.setFont(new SetPreferences().getFont(10));

        close.addActionListener(e -> {
            deleteInformation();
        });

        top.setPreferredSize(new Dimension(300, 40));
        top.setBackground(null);
        top.add(close);

        idLabel.setPreferredSize(new Dimension(140, 20));
        idLabel.setHorizontalAlignment(JLabel.RIGHT);
        idArea.setPreferredSize(new Dimension(100, 20));
        idArea.setHorizontalAlignment(JLabel.LEFT);
        dateLabel.setPreferredSize(new Dimension(75, 20));
        dateArea.setPreferredSize(new Dimension(75, 20));
        dateArea.setHorizontalAlignment(JLabel.LEFT);
        timeLabel.setPreferredSize(new Dimension(50, 20));
        timeArea.setPreferredSize(new Dimension(50, 20));
        timeArea.setHorizontalAlignment(JLabel.LEFT);
        CustomerLabel.setPreferredSize(new Dimension(75, 20));
        CustomerArea.setPreferredSize(new Dimension(185, 20));
        CustomerArea.setHorizontalAlignment(JLabel.LEFT);
        EmpNameLabel.setPreferredSize(new Dimension(75, 20));
        EmpNameArea.setPreferredSize(new Dimension(185, 20));
        EmpNameArea.setHorizontalAlignment(JLabel.LEFT);
        TPriceLabel.setPreferredSize(new Dimension(75, 20));
        TPriceArea.setPreferredSize(new Dimension(185, 20));
        TPriceArea.setHorizontalAlignment(JLabel.LEFT);
        productLabel.setPreferredSize(new Dimension(265, 20));
        informationPanel.setPreferredSize(new Dimension(270, 165));
        informationPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        informationPanel.setBackground(Color.WHITE);
        informationPanel.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(270, 200));
        scrollPane.setBorder(null);
        refundBtn.setBackground(null);
        area.setPreferredSize(new Dimension(300, this.height));
        area.setBackground(Color.white);

        informationPanel.add(idLabel);
        informationPanel.add(idArea);
        informationPanel.add(dateLabel);
        informationPanel.add(dateArea);
        informationPanel.add(timeLabel);
        informationPanel.add(timeArea);
        informationPanel.add(CustomerLabel);
        informationPanel.add(CustomerArea);
        informationPanel.add(EmpNameLabel);
        informationPanel.add(EmpNameArea);
        informationPanel.add(TPriceLabel);
        informationPanel.add(TPriceArea);
        informationPanel.add(productLabel);
        area.add(top);
        area.add(informationPanel);
        area.add(scrollPane);
        area.add(refundBtn);

        refundBtn.addActionListener(e -> {
            new DashboardService().refundBill(BillID);
            container.removeAll();
            container.add(search(), BorderLayout.NORTH);
            container.add(display(), 1);
            revalidate();
            repaint();
        });
        return area;
    }

    private void deleteInformation() {
        container.remove(2);
        container.revalidate();
        container.repaint();
    }

    private String getCname(String billId) {
        String[][] history = new InventoryService().getAllSalesHistory();
        String name = "";

        for (int c = 0; c < history.length; c++) {
            if (history[c][0].equals(billId)) {
                name = history[c][4];
                break;
            }
        }

        return name;
    }

    // File puller
    public String[][] pullStoreBillEmp() {
        String[][] data = new String[0][0];
        try (BufferedReader reader = new BufferedReader(new FileReader("./src/backend/data/StoreBillEmp.txt"))) {
            data = new String[(int) reader.lines().count()][1];
            int i = 0;
            BufferedReader reader2 = new BufferedReader(new FileReader("./src/backend/data/StoreBillEmp.txt"));
            String line = "";
            while ((line = reader2.readLine()) != null) {
                data[i] = line.split(",");
                i++;
            }
            reader2.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Can't open file StoreBill");
        }
        return data;
    }

    // simple service
    private String[][] getProductThisBill(String BillID) {
        String[][] history = new InventoryService().getAllSalesHistory();
        String[][] products = new String[0][0];
        // Find range
        int count = 0;
        for (String[] recode : history) {
            if (recode[0].equals(BillID)) {
                count++;
            }
        }
        products = new String[count][2];
        System.out.println(count);
        count = 0;
        for (int c = 0; c < history.length; c++) {
            if (history[c][0].equals(BillID)) {
                products[count][0] = history[c][5];
                products[count++][1] = history[c][6];
            }
        }

        return products;
    }
}
