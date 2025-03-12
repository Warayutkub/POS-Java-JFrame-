import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class TableExample {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Table Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        String[] columnNames = {"QUEUE ID", "TYPE", "DATE", "CUSTOMER", "PRODUCT", "QTY", "TOTAL"};
        Object[][] data = {
            {"1", "Order", "2025-03-11", "John Doe", "Product A", "3", "$150"},
            {"2", "Order", "2025-03-10", "Jane Smith", "Product B", "2", "$120"},
            {"3", "Order", "2025-03-09", "Tom Brown", "Product C", "1", "$50"},
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        // ตกแต่ง JTable
        // table.setFont(new Font("Arial", Font.PLAIN, 14));
        // table.setRowHeight(30);
        // table.setGridColor(Color.GRAY);  // เปลี่ยนสีเส้นขอบ
        // table.setBackground(Color.LIGHT_GRAY);  // สีพื้นหลังของตาราง

        // ใช้ TableCellRenderer เพื่อปรับแต่งสีพื้นหลัง
        // table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        //     @Override
        //     public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //         Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        //         if (value != null && value.equals("$150")) {  // ถ้าค่าคือ "$150"
        //             cell.setBackground(Color.YELLOW);  // ตั้งค่าสีพื้นหลังเป็นสีเหลือง
        //         } else {
        //             cell.setBackground(Color.WHITE);  // สีพื้นหลังปกติ
        //         }
        //         return cell;
        //     }
        // });

        // เพิ่ม JTable ลงใน JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
