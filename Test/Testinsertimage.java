import javax.swing.*;

import client.components.InsertImage;

import java.awt.*;

public class Testinsertimage {
    public static void main(String[] args) {
        new InnerTestinsertimage();
    }
}

class InnerTestinsertimage extends JFrame{
    public InnerTestinsertimage(){
        createGui();
        setSize(600,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createGui(){
        Container container =getContentPane();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        container.add(new InsertImage());
    }
    
}
