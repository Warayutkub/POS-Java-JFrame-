package client.components;

import javax.swing.*;
import backend.services.AuthService;

import java.io.*;
import java.util.Map;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;

public class Login extends JFrame implements ActionListener {
    JLabel login, phone, password;
    JTextField phone1;
    JButton btn1, btn2, btn3;
    JPasswordField ps1;
    Container c = getContentPane();

    public Login() {
        c.setBackground(Color.WHITE);
        setSize(500, 500);
        setTitle("Login");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login = new JLabel("Login");
        login.setFont(new Font("Senif", Font.BOLD, 20));
        phone = new JLabel("Enter Phone Number : ");
        password = new JLabel("Enter Password : ");
        phone1 = new JTextField();
        ps1 = new JPasswordField();
        btn1 = new JButton("Login");

        btn2 = new JButton("Register For User");
        btn2.setBackground(Color.WHITE);
        btn2.setBorder(null);
        btn2.addActionListener(ev -> {
            new Register().setVisible(true);
            setVisible(false);
        });
        Font plainFont = btn2.getFont();
        Font underlineFont = new Font(plainFont.getName(), plainFont.getStyle(), plainFont.getSize());
        
        @SuppressWarnings("unchecked")
        Map<TextAttribute, Object> attributes = (Map<TextAttribute, Object>) underlineFont.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        Font fontWithUnderline = new Font(attributes);

        btn2.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn2.setFont(fontWithUnderline);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btn2.setFont(plainFont);
            }
        });
        
        btn3 = new JButton("Register For Employee");
        btn3.setBackground(Color.WHITE);
        btn3.setBorder(null);
        btn3.addActionListener(ev ->{
            new Register_Employee().setVisible(true);
            setVisible(false);
        });
        btn3.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn3.setFont(fontWithUnderline);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn3.setFont(plainFont);
            }
        });
        login.setBounds(195, 50, 400, 30);
        phone.setBounds(80, 150, 200, 30);
        password.setBounds(80, 200, 200, 30);
        phone1.setBounds(200, 150, 200, 30);
        ps1.setBounds(200, 200, 200, 30);
        btn1.setBounds(190, 300, 100, 30);
        btn2.setBounds(60, 370, 170, 30);
        btn3.setBounds(250, 370, 170, 30);
        add(login);
        add(phone);
        add(phone1);
        add(password);
        add(ps1);
        add(btn1);
        add(btn2);
        add(btn3);
        btn1.addActionListener(this);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn1) {
            Logins();
        }
    }

    public void Logins(){
        String[] person = null;
        String password;
        String[][] s = new AuthService().getAllUserData("user");
        String[][] g = new AuthService().getAllUserData("emp");
        Boolean d = new AuthService().CheckDataUser(phone1.getText(),"user");
        Boolean f = new AuthService().CheckDataUser(phone1.getText(),"emp");

        if (d) {
            for(String[] i : s){
                for(String a : i){
                    if (phone1.getText().equals(a)) {
                        person = i;
                    } 
                }
            }
            password = person[person.length -1];
            if (password.equals(new String(ps1.getPassword()))) {
                try {
                    FileWriter fw = new FileWriter("./src/backend/data/Token.txt");
                    try (BufferedWriter wt = new BufferedWriter(fw)) {
                        wt.write(person[0]+ "," +person[1]+ "," +person[2]+ "," +person[3]+ "," +person[4]);
                    }
                    JOptionPane.showMessageDialog(this, "Login Complete");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Your Password Incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                    phone1.setText("");
                    ps1.setText("");
                }
            }
        } else if (f) {
            for(String[] ii : g){
                for(String aa : ii){
                    if (phone1.equals(aa)){
                        person = ii; break;
                    }
                }
            }
            password = person[person.length -1];
            if (password.equals(new String(ps1.getPassword()))) {
                try {
                    FileWriter fw = new FileWriter("./src/backend/data/Token.txt");
                    try(BufferedWriter wt = new BufferedWriter(fw)){
                        wt.write(person[0] + "," + person[1]+ "," +person[2]+ "," +person[3]+ "," +person[4]);
                        JOptionPane.showMessageDialog(this,"Login Complete");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,"Your Password Incorrect","Error",JOptionPane.ERROR_MESSAGE);
                    phone1.setText("");
                    ps1.setText("");
                }
            }
        }

    }

    public static void main(String[] args) {
        new Login();
    }

}
