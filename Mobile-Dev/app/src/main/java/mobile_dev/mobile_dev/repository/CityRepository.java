package mobile_dev.mobile_dev.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import mobile_dev.mobile_dev.connection.Connection;
import mobile_dev.mobile_dev.model.City;
import mobile_dev.mobile_dev.R;

/**
 * Created by kevin on 03/10/2017.
 */

public class CityRepository implements IRepository {

    private Connection connection;
    private String result;
    private Gson gson;

    public CityRepository() {
        this.connection = new Connection(this);
        this.gson = new Gson();
    }

    public City find(String postalCode) {
        connection.setUrl("http://10.0.2.2:4041/mdev-api/cities/get/" + postalCode);
        connection.getString();
        City city = gson.fromJson(this.result, City.class);
        return city;
    }

    public List<City> all() {
        connection.setUrl("http://10.0.2.2:4041/mdev-api/cities/all");
        connection.getString();
        List<City> cities = gson.fromJson(this.result, new TypeToken<List<City>>(){}.getType());
        return cities;
    }

    @Override
    public void setString(String result) {
        this.result = result;
    }
}
