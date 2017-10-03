package be.pxl.mdev.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by kevin on 02/10/2017.
 */

@Entity
@Table(name="users")
public class User {

    @Id
    private String username;
    private String passWord;
    private String firstName;
    private String lastName;
    private String mobileNr;
    private String address;
    @ManyToOne
    @JsonManagedReference(value = "cities_users")
    private City city;

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User() {}

    public User(String username, String firstName, String lastName, String mobileNr, String address) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNr = mobileNr;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
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

    public String getMobileNr() {
        return mobileNr;
    }

    public void setMobileNr(String mobileNr) {
        this.mobileNr = mobileNr;
    }
}
