import javax.swing.*;
import javax.swing.border.LineBorder;

import backend.models.Users.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserPanels extends JFrame {
    JPanel panelMain, sidePanel;
    JButton newUser;
    JPanel fileUserPanel; // ใช้ JPanel แทน JTextArea
    JScrollPane scrollPane;
    
    public UserPanels() {
        setTitle("User Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 760);
        setResizable(false);
        setLayout(new FlowLayout());

        newUser = new JButton("New User");
        newUser.setPreferredSize(new Dimension(120, 60));
        newUser.addActionListener(ev -> new NewUserRegister().setVisible(true));

        panelMain = new JPanel();
        panelMain.setPreferredSize(new Dimension(700, 600));
        panelMain.setBorder(new LineBorder(Color.BLACK, 2));
        panelMain.setLayout(new BorderLayout());

        sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(670, 500));
        sidePanel.setBorder(new LineBorder(Color.RED, 2));
        sidePanel.setLayout(new BorderLayout());

        fileUserPanel = new JPanel();
        fileUserPanel.setLayout(new BoxLayout(fileUserPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(fileUserPanel);
        scrollPane.setPreferredSize(new Dimension(670, 100));

        sidePanel.add(scrollPane, BorderLayout.CENTER);
        panelMain.add(sidePanel, BorderLayout.NORTH);
        
        add(newUser);
        add(panelMain);

        loadUserData();

        setVisible(true);
    }

    public void openNewUser(){
        new NewUserRegister();
        setVisible(true);
    }


    public void loadUserData() {

        try (BufferedReader read = new BufferedReader(new FileReader("./src/backend/data/UserData.txt"))) {
            String line;
            while ((line = read.readLine()) != null) {
                
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
}
