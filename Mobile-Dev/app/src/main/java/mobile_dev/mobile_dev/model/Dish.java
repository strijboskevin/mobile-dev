package mobile_dev.mobile_dev.model;

import java.io.Serializable;
import java.util.List;

public class Dish implements Serializable {

    private int id;
    private String name;
    private String image;
    private List<Restaurant> restaurants;

    public Dish(int id, String name, List<Restaurant> restaurants, String image) {
        this.id = id;
        this.name = name;
        this.restaurants = restaurants;
        this.image = image;
    }

    public Dish() {};

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
