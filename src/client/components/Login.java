package client.components;

import javax.swing.*;

import backend.services.AuthService;
import client.MainFrame;
import resources.SetPreferences;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Login extends JDialog {
    Container c = getContentPane();
    JLabel loginLabel = new JLabel("Login");
    JLabel accountLabel = new JLabel("Username : ");
    JLabel passwordLabel = new JLabel("Password : ");
    JTextField accountName = new JTextField();
    JPasswordField password = new JPasswordField();
    JButton SubmitBtn = new JButton("Submit");
    Font font = new SetPreferences().getFont(20);
    MainFrame mainFrame;

    public Login(MainFrame mainFrame) {
        super(mainFrame, "Login", true);
        this.mainFrame = mainFrame;
        setSize(350, 500);
        c.setLayout(new FlowLayout(FlowLayout.CENTER));
        c.setBackground(Color.WHITE);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JPanel submitField = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));

        loginLabel.setFont(new SetPreferences().getFont(20));
        loginLabel.setPreferredSize(new Dimension(280, 30));
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLabel.setBackground(Color.BLACK);

        accountLabel.setPreferredSize(new Dimension(280, 30));
        accountLabel.setHorizontalTextPosition(JLabel.LEFT);
        accountName.setPreferredSize(new Dimension(280, 30));

        passwordLabel.setPreferredSize(new Dimension(280, 30));
        passwordLabel.setHorizontalAlignment(JLabel.LEFT);
        password.setPreferredSize(new Dimension(280, 30));
        SubmitBtn.setFont(font);

        // UnderLine Font
        SubmitBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                SubmitBtn.setText("<html><u>Login ?</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                SubmitBtn.setText("Login");
            }
        });

        SubmitBtn.addActionListener(e -> {
            Submit();
            mainFrame.resetSideBar();
        });
        
        password.addActionListener(e -> {
            Submit();
            mainFrame.resetSideBar();
        });

        submitField.add(SubmitBtn);
        SubmitBtn.setForeground(Color.WHITE);
        SubmitBtn.setPreferredSize(new Dimension(100, 50));
        float[] hsbValues = Color.RGBtoHSB(3, 153, 254, null);
        SubmitBtn.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
        submitField.setPreferredSize(new Dimension(380, 150));
        submitField.setBackground(Color.WHITE);

        add(LogoArea(350, 350));
        add(accountLabel);
        add(accountName);
        add(passwordLabel);
        add(password);
        add(submitField);
        setResizable(true);
        setLocationRelativeTo(mainFrame);
        setVisible(true);
    }

    private JPanel LogoArea(int width, int height) {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 25));
        JLabel img = new JLabel();
        area.setPreferredSize(new Dimension(width, 150));
        ImageIcon icon = new ImageIcon(getClass().getResource("/backend/data/images/logo.png"));
        img.setIcon(new ImageIcon(icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        area.setBackground(Color.WHITE);
        area.add(img);
        return area;
    }

    private void Submit() {
        boolean check = false;
        if (!this.accountName.getText().isEmpty() && !this.password.getPassword().toString().isEmpty()) {
            String[][] persons = new AuthService().getAllUserData("Emp");
            String acc = this.accountName.getText();
            String passwordS = new String(this.password.getPassword());
            for (String[] person : persons) {
                if (person[1].equals(acc)) {
                    if (person[4].equals(passwordS)){
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/backend/data/Token.txt"))) {
                            writer.write(person[0] + "," + person[1] + "," + person[2] + "," + person[3] + "," + person[4]
                                    + "," + person[5] + "\n");
                                    
                                    dispose();
                                    mainFrame.setAccountData(person);
                                    mainFrame.resetTopBar();
                                    check = true;
                                    break;
                            
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(this, "Error can't login!");
                        }
                    }if (!check) JOptionPane.showMessageDialog(this, "Invalid password. Please try again.");
                }
            }
            if (!check) JOptionPane.showMessageDialog(this, "Invalid username. Please try again.");
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in both username and password fields.");
        }
    }
}
