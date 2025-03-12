import java.io.BufferedReader;
import java.io.FileReader;

public class ReadLineExample {
    static String[][] product;

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("example.txt"))) {
            String line;
            product = new String[(int)br.lines().count()][7];
            int i = 0;
            try(BufferedReader br2 = new BufferedReader(new FileReader("example.txt"))){
                while ((line = br2.readLine()) != null) {
                    product[i] = line.split(",");
                    i++;
                }
            }
            for (String[] o : product) {
                System.out.println(java.util.Arrays.toString(o));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}