package mobile_dev.mobile_dev.connection;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import mobile_dev.mobile_dev.repository.IRepository;

/**
 * Created by kevin on 03/10/2017.
 */

public class Connection {

    private String url = null;
    private IRepository repo;

    public Connection(String url, IRepository repo) {
        this.url = url;
        this.repo = repo;
    }

    public Connection(IRepository repo) { this.repo = repo; }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void getString() {
        if (url != null) {
            RetrieveInput input = new RetrieveInput();
            input.execute(url);
            try {
                repo.setString(input.get(15000, TimeUnit.MILLISECONDS));
            } catch (InterruptedException e) {
                Log.d("Error", e.getStackTrace().toString());
            } catch (ExecutionException e) {
                Log.d("Error", e.getStackTrace().toString());
            } catch (TimeoutException e) {
                Log.d("Error", e.getStackTrace().toString());
            }
        }
    }

    class RetrieveInput extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000); //set timeout to 5 seconds
                connection.setRequestMethod("GET");
                int res = connection.getResponseCode();
                InputStream stream = connection.getInputStream();
                Scanner s = new Scanner(stream).useDelimiter("\\A");
                result = s.hasNext() ? s.next() : "";
            } catch (IOException ex) {
                Log.d("Error", ex.getStackTrace().toString());
            } catch (Exception ex)
                {
                    ex.getStackTrace();


            } finally {
                return result;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            repo.setString(result);
        }
    }

}
