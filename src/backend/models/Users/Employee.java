package backend.models.Users;

public class Employee extends User {
    private String permission;

    public Employee(String id,String name, String phone, String email, String password){
        super(id,name, phone, email, password);
    }

    public Employee(String id,String name, String phone, String email, String password, String permission) {
        super(id,name, phone, email, password);
        this.permission = permission;
    }
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission){this.permission = permission;}

    public String toString(){
        return (super.toString() + "\nPermission : " + permission);
    }
}
