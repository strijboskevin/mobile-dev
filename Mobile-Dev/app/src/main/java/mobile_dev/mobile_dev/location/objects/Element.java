package mobile_dev.mobile_dev.location.objects;

/**
 * Created by kevin on 03/10/2017.
 */

public class Element {

    private Distance distance;
    private Duration duration;

    public Element(Distance distance, Duration duration) {
        this.distance = distance;
        this.duration = duration;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
