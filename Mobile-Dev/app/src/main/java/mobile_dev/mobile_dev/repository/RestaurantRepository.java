package mobile_dev.mobile_dev.repository;

import mobile_dev.mobile_dev.BuildConfig;
import mobile_dev.mobile_dev.activity.IActivity;
import mobile_dev.mobile_dev.api.Consumer;

public class RestaurantRepository {

    private Consumer consumer;

    public RestaurantRepository(IActivity activity) {
        this.consumer = new Consumer(activity);
    }

    public void find(int id) {
        consumer.setUrl(BuildConfig.SERVER_URL + "/restaurants/get/" + id);
        consumer.getString();
    }

    public void all() {
        consumer.setUrl(BuildConfig.SERVER_URL + "/restaurants/all");
        consumer.getString();
    }
}
