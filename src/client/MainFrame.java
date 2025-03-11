package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import client.components.POSDateTimeFrame;

public class MainFrame extends JFrame{
    private int width = 900;
    private int height = 500;
    private JPanel leftPanel,rightPanel,topPanel,bodyPanel;
    private Container container = getContentPane();
    public MainFrame(){
        CreateGui();
        CreateWindow();
    }

    private void CreateWindow(){
        setTitle("POS System");
        setSize(width,height);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void CreateGui(){
        container.setLayout(new BorderLayout());
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        topPanel = new JPanel();
        bodyPanel = new JPanel();

        leftPanel.setPreferredSize(new Dimension(width-800,height));
        leftPanel.setBorder(new LineBorder(Color.BLUE,2));
        
        rightPanel.setPreferredSize(new Dimension(width-700,height));
        rightPanel.setBorder(new LineBorder(Color.red,2));

        TopPanelManage();
        topPanel.setPreferredSize(new Dimension(width,height-470));
        topPanel.setBorder(new LineBorder(Color.PINK));

        bodyPanel.setBorder(new LineBorder(Color.BLUE));

        container.add(bodyPanel,BorderLayout.CENTER);
        container.add(leftPanel, BorderLayout.WEST);
        container.add(rightPanel, BorderLayout.EAST);
        container.add(topPanel, BorderLayout.NORTH);
    }

    private void TopPanelManage(){
        topPanel.setLayout(new FlowLayout());
        topPanel.add(new POSDateTimeFrame());
    }

}
