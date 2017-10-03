package mobile_dev.mobile_dev.repository;

import android.util.Log;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.MalformedURLException;
import mobile_dev.mobile_dev.connection.Connection;
import mobile_dev.mobile_dev.model.User;

/**
 * Created by kevin on 03/10/2017.
 */

public class UserRepository {

    private Connection connection;
    private Gson gson;

    public UserRepository() {
        this.connection = new Connection();
        this.gson = new Gson();
    }

    public User find(String userName) {
        Log.d("eh", "hoho");
        try {
            connection.setUrl("http://localhost:8080/api-0.1.0/users/get/" + userName);
            String json = connection.getString();
            User user = gson.fromJson(json, User.class);
            Log.d("oh","HEHE");
            Log.d("user:", user.getAddress());
            return user;
        } catch (MalformedURLException ex) {
            Log.d("Error", "Error fetching user with username: " + userName + ".\n" );
            Log.d("STACKTRACE", ex.getStackTrace().toString());
        } catch (IOException ex) {
            Log.d("Error", "Error fetching user with username: " + userName + ".\n");
            Log.d("STACKTRACE", ex.getStackTrace().toString());
        } finally {
            return null;
        }
    }

}
