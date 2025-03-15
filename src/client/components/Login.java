import javax.swing.*;
import backend.models.Users.User;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Login extends JFrame implements ActionListener{
    JLabel login,email ,password;
    JTextField email1;
    JButton btn1;
    JPasswordField ps1;




    public Login(){
        setSize(500, 500);
        setTitle("Login");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login = new JLabel("Login");
        login.setFont(new Font("Senif" , Font.BOLD , 20));
        email = new JLabel("Enter Email : ");
        password = new JLabel("Enter Password : ");
        email1 = new JTextField();
        ps1 = new JPasswordField();
        btn1 = new JButton("Login");
        login.setBounds(200,50, 400, 30);
        email.setBounds(80, 150, 200, 30);
        password.setBounds(80,200, 200, 30);
        email1.setBounds(200, 150, 200, 30);
        ps1.setBounds(200, 200, 200, 30);
        btn1.setBounds(180, 350, 100, 30);
        add(login);
        add(email);
        add(email1);
        add(password);
        add(ps1);
        add(btn1);
        btn1.addActionListener(this);
        setResizable(false);
        setVisible(true);
    }
    
}
