package mobile_dev.mobile_dev.repository;

import mobile_dev.mobile_dev.BuildConfig;
import mobile_dev.mobile_dev.activity.ICallback;
import mobile_dev.mobile_dev.api.Consumer;

public class MenuRepository {

    private Consumer consumer;

    public MenuRepository(ICallback callback) {
        this.consumer = new Consumer(callback);
    }

    public void find(int id) {
        consumer.setUrl(BuildConfig.SERVER_URL + "/menus/get/" + id);
        consumer.getString();
    }

    public void all() {
        consumer.setUrl(BuildConfig.SERVER_URL + "/menus/all");
        consumer.getString();
    }
}
