package mobile_dev.mobile_dev.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import mobile_dev.mobile_dev.connection.Connection;
import mobile_dev.mobile_dev.model.Menu;

/**
 * Created by kevin on 03/10/2017.
 */

public class MenuRepository implements IRepository {

    private Connection connection;
    private String result;
    private Gson gson;

    public MenuRepository() {
        this.connection = new Connection(this);
        this.gson = new Gson();
    }

    public Menu find(int id) {
        connection.setUrl("http://10.0.2.2:8080/mdev-api/menus/get/" + id);
        connection.getString();
        Menu menu = gson.fromJson(this.result, Menu.class);
        return menu;
    }

    public List<Menu> all() {
        connection.setUrl("http://10.0.2.2:8080/mdev-api/menus/all");
        connection.getString();
        List<Menu> menus = gson.fromJson(this.result, new TypeToken<List<Menu>>(){}.getType());
        return menus;
    }

    @Override
    public void setString(String result) {
        this.result = result;
    }
}
