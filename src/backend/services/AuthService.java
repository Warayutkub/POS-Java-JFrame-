package backend.services;

import java.io.BufferedReader;
import java.io.FileReader;

import resources.Tools;

public class AuthService {
    private String[][] Users = getAllUserData("user");
    private String[][] Emps = getAllUserData("emp");

    public String[][] getAllUserData(String typeUser) { 
        if (typeUser.equals("user")) {
            String[][] Users = new String[0][0];
            try (BufferedReader br = new BufferedReader(new FileReader("./src/backend/data/UserData.txt"))) {
                String line;
                Users = new String[(int) br.lines().count()][4];
                int i = 0;
                try (BufferedReader br2 = new BufferedReader(new FileReader("./src/backend/data/UserData.txt"))) {
                    while ((line = br2.readLine()) != null) {
                        Users[i] = line.split(",");
                        i++;
                    }
                }
                return Users;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return Users;
        } else {
            String[][] Users = new String[0][0];
            try (BufferedReader br = new BufferedReader(
                    new FileReader(getClass().getResource("../data/EmployeeData.txt").getFile()))) {
                String line;
                Users = new String[(int) br.lines().count()][4];
                int i = 0;
                try (BufferedReader br2 = new BufferedReader(
                        new FileReader(getClass().getResource("../data/EmployeeData.txt").getPath()))) {
                    while ((line = br2.readLine()) != null) {
                        Users[i] = line.split(",");
                        i++;
                    }
                }
                return Users;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return Users;
        }
    }

    public boolean CheckDataUser(String data, String typeUser) {
        if (typeUser.equals("user")) {
            for (int i = 0; i < Users.length; i++) {
                if (new Tools().LinearSearch(Users[i], data)) {
                    return true;
                }
            }
            return false;

        } else {
            for (int i = 0; i < Emps.length; i++) {
                if (new Tools().LinearSearch(Emps[i], data)) {
                    return true;
                }
            }
            return false;
        }
    }

    public String[] getDataUser(String dataFine){
        String[] data = new String[0];

        for (String[] recode : Users){
            if (new Tools().LinearSearch(recode, dataFine)) {
                data = recode;
            }
        }

        return data;
    }

    public String[] getDataEmp(String dataFine){
        String[] data = new String[0];
    
        for (String[] recode : Emps){
            if (new Tools().LinearSearch(recode, dataFine)) {
                data = recode;
            }
        }
    
        return data;
    }
}
