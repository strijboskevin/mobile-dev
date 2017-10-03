package mobile_dev.mobile_dev.model;

import java.util.List;

/**
 * Created by kevin on 03/10/2017.
 */

public class Dish {

    private int id;
    private String name;
    private List<Restaurant> restaurants;

    public Dish(int id, String name, List<Restaurant> restaurants) {
        this.id = id;
        this.name = name;
        this.restaurants = restaurants;
    }

    public Dish() {};

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
