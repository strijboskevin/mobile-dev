package mobile_dev.mobile_dev.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;

import mobile_dev.mobile_dev.BuildConfig;
import mobile_dev.mobile_dev.activity.IActivity;
import mobile_dev.mobile_dev.api.Consumer;
import mobile_dev.mobile_dev.model.Dish;

public class DishRepository {

    private Consumer consumer;
    private String result;
    private Gson gson;
    private IActivity activity;

    public DishRepository(IActivity activity) {
        this.consumer = new Consumer(activity);
        this.activity = activity;
    }

    public void find(int id) {
        consumer.setUrl(BuildConfig.SERVER_URL + "/dishes/get/" + id);
        consumer.getString();
    }

    public void all() {
        consumer.setUrl(BuildConfig.SERVER_URL + "/dishes/all");
        consumer.getString();
    }
}
