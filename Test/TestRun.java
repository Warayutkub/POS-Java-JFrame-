import java.util.Arrays;

import backend.services.AuthService;

public class TestRun {
    public static void main(String[] args) {
        for (String[] d : new AuthService().getAllUserData("emp")){
            System.out.println(Arrays.toString(d));
        }
    }
}
