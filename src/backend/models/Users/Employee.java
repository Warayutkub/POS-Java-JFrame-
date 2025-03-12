package backend.models.Users;

public class Employee extends User {
    private String permission;
    private String role;
    private double trackSales = 0;
    private double trackSalesTotal = 0;

    public Employee(String name, String phone, String email, String password, String permission, String role) {
        super(name, phone, email, password);
        this.permission = permission;
        this.role = role;
    }

    public void TrackSale(double sales) {
        this.trackSales += sales;
        this.trackSalesTotal += sales;
    }

    public void setTrackSales(double data) {
        this.trackSales = data;
    }

    public void setTrackSalesTotal(double data){
        this.trackSalesTotal = data;
    }

    public void setPermission(String permission){this.permission = permission;}
    public void setRole(String Role){this.role = Role;}

    public String toString(){
        return (super.toString() + "\nPermission : " + permission + "\n" + 
        "Role : " + role + "\n" + 
        "Sales this week : " + trackSales + "\n" + 
        "Total sales : " + trackSalesTotal);
    }
}
