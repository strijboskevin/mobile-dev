package mobile_dev.mobile_dev.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;

import mobile_dev.mobile_dev.BuildConfig;
import mobile_dev.mobile_dev.consumer.Consumer;
import mobile_dev.mobile_dev.model.Restaurant;

/**
 * Created by kevin on 03/10/2017.
 */

public class RestaurantRepository implements IRepository {

    private Consumer consumer;
    private String result;
    private Gson gson;

    public RestaurantRepository() {
        this.consumer = new Consumer(this);
        this.gson = new Gson();
    }

    public Restaurant find(int id) {
        consumer.setUrl(BuildConfig.SERVER_URL + "/restaurants/get/" + id);
        consumer.getString();
        Restaurant restaurant = gson.fromJson(this.result, Restaurant.class);
        return restaurant;
    }

    public List<Restaurant> all() {
        consumer.setUrl(BuildConfig.SERVER_URL + "/restaurants/all");
        consumer.getString();
        List<Restaurant> restaurants = gson.fromJson(this.result, new TypeToken<List<Restaurant>>(){}.getType());
        return restaurants;
    }

    @Override
    public void setString(String result) {
        this.result = result;
    }
}