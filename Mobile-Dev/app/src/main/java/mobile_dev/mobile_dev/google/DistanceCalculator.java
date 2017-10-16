package mobile_dev.mobile_dev.google;

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

import mobile_dev.mobile_dev.BuildConfig;
import mobile_dev.mobile_dev.activity.IActivity;
import mobile_dev.mobile_dev.activity.RestaurantListActivity;
import mobile_dev.mobile_dev.activity.adapter.RestaurantListAdapter;

public class DistanceCalculator {

    private String from;
    private String to;
    private String link;
    private IActivity activity;

    public DistanceCalculator(String from, String to, RestaurantListActivity activity) {
        this.from = from;
        this.to = to;
        this.activity = activity;
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
        link = BuildConfig.MATRIX_URL + fromSplit[0] + "+" + fromSplit[1] + "+" + fromSplit[2] + "&destinations=" + toSplit[0] + "+" + toSplit[1] + "+" + toSplit[2] + "&key" + BuildConfig.MATRIX_KEY;
        System.out.println("test");
    }

    public void calculate() {
        if (link != null) {
            RetrieveInput input = new RetrieveInput();
            input.execute(this.link);
            try {
                activity.setJson(input.get(15000, TimeUnit.MILLISECONDS));
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
            activity.setJson(result);
        }
    }

}
