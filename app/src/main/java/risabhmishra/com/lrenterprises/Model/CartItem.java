package risabhmishra.com.lrenterprises.Model;

public class CartItem {
    String name;
    String type;
    String price;
    String qty;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    String amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public CartItem(String name, String type, String price, String qty) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.qty = qty;
        this.amount = Float.toString(Float.parseFloat(price)*Float.parseFloat(qty));
    }
}
