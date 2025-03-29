package backend.models.Products;

public class Product {
    private String id;
    private String name;
    private String price;
    private String discount;
    private String stock;
    private String type;
    private String image;
    

    public Product() {
        this.id = "0";
        this.name = "";
        this.price = "0";
        this.discount = "0";
        this.image = "";
    }

    public Product(String id, String name, String price, String discount, String stock, String type, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.stock = stock;
        this.type = type;
        this.image = image;
        priceChange();
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
        priceChange();
    }
    
    public void setStock(String stock) {
        this.stock = stock;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void addStock(String stock) {
        this.stock += stock;
    }

    public void MinusStock(String stock) {
        this.stock = String.valueOf(Integer.parseInt(this.stock) - Integer.parseInt(stock));
    }

    public String getName() {
        return this.name;
    }

    public String getPrice() {
        return this.price;
    }

    public String getDiscount() {
        return this.discount;
    }


    public String getStock() {
        return this.stock;
    }

    public String getID() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public String getImage() {
        return this.image;
    }

    private void priceChange() {
        this.price = String.valueOf(Double.parseDouble(this.price) * (1 - (1 * (Double.parseDouble(this.discount) / 100))));
    }

    public String toString() {
        return ("ID : " + id + "\n" +
                "Name : " + name + "\n" +
                "Price : " + price + "\n" +
                "Discount : " + discount + "%\n" +
                "Stock : " + stock + "\n" +
                "Type : " + type);
    }
}
