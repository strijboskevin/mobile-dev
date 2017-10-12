package mobile_dev.mobile_dev.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;

import mobile_dev.mobile_dev.BuildConfig;
import mobile_dev.mobile_dev.activity.IActivity;
import mobile_dev.mobile_dev.api.Consumer;
import mobile_dev.mobile_dev.api.Poster;
import mobile_dev.mobile_dev.api.Putter;
import mobile_dev.mobile_dev.model.User;

public class UserRepository implements IRepository {

    private Consumer consumer;
    private String result;
    private Gson gson;
    private IActivity activity;

    public UserRepository(IActivity activity) {
        this.consumer = new Consumer(activity);
        this.activity = activity;
        this.gson = new Gson();
    }

    public List<User> all() {
        consumer.setUrl(BuildConfig.SERVER_URL + "/users/all");
        consumer.getString();
        List<User> users = gson.fromJson(this.result, new TypeToken<List<User>>(){}.getType());
        return users;
    }

<<<<<<< HEAD
    public void find(String userName) {
        consumer.setUrl(BuildConfig.SERVER_URL + "/users/get/" + userName);
        consumer.getString();
=======
    public User find(String userName) {
        configureConsumer(userName);
        User user = gson.fromJson(this.result, User.class);
        return user;
>>>>>>> f31dff05e80327986c0fb6ebbd8403edc9e35bbf
    }

    private void configureConsumer(String userName) {
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

    @Override
    public void setString(String result) {
        this.result = result;
    }
}
