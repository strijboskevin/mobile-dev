package mobile_dev.mobile_dev.model;

import java.io.Serializable;

/**
 * Created by kevin on 03/10/2017.
 */

public class User implements Serializable {

    private String username;
    private String firstName;
    private String lastName;
    private String passWord;
    private String city;
    private String address;
    private String mobileNr;
    private int radius;

    public User(String username, String firstName, String lastName, String passWord, String city, String address, int radius, String mobileNr) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passWord = passWord;
        this.city = city;
        this.address = address;
        this.radius = radius;
        this.mobileNr = mobileNr;
    }

    public String getMobileNr() {
        return mobileNr;
    }

    public void setMobileNr(String mobileNr) {
        this.mobileNr = mobileNr;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public User() {};

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String  getCity() {
        return city;
    }

    public void setCity(String  city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
