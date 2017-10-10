package mobile_dev.mobile_dev.api;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import mobile_dev.mobile_dev.repository.IRepository;


public class Consumer {

    private String url = null;
    private IRepository repo;

    public Consumer(String url, IRepository repo) {
        this.url = url;
        this.repo = repo;
    }

    public Consumer(IRepository repo) { this.repo = repo; }

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
