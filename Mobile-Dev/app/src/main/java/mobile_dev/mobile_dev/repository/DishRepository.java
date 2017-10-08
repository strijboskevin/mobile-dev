package mobile_dev.mobile_dev.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;

import mobile_dev.mobile_dev.BuildConfig;
import mobile_dev.mobile_dev.api.Consumer;
import mobile_dev.mobile_dev.model.Dish;

/**
 * Created by kevin on 03/10/2017.
 */

public class DishRepository implements IRepository {

    private Consumer consumer;
    private String result;
    private Gson gson;

    public DishRepository() {
        this.consumer = new Consumer(this);
        this.gson = new Gson();
    }

    public Dish find(int id) {
        consumer.setUrl(BuildConfig.SERVER_URL + "/dishes/get/" + id);
        consumer.getString();
        Dish dish = gson.fromJson(this.result, Dish.class);
        return dish;
    }

    public List<Dish> all() {
        consumer.setUrl(BuildConfig.SERVER_URL + "/dishes/all");
        consumer.getString();
        List<Dish> dishes = gson.fromJson(this.result, new TypeToken<List<Dish>>(){}.getType());
        return dishes;
    }

    @Override
    public void setString(String result) {
        this.result = result;
    }
}
