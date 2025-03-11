package backend.models;

public class User {
    private String data;
    public User(String userName,String password,int type){
        this.data = (type == 0) ? Login(userName,password): Register(userName,password);
    }

    private String Login(String userName,String password){
        // Add your login logic here and return a string
        return "Login successful";
    }

    private String Register(String userName,String password){
        // Add your registration logic here and return a string
        return "Registration successful";
    }
}
