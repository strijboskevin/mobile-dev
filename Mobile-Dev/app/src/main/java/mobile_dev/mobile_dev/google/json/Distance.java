package mobile_dev.mobile_dev.google.json;

/**
 * Created by kevin on 03/10/2017.
 */

public class Distance {

    private String text;
    private int value;

    public Distance(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}