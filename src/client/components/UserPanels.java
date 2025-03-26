package client.components;

import javax.swing.*;

import backend.models.Users.User;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.*;

public class UserPanels extends JFrame {

    private Container c = getContentPane();

    public UserPanels() {
        MainPanel();
        setupWindow();
    }

    private void MainPanel() {
        c.setLayout(new FlowLayout(FlowLayout.CENTER));
        c.setPreferredSize(new Dimension(700, 500));
        for (String[] recode : pullData()) {
            c.add(new UserPanel(recode[0], recode[1], recode[2], recode[3],this));
            System.out.println(recode[0]);
        }
    }

    private void setupWindow() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void resetPage(){
        c.removeAll();
        for (String[] recode : pullData()) {
            c.add(new UserPanel(recode[0], recode[1], recode[2], recode[3],this));
            System.out.println(recode[0]);
        }
        c.revalidate();
        c.repaint();
    }

    class UserPanel extends JPanel {
        private String id;
        private String name;
        private String phone;
        private String email;
        private UserPanels n;

        public UserPanel(String id, String name, String phone, String email,UserPanels n) {
            this.id = id;
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.n = n;
            setLayout(new FlowLayout(FlowLayout.CENTER));
            setPreferredSize(new Dimension(700, 40));
            setBackground(Color.GRAY);
            add(getPanelId());
            add(getPanelName());
            add(getPanelPhone());
            add(getPanelEmail());
            add(editPanel());
            add(delPanel());
            
        }
        
        private JPanel newUser() {
            JPanel panel = new JPanel();
            JButton bt = new JButton("New User");
            panel.setPreferredSize(new Dimension(700, 60));
            bt.setPreferredSize(new Dimension(100, 50));
            bt.addActionListener(e -> {
                new NewUserRegister();
                setVisible(true);
            });
            panel.add(bt);
            return panel;
        }

        private JPanel getPanelId() {
            JPanel Panel = new JPanel();
            JLabel Label = new JLabel(this.id);
            Panel.setBackground(Color.GRAY);
            Panel.add(Label);
            return Panel;
        }
        
        private JPanel getPanelName() {
            JPanel Panel = new JPanel();
            JLabel Label = new JLabel(this.name);
            Panel.setBackground(Color.GRAY);
            Panel.add(Label);
            return Panel;
        }
        
        private JPanel getPanelPhone() {
            JPanel Panel = new JPanel();
            JLabel Label = new JLabel(this.phone);
            Panel.setBackground(Color.GRAY);
            Panel.add(Label);
            return Panel;
        }
        
        private JPanel getPanelEmail() {
            JPanel Panel = new JPanel();
            JLabel Label = new JLabel(this.email);
            Panel.setBackground(Color.GRAY);
            Panel.setFont(getFont());
            Panel.add(Label);
            return Panel;
        }
        
        public void resetPage() {
            n.resetPage();
        }
        
        private JButton editButton() {
            JButton Button = new JButton("Edit");
            Button.addActionListener(e -> {
                new editUser(id,n);
                c.revalidate();
                c.repaint();
            });
            return Button;
        }
        
        private JPanel editPanel() {
            JPanel Panel = new JPanel();
            Panel.add(editButton());
            Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            return Panel;
        }
        
        private JButton delButton() {
            JButton Button = new JButton("Delete");
            Button.addActionListener(e -> {
                DeleteData(this.id);
                c.removeAll();
                for (String[] recode : pullData()) {
                    c.add(new UserPanel(recode[0], recode[1], recode[2], recode[3],n));
                }
                c.revalidate();
                c.repaint();
            });
            return Button;
        }

        private JPanel delPanel() {
            JPanel Panel = new JPanel();
            Panel.add(delButton());
            Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            return Panel;
        }

        private void DeleteData(String id) {
            String[][] data = pullData();
            String[][] newData = new String[data.length - 1][data[0].length - 1];
            int c = 0;
            for (String[] person : data) {
                if (!id.equals(person[0])) {
                    newData[c++] = person;
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/backend/data/UserData.txt"))) {
                for (String[] person : newData) {
                    writer.write(person[0] + "," + person[1] + "," + person[2] + "," + person[3] + "\n");
                }
            } catch (Exception e) {
            }
        }

    }

    public String[][] pullData() {
        String[][] data = new String[0][0];

        try (BufferedReader rd = new BufferedReader(new FileReader("./src/backend/data/UserData.txt"))) {
            String lines;
            int row = 0;
            int column = 0;
            while ((lines = rd.readLine()) != null) {
                row++;
                column = lines.split(",").length;
            }
            data = new String[row][column];
            try (BufferedReader rd2 = new BufferedReader(new FileReader("./src/backend/data/UserData.txt"))) {
                int i = 0;
                while ((lines = rd2.readLine()) != null) {
                    data[i++] = lines.split(",");
                }
            } catch (Exception e) {
                
            }
        } catch (Exception e) {

        }
        return data;
    }

    public static void main(String[] args) {
        new UserPanels();
    }
}
