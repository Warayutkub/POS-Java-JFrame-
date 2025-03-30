package resources;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
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
        String newId = "";

        if (data.length == 0) {
            newId = "1";
        } else {
            newId = String.valueOf(Integer.parseInt(data[data.length - 1][0]) + 1);
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

    public void removeFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            File parentDir = file.getParentFile();
            if (parentDir != null && parentDir.isDirectory()) {
                File[] files = parentDir.listFiles();
                if (files != null && files.length > 1) {
                    file.delete();
                }
            }
        }
    }

}
