package mobile_dev.mobile_dev.location;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import mobile_dev.mobile_dev.adapter.RestaurantListAdapter;
import mobile_dev.mobile_dev.location.objects.MapsContainer;

/**
 * Created by kevin on 03/10/2017.
 */

public class DistanceCalculator {

    private String from;
    private String to;
    private String link;
    private RestaurantListAdapter adapter;

    public DistanceCalculator(String from, String to, RestaurantListAdapter adapter) {
        this.from = from;
        this.to = to;
        this.adapter = adapter;
        build();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void build() {
        String[] fromSplit = from.split(" ");
        String[] toSplit = to.split(" ");
        link = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" + fromSplit[0] + "+" + fromSplit[1] + "+" + fromSplit[2] + "&destinations=" + toSplit[0] + "+" + toSplit[1] + "+" + toSplit[2];
    }

    public void calculate() {
        if (link != null) {
            RetrieveInput input = new RetrieveInput();
            input.execute(this.link);
            try {
                adapter.setJson(input.get(15000, TimeUnit.MILLISECONDS));
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
            adapter.setJson(result);
        }
    }

}
