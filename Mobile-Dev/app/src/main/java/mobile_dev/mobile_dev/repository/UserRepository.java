package mobile_dev.mobile_dev.repository;

import mobile_dev.mobile_dev.BuildConfig;
import mobile_dev.mobile_dev.activity.ICallback;
import mobile_dev.mobile_dev.api.Consumer;
import mobile_dev.mobile_dev.api.Poster;
import mobile_dev.mobile_dev.api.Putter;
import mobile_dev.mobile_dev.model.User;

public class UserRepository  {

    private Consumer consumer;

    public UserRepository(ICallback callback) {
        this.consumer = new Consumer(callback);
    }

    public void all() {
        consumer.setUrl(BuildConfig.SERVER_URL + "/users/all");
        consumer.getString();
    }

    public void find(String userName) {
        consumer.setUrl(BuildConfig.SERVER_URL + "/users/get/" + userName);
        consumer.getString();
    }

    public void add(User user) {
        Poster poster = new Poster(user);
        poster.setUrl(BuildConfig.SERVER_URL + "/users/add");
        poster.update();
    }

    public void update(User user) {
        Putter putter = new Putter(user);
        putter.setUrl(BuildConfig.SERVER_URL + "/users/update");
        putter.update();
    }
}
