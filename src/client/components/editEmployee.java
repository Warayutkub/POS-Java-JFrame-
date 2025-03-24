package client.components;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import java.awt.Insets;
import java.awt.Label;
import javax.swing.JTextArea;
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
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class editEmployee extends JPanel implements ActionListener {

    private String fileEmployee = "src\\backend\\data\\EmployeeData.txt";
    private JButton btnNewEmployee, edit, delete;
    private JPanel btnNewEmp = newEmployee();
    private JPanel listEmp = listEmployee();
    private JScrollPane scrollPane;
    private JDialog diaEditEmp;
    private JComboBox<String> newPermission;
    private JTextField tfName, tfPhone, tfEmail, newName, newPhone, newEmail, newPassword, newConfirmPassword;
    private JButton saveButton, newBtnConfirm;
    private String updatedName, updatedPhone, updatedEmail;
   
    //Font myFont = new Font("Arial", Font.BOLD, 15);
    Font myFont2 = new Font("Arial", Font.PLAIN, 12);;


    public editEmployee(){
        CreateGui();
    }

    private void refreshEmployeeList() {
        remove(listEmp);
        remove(btnNewEmp);
        listEmp = listEmployee(); 
        btnNewEmp = newEmployee();

        add(btnNewEmp, BorderLayout.NORTH);
        add(listEmp, BorderLayout.CENTER);
        
        revalidate();
        repaint();
    }
    private void CreateGui() {
        setPreferredSize(new Dimension(860,600));
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        add(btnNewEmp, BorderLayout.NORTH);
        add(listEmp, BorderLayout.CENTER);
    }

    private JPanel newEmployee(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnNewEmployee = new JButton("New Employee");
        btnNewEmployee.addActionListener(this);
        panel.add(btnNewEmployee);
        return panel;
    }
    private JDialog createNewEmp(){
        JDialog diaNewEmp = new JDialog((JFrame) null, "New Employee", true);
        diaNewEmp.setLayout(new GridLayout(7, 2));
        diaNewEmp.setSize(500, 400);
        diaNewEmp.add(new Label("Name :"));
        newName = new JTextField();
        diaNewEmp.add(newName);
        
        diaNewEmp.add(new Label("Phone :"));
        newPhone = new JTextField();
        diaNewEmp.add(newPhone);
        
        diaNewEmp.add(new Label("Email :"));
        newEmail = new JTextField();
        diaNewEmp.add(newEmail);

        diaNewEmp.add(new Label("Permission :"));
        newPermission = new JComboBox<String>();
        newPermission.addItem("Employee");
        newPermission.addItem("Manager");
        diaNewEmp.add(newPermission);

        diaNewEmp.add(new Label("Password :"));
        newPassword = new JTextField();
        diaNewEmp.add(newPassword);

        diaNewEmp.add(new Label("Confirm Password :"));
        newConfirmPassword = new JTextField();
        diaNewEmp.add(newConfirmPassword);

        diaNewEmp.add(new Label());
        newBtnConfirm = new JButton("Confirm");
        newBtnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!newName.getText().isEmpty() 
                    && !newPhone.getText().isEmpty() 
                    && !newEmail.getText().isEmpty() 
                    && !newPassword.getText().isEmpty()) {
                    if (newPassword.getText().equals(newConfirmPassword.getText())) {
                        String name = newName.getText();
                        String phone = newPhone.getText();
                        String email = newEmail.getText();
                        String permission = (String) newPermission.getSelectedItem();
                        String password = newPassword.getText();
            
                        saveNewEmployee(name, phone, email, permission, password);
            
                        diaNewEmp.dispose();
                        refreshEmployeeList();
                    } else {
                        JOptionPane.showMessageDialog(diaNewEmp, "Passwords do not match.", "Error", JOptionPane.WARNING_MESSAGE);
                }} else{
                    JOptionPane.showMessageDialog(diaNewEmp, "Please fill it in completely.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        diaNewEmp.add(newBtnConfirm);
        diaNewEmp.setLocation(700, 200);
        diaNewEmp.setVisible(true);

        return diaNewEmp;
    }

    private void saveNewEmployee(String name, String phone, String email, String permission, String password) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileEmployee, true));
            String newEmployeeData = generateNewEmployeeId() + "," + name + "," + phone + "," + email + "," + password + "," + permission;
            writer.append(newEmployeeData);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving new employee", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateNewEmployeeId() {
        int newId = 1;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileEmployee));
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

   private JPanel listEmployee(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(700, 500));
        panel.setBorder(new LineBorder(Color.PINK, 5));

        JPanel listContainer = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        listContainer.setLayout(layout);
        listContainer.setBackground(Color.WHITE);

        List<JPanel> empPanels = readEmployeePanels(fileEmployee);
        for (int i = 0; i < empPanels.size(); i++) {
            JPanel empPanel = empPanels.get(i);
            empPanel.setFont(myFont2);  

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;  
            gbc.gridy = i;  
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;
          
            listContainer.add(empPanel, gbc);  
        }

        scrollPane = new JScrollPane(listContainer);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    private void openEditEmp(String id, String name, String phone, String email) {
       
        diaEditEmp = new JDialog((JFrame) null, "Edit Employee", true);
        diaEditEmp.setLayout(new GridLayout(5, 2)); 
        diaEditEmp.setSize(400, 250);

        diaEditEmp.add(new JLabel("Name :"));
        tfName = new JTextField(name);
        diaEditEmp.add(tfName);

        diaEditEmp.add(new JLabel("Phone :"));
        tfPhone = new JTextField(phone);
        diaEditEmp.add(tfPhone);

        diaEditEmp.add(new JLabel("Email :"));
        tfEmail = new JTextField(email);
        diaEditEmp.add(tfEmail);

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatedName = tfName.getText();
                updatedPhone = tfPhone.getText();
                updatedEmail = tfEmail.getText();

                updateEmployeeData(id, updatedName, updatedPhone, updatedEmail);
                diaEditEmp.dispose();
              
                refreshEmployeeList();
            }
        });
        diaEditEmp.add(saveButton);
        diaEditEmp.setLocation(850, 300);
        diaEditEmp.setVisible(true); 
    
        JOptionPane.showMessageDialog(this, "Edit Employee\nID: " + id + "\nName: " + updatedName + "\nPhone: " + updatedPhone + "\nEmail: " + updatedEmail);
    }
    private void updateEmployeeData(String id, String name, String phone, String email) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileEmployee));
            StringBuilder newFileContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] s = line.split(",");
                if (s[0].equals(id)) {
                    line = id + "," + name + "," + phone + "," + email + "," + s[4] + "," + s[5];
                }
                newFileContent.append(line).append("\n");
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(fileEmployee));
            writer.write(newFileContent.toString());
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error updating file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void edit(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        
        if(actionCommand.startsWith("Edit")) {
            // Id, Name, Phone, Email
            JPanel parentPanel = (JPanel) ((JButton) e.getSource()).getParent(); 
            String labelText = ((JLabel) parentPanel.getComponent(0)).getText(); 
            String[] details = labelText.split(","); 
            
            String id = details[0].split(":")[1].trim(); 
            String name = details[1].split(":")[1].trim(); 
            String phone = details[2].split(":")[1].trim(); 
            String email = details[3].split(":")[1].trim(); 
        
            openEditEmp(id, name, phone, email);
        }
    }
    
    private void deleteEmployee(String id) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileEmployee));
            StringBuilder newFileContent = new StringBuilder();
            String line;
    
            while ((line = reader.readLine()) != null) {
                String[] s = line.split(",");
                if (!s[0].equals(id)) {  
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
    
    private JPanel showEmp(String id, String name, String phone, String email, String password, String permission) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(new LineBorder(Color.BLACK));
        
        edit = new JButton("Edit");
        delete = new JButton("Delete");
        edit.setFont(myFont2);
        delete.setFont(myFont2);

        edit.setActionCommand("Edit-" + id);
        delete.setActionCommand("Delete-" + id);
        
        edit.addActionListener(this);
        delete.addActionListener(this);
        
        JLabel label = new JLabel("ID: " + id + ", Name: " + name + ", Phone: " + phone + ", Email: " + email + ", Permission: " + permission + "                     ");
        label.setFont(myFont2);
        
        panel.add(label);
        panel.add(edit);
        panel.add(delete);
        
        return panel;
    }
    
    private List<JPanel> readEmployeePanels(String fileEmployee) {
        List<JPanel> empPanels = new ArrayList<>();
        try {
            FileReader in = new FileReader(fileEmployee);
            BufferedReader reader = new BufferedReader(in);
            String s1;
            while ((s1 = reader.readLine()) != null) {
                String[] s = s1.split(",");
                if (s.length >= 6) {
                    String id = s[0];
                    String name = s[1];
                    String phone = s[2];
                    String email = s[3];
                    String password = s[4];
                    String permission = s[5];
                    // String tackSaleDay = s[6];
                    // String trackSalesTotal = s[7];

                    JPanel empPanel = showEmp(id, name, phone, email, password, permission);
                    empPanel.setFont(myFont2); 
                    empPanels.add(empPanel);
                }
            }
            reader.close();
            in.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Cannot read file", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        return empPanels;

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        
        if(actionCommand.startsWith("Edit")) {
            String id = actionCommand.split("-")[1];
            edit(e);
        } else if(actionCommand.startsWith("Delete")) {
            String id = actionCommand.split("-")[1];
            deleteEmployee(id);
        } else if(actionCommand.equals("New Employee")){
            createNewEmp();
        }
    }
}
