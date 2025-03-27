package client.components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import backend.services.LoginServices;
import client.MainFrame;
import resources.SetPreferences;

public class InformationAccount extends JDialog{
    private MainFrame mainFrame;
    private String[] accountData;
    private Font font = new SetPreferences().getFont(20);
    
    public InformationAccount(MainFrame mainFrame,String[] accountData){
        super(mainFrame,"Information",true);
        this.mainFrame = mainFrame;
        this.accountData = accountData;
        CreateGui();
        setSize(350,200);
        setLocationRelativeTo(mainFrame);
        setVisible(true);
    }

    private void CreateGui(){
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(nameLabel());
        add(permissionLabel());
        add(Logout());
    }
    
    private JLabel nameLabel(){
        JLabel name = new JLabel(this.accountData[1]);
        name.setPreferredSize(new Dimension(300,40));
        name.setHorizontalAlignment(JLabel.CENTER);
        name.setFont(font);
        return name;
    }

    private JLabel permissionLabel(){
        JLabel permission = new JLabel("Permission : "+this.accountData[5]);
        permission.setPreferredSize(new Dimension(300,60));
        permission.setHorizontalAlignment(JLabel.CENTER);
        permission.setFont(font);
        return permission;
    }
    
    private JButton Logout(){
        JButton btn = new JButton("Sign out");
        btn.addActionListener(e -> {
            new LoginServices().signOut(mainFrame);
            mainFrame.resetTopBar();
            mainFrame.resetSideBar();
            dispose();
        });
        return btn;
    }

}
