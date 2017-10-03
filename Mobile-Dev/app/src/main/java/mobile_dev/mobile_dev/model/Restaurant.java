package mobile_dev.mobile_dev.model;

import java.util.List;

/**
 * Created by kevin on 03/10/2017.
 */

public class Restaurant {

    private int id;
    private String name;
    private City city;
    private List<Menu> menus;

    public Restaurant(int id, String name, City city, List<Menu> menus) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.menus = menus;
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

}
