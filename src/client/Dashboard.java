package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import backend.services.DashboardService;
import backend.services.InventoryService;
import resources.SetPreferences;

public class Dashboard extends JFrame {
    private int width = 900;
    private int height = 515;
    private Container container = getContentPane();
    private JPanel topPanel, bodyPanel, topBody;
    private SetPreferences preferences = new SetPreferences();
    private String[][] data;
    private String[] columnNames = { "QUEUE ID", "TYPE", "DATE", "TIME", "CUSTOMER", "PRODUCT", "QTY", "TOTAL" };
    private String dateGet;
    private JPanel topCardDayOrder,topCardDayTotal,topCardAllOrder,topCardAllTotal;
    private String[][] inventory = new InventoryService().getAllSalesHistory();

    @SuppressWarnings("rawtypes")
    private JComboBox dateSelect;

    public Dashboard() {
        CreateGui(14);
        CreateWindow();
    }

    private void CreateWindow() {
        setTitle("DashBoard to day");
        setSize(this.width, this.height);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void CreateGui(int font) {
        container.setLayout(new BorderLayout());
        
        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        
        dateSelect = new JComboBox<String>(new Vector<>(new DashboardService().getChoice()));
        dateSelect.setSelectedIndex(0);
        dateGet = (String) dateSelect.getSelectedItem();

        topCardAllOrder = TopCard(new DashboardService().getDataAllForTopBoard("order"),"Order","All Total Order",true);
        topCardAllTotal = TopCard(new DashboardService().getDataAllForTopBoard("total"),"Bath","All Total Income",false);

        topCardDayOrder = TopCard(new DashboardService().getDataTopDashboard("order",dateGet), "Order", "Daily Total Order",true);
        topCardDayTotal = TopCard(new DashboardService().getDataTopDashboard("total", dateGet), "Bath", "Daily Total Income", false);
        System.out.println(inventory[inventory.length-1][0]);

        topPanel.add(topCardAllOrder);
        topPanel.add(topCardAllTotal);
        topPanel.add(topCardDayOrder);
        topPanel.add(topCardDayTotal);
        
        topBody = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 5));
        topBody.setPreferredSize(new Dimension(width, 35));
        topBody.add(dateSelect);
        
        bodyPanel = new JPanel(new FlowLayout());
        data = new DashboardService().getTable((String) dateSelect.getSelectedItem());

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(850, 300));
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);


        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        bodyPanel.add(topBody);
        bodyPanel.add(new JScrollPane(table));

        dateSelect.addItemListener(event -> {
            String dateString = "";
            if (event.getStateChange() == ItemEvent.SELECTED) {
                dateString = (String) event.getItem();
                data = new DashboardService().getTable(dateString);
                model.setRowCount(0);

                for (String[] row : data) {
                    model.addRow(row);
                }
                topPanel.remove(topCardDayOrder);
                topPanel.remove(topCardDayTotal);
                topCardDayOrder = TopCard(new DashboardService().getDataTopDashboard("order", dateString), "Order", "Daily Total Order", true);
                topCardDayTotal = TopCard(new DashboardService().getDataTopDashboard("total", dateString), "Bath", "Daily Total Income", false);
                topPanel.add(topCardDayOrder);
                topPanel.add(topCardDayTotal);
                topPanel.revalidate();
                topPanel.repaint();
            }
        });

        container.add(topPanel, BorderLayout.NORTH);
        container.add(bodyPanel, BorderLayout.CENTER);
    }

    private JPanel TopCard(double amount, String type, String title, boolean setdecimal) {
        DecimalFormat format = new DecimalFormat("#,###,##0");
        JPanel container = new JPanel(new GridLayout(2, 1));
        JTextField top = new JTextField();
        JTextField bottom = new JTextField();

        container.setBackground(Color.GRAY);
        container.setPreferredSize(new Dimension(200, 60));
        container.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));

        top.setFont(preferences.getFont(16));
        top.setText(amount + " " + type);
        top.setEditable(false);
        top.setHorizontalAlignment(JTextField.CENTER);
        top.setBorder(BorderFactory.createEmptyBorder());

        bottom.setFont(preferences.getFont(12));
        bottom.setText(title);
        bottom.setEditable(false);
        bottom.setHorizontalAlignment(JTextField.CENTER);
        bottom.setBorder(BorderFactory.createEmptyBorder());

        if (setdecimal) {
            top.setText(format.format(amount) + " " + type);
        }

        container.add(top);
        container.add(bottom);
        return container;
    }
    public void On() {
        setVisible(true);
    }

}
