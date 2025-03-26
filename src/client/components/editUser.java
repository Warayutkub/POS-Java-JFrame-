package client.components;

import javax.swing.*;
import backend.models.Users.User;
import backend.services.AuthService;
import client.components.UserPanels.UserPanel;
import resources.Tools;

import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class editUser extends JFrame implements ActionListener {
    User u = new User(getName(), getTitle(), getWarningString(), getName());
    JLabel regis, name, email, password, confirmps, phone;
    JTextField tf1, tf2, tf3, tf4;
    JButton bt1, bt2;
    JPasswordField ps1, ps2;
    String id1;
    UserPanels up;

    public editUser(String id1,UserPanels up) {
        this.id1 = id1;
        this.up = up;
        setSize(500, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("New User");
        regis = new JLabel("New User");
        regis.setFont(new Font("Senif", Font.BOLD, 20));
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
        bt1 = new JButton("Confirm");
        bt1.addActionListener(this);
        regis.setBounds(180, 30, 400, 30);
        name.setBounds(80, 80, 200, 30);
        email.setBounds(80, 130, 200, 30);
        phone.setBounds(80, 180, 200, 30);
        password.setBounds(80, 230, 200, 30);
        confirmps.setBounds(80, 280, 200, 30);
        tf1.setBounds(200, 80, 200, 30);
        tf2.setBounds(200, 130, 200, 30);
        tf3.setBounds(200, 180, 200, 30);
        ps1.setBounds(200, 230, 200, 30);
        ps2.setBounds(200, 280, 200, 30);
        bt1.setBounds(180, 340, 120, 30);
        add(regis);
        add(name);
        add(tf1);
        add(email);
        add(tf2);
        add(phone);
        add(tf3);
        add(password);
        add(ps1);
        add(confirmps);
        add(ps2);
        add(bt1);
        setResizable(false);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt1) {
            saveLogin(id1);
        }
    }

    public void saveLogin(String id1) {
        String[][] pl = pullData();
        char[] s3 = ps1.getPassword();
        char[] s4 = ps2.getPassword();
        String s6 = new String(s3);
        String s7 = new String(s4);
        for (int i = 0; i < pl.length; i++) {
            if (pl[i][0].equals(id1)) {
                pl[i][1] = tf1.getText();
                pl[i][2] = tf2.getText();
                pl[i][3] = tf3.getText();
                break;
            }
        }

        if (s6.equals(s7)) {
            try {
                FileWriter out = new FileWriter("./src/backend/data/UserData.txt");
                BufferedWriter writer = new BufferedWriter(out);
                for (int a = 0; a < pl.length; a++) {
                    writer.write(pl[a][0] + "," + pl[a][1] + "," + pl[a][2] + "," + pl[a][3] + "" + pl[a][4] + "" + pl[a][5] + "\n");
                }
                JOptionPane.showMessageDialog(this, "Save new User.");
                up.resetPage();
                writer.close();

            } catch (IOException ex) {
                System.out.println(ex);
            }
            setVisible(false);
        } else {
            JOptionPane.showMessageDialog(bt1, "Password Does Not Match.");
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

}