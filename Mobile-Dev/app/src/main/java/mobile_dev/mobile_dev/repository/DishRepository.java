package mobile_dev.mobile_dev.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;

import mobile_dev.mobile_dev.BuildConfig;
import mobile_dev.mobile_dev.consumer.Consumer;
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
<<<<<<< HEAD
        connection.setUrl("http://10.0.2.2:4041/mdev-api/dishes/get/" + id);
        connection.getString();
=======
        consumer.setUrl(BuildConfig.SERVER_URL + "/dishes/get/" + id);
        consumer.getString();
>>>>>>> fc47ffafc14dcd97ceec53b7736ec3c2836dfb77
        Dish dish = gson.fromJson(this.result, Dish.class);
        return dish;
    }

    public List<Dish> all() {
<<<<<<< HEAD
        connection.setUrl("http://10.0.2.2:4041/mdev-api/dishes/all");
        connection.getString();
=======
        consumer.setUrl(BuildConfig.SERVER_URL + "/dishes/all");
        consumer.getString();
>>>>>>> fc47ffafc14dcd97ceec53b7736ec3c2836dfb77
        List<Dish> dishes = gson.fromJson(this.result, new TypeToken<List<Dish>>(){}.getType());
        return dishes;
    }

    @Override
    public void setString(String result) {
        this.result = result;
    }
}
