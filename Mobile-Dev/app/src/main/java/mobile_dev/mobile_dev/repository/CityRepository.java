package mobile_dev.mobile_dev.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;

import mobile_dev.mobile_dev.BuildConfig;
import mobile_dev.mobile_dev.api.Consumer;
import mobile_dev.mobile_dev.model.City;

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
        consumer.setUrl(BuildConfig.SERVER_URL + "/cities/get/" + postalCode);
        consumer.getString();
        City city = gson.fromJson(this.result, City.class);
        return city;
    }

    public List<City> all() {
        consumer.setUrl(BuildConfig.SERVER_URL + "/cities/all");
        consumer.getString();
        List<City> cities = gson.fromJson(this.result, new TypeToken<List<City>>(){}.getType());
        return cities;
    }

    @Override
    public void setString(String result) {
        this.result = result;
    }
}
