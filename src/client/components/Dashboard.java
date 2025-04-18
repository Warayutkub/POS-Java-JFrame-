package client.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
import client.MainFrame;
import resources.SetPreferences;

public class Dashboard extends JPanel {
    private DecimalFormat format = new DecimalFormat("#,###.##");
    private int width = 900;
    private int height = 515;
    private JPanel topPanel, bodyPanel, topBody;
    private SetPreferences preferences = new SetPreferences();
    private String[][] data;
    private String[] columnNames = { "QUEUE ID", "TYPE", "DATE", "TIME", "CUSTOMER", "PRODUCT", "QTY", "TOTAL" };
    private String dateGet;
    private JPanel topCardDayOrder, topCardDayTotal, topCardAllOrder, topCardAllTotal;
    private MainFrame mainFrame;
    private FindBill fb = new FindBill();

    @SuppressWarnings("rawtypes")
    private JComboBox dateSelect;

    public Dashboard(MainFrame mainFrame,int width) {
        setPreferredSize(new Dimension(width, height));
        this.mainFrame = mainFrame;
        this.width = width;
        CreateGui(14);
    }

    private void CreateGui(int font) {
        setLayout(new BorderLayout());

        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));

        dateSelect = new JComboBox<String>(new Vector<>(new DashboardService().getChoice()));
        dateSelect.setSelectedIndex(0);
        dateGet = (String) dateSelect.getSelectedItem();
        JButton findBillBtn = new JButton("Find Bill");
        float[] hsbValues = Color.RGBtoHSB(3, 153, 254, null);
        findBillBtn.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
        findBillBtn.setForeground(Color.WHITE);
        findBillBtn.addActionListener(e -> {
            if(fb.isVisible() == false) {
                fb = new FindBill(mainFrame);
                fb.setVisible(true);
            }
            else if (!fb.isActive() || fb.getState() == JFrame.ICONIFIED) {
                fb.toFront();
                fb.requestFocus();
                fb.setState(JFrame.NORMAL);
            }
        });

        topCardAllOrder = TopCard(new DashboardService().getDataAllForTopBoard("order"), "Order", "All Total Order");
        topCardAllTotal = TopCard(new DashboardService().getDataAllForTopBoard("total"), "Bath", "All Total Income");

        topCardDayOrder = TopCard(new DashboardService().getDataTopDashboard("order", dateGet), "Order",
                "Daily Total Order");
        topCardDayTotal = TopCard(new DashboardService().getDataTopDashboard("total", dateGet), "Bath",
                "Daily Total Income");

        topPanel.add(topCardAllOrder);
        topPanel.add(topCardAllTotal);
        topPanel.add(topCardDayOrder);
        topPanel.add(topCardDayTotal);

        topBody = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 5));
        topBody.setPreferredSize(new Dimension(width, 35));
        topBody.add(dateSelect);
        topBody.add(findBillBtn);

        bodyPanel = new JPanel(new FlowLayout());
        data = new DashboardService().getTable((String) dateSelect.getSelectedItem());

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
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
                topCardDayOrder = TopCard(new DashboardService().getDataTopDashboard("order", dateString), "Order",
                "Daily Total Order");
                topCardDayTotal = TopCard(new DashboardService().getDataTopDashboard("total", dateString), "Bath",
                "Daily Total Income");
                topPanel.add(topCardDayOrder);
                topPanel.add(topCardDayTotal);
                topPanel.revalidate();
                topPanel.repaint();
            }
        });
        
        add(topPanel, BorderLayout.NORTH);
        add(bodyPanel, BorderLayout.CENTER);
        table.setPreferredScrollableViewportSize(new Dimension(width, 550));
    }
    
    private JPanel TopCard(double amount, String type, String title) {
        JPanel container = new JPanel(new GridLayout(2, 1));
        JTextField top = new JTextField();
        JTextField bottom = new JTextField();

        container.setBackground(Color.WHITE);
        container.setPreferredSize(new Dimension(200, 60));
        container.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));

        top.setFont(preferences.getFont(16));
        top.setText(format.format(amount) + " " + type);
        top.setEditable(false);
        top.setHorizontalAlignment(JTextField.CENTER);
        top.setBorder(BorderFactory.createEmptyBorder());
        top.setBackground(Color.WHITE);

        bottom.setFont(preferences.getFont(12));
        bottom.setText(title);
        bottom.setEditable(false);
        bottom.setHorizontalAlignment(JTextField.CENTER);
        bottom.setBorder(BorderFactory.createEmptyBorder());
        bottom.setBackground(Color.WHITE);

        container.add(top);
        container.add(bottom);
        return container;
    }
}
