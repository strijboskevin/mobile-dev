package mobile_dev.mobile_dev.api;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import mobile_dev.mobile_dev.model.User;

/**
 * Created by kevin on 03/10/2017.
 */

public class Poster {

    private String url = null;
    private User user;

    public Poster(String url, User user) {
        this.url = url;
        this.user = user;
    }

    public Poster(User user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void update() {
        if (url != null) {
            ApiPoster input = new ApiPoster();
            input.execute(url);
        }
    }

    class ApiPoster extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000); //set timeout to 5 seconds
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream ());
                wr.write(new Gson().toJson(user).toString());
                wr.close();
                connection.getInputStream();
            } catch (IOException ex) {
                Log.d("Error", ex.getStackTrace().toString());
            } catch (Exception ex)
            {
                ex.getStackTrace();
            } finally {
                return null;
            }
        }
    }

}
