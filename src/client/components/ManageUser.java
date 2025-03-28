package client.components;

import javax.swing.*;
import javax.swing.border.LineBorder;

import backend.services.AuthService;
import client.MainFrame;

import java.awt.*;
import java.io.*;

public class ManageUser extends JPanel {
    private MainFrame mainFrame;
    private int width;

    public ManageUser(MainFrame mainFrame, int width) {
        this.mainFrame = mainFrame;
        this.width = width;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, 500));
        add(Top(), BorderLayout.NORTH);
        add(loadUserPanels(), BorderLayout.CENTER);
    }

    private JPanel Top() {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER));

        area.setBackground(Color.WHITE);
        area.setPreferredSize(new Dimension(width, 60));
        return area;
    }

    private JPanel loadUserPanels() {
        JPanel area = new JPanel();
        JScrollPane scrollPane = new JScrollPane();
        JPanel InnerArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
        for (String[] record : new card().pullData()) {
            InnerArea.add(new card(record[0], record[1], record[2], record[3], this, width - 10,mainFrame));
        }
        InnerArea.setBackground(null);
        scrollPane.setBackground(null);
        scrollPane.add(InnerArea);
        area.add(scrollPane);
        InnerArea.setPreferredSize(new Dimension(width,900));
        scrollPane.setPreferredSize(new Dimension(width,900));
        area.setPreferredSize(new Dimension(width,900));
        return area;
    }

    public void resetPage() {
        removeAll();
        add(Top(), BorderLayout.NORTH);
        add(loadUserPanels(), BorderLayout.CENTER);
    }

}

class card extends JPanel {
    private String id, name, phone, email;
    private ManageUser manageUser;
    private MainFrame mainFrame;

    public card() {
    }

    public card(String id, String name, String phone, String email, ManageUser parent, int width,MainFrame mainFrame) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.manageUser = parent;
        this.mainFrame = mainFrame;

        setLayout(new FlowLayout(FlowLayout.CENTER));
        setPreferredSize(new Dimension(width, 40));
        setBackground(Color.WHITE);
        setBorder(new LineBorder(Color.black));

        add(idPanel());
        add(namePanel());
        add(phonePanel());
        add(emailPanel());
        add(editButton());
        add(delButton());
    }

    private JLabel idPanel() {
        JLabel idLabel = new JLabel("ID : " + this.id);
        idLabel.setHorizontalAlignment(JLabel.LEFT);
        idLabel.setPreferredSize(new Dimension(50, 30));

        return idLabel;
    }

    private JLabel namePanel() {
        JLabel nameLabel = new JLabel("Name : " + this.name);
        nameLabel.setHorizontalAlignment(JLabel.LEFT);
        nameLabel.setPreferredSize(new Dimension(230, 30));

        return nameLabel;
    }

    private JLabel phonePanel() {
        JLabel phoneLabel = new JLabel("Phone : " + this.phone);
        phoneLabel.setHorizontalAlignment(JLabel.LEFT);
        phoneLabel.setPreferredSize(new Dimension(175, 30));

        return phoneLabel;
    }

    private JLabel emailPanel() {
        JLabel emailLabel = new JLabel("Email : " + this.email);
        emailLabel.setHorizontalAlignment(JLabel.LEFT);
        emailLabel.setPreferredSize(new Dimension(375, 30));

        return emailLabel;
    }

    private JPanel editButton() {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        JButton button = new JButton("Edit");
        float[] hsbValues = Color.RGBtoHSB(3, 153, 254, null);
        button.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
        button.setForeground(Color.WHITE);

        button.addActionListener(e -> {
            editDialog();
        });

        area.add(button);
        area.setBackground(null);
        area.setPreferredSize(new Dimension(100, 30));
        return area;
    }

    private JPanel delButton() {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        JButton Button = new JButton("Delete");
        float[] hsbValues = Color.RGBtoHSB(3, 153, 254, null);
        Button.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
        Button.setForeground(Color.WHITE);

        area.add(Button);
        area.setBackground(null);
        area.setPreferredSize(new Dimension(100, 30));
        return area;
    }

    private void Edit(String[] newData) {
        String[][] data = pullData();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/backend/data/NowUser.txt"))) {
            for (String[] recode : data) {
                if (recode[0].equals(newData[0])){
                    writer.write(newData[0] + "," + newData[1] + "," + newData[2] + "," + newData[3] + "," + newData[4] + "\n");
                }else{
                    writer.write(recode[0] + "," + recode[1] + "," + recode[2] + "," + recode[3] + "," + recode[4] + "\n");
                }
            }
            JOptionPane.showMessageDialog(mainFrame, "Successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error can't edit");
        }

    }

    private JDialog editDialog(){
        String[] User = new AuthService().getDataUser(this.id);

        JDialog area = new JDialog(mainFrame, "Edit", true);
        area.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel areaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel nameLabel = new JLabel("Name : ");
        nameLabel.setPreferredSize(new Dimension(300,30));
        nameLabel.setHorizontalAlignment(JLabel.LEFT);
        JTextField nameField = new JTextField(this.name);
        nameField.setPreferredSize(new Dimension(300,30));
        JLabel phoneLabel = new JLabel("Phone : ");
        phoneLabel.setPreferredSize(new Dimension(300,30));
        phoneLabel.setHorizontalAlignment(JLabel.LEFT);
        JTextField phoneField = new JTextField(this.phone);
        phoneField.setPreferredSize(new Dimension(300,30));
        JLabel emailLabel = new JLabel("Email : ");
        emailLabel.setPreferredSize(new Dimension(300,30));
        emailLabel.setHorizontalAlignment(JLabel.LEFT);
        JTextField emailField = new JTextField(this.email);
        emailField.setPreferredSize(new Dimension(300,30));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,20));
        btnPanel.setPreferredSize(new Dimension(300,120));
        JButton btn = new JButton("Submit");
        btn.setPreferredSize(new Dimension(100,40));
        btn.addActionListener(e -> {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            System.out.println("Name : " + name + " phone : " + phone + " email : " + email);
            User[1] = name;
            User[2] = phone;
            User[3] = email;
            Edit(User);
            area.dispose();
            manageUser.resetPage();
        });

        btnPanel.add(btn);

        areaPanel.add(nameLabel);
        areaPanel.add(nameField);
        areaPanel.add(phoneLabel);
        areaPanel.add(phoneField);
        areaPanel.add(emailLabel);
        areaPanel.add(emailField);
        areaPanel.add(btnPanel);
        area.add(areaPanel);
        areaPanel.setPreferredSize(new Dimension(400,400));
        area.setSize(new Dimension(400, 400));
        area.setLocationRelativeTo(mainFrame);
        area.setVisible(true);
        return area;
    }

    private void deleteUser(String id) {
        String[][] data = pullData();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/backend/data/NowUser.txt"))) {
            for (String[] recode : data) {
                if (!recode[0].equals(id)) {
                    writer.write(
                            recode[0] + "," + recode[1] + "," + recode[2] + "," + recode[3] + "," + recode[4] + "\n");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error can't delete");
        }
    }

    public String[][] pullData() {
        String[][] data = new String[0][0];
        String line;
        int column = 0;
        int row = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("./src/backend/data/NowUser.txt"))) {
            while ((line = reader.readLine()) != null) {
                column = line.split(",").length;
                row++;
            }
            data = new String[row][column];
            row = 0;
            try (BufferedReader reader2 = new BufferedReader(new FileReader("./src/backend/data/NowUser.txt"))) {
                while ((line = reader2.readLine()) != null) {
                    data[row++] = line.split(",");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error can't open file user data");
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error can't open file user data");
        }
        return data;
    }
}