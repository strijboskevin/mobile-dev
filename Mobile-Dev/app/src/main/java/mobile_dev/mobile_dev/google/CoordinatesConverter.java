package mobile_dev.mobile_dev.google;

import mobile_dev.mobile_dev.BuildConfig;
import mobile_dev.mobile_dev.activity.ICallback;
import mobile_dev.mobile_dev.util.GetInput;

public class CoordinatesConverter {

    private double longitude;
    private double latitude;
    private ICallback callback;
    private String link;

    public CoordinatesConverter(double longitude, double latitude, ICallback callback) {
        this.callback = callback;
        this.longitude = longitude;
        this.latitude = latitude;
        build();
    }

    private void build() {
        this.link = BuildConfig.GEOCODING_URL + String.valueOf(latitude) + "," + String.valueOf(longitude) + "&key=" + BuildConfig.GEOCODING_KEY;
    }

    public void convert() {
        if (link != null) {
            GetInput input = new GetInput(callback);
            input.execute(this.link);
        }
    }
}
