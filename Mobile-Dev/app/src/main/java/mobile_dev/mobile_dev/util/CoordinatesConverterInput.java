package mobile_dev.mobile_dev.util;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import mobile_dev.mobile_dev.activity.IDistanceCalculatorActivity;
import mobile_dev.mobile_dev.activity.container.ICoordinatesConverterActivity;

public class CoordinatesConverterInput extends AsyncTask<String, Void, String> {

    private ICoordinatesConverterActivity activity;

    public CoordinatesConverterInput(ICoordinatesConverterActivity activity) {
        this.activity = activity;
    }

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
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            InputStream stream = connection.getInputStream();
            Scanner s = new Scanner(stream).useDelimiter("\\A");
            result = s.hasNext() ? s.next() : "";
        } catch (IOException ex) {
            Log.d("Error", ex.getStackTrace().toString());
        } catch (Exception ex) {
            Log.d("Error", ex.getStackTrace().toString());
        } finally {
            return result;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        activity.setAddress(result);
    }
}

