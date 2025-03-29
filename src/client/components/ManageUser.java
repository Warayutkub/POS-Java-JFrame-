package client.components;

import javax.swing.*;
import javax.swing.border.LineBorder;

import backend.models.Users.User;
import backend.services.AuthService;
import client.MainFrame;
import resources.SetPreferences;
import resources.Tools;

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
        JPanel area = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 20));
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 25));
        searchField.setFont(new SetPreferences().getFont(16));
        searchField.addActionListener(e -> {
            if (!searchField.getText().isEmpty()) {
                removeAll();
                add(Top(), BorderLayout.NORTH);
                add(loadUserPanelsSearch(searchField.getText().toLowerCase()), BorderLayout.CENTER);
                revalidate();
                repaint();
            } else {
                removeAll();
                add(Top(), BorderLayout.NORTH);
                add(loadUserPanels(), BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });

        JButton btnSearch = new JButton("Search");
        btnSearch.setFont(new SetPreferences().getFont(16));
        btnSearch.setPreferredSize(new Dimension(150, 25));
        btnSearch.setBackground(Color.white);
        btnSearch.addActionListener(e -> {
            if (!searchField.getText().isEmpty()) {
                removeAll();
                add(Top(), BorderLayout.NORTH);
                add(loadUserPanelsSearch(searchField.getText()), BorderLayout.CENTER);
                revalidate();
                repaint();
            } else {
                removeAll();
                add(Top(), BorderLayout.NORTH);
                add(loadUserPanels(), BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });

        JPanel newUserPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton BtnNewUser = new JButton("New User");
        BtnNewUser.setForeground(Color.white);
        float[] hsbValues = Color.RGBtoHSB(3, 153, 254, null);
        BtnNewUser.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
        BtnNewUser.addActionListener(e -> {
            newUserDialog();
            removeAll();
            add(Top(), BorderLayout.NORTH);
            add(loadUserPanels(), BorderLayout.CENTER);
            revalidate();
            repaint();
        });

        newUserPanel.add(BtnNewUser);
        newUserPanel.setPreferredSize(new Dimension(400,40));
        JPanel blankJPanel = new JPanel();
        blankJPanel.setPreferredSize(new Dimension(155,40));
        area.add(searchField);
        area.add(btnSearch);
        area.add(newUserPanel);
        area.add(blankJPanel);
        area.setPreferredSize(new Dimension(width, 60));
        return area;
    }

    private JPanel loadUserPanelsSearch(String searchKey) {
        JPanel area = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        JPanel innerArea = new JPanel(new FlowLayout(FlowLayout.CENTER));

        int row = 0;
        for (String[] record : new card().pullData()) {
            if (record[0].toLowerCase().contains(searchKey) || record[1].toLowerCase().contains(searchKey)) {
                innerArea.add(new card(record[0], record[1], record[2], record[3], this, width - 10, mainFrame));
                row++;
            }
        }

        innerArea.setBackground(null);
        scrollPane.setViewportView(innerArea);
        scrollPane.setBorder(null);
        area.add(scrollPane, BorderLayout.CENTER);

        innerArea.setPreferredSize(new Dimension(width - 20, row*50));
        scrollPane.setPreferredSize(new Dimension(width, 500));
        area.setPreferredSize(new Dimension(width, 500));

        return area;
    }

    private JPanel loadUserPanels() {
        JPanel area = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        JPanel innerArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
        int row = 0;
        for (String[] record : new card().pullData()) {
            innerArea.add(new card(record[0], record[1], record[2], record[3], this, width - 10, mainFrame));
            row++;
        }

        innerArea.setBackground(null);
        scrollPane.setViewportView(innerArea);
        scrollPane.setBorder(null);
        area.add(scrollPane, BorderLayout.CENTER);

        innerArea.setPreferredSize(new Dimension(width - 20,  row*50));
        scrollPane.setPreferredSize(new Dimension(width, 500));
        area.setPreferredSize(new Dimension(width, 500));

        return area;
    }

    private JDialog newUserDialog() {
        JDialog area = new JDialog(mainFrame, "New User", true);
        area.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel areaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel nameLabel = new JLabel("Name : ");
        nameLabel.setPreferredSize(new Dimension(300, 30));
        nameLabel.setHorizontalAlignment(JLabel.LEFT);
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(300, 30));

        JLabel phoneLabel = new JLabel("Phone : ");
        phoneLabel.setPreferredSize(new Dimension(300, 30));
        phoneLabel.setHorizontalAlignment(JLabel.LEFT);
        JTextField phoneField = new JTextField();
        phoneField.setPreferredSize(new Dimension(300, 30));

        JLabel emailLabel = new JLabel("Email : ");
        emailLabel.setPreferredSize(new Dimension(300, 30));
        emailLabel.setHorizontalAlignment(JLabel.LEFT);
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(300, 30));

        JLabel passwordLabel = new JLabel("Password : ");
        passwordLabel.setPreferredSize(new Dimension(300, 30));
        passwordLabel.setHorizontalAlignment(JLabel.LEFT);
        JTextField passwordField = new JTextField();
        passwordField.setPreferredSize(new Dimension(300, 30));

        JLabel confirmPasswordLabel = new JLabel("Confirm password : ");
        confirmPasswordLabel.setPreferredSize(new Dimension(300, 30));
        confirmPasswordLabel.setHorizontalAlignment(JLabel.LEFT);
        JTextField confirmPasswordField = new JTextField();
        confirmPasswordField.setPreferredSize(new Dimension(300, 30));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        btnPanel.setPreferredSize(new Dimension(300, 150));
        JButton btn = new JButton("Submit");
        btn.setPreferredSize(new Dimension(100, 40));
        btn.setForeground(Color.white);
        float[] hsbValues = Color.RGBtoHSB(3, 153, 254, null);
        btn.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
        btn.addActionListener(e -> {
            if (!nameField.getText().isEmpty() && !phoneField.getText().isEmpty() && !emailField.getText().isEmpty() && 
                !passwordField.getText().isEmpty() && !confirmPasswordField.getText().isEmpty()) {
                if (passwordField.getText().equals(confirmPasswordField.getText())) {
                    saveNewUser(nameField.getText(),phoneField.getText(),emailField.getText(),passwordField.getText(),area);
                    area.dispose();
                } else {
                    JOptionPane.showMessageDialog(area, "Password does not match!");
                }
            } else {
                JOptionPane.showMessageDialog(area, "All fields must be filled!");
            }
        });

        passwordField.addActionListener(e -> {
            if (!nameField.getText().isEmpty() && !phoneField.getText().isEmpty() && !emailField.getText().isEmpty() && 
                !passwordField.getText().isEmpty() && !confirmPasswordField.getText().isEmpty()) {
                if (passwordField.getText().equals(confirmPasswordField.getText())) {
                    saveNewUser(nameField.getText(),phoneField.getText(),emailField.getText(),passwordField.getText(),area);
                    area.dispose();
                } else {
                    JOptionPane.showMessageDialog(area, "Password does not match!");
                }
            } else {
                JOptionPane.showMessageDialog(area, "All fields must be filled!");
            }
        });

        confirmPasswordField.addActionListener(e->{
            if (!nameField.getText().isEmpty() && !phoneField.getText().isEmpty() && !emailField.getText().isEmpty() && 
                !passwordField.getText().isEmpty() && !confirmPasswordField.getText().isEmpty()) {
                if (passwordField.getText().equals(confirmPasswordField.getText())) {
                    saveNewUser(nameField.getText(),phoneField.getText(),emailField.getText(),passwordField.getText(),area);
                    area.dispose();
                } else {
                    JOptionPane.showMessageDialog(area, "Password does not match!");
                }
            } else {
                JOptionPane.showMessageDialog(area, "All fields must be filled!");
            }
        });

        btnPanel.add(btn);

        areaPanel.add(nameLabel);
        areaPanel.add(nameField);
        areaPanel.add(phoneLabel);
        areaPanel.add(phoneField);
        areaPanel.add(emailLabel);
        areaPanel.add(emailField);
        areaPanel.add(passwordLabel);
        areaPanel.add(passwordField);
        areaPanel.add(confirmPasswordLabel);
        areaPanel.add(confirmPasswordField);
        areaPanel.add(btnPanel);
        area.add(areaPanel);
        areaPanel.setPreferredSize(new Dimension(400, 500));
        area.setSize(new Dimension(400, 500));
        area.setLocationRelativeTo(mainFrame);
        area.setVisible(true);
        return area;
    }

    private void saveNewUser(String name,String phone,String email,String password,JDialog area) {
        String id = new Tools().genNewId(new AuthService().getAllUserData("user"));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/backend/data/NowUser.txt",true))){
            writer.write(id + "," + name + "," + phone + "," + email + "," + password + "\n");
            try(BufferedWriter writer2 = new BufferedWriter(new FileWriter("./src/backend/data/UserData.txt",true))) {
                writer2.write(id + "," + name + "," + phone + "," + email + "," + password + "\n");
                
            } catch (IOException e) {
                JOptionPane.showMessageDialog(area, "Error can't save new user");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(area, "Error can't save new user");
        }
        JOptionPane.showMessageDialog(area, "Successfully!");

    }

    public void resetPage() {
        removeAll();
        add(Top(), BorderLayout.NORTH);
        add(loadUserPanels(), BorderLayout.CENTER);
    }

}

