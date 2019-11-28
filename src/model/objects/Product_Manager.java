package model.objects;

import model.IOWriterReader;

public class Product_Manager extends User {
    public Product_Manager(int userId, String username, String password, String name, String address,
                           String contactNumber, String emailAddress, boolean status) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = "Product Manager";
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.status = status;
    }

    public Product_Manager(String username, String password, String name, String address,
                           String contactNumber, String emailAddress) {
        this.userId = IOWriterReader.getUserId();
        this.username = username;
        this.password = password;
        this.role = "Product Manager";
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.status = true;
    }
}
