package client.components;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import backend.models.Users.Employee;
import client.MainFrame;
import resources.SetPreferences;

import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageEmp extends JPanel{

    private String fileEmployee = "./src/backend/data/NowEmployee.txt";
    private String fileAllEmployee = "./src/backend/data/EmployeeData.txt";
    private JButton btnNewEmployee, edit, delete;
    private JPanel btnNewEmp = newEmployee();
    private JPanel listEmp;
    private JPanel chooseEmpNMana = chooseMandE();
    private JScrollPane scrollPane;
    private JDialog diaEditEmp;
    private JComboBox<String> newPermission;
    private JTextField tfName, tfPhone, tfEmail, newName, newPhone, newEmail, newPassword, newConfirmPassword;
    private JTextField tfSearch;
    private JButton saveButton, newBtnConfirm;
    private String updatedName, updatedPhone, updatedEmail;
    private MainFrame mainFrame;
    private int width = 0;
    private Employee employee = new Employee("", "", "", "", "");

    // Font myFont = new Font("Arial", Font.BOLD, 15);

    public ManageEmp(MainFrame mainFrame, int width) {
        this.mainFrame = mainFrame;
        this.width = width;
        listEmp = listEmployee("All");
        CreateGui();
    }

    private void refreshEmployeeList() {
        removeAll();

        listEmp = listEmployee("All");

        add(btnNewEmp, BorderLayout.NORTH);
        add(listEmp, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void refreshEmployeeList(JPanel listEmps) {
        removeAll();

        add(btnNewEmp, BorderLayout.NORTH);
        add(listEmps, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void CreateGui() {
        setPreferredSize(new Dimension(width, 600));
        setLayout(new BorderLayout());
        add(btnNewEmp, BorderLayout.NORTH);
        add(listEmp, BorderLayout.CENTER);
    }

    private JPanel newEmployee() {
        this.chooseEmpNMana = chooseMandE();

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        panel.setPreferredSize(new Dimension(this.width, 105));
        btnNewEmployee = new JButton("New Employee");
        btnNewEmployee.addActionListener(e -> {
            createNewEmp();
        });
        float[] hsbValues = Color.RGBtoHSB(3, 153, 254, null);
        btnNewEmployee.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
        btnNewEmployee.setForeground(Color.WHITE);
        tfSearch = new JTextField(10);
        tfSearch.setFont(new SetPreferences().getFont(14));
        JButton btnSearch = new JButton("Search");
        btnSearch.setBackground(Color.WHITE);
        btnSearch.setFont(new SetPreferences().getFont(10));
        btnSearch.addActionListener(e -> {
            searchEmployee(tfSearch.getText().trim());
        });

        btnPanel.add(btnNewEmployee);
        btnPanel.setPreferredSize(new Dimension(300, 40));

        searchPanel.add(tfSearch);
        searchPanel.add(btnSearch);
        searchPanel.setPreferredSize(new Dimension(550, 40));

        filterPanel.add(this.chooseEmpNMana);
        filterPanel.setPreferredSize(new Dimension(1500, 100));

        panel.add(searchPanel);
        panel.add(btnPanel);
        panel.add(filterPanel);
        return panel;
    }

    private JPanel chooseMandE() {
        JPanel panel = new JPanel(new FlowLayout());
        JButton btnAll = new JButton("All");
        btnAll.setBackground(Color.WHITE);
        btnAll.setPreferredSize(new Dimension(200, 35));
        btnAll.setFont(new SetPreferences().getFont(15));
        JButton btnManager = new JButton("Manager");
        btnManager.setBackground(Color.WHITE);
        btnManager.setPreferredSize(new Dimension(200, 35));
        btnManager.setFont(new SetPreferences().getFont(15));
        JButton btnEmployee = new JButton("Employee");
        btnEmployee.setBackground(Color.WHITE);
        btnEmployee.setPreferredSize(new Dimension(200, 35));
        btnEmployee.setFont(new SetPreferences().getFont(15));

        btnAll.addActionListener(e -> {
            remove(listEmp);
            listEmp = listEmployee("All");
            add(listEmp, BorderLayout.CENTER);
            revalidate();
            repaint();
        });
        btnManager.addActionListener(e -> {
            remove(listEmp);
            listEmp = listEmployee("Manager");
            add(listEmp, BorderLayout.CENTER);
            revalidate();
            repaint();
        });

        btnEmployee.addActionListener(e -> {
            remove(listEmp);
            listEmp = listEmployee("Employee");
            add(listEmp, BorderLayout.CENTER);
            revalidate();
            repaint();
        });
        panel.add(btnAll);
        panel.add(btnManager);
        panel.add(btnEmployee);

        return panel;
    }

    private JDialog createNewEmp() {
        Font labelFont = new SetPreferences().getFont(14);
        JDialog diaNewEmp = new JDialog(mainFrame, "New Employee", true);
        diaNewEmp.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        diaNewEmp.setSize(350, 550);
        diaNewEmp.setResizable(false);
        diaNewEmp.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon(getClass().getResource("/backend/data/images/logo.png"));
        diaNewEmp.setIconImage(icon.getImage());

        JPanel nameArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel nameLabel = new JLabel("Name :");
        nameLabel.setFont(labelFont);
        nameLabel.setPreferredSize(new Dimension(300, 30));
        nameLabel.setHorizontalAlignment(JLabel.LEFT);
        newName = new JTextField();
        newName.setPreferredSize(new Dimension(300, 25));
        nameArea.add(nameLabel);
        nameArea.add(newName);
        nameArea.setPreferredSize(new Dimension(330, 65));

        JPanel phoneArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel phoneLabel = new JLabel("Phone :");
        phoneLabel.setFont(labelFont);
        phoneLabel.setPreferredSize(new Dimension(300, 30));
        phoneLabel.setHorizontalAlignment(JLabel.LEFT);
        newPhone = new JTextField();
        newPhone.setPreferredSize(new Dimension(300, 25));
        phoneArea.add(phoneLabel);
        phoneArea.add(newPhone);
        phoneArea.setPreferredSize(new Dimension(330, 65));

        JPanel emailArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel emailLabel = new JLabel("Email :");
        emailLabel.setFont(labelFont);
        emailLabel.setPreferredSize(new Dimension(300, 30));
        emailLabel.setHorizontalAlignment(JLabel.LEFT);
        newEmail = new JTextField();
        newEmail.setPreferredSize(new Dimension(300, 25));
        emailArea.add(emailLabel);
        emailArea.add(newEmail);
        emailArea.setPreferredSize(new Dimension(330, 65));

        JPanel permissionArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel permissionLabel = new JLabel("Permission :");
        permissionLabel.setFont(labelFont);
        permissionLabel.setPreferredSize(new Dimension(300, 30));
        permissionLabel.setHorizontalAlignment(JLabel.LEFT);
        newPermission = new JComboBox<>();
        newPermission.addItem("Employee");
        newPermission.addItem("Manager");
        newPermission.setPreferredSize(new Dimension(300, 25));
        permissionArea.add(permissionLabel);
        permissionArea.add(newPermission);
        permissionArea.setPreferredSize(new Dimension(330, 65));

        JPanel passwordArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel passLabel = new JLabel("Password :");
        passLabel.setFont(labelFont);
        passLabel.setPreferredSize(new Dimension(300, 30));
        passLabel.setHorizontalAlignment(JLabel.LEFT);
        newPassword = new JTextField();
        newPassword.setPreferredSize(new Dimension(300, 25));
        passwordArea.add(passLabel);
        passwordArea.add(newPassword);
        passwordArea.setPreferredSize(new Dimension(330, 70));

        JPanel confirmPasswordArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel confirmPassLabel = new JLabel("Confirm Password :");
        confirmPassLabel.setFont(labelFont);
        confirmPassLabel.setPreferredSize(new Dimension(300, 30));
        confirmPassLabel.setHorizontalAlignment(JLabel.LEFT);
        newConfirmPassword = new JTextField();
        newConfirmPassword.setPreferredSize(new Dimension(300, 25));
        confirmPasswordArea.add(confirmPassLabel);
        confirmPasswordArea.add(newConfirmPassword);
        confirmPasswordArea.setPreferredSize(new Dimension(330, 70));

        JPanel BtnArea = new JPanel();
        newBtnConfirm = new JButton("Confirm");
        newBtnConfirm.setFont(labelFont);
        float[] hsbValues = Color.RGBtoHSB(3, 153, 254, null);
        newBtnConfirm.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
        BtnArea.add(newBtnConfirm);
        newBtnConfirm.setForeground(Color.WHITE);
        newBtnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!newName.getText().isEmpty()
                        && !newPhone.getText().isEmpty()
                        && !newEmail.getText().isEmpty()
                        && !newPassword.getText().isEmpty()) {
                    if (newPassword.getText().equals(newConfirmPassword.getText())) {
                        employee.setName(newName.getText());
                        employee.setPhone(newPhone.getText());
                        employee.setEmail(newEmail.getText());
                        employee.setPermission((String) newPermission.getSelectedItem());
                        employee.setPassword(newPassword.getText());
                        saveNewEmployee();
                        diaNewEmp.dispose();

                        refreshEmployeeList();
                    } else {
                        JOptionPane.showMessageDialog(diaNewEmp, "Passwords do not match.", "Error",
                                JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(diaNewEmp, "Please fill it in completely.", "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        diaNewEmp.add(nameArea);
        diaNewEmp.add(phoneArea);
        diaNewEmp.add(emailArea);
        diaNewEmp.add(permissionArea);
        diaNewEmp.add(passwordArea);
        diaNewEmp.add(confirmPasswordArea);
        diaNewEmp.add(newBtnConfirm);
        diaNewEmp.setLocation(700, 200);
        diaNewEmp.setVisible(true);

        return diaNewEmp;
    }

    private void saveNewEmployee() {
        employee.setId(generateNewEmployeeId());
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileEmployee, true));
            String newEmployeeData = employee.getID() + "," + employee.getName() + "," + employee.getPhone() + ","
                    + employee.getEmail() + "," + employee.getPassword() + "," + employee.getPermission();
            writer.append(newEmployeeData);
            writer.newLine();
            BufferedWriter writer2 = new BufferedWriter(new FileWriter(fileAllEmployee, true));
            writer2.append(newEmployeeData);
            writer.newLine();
            writer.close();
            writer2.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error saving new employee", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateNewEmployeeId() {
        int newId = 1;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileAllEmployee));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int currentId = Integer.parseInt(data[0]);
                newId = Math.max(newId, currentId + 1);
            }
            reader.close();
        } catch (IOException e) {

        }
        return String.valueOf(newId);
    }

    private void openEditEmp(Employee employeeThis) {
        diaEditEmp = new JDialog(mainFrame, "Edit Employee", true);
        diaEditEmp.setLayout(new FlowLayout(FlowLayout.CENTER));
        diaEditEmp.setSize(400, 450);
        diaEditEmp.setResizable(false);
        diaEditEmp.setLocationRelativeTo(null);

        JPanel nameArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
        nameArea.setPreferredSize(new Dimension(360, 70));
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setPreferredSize(new Dimension(360, 30));
        nameLabel.setHorizontalAlignment(JLabel.LEFT);
        tfName = new JTextField(employeeThis.getName());
        tfName.setPreferredSize(new Dimension(360, 30));
        nameArea.add(nameLabel);
        nameArea.add(tfName);

        JPanel phoneArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
        phoneArea.setPreferredSize(new Dimension(360, 70));
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setPreferredSize(new Dimension(360, 30));
        phoneLabel.setHorizontalAlignment(JLabel.LEFT);
        tfPhone = new JTextField(employeeThis.getPhone());
        tfPhone.setPreferredSize(new Dimension(360, 30));
        phoneArea.add(phoneLabel);
        phoneArea.add(tfPhone);

        JPanel emailArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
        emailArea.setPreferredSize(new Dimension(360, 70));
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setPreferredSize(new Dimension(360, 30));
        emailLabel.setHorizontalAlignment(JLabel.LEFT);
        tfEmail = new JTextField(employeeThis.getEmail());
        tfEmail.setPreferredSize(new Dimension(360, 30));
        emailArea.add(emailLabel);
        emailArea.add(tfEmail);

        JPanel permissionArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
        permissionArea.setPreferredSize(new Dimension(360, 70));
        JLabel permissionLabel = new JLabel("Permission:");
        permissionLabel.setPreferredSize(new Dimension(360, 30));
        permissionLabel.setHorizontalAlignment(JLabel.LEFT);
        JComboBox<String> permissionComboBox = new JComboBox<>();
        permissionComboBox.addItem("Employee");
        permissionComboBox.addItem("Manager");
        permissionComboBox.setSelectedItem(employeeThis.getPermission());
        permissionComboBox.setPreferredSize(new Dimension(360, 30));
        permissionArea.add(permissionLabel);
        permissionArea.add(permissionComboBox);

        JPanel BtnArea = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        saveButton = new JButton("Save");
        float[] hsbValues = Color.RGBtoHSB(3, 153, 254, null);
        saveButton.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
        saveButton.setForeground(Color.WHITE);
        saveButton.setPreferredSize(new Dimension(100, 30));
        BtnArea.setPreferredSize(new Dimension(360, 100));

        saveButton.addActionListener(e -> {
            updatedName = tfName.getText().trim();
            updatedPhone = tfPhone.getText().trim();
            updatedEmail = tfEmail.getText().trim();
            String updatedPermission = (String) permissionComboBox.getSelectedItem();
            
            if (updatedName.isEmpty() || updatedPhone.isEmpty() || updatedEmail.isEmpty()) {
                JOptionPane.showMessageDialog(diaEditEmp, "All fields are required.", "Error",
                JOptionPane.WARNING_MESSAGE);
                return;
            }
            employeeThis.setName(updatedName);
            employeeThis.setPhone(updatedPhone);
            employeeThis.setEmail(updatedEmail);
            employeeThis.setPermission(updatedPermission);

            updateEmployeeData(employeeThis);
            diaEditEmp.dispose();
            refreshEmployeeList();
        });
        BtnArea.add(saveButton);

        // Add all components to the dialog
        diaEditEmp.add(nameArea);
        diaEditEmp.add(phoneArea);
        diaEditEmp.add(emailArea);
        diaEditEmp.add(permissionArea);
        diaEditEmp.add(BtnArea);

        diaEditEmp.setVisible(true);
    }

    private void updateEmployeeData(Employee employeeThis) {
        // Update fileEmployee
        try (BufferedReader reader = new BufferedReader(new FileReader(fileEmployee))) {
            StringBuilder newFileContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(employeeThis.getID())) {
                    // Update the employee data
                    line = employeeThis.getID() + "," + employeeThis.getName() + "," + employeeThis.getPhone() + "," + employeeThis.getEmail() + "," + data[4] + "," + employeeThis.getPermission();
                }
                newFileContent.append(line).append("\n");
            }

            // Write the updated content back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileEmployee))) {
                writer.write(newFileContent.toString());
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error updating employee data in NowEmployee.txt", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update fileAllEmployee if employee exists there
        try (BufferedReader reader = new BufferedReader(new FileReader(fileAllEmployee))) {
            StringBuilder newFileContent = new StringBuilder();
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(employeeThis.getID())) {
                    // Update the employee data
                    line = employeeThis.getID() + "," + employeeThis.getName() + "," + employeeThis.getPhone() + "," + employeeThis.getEmail() + "," + data[4] + "," + employeeThis.getPermission();
                    found = true;   
                }
                newFileContent.append(line).append("\n");
            }

            // Only write if employee was found
            if (found) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileAllEmployee))) {
                    writer.write(newFileContent.toString());
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error updating employee data in EmployeeData.txt", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Update fileAllEmployee if employee exists there
        try (BufferedReader reader = new BufferedReader(new FileReader("./src/backend/data/Token.txt"))) {
            StringBuilder newFileContent = new StringBuilder();
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(employeeThis.getID())) {
                    // Update the employee data
                    line = employeeThis.getID() + "," + employeeThis.getName() + "," + employeeThis.getPhone() + "," + employeeThis.getEmail() + "," + data[4] + "," + employeeThis.getPermission();
                    found = true;
                }
                newFileContent.append(line).append("\n");
            }

            // Only write if employee was found
            if (found) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/backend/data/Token.txt"))) {
                    writer.write(newFileContent.toString());
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error updating employee data in EmployeeData.txt", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void edit(Employee employeeThis) {

            try (BufferedReader reader = new BufferedReader(new FileReader(fileEmployee))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data[0].equals(employeeThis.getID())) {
                        openEditEmp(employeeThis);
                        break;
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error reading employee data", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    private void deleteEmployee(Employee employeeThis) {
        int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this user?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileEmployee));
            StringBuilder newFileContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] s = line.split(",");
                if (!s[0].equals(employeeThis.getID())) {
                    newFileContent.append(line).append("\n");
                }
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(fileEmployee));
            writer.write(newFileContent.toString());
            writer.close();
            refreshEmployeeList();

            JOptionPane.showMessageDialog(this, "Employee deleted successfully.");
        } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error deleting employee", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JPanel listEmployee(String type) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setPreferredSize(new Dimension(width, 700));

        JPanel listContainer = new JPanel();
        listContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        List<JPanel> empPanels = readEmployeePanels(fileEmployee, type);
        int c = 0;
        for (JPanel empPanel : empPanels) {
            empPanel.setFont(new SetPreferences().getFont(12));
            listContainer.add(empPanel);
            c++;
        }
        listContainer.setPreferredSize(new Dimension(width - 20, c * 55));

        scrollPane = new JScrollPane(listContainer);
        scrollPane.setPreferredSize(new Dimension(width, 700));
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(scrollPane);

        return panel;
    }

    private JPanel showEmp(String id, String name, String phone, String email, String password, String permission) {
        Employee employeeThis = new Employee(id, name, phone, email, password, permission);
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(new LineBorder(Color.GRAY, 1));
        panel.setBackground(Color.WHITE);

        edit = new JButton("Edit");
        delete = new JButton("Delete");
        edit.setFont(new SetPreferences().getFont(12));
        delete.setFont(new SetPreferences().getFont(12));
        float[] hsbValues = Color.RGBtoHSB(3, 153, 254, null);
        edit.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
        delete.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
        edit.setForeground(Color.WHITE);
        delete.setForeground(Color.WHITE);

        edit.addActionListener(e -> {
            edit(employeeThis);
        });

        delete.addActionListener(e -> {
            deleteEmployee(employeeThis);
        });

        JLabel idLabel = new JLabel("ID : " + id);
        JLabel nameLabel = new JLabel("Name : " + name);
        JLabel phoneLabel = new JLabel("Phone : " + phone);
        JLabel emailLabel = new JLabel("Email : " + email);
        JLabel permissionLabel = new JLabel("Permission : " + permission);

        idLabel.setFont(new SetPreferences().getFont(12));
        nameLabel.setFont(new SetPreferences().getFont(12));
        phoneLabel.setFont(new SetPreferences().getFont(12));
        emailLabel.setFont(new SetPreferences().getFont(12));
        permissionLabel.setFont(new SetPreferences().getFont(12));

        idLabel.setPreferredSize(new Dimension(50, 30));
        nameLabel.setPreferredSize(new Dimension(180, 30));
        phoneLabel.setPreferredSize(new Dimension(150, 30));
        emailLabel.setPreferredSize(new Dimension(270, 30));
        permissionLabel.setPreferredSize(new Dimension(150, 30));

        panel.add(idLabel);
        panel.add(nameLabel);
        panel.add(phoneLabel);
        panel.add(emailLabel);
        panel.add(permissionLabel);
        panel.add(edit);
        panel.add(delete);
        panel.setPreferredSize(new Dimension(1000, 40));
        return panel;
    }

    private List<JPanel> readEmployeePanels(String fileEmployee, String type) {
        List<JPanel> empPanels = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileEmployee));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6) {
                    String id = data[0];
                    String name = data[1];
                    String phone = data[2];
                    String email = data[3];
                    String password = data[4];
                    String permission = data[5];

                    if (type.equals("All")) {
                        JPanel empPanel = showEmp(id, name, phone, email, password, permission);
                        empPanels.add(empPanel);
                    } else if (permission.equalsIgnoreCase(type)) {
                        JPanel empPanel = showEmp(id, name, phone, email, password, permission);
                        empPanels.add(empPanel);
                    } else if (id.contains(type) || name.toLowerCase().contains(type.toLowerCase())) {
                        JPanel empPanel = showEmp(id, name, phone, email, password, permission);
                        empPanels.add(empPanel);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Cannot read file", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        return empPanels;
    }

    private void searchEmployee(String searchText) {
        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name or ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        remove(listEmp);

        List<JPanel> empPanels = readEmployeePanels(fileEmployee, searchText);
        if (empPanels.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No results found", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            listEmp = new JPanel();
            for (JPanel panel : empPanels) {
                listEmp.add(panel);
            }
        }
        refreshEmployeeList(listEmp);
    }

}
