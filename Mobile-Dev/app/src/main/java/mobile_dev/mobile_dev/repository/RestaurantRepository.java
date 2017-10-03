package mobile_dev.mobile_dev.repository;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.MalformedURLException;
import mobile_dev.mobile_dev.connection.Connection;
import mobile_dev.mobile_dev.model.Dish;
import mobile_dev.mobile_dev.model.Restaurant;
import mobile_dev.mobile_dev.model.User;

/**
 * Created by kevin on 03/10/2017.
 */

public class RestaurantRepository {

    private Connection connection;
    private Gson gson;

    public RestaurantRepository() {
        this.connection = new Connection();
        this.gson = new Gson();
    }

    public Restaurant find(int id) {
        try {
            connection.setUrl("http://localhost:8080/api-0.1.0/restaurants/get/" + id);
            String json = connection.getString();
            Restaurant restaurant = gson.fromJson(json, Restaurant.class);
            System.out.println(restaurant);
            return restaurant;
        } catch (MalformedURLException ex) {
            System.out.println("Error fetching restaurant with id: " + id + ".\n" );
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error fetching restaurant with id: " + id + ".\n");
            ex.printStackTrace();
        } finally {
            return null;
        }
    }

}
