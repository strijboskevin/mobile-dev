package mobile_dev.mobile_dev.model;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kevin on 03/10/2017.
 */

public class Restaurant implements Serializable {

    private int id;
    private String name;
    private String city;
    private String address;
    private List<Menu> menus;

    public Restaurant(int id, String name, String city, List<Menu> menus) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.menus = menus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Restaurant() {};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String  getCity() {
        return city;
    }

    public void setCity(String  city) {
        this.city = city;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

}
