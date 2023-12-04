package Model;

public class Product {


    private String productID;
    private String productName;
    private String price;
    private String catagory;
    private String productStocks;

    public Product(String productID, String productName, String price, String catagory, String productStocks) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.catagory = catagory;
        this.productStocks = productStocks;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getProductStocks() {
        return productStocks;
    }

    public void setProductStocks(String productStocks) {
        this.productStocks = productStocks;
    }

    @Override
    public String toString() {
        return productID;
    }


}