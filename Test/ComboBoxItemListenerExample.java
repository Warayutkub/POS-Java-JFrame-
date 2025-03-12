import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ComboBoxItemListenerExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JComboBox ItemListener Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        String[] items = {"Apple", "Banana", "Cherry", "Mango"};
        JComboBox<String> comboBox = new JComboBox<>(items);

        // เพิ่ม ItemListener
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println("เลือก: " + e.getItem());
                }
            }
        });

        frame.add(comboBox);
        frame.setVisible(true);
    }
}
