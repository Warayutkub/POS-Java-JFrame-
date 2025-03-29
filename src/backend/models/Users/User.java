package backend.models.Users;

public class User {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String password;

    public User(){}

    public User(String id,String name,String phone,String email,String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    
    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPassword(String password){
        this.password = password;
    }


    public String getName(){
        return this.name;
    }
    public String getPhone(){
        return this.phone;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPassword(){
        return this.password;
    }
    public String getID(){
        return this.id;
    }


    public String toString(){
        return ("ID : " + id + "\n" + 
        "Name : " + name + "\n" +
        "Phone : " + phone + "\n" + 
        "Email : " + email);
    }
}
