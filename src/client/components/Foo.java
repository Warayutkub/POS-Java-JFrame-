import java.io.*;

public class Foo {

    public static void main(String[] args) throws IOException {

        FileReader file = new FileReader(new File("./src/backend/data/UserData.txt"));

        BufferedReader read = new BufferedReader(file);

        String line = null;

        while ((line = read.readLine()) != null) 
        {
        for (int i = 0; i < line.length(); i++) 
        {
            if (Character.isLetter(line.charAt(i))) 
            {
            System.out.print(line.charAt(i));
            }
    }
}
    }
}