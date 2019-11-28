package model.objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import model.IOWriterReader;

public class Supplier extends Object {
    protected int supplierId;
    protected String name;
    protected String address;
    protected String postCode;
    protected String country;
    protected String stateOrProvince;
    protected String city;
    protected String contactNumber;
    protected String emailAddress;
    protected boolean status;
    public static ObservableList<Supplier> suppliers = FXCollections.observableArrayList();

    public Supplier(int supplierId, String name, String address, String postCode, String country, String stateOrProvince,
                    String city, String contactNumber, String emailAddress, boolean status) {
        this.supplierId = supplierId;
        this.name = name;
        this.address = address;
        this.postCode = postCode;
        this.country = country;
        this.stateOrProvince = stateOrProvince;
        this.city = city;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.status = status;
    }

    public Supplier(String name, String address, String postCode, String country, String stateOrProvince,
                    String city, String contactNumber, String emailAddress) {
        this.supplierId = IOWriterReader.getSupplierId();
        this.name = name;
        this.address = address;
        this.postCode = postCode;
        this.country = country;
        this.stateOrProvince = stateOrProvince;
        this.city = city;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.status = true;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
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

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
        return String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s",
                supplierId, name, address, postCode,
                country, stateOrProvince, city, contactNumber,
                emailAddress, status
        );
    }
}
