package mobile_dev.mobile_dev.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import mobile_dev.mobile_dev.connection.Connection;
import mobile_dev.mobile_dev.model.Restaurant;

/**
 * Created by kevin on 03/10/2017.
 */

public class RestaurantRepository implements IRepository {

    private Connection connection;
    private String result;
    private Gson gson;

    public RestaurantRepository() {
        this.connection = new Connection(this);
        this.gson = new Gson();
    }

    public Restaurant find(int id) {
        connection.setUrl("http://10.0.2.2:8080/mdev-api/restaurants/get/" + id);
        connection.getString();
        Restaurant restaurant = gson.fromJson(this.result, Restaurant.class);
        return restaurant;
    }

    public List<Restaurant> all() {
        connection.setUrl("http://10.0.2.2:8080/mdev-api/restaurants/all");
        connection.getString();
        List<Restaurant> restaurants = gson.fromJson(this.result, new TypeToken<List<Restaurant>>(){}.getType());
        return restaurants;
    }

    @Override
    public void setString(String result) {
        this.result = result;
    }
}
