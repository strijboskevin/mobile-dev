package mobile_dev.mobile_dev.connection;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by kevin on 03/10/2017.
 */

public class Connection {

    private URL url = null;
    private HttpURLConnection connection = null;

    public Connection(String url) throws MalformedURLException {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException ex) {
            throw ex;
        }
    }

    public Connection() {};

    public URL getUrl() {
        return url;
    }

    public void setUrl(String url) throws MalformedURLException, IOException {
        try {
            this.url = new URL(url);
            init();
        } catch (MalformedURLException ex) {
            throw ex;
        } catch (IOException ex) {
            throw ex;
        }
    }

    public HttpURLConnection getConnection() { return connection; }

    public String getString() throws IOException {
        if (connection != null) {
            try {
                InputStream stream = connection.getInputStream();
                Scanner s = new Scanner(stream).useDelimiter("\\A");
                String result = s.hasNext() ? s.next() : "";
                return result;
            } catch (IOException ex) {
                throw ex;
            }
        } else {
            return null;
        }
    }

    private void init() throws IOException {
        if (url != null) {
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");
            } catch (IOException ex) {
                throw ex;
            }
        }
    }

}
