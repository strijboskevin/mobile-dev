package mobile_dev.mobile_dev.google;

import mobile_dev.mobile_dev.BuildConfig;
import mobile_dev.mobile_dev.activity.container.ICoordinatesConverterActivity;
import mobile_dev.mobile_dev.util.CoordinatesConverterInput;

public class CoordinatesConverter {

    private double longitude;
    private double latitude;
    private ICoordinatesConverterActivity activity;
    private String link;

    public CoordinatesConverter(double longitude, double latitude, ICoordinatesConverterActivity activity) {
        this.activity = activity;
        this.longitude = longitude;
        this.latitude = latitude;
        build();
    }

    private void build() {
        this.link = BuildConfig.GEOCODING_URL + String.valueOf(latitude) + ",%" + String.valueOf(longitude) + "&key=" + BuildConfig.GEOCODING_KEY;
    }

    public void convert() {
        if (link != null) {
            CoordinatesConverterInput input = new CoordinatesConverterInput(activity);
            input.execute(this.link);
        }
    }
}
