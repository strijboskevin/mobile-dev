package mobile_dev.mobile_dev.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;

import mobile_dev.mobile_dev.BuildConfig;
import mobile_dev.mobile_dev.consumer.Consumer;
import mobile_dev.mobile_dev.model.City;
import mobile_dev.mobile_dev.R;

/**
 * Created by kevin on 03/10/2017.
 */

public class CityRepository implements IRepository {

    private Consumer consumer;
    private String result;
    private Gson gson;

    public CityRepository() {
        this.consumer = new Consumer(this);
        this.gson = new Gson();
    }

    public City find(String postalCode) {
<<<<<<< HEAD
        connection.setUrl("http://10.0.2.2:4041/mdev-api/cities/get/" + postalCode);
        connection.getString();
=======
        consumer.setUrl(BuildConfig.SERVER_URL + "/cities/get/" + postalCode);
        consumer.getString();
>>>>>>> fc47ffafc14dcd97ceec53b7736ec3c2836dfb77
        City city = gson.fromJson(this.result, City.class);
        return city;
    }

    public List<City> all() {
<<<<<<< HEAD
        connection.setUrl("http://10.0.2.2:4041/mdev-api/cities/all");
        connection.getString();
=======
        consumer.setUrl(BuildConfig.SERVER_URL + "/cities/all");
        consumer.getString();
>>>>>>> fc47ffafc14dcd97ceec53b7736ec3c2836dfb77
        List<City> cities = gson.fromJson(this.result, new TypeToken<List<City>>(){}.getType());
        return cities;
    }

    @Override
    public void setString(String result) {
        this.result = result;
    }
}
