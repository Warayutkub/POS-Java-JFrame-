package backend.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

import client.MainFrame;

public class LoginServices {
    private String path = "./src/backend/data/Token.txt";

    public String[] getDataToken(){
        String[] data = new String[0];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.path));
            String line;
            while ((line = reader.readLine()) != null) {
                data = line.split(",");
            }
            reader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Data file have problem!");
        }

        return data;
    }

    public void signOut(MainFrame mainFrame){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))){
            writer.write("");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error can't sing out!");
        }
    }

   
}