class card extends JPanel {
    private ManageUser manageUser;
    private MainFrame mainFrame;
    private User user = new User("", "", "", "", "");

    public card() {
    }

    public card(String id, String name, String phone, String email, ManageUser parent, int width, MainFrame mainFrame) {
        this.user.setId(id);
        this.user.setName(name);
        this.user.setPhone(phone);
        this.user.setEmail(email);
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
        JLabel idLabel = new JLabel("ID : " + this.user.getID());
        idLabel.setHorizontalAlignment(JLabel.LEFT);
        idLabel.setPreferredSize(new Dimension(50, 30));

        return idLabel;
    }

    private JLabel namePanel() {
        JLabel nameLabel = new JLabel("Name : " + this.user.getName());
        nameLabel.setHorizontalAlignment(JLabel.LEFT);
        nameLabel.setPreferredSize(new Dimension(230, 30));

        return nameLabel;
    }

    private JLabel phonePanel() {
        JLabel phoneLabel = new JLabel("Phone : " + this.user.getPhone());
        phoneLabel.setHorizontalAlignment(JLabel.LEFT);
        phoneLabel.setPreferredSize(new Dimension(175, 30));

        return phoneLabel;
    }

    private JLabel emailPanel() {
        JLabel emailLabel = new JLabel("Email : " + this.user.getEmail());
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

        Button.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this user?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                deleteUser();
                manageUser.resetPage();
            }
        });

        area.add(Button);
        area.setBackground(null);
        area.setPreferredSize(new Dimension(100, 30));
        return area;
    }

    private void Edit() {
        String[][] data = pullData();
        String[][] dataAll = new AuthService().getAllUserData("user");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/backend/data/NowUser.txt"))) {
            for (String[] recode : data) {
                if (recode[0].equals(this.user.getID())) {
                    writer.write(this.user.getID() + "," + this.user.getName() + "," + this.user.getPhone() + "," + this.user.getEmail() + "," + this.user.getPassword()
                            + "\n");
                } else {
                    writer.write(
                            recode[0] + "," + recode[1] + "," + recode[2] + "," + recode[3] + "," + recode[4] + "\n");
                }
            }
            JOptionPane.showMessageDialog(mainFrame, "Successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error can't edit");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/backend/data/UserData.txt"))) {
            for (String[] recode : dataAll) {
                if (recode[0].equals(this.user.getID())) {
                    writer.write(this.user.getID() + "," + this.user.getName() + "," + this.user.getPhone() + "," + this.user.getEmail() + "," + this.user.getPassword()
                            + "\n");
                } else {
                    writer.write(
                            recode[0] + "," + recode[1] + "," + recode[2] + "," + recode[3] + "," + recode[4] + "\n");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error can't edit");
        }

    }

    private JDialog editDialog() {
        JDialog area = new JDialog(mainFrame, "Edit", true);
        area.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel areaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel nameLabel = new JLabel("Name : ");
        nameLabel.setPreferredSize(new Dimension(300, 30));
        nameLabel.setHorizontalAlignment(JLabel.LEFT);
        JTextField nameField = new JTextField(this.user.getName());
        nameField.setPreferredSize(new Dimension(300, 30));
        JLabel phoneLabel = new JLabel("Phone : ");
        phoneLabel.setPreferredSize(new Dimension(300, 30));
        phoneLabel.setHorizontalAlignment(JLabel.LEFT);
        JTextField phoneField = new JTextField(this.user.getPhone());
        phoneField.setPreferredSize(new Dimension(300, 30));
        JLabel emailLabel = new JLabel("Email : ");
        emailLabel.setPreferredSize(new Dimension(300, 30));
        emailLabel.setHorizontalAlignment(JLabel.LEFT);
        JTextField emailField = new JTextField(this.user.getEmail());
        emailField.setPreferredSize(new Dimension(300, 30));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        btnPanel.setPreferredSize(new Dimension(300, 120));
        JButton btn = new JButton("Submit");
        btn.setPreferredSize(new Dimension(100, 40));
        float[] hsbValues = Color.RGBtoHSB(3, 153, 254, null);
        btn.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
        btn.setForeground(Color.WHITE);
        btn.addActionListener(e -> {
            this.user.setName(nameField.getText());
            this.user.setPhone(phoneField.getText());
            this.user.setEmail(emailField.getText());
            Edit();
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
        areaPanel.setPreferredSize(new Dimension(400, 400));
        area.setSize(new Dimension(400, 400));
        area.setLocationRelativeTo(mainFrame);
        area.setVisible(true);
        return area;
    }

    private void deleteUser() {
        String[][] data = pullData();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/backend/data/NowUser.txt"))) {
            for (String[] recode : data) {
                if (!recode[0].equals(this.user.getID())) {
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