package mobile_dev.mobile_dev.model;

/**
 * Created by kevin on 03/10/2017.
 */

public class User {

    private String userName;
    private String firstName;
    private String lastName;
    private String passWord;
    private City city;
    private String address;

    public User(String userName, String firstName, String lastName, String passWord, City city, String address) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passWord = passWord;
        this.city = city;
        this.address = address;
    }

    public User() {};

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
