package mobile_dev.mobile_dev.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import mobile_dev.mobile_dev.connection.Connection;
import mobile_dev.mobile_dev.model.Dish;

/**
 * Created by kevin on 03/10/2017.
 */

public class DishRepository implements IRepository {

    private Connection connection;
    private String result;
    private Gson gson;

    public DishRepository() {
        this.connection = new Connection(this);
        this.gson = new Gson();
    }

    public Dish find(int id) {
        connection.setUrl("http://10.0.2.2:8080/mdev-api/dishes/get/" + id);
        connection.getString();
        Dish dish = gson.fromJson(this.result, Dish.class);
        return dish;
    }

    public List<Dish> all() {
        connection.setUrl("http://10.0.2.2:8080/mdev-api/dishes/all");
        connection.getString();
        List<Dish> dishes = gson.fromJson(this.result, new TypeToken<List<Dish>>(){}.getType());
        return dishes;
    }

    @Override
    public void setString(String result) {
        this.result = result;
    }
}
