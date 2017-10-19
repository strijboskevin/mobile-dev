package mobile_dev.mobile_dev.util;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import mobile_dev.mobile_dev.model.User;

public class PostInput extends AsyncTask<String, Void, Void> {

    private User user;

    public PostInput(User user) {
        this.user = user;
    }

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
            connection.setRequestMethod("POST");
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
        } catch (Exception ex) {
            Log.d("Error", ex.getStackTrace().toString());
        } finally {
            return null;
        }
    }
}
