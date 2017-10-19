package mobile_dev.mobile_dev.google;

import mobile_dev.mobile_dev.BuildConfig;
import mobile_dev.mobile_dev.activity.IDistanceCalculatorActivity;
import mobile_dev.mobile_dev.util.DistanceCalculatorInput;

public class DistanceCalculator {

    private String from;
    private String to;
    private String link;
    private IDistanceCalculatorActivity activity;

    public DistanceCalculator(String from, String to, IDistanceCalculatorActivity activity) {
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

    private void build() {
        String[] fromSplit = from.split(" ");
        String[] toSplit = to.split(" ");
        link = BuildConfig.MATRIX_URL + fromSplit[0] + "+" + fromSplit[1] + "+" + fromSplit[2] + "&destinations=" + toSplit[0] + "+" + toSplit[1] + "+" + toSplit[2] + "&key=" + BuildConfig.MATRIX_KEY;
    }

    public void calculate() {
        if (link != null) {
            DistanceCalculatorInput input = new DistanceCalculatorInput(activity);
            input.execute(this.link);
        }
    }


}
