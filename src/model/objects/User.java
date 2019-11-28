package model.objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

abstract public class User {
    protected int userId;
    protected String username;
    protected String password;
    protected String role;
    protected String name;
    protected String address;
    protected String contactNumber;
    protected String emailAddress;
    protected boolean status;
    public static ObservableList<User> users = FXCollections.observableArrayList();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getStatusText() {
        if (status == true) {
            return "Active";
        } else {
            return "Inactive";
        }
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s",
                userId, username, password, role, name, address,
                contactNumber, emailAddress, status);
    }
}
