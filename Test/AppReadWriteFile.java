import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AppReadWriteFile extends JFrame implements ActionListener{
    JPanel panel;
    JTextField textFile;
    JLabel label;
    JTextArea outputArea;
    JScrollPane scrollPane;
    Container c;
    Font myFont = new Font("Tahoma",Font.BOLD, 18);
    

    public AppReadWriteFile() {
        super(" Example Read and Write Product " );
        c = getContentPane();
        c.setLayout(new FlowLayout());
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        panel.setPreferredSize(new Dimension(500, 70));
        label = new JLabel("Enter file name : ");
        label.setFont(myFont);
        panel.add(label);
        textFile = new JTextField(10);
        textFile.addActionListener(this);
        textFile.setFont(myFont);
        panel.add(textFile);
        c.add(panel);
        outputArea = new JTextArea(12, 30);
        outputArea.setEditable(false);
        outputArea.setFont(myFont);
        scrollPane = new JScrollPane(outputArea);
        c.add(scrollPane);
        setSize( 600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String filename = textFile.getText().toString();
        outputArea.setText("");
        try {
            BufferedReader reader = new BufferedReader( new FileReader( filename));
            BufferedWriter writer = new BufferedWriter(new FileWriter( "output.txt",true));
            
            String s1;
            while((s1 = reader.readLine()) != null) {
                writer.write(s1+"\n");
                outputArea.append(s1+"\n");
            }
            reader.close();   // close stream input
            writer.close();
            
        }
        catch (IOException exception) {
            JOptionPane.showMessageDialog(this, "can not open file " +filename ,
            "Error Message ",JOptionPane.ERROR_MESSAGE);
        }
        
    }

    public static void main(String[] args) {
        new AppReadWriteFile();
    }
}