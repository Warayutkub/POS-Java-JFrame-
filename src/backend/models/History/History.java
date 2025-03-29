package backend.models.History;

public class History {
    private String billID;
    private String date;
    private String time;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String[] products;
    private String[] quantity;
    private String[] price;
    private String totalPrice;
    private String totalAmount;

    public History(){}

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setProducts(String[] products) {
        this.products = products;
    }

    public void setQuantity(String[] quantity) {
        this.quantity = quantity;
    }

    public void setPrice(String[] price) {
        this.price = price;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBillID() {
        return billID;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String[] getProducts() {
        return products;
    }

    public String[] getQuantity() {
        return quantity;
    }

    public String[] getPrice() {
        return price;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public String getTotalPrice() {
        return totalPrice;
    }
    
}
