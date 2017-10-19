package mobile_dev.mobile_dev.repository;

import mobile_dev.mobile_dev.BuildConfig;
import mobile_dev.mobile_dev.activity.IActivity;
import mobile_dev.mobile_dev.api.Consumer;

public class CityRepository  {

    private Consumer consumer;

    public CityRepository(IActivity activity) {
        this.consumer = new Consumer(activity);
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
