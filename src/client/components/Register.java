package client.components;

import javax.swing.*;
import backend.models.Users.User;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JFrame implements ActionListener{
    User u = new User(getName(), getTitle(), getWarningString(), getName());
    JLabel regis , name , email , password , confirmps , phone;
    JTextField tf1 , tf2 , tf3 , tf4;
    JButton bt1 , bt2;
    JPasswordField ps1 ,ps2;


    
    public Register(){
        setSize(500,500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Register");
        regis = new JLabel("Register");
        regis.setFont(new Font("Senif" , Font.BOLD , 20));
        name = new JLabel("Name : ");
        email = new JLabel("E-Mail : ");
        password = new JLabel("Password : ");
        confirmps = new JLabel("Confirm Password : ");
        phone = new JLabel("Phone : ");
        tf1 = new JTextField();
        tf2 = new JTextField();
        ps1 = new JPasswordField();
        ps2 = new JPasswordField();
        tf3 = new JTextField();
        bt1 = new JButton("Register");
        bt2 = new JButton("Back to Login");
        bt1.addActionListener(this);
        bt2.addActionListener(this);
        regis.setBounds(150,30,400,30);
        name.setBounds(80,70,200,30);
        email.setBounds(80, 120, 200, 30);
        password.setBounds(80, 170, 200, 30);
        confirmps.setBounds(80, 220, 200, 30);
        phone.setBounds(80, 270, 200, 30);
        tf1.setBounds(200,70,200,30);
        tf2.setBounds(200, 120, 200, 30);
        ps1.setBounds(200,170, 200, 30);
        ps2.setBounds(200, 220, 200, 30);
        tf3.setBounds(200, 270, 200, 30);
        bt1.setBounds(100, 320, 120, 30);
        bt2.setBounds(200, 320, 120, 30);
        add(regis);
        add(name);
        add(tf1);
        add(email);
        add(tf2);
        add(password);
        add(ps1);
        add(confirmps);
        add(ps2);
        add(phone);
        add(tf3);
        add(bt1);
        add(bt2);
        setResizable(false);
        setVisible(true);

    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt1) {
            // int x = 0;
            // String s1 = tf1.getText();
            // String s2 = tf2.getText();
            char[] s3 = ps1.getPassword();
            char[] s4 = ps2.getPassword();
            String s6 = new String(s3);
            String s7 = new String(s4);
            // String s5 = tf3.getText();

            //เก็บข้อมูลฉันไม่รู้จะเอาไปเก็บไหน
            if (s6.equals(s7)){
                try {
                    JOptionPane.showMessageDialog(bt1, "Register Success");
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
            else{
                JOptionPane.showMessageDialog(bt1, "Password Does Not Match");
            }
        }
        else{
            tf1.setText("");
            tf2.setText("");
            ps1.setText("");
            ps2.setText("");
            tf3.setText("");
        }
    }
}
