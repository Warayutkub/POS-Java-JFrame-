package client.components;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class UserPanels extends JPanel {

    public UserPanels() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setPreferredSize(new Dimension(700, 500));
        loadUserPanels();
    }

    private void loadUserPanels() {
        removeAll();
        for (String[] record : pullData()) {
            add(new UserPanel(record[0], record[1], record[2], record[3], this));
        }
        revalidate();
        repaint();
    }

    public void resetPage() {
        loadUserPanels();
    }

    class UserPanel extends JPanel {
        private String id, name, phone, email;
        private UserPanels parent;

        public UserPanel(String id, String name, String phone, String email, UserPanels parent) {
            this.id = id;
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.parent = parent;
            setLayout(new FlowLayout(FlowLayout.CENTER));
            setPreferredSize(new Dimension(700, 40));
            setBackground(Color.GRAY);
            add(new JLabel(id));
            add(new JLabel(name));
            add(new JLabel(phone));
            add(new JLabel(email));
            add(editButton());
            add(deleteButton());
        }

        private JButton editButton() {
            JButton button = new JButton("Edit");
            button.addActionListener(e -> new editUser(id, parent));
            return button;
        }

        private JButton deleteButton() {
            JButton button = new JButton("Delete");
            button.addActionListener(e -> {
                deleteUser(id);
                parent.resetPage();
            });
            return button;
        }

        private void deleteUser(String id) {
            String[][] data = pullData();
            StringBuilder newData = new StringBuilder();
            for (String[] record : data) {
                if (!record[0].equals(id)) {
                    newData.append(String.join(",", record)).append("\n");
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/backend/data/UserData.txt"))) {
                writer.write(newData.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String[][] pullData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("./src/backend/data/UserData.txt"))) {
            return reader.lines().map(line -> line.split(",")).toArray(String[][]::new);
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0][0];
        }
    }
}
