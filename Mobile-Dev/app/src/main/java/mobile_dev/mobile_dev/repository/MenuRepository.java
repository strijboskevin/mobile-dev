package mobile_dev.mobile_dev.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;

import mobile_dev.mobile_dev.BuildConfig;
import mobile_dev.mobile_dev.consumer.Consumer;
import mobile_dev.mobile_dev.model.Menu;

/**
 * Created by kevin on 03/10/2017.
 */

public class MenuRepository implements IRepository {

    private Consumer consumer;
    private String result;
    private Gson gson;

    public MenuRepository() {
        this.consumer = new Consumer(this);
        this.gson = new Gson();
    }

    public Menu find(int id) {
<<<<<<< HEAD
        connection.setUrl("http://10.0.2.2:4041/mdev-api/menus/get/" + id);
        connection.getString();
=======
        consumer.setUrl(BuildConfig.SERVER_URL + "/menus/get/" + id);
        consumer.getString();
>>>>>>> fc47ffafc14dcd97ceec53b7736ec3c2836dfb77
        Menu menu = gson.fromJson(this.result, Menu.class);
        return menu;
    }

    public List<Menu> all() {
        consumer.setUrl(BuildConfig.SERVER_URL + "/menus/all");
        consumer.getString();
        List<Menu> menus = gson.fromJson(this.result, new TypeToken<List<Menu>>(){}.getType());
        return menus;
    }

    @Override
    public void setString(String result) {
        this.result = result;
    }
}
