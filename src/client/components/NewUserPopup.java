package client.components;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import backend.services.AuthService;
import client.MainFrame;
import resources.Tools;

public class NewUserPopup extends JFrame {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String password;
    private MainFrame mainFrame;
    private Cart cart;

    public NewUserPopup(MainFrame mainFrame, Cart cart) {
        this.id = new Tools().genNewId(new AuthService().getAllUserData("user"));
        this.mainFrame = mainFrame;
        this.cart = cart;
        CreateGUI();
        CreateWindow();
    }

    private void CreateWindow() {
        setTitle("Enter User Data");
        setSize(400, 450);
        setLocationRelativeTo(mainFrame);
        setResizable(false);
    }

    private void CreateGUI() {
        Container container = getContentPane();
        container.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));

        // Name
        JLabel labelName = new JLabel("Name : ");
        JTextField nameField = new JTextField();
        labelName.setPreferredSize(new Dimension(350, 30));
        nameField.setPreferredSize(new Dimension(350, 30));

        // Phone
        JLabel labelPhone = new JLabel("Phone : ");
        JTextField phoneFiled = new JTextField();
        labelPhone.setPreferredSize(new Dimension(350, 30));
        phoneFiled.setPreferredSize(new Dimension(350, 30));

        // Email
        JLabel labelEmail = new JLabel("Email : ");
        JTextField emailField = new JTextField();
        labelEmail.setPreferredSize(new Dimension(350, 30));
        emailField.setPreferredSize(new Dimension(350, 30));

        // Password
        JLabel labelPassword = new JLabel("Pass word");
        JPasswordField passwordField = new JPasswordField();
        labelPassword.setPreferredSize(new Dimension(350, 30));
        passwordField.setPreferredSize(new Dimension(350, 30));

        // Password Confirm
        JLabel labelConfirmPassword = new JLabel("Confirm Password");
        JPasswordField confirmPasswordField = new JPasswordField();
        labelConfirmPassword.setPreferredSize(new Dimension(350, 30));
        confirmPasswordField.setPreferredSize(new Dimension(350, 30));

        // Submit Button
        JButton SubmitBTn = new JButton("Register");
        SubmitBTn.setPreferredSize(new Dimension(150, 30));
        SubmitBTn.addActionListener(e -> {
            if (!nameField.getText().isEmpty() && !phoneFiled.getText().isEmpty() && !emailField.getText().isEmpty()
                    && passwordField.getPassword().length > 0) {
                //
                if (!new AuthService().CheckDataUser(phoneFiled.getText(), "user")) {

                    if (Arrays.equals(passwordField.getPassword(), confirmPasswordField.getPassword())) {
                        this.name = nameField.getText();
                        this.phone = phoneFiled.getText();
                        this.email = emailField.getText();
                        this.password = new String(passwordField.getPassword());

                        try (BufferedWriter writer = new BufferedWriter(
                                new FileWriter("./src/backend/data/UserData.txt", true))) {
                            writer.write(this.id + "," + this.name + "," + this.phone + "," + this.email + ","
                                    + this.password + "\n");
                            cart.SendStatusOfRegister(true);
                            JOptionPane.showMessageDialog(this, "Success!");
                            dispose();
    
                        } catch (IOException exception) {
                            JOptionPane.showMessageDialog(this, "File user can't open", "Error", 0);
                        }
                        
                    } else {
                        JOptionPane.showMessageDialog(this, "Password not match!", "Error", 0);

                    }

                } else {
                    JOptionPane.showMessageDialog(this, "This phone is already in use");
                }

            } else {
                JOptionPane.showMessageDialog(this, "Data is Empty!", "Error", 0);
            }

        });

        container.add(labelName);
        container.add(nameField);
        container.add(labelPhone);
        container.add(phoneFiled);
        container.add(labelEmail);
        container.add(emailField);
        container.add(labelPassword);
        container.add(passwordField);
        container.add(labelConfirmPassword);
        container.add(confirmPasswordField);
        container.add(SubmitBTn);
    }

    public String getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

}
