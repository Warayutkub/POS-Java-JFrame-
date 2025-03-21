package resources;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class Tools {

    public boolean LinearSearch(String[] data, String search) {
        for (String item : data) {
            if (item != null) {
                if (item.equals(search)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean LinearSearch(ArrayList<String> data, String search) {
        for (String item : data) {
            if (item != null) {
                if (item.equals(search)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String genNewId(String[][] data) {
        String[] ids = new String[data.length];
        String newId = "";

        for (int i = 0; i < data.length; i++) {
            ids[i] = data[i][0];
        }

        if (ids.length == 0) {
            newId = "1";
        } else {
            newId = String.valueOf(Integer.parseInt(ids[ids.length - 1]) + 1);
        }
        return newId;
    }

    public void SaveFileCopy(String originalPath, String directionPath) {
        try {
            Path original = Paths.get(originalPath);
            Path direction = Paths.get(directionPath);
            Files.copy(original, direction, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
