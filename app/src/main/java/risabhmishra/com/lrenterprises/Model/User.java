package risabhmishra.com.lrenterprises.Model;

public class User {
    String Address,GST,Name,Phone;

    public User(String address, String GST, String name, String phone) {
        Address = address;
        this.GST = GST;
        Name = name;
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public User(){}

}
