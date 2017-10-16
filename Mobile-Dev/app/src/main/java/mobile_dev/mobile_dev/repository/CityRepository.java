package mobile_dev.mobile_dev.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;

import mobile_dev.mobile_dev.BuildConfig;
import mobile_dev.mobile_dev.activity.IActivity;
import mobile_dev.mobile_dev.api.Consumer;
import mobile_dev.mobile_dev.model.City;
import mobile_dev.mobile_dev.R;

public class CityRepository  {

    private Consumer consumer;
    private IActivity activity;

    public CityRepository(IActivity activity) {
        this.consumer = new Consumer(activity);
        this.activity = activity;
    }

    public void find(String postalCode) {
        consumer.setUrl(BuildConfig.SERVER_URL + "/cities/get/" + postalCode);
        consumer.getString();
    }

    public void all() {
        consumer.setUrl(BuildConfig.SERVER_URL + "/cities/all");
        consumer.getString();
    }
}
