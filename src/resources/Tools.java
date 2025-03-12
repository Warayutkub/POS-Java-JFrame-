package resources;

public class Tools {
    
    public boolean LinearSearch(String[] data,String search){
        for (String item : data) {
            if(item != null){
                if (item.equals(search)) {
                    return true;  
                }
            }
        }
        return false;
    }
}
