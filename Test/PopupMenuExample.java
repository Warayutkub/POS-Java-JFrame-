import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PopupMenuExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JPopupMenu Example");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // 🔹 สร้าง JPopupMenu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem copyItem = new JMenuItem("Copy");
        JMenuItem pasteItem = new JMenuItem("Paste");
        JMenuItem deleteItem = new JMenuItem("Delete");

        popupMenu.add(copyItem);
        popupMenu.add(pasteItem);
        popupMenu.add(deleteItem);

        // 🔹 เพิ่ม Action ให้เมนู
        copyItem.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Copy clicked"));
        pasteItem.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Paste clicked"));
        deleteItem.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Delete clicked"));

        // 🔹 ติดตั้ง Mouse Listener ให้แสดงเมนูเมื่อคลิกขวา
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }

            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) { // ตรวจสอบว่าเป็นคลิกขวาหรือไม่
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        frame.setVisible(true);
    }
}
