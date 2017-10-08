package mobile_dev.mobile_dev.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;

import mobile_dev.mobile_dev.BuildConfig;
import mobile_dev.mobile_dev.consumer.Consumer;
import mobile_dev.mobile_dev.model.User;

/**
 * Created by kevin on 03/10/2017.
 */

public class UserRepository implements IRepository {

    private Consumer consumer;
    private String result;
    private Gson gson;

    public UserRepository() {
        this.consumer = new Consumer(this);
        this.gson = new Gson();
    }

    public User find(String userName) {
        consumer.setUrl(BuildConfig.SERVER_URL + "/users/get/" + userName);
        consumer.getString();
        User user = gson.fromJson(this.result, User.class);
        return user;
    }

    public List<User> all() {
        consumer.setUrl(BuildConfig.SERVER_URL + "/users/all");
        consumer.getString();
        List<User> users = gson.fromJson(this.result, new TypeToken<List<User>>(){}.getType());
        return users;
    }

    @Override
    public void setString(String result) {
        this.result = result;
    }
}
