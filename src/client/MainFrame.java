package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame{
    private int width = 1000;
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
        
        
        

        TopPanelManage();
        topPanel.setPreferredSize(new Dimension(width,height-470));
    
        bodyPanelManage();


        container.add(bodyPanel,BorderLayout.CENTER);
        container.add(leftPanel, BorderLayout.WEST);
        container.add(topPanel, BorderLayout.NORTH);
    }

    private void TopPanelManage(){
        topPanel.setLayout(new FlowLayout());
        topPanel.setBackground(Color.white);
    }

    public void bodyPanelManage(){
        bodyPanel.setLayout(new FlowLayout());
        bodyPanel.setBackground(Color.white);


        bodyPanel.setSize(500, 400);

        // ใช้ BorderLayout
        bodyPanel.setLayout(new BorderLayout());

        // สร้าง Panel ที่มี GridLayout
        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 10, 10)); // 2x2 Grid พร้อมช่องว่าง
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // กำหนด Padding

        // เพิ่มองค์ประกอบลงใน Grid
        gridPanel.add(new JButton("Warayut"));
        gridPanel.add(new JButton("Button 2"));
        gridPanel.add(new JButton("Button 3"));
        gridPanel.add(new JButton("Button 4"));

        // นำ Grid Panel ใส่ลงใน CENTER ของ JFrame
        bodyPanel.add(gridPanel, BorderLayout.CENTER);

        bodyPanel.setVisible(true);
    }
    

}
