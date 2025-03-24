package client.components;


import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
//import client.components.editEmployee;
import javax.swing.JFrame;


public class editTestEmploy extends JFrame{
    private editEmployee editEmployee = new editEmployee();

    public editTestEmploy() {
        CreateGUI();
        CreateWindow();
    }

    private void CreateWindow() {
        setSize(875, 650);
        setResizable(false);
        setLocation(10, 30);
        //setAlwaysOnTop(true);
        //setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void CreateGUI() {
        Container container = getContentPane();
        container.setLayout(new FlowLayout());
        container.setBackground(Color.DARK_GRAY);
        container.setPreferredSize(new Dimension(200,200));
        container.add(editEmployee);
    }
    public static void main(String[] args) {
        new editTestEmploy();
    }
    
}

