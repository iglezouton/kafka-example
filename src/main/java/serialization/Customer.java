package serialization;

public class Customer {
    private int customerID;
    private String customerName;

    public Customer(int ID, String name) {
        this.customerID = ID;
        this.customerName = name;
    }
    public int getID() {
        return this.customerID;
    }
    public String getName() {
        return this.customerName;
    }

    @Override
    public String toString() {
        return "id:" + customerID + " name:" + customerName;
    }
}

