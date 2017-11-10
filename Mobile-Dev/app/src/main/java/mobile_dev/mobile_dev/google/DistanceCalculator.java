package mobile_dev.mobile_dev.google;

import mobile_dev.mobile_dev.BuildConfig;
import mobile_dev.mobile_dev.activity.ICallback;
import mobile_dev.mobile_dev.util.GetInput;

public class DistanceCalculator {

    private String from;
    private String to;
    private String link;
    private ICallback callback;

    public DistanceCalculator(String from, String to, ICallback callback) {
        this.from = from;
        this.to = to;
        this.callback = callback;
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

    private void build() {
        String[] fromSplit = from.split(" ");
        String[] toSplit = to.split(" ");
        link = BuildConfig.MATRIX_URL + fromSplit[0] + "+" + fromSplit[1] + "+" + fromSplit[2] + "&destinations=" + toSplit[0] + "+" + toSplit[1] + "+" + toSplit[2] + "&key=" + BuildConfig.MATRIX_KEY;
    }

    public void calculate() {
        if (link != null) {
            GetInput input = new GetInput(callback);
            input.execute(this.link);
        }
    }
}
