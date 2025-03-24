package backend.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

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

   
}
