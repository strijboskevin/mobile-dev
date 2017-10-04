package mobile_dev.mobile_dev.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import mobile_dev.mobile_dev.connection.Connection;
import mobile_dev.mobile_dev.model.User;

/**
 * Created by kevin on 03/10/2017.
 */

public class UserRepository implements IRepository {

    private Connection connection;
    private String result;
    private Gson gson;

    public UserRepository() {
        this.connection = new Connection(this);
        this.gson = new Gson();
    }

    public User find(String userName) {
        connection.setUrl("http://10.0.2.2:4041/mdev-api/users/get/" + userName);
        connection.getString();
        User user = gson.fromJson(this.result, User.class);
        return user;
    }

    public List<User> all() {
        connection.setUrl("http://10.0.2.2:4041/mdev-api/users/all");
        connection.getString();
        List<User> users = gson.fromJson(this.result, new TypeToken<List<User>>(){}.getType());
        return users;
    }

    @Override
    public void setString(String result) {
        this.result = result;
    }
}
