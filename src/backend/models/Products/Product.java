package backend.models.Products;

public class Product {
    private String name;
    private double price;
    private double discount;
    private double tax;
    private int stock;
    private String type;


    public Product(String name,double price,double discount,double tax,int stock,String type){
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.tax = tax;
        this.stock = stock;
        this.type = type;
        priceChange();
    }

    
    public void setName(String name){this.name = name;}
    public void setPrice(double price){this.price = price;}
    public void setDiscount(double discount){this.discount = discount;priceChange();}
    public void setTax(double tax){this.tax = tax;priceChange();}
    public void setStock(int stock){this.stock = stock;}
    public void setType(String type){this.type = type;}


    public void addStock(int stock){this.stock += stock;}
    public void MinusStock(int stock){this.stock -= stock;}


    public String getName(){return this.name;}
    public double getPrice(){return this.price;}
    public double getDiscount(){return this.discount;}
    public double getTax(){return this.tax;}
    public int getStock(){return this.stock;}
    public String getType(){return this.type;}


    private void priceChange(){
        this.price =  this.price*(1-(1*(this.discount/100))) + (this.price*(tax/100));
    }


    public String toString(){
        return ("Name : " + name + "\n" + 
        "Price : " + price + "\n" + 
        "Discount : " + discount + "%\n" + 
        "Tax : " + tax + "%\n" + 
        "Stock : " + stock + "\n" + 
        "Type : " + type);
    }
}
