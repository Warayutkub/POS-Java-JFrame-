package client.components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import backend.services.InventoryService;
import resources.SetPreferences;

import java.awt.*;
import java.text.DecimalFormat;

public class Receipt extends JFrame {
    private DecimalFormat format = new DecimalFormat("#,###.##");
    private Container c;
    private String[][] dataInCart;

    public Receipt(String[][] dataInCart) {
        super("Receipt");
        this.dataInCart = dataInCart;
        receiptGUI();
        ImageIcon icon = new ImageIcon(getClass().getResource("/backend/data/images/logo.png"));
        setIconImage(icon.getImage());
        setSize(450, 500);
        setLocationRelativeTo(null);
    }
    
    public void receiptGUI() {
        c = getContentPane();
        c.setLayout(new FlowLayout());
        c.setBackground(Color.WHITE);

        String[][] dataUse = getData();
        String[][] data = getDataTable();
        String[] columns = {"Qty", "Product", "Unit Price", "Total Price"};

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        table.setEnabled(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setBackground(Color.WHITE);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);  // Qty
        table.getColumnModel().getColumn(1).setPreferredWidth(167); // Product
        table.getColumnModel().getColumn(2).setPreferredWidth(80);  // Unit Price
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // Amount

        table.setPreferredScrollableViewportSize(new Dimension(500, 500));
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        scrollPane.setBackground(Color.WHITE);

        // CALCULATOR TOTAL
        double totalPrice = calculateTotalPrice(dataUse);
        double cash = 1500.00;
        double change = cash >= totalPrice ? cash - totalPrice : 0;

        String currentDate = dataUse[0][1];
        String currentTime = dataUse[0][2];

        // GET ID CUSTOMER
        String memberName = dataUse[0][4];

        // Header
        JLabel headerLabel = new JLabel("SHOP", JLabel.CENTER);
        headerLabel.setFont(new SetPreferences().getFont(26));

        JLabel memberNameLabel = new JLabel("Member: " + memberName);
        memberNameLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel dateLabel = new JLabel("Date : " + currentDate + "  Time : " + currentTime);
        dateLabel.setHorizontalAlignment(JLabel.CENTER);

        // Payment Panel
        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(new GridLayout(3, 1));
        paymentPanel.setPreferredSize(new Dimension(400,100));
        paymentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        paymentPanel.setBackground(Color.WHITE);

        Font myFont = new Font("Arial", Font.BOLD, 18);

        JLabel totalLabel = new JLabel("Total Price: " + format.format(totalPrice));
        totalLabel.setForeground(Color.RED);
        totalLabel.setFont(myFont);
        JLabel cashLabel = new JLabel("Cash: " + format.format(cash));
        cashLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel changeLabel = new JLabel("Change: " + format.format(change));
        changeLabel.setFont(new Font("Arial", Font.BOLD, 16));

        paymentPanel.add(totalLabel);
        paymentPanel.add(cashLabel);
        paymentPanel.add(changeLabel);

        JLabel BillIdLabel = new JLabel("Bill Id : " + dataUse[0][0]);
        BillIdLabel.setPreferredSize(new Dimension(400,20));
        BillIdLabel.setHorizontalAlignment(JLabel.RIGHT);

        // Bottom Panel
        JLabel thankYouLabel = new JLabel("Thank you!", JLabel.CENTER);
        thankYouLabel.setFont(new Font("Arial", Font.BOLD, 20));
        thankYouLabel.setForeground(Color.BLACK);
        thankYouLabel.setBackground(Color.WHITE);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(thankYouLabel, BorderLayout.NORTH);
        bottomPanel.setBackground(Color.WHITE);

        // Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setPreferredSize(new Dimension(430,430));
        panel.setBackground(Color.WHITE);
        // panel.add(headerLabel);
        // panel.add(memberNameLabel);
        // panel.add(dateLabel);
        panel.add(scrollPane);
        panel.add(paymentPanel);
        panel.add(BillIdLabel);
        panel.add(bottomPanel);

        JPanel headPanel = new JPanel(new GridLayout(2,1));
        JPanel bhJPanel = new JPanel(new GridLayout(1,2));
        bhJPanel.add(dateLabel);
        bhJPanel.add(memberNameLabel);
        bhJPanel.setBackground(Color.WHITE);
        headPanel.add(headerLabel);
        headPanel.add(bhJPanel);
        headPanel.setBackground(Color.WHITE);

        c.add(headPanel,BorderLayout.NORTH);
        c.add(panel, BorderLayout.CENTER);
    }

    private String[][] getDataTable(){
        String[][] data = new String[this.dataInCart.length][4];
        for (int count = 0 ;count < this.dataInCart.length;count++){
            data[count][0] = this.dataInCart[count][6];
            data[count][1] = this.dataInCart[count][5];
            data[count][2] = format.format(Double.parseDouble(getPrice(this.dataInCart[count][5])));
            data[count][3] = format.format(Double.parseDouble(this.dataInCart[count][7]));
        }

        return data;
    }
    private String[][] getData(){
        String[][] data = new String[this.dataInCart.length][7];
        for (int count = 0 ;count < this.dataInCart.length;count++){
            data[count][0] = this.dataInCart[count][0];
            data[count][1] = this.dataInCart[count][2];
            data[count][2] = this.dataInCart[count][3];
            data[count][3] = this.dataInCart[count][4];
            data[count][4] = this.dataInCart[count][4];
            data[count][5] = this.dataInCart[count][6];
            data[count][6] = this.dataInCart[count][7];
        }

        return data;
    }

    private double calculateTotalPrice(String[][] data){
        double ttp = 0.0;
        for (String[] recode : data){
            ttp += Double.parseDouble(recode[6]);
        }
        return ttp;
    }

    private String getPrice(String name){
        String[][] inventory = new InventoryService().getAllProductData();
        String price = "";
        for (int c = 0 ; c< inventory.length;c++){
            if(inventory[c][1].equals(name)){
                price = inventory[c][2];
                break;
            }
        }
        return price;
    }

}