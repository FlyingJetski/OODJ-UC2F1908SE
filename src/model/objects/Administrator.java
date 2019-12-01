package model.objects;

import model.IOWriterReader;

public class Administrator extends User {
    public Administrator() {
        this.role = "Administrator";
    }

    public Administrator(int userId, String username, String password, String name, String address,
                         String contactNumber, String emailAddress, boolean status) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = "Administrator";
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.status = status;
    }

    public Administrator(String username, String password, String name, String address,
                         String contactNumber, String emailAddress) {
        this.userId = IOWriterReader.getUserId();
        this.username = username;
        this.password = password;
        this.role = "Administrator";
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.status = true;
    }
}
