package mobile_dev.mobile_dev.adapter.bundle;

import mobile_dev.mobile_dev.location.objects.MapsContainer;
import mobile_dev.mobile_dev.model.Restaurant;

/**
 * Created by kevin on 04/10/2017.
 */

public class Bundle {

    private MapsContainer container;
    private Restaurant restaurant;

    public Bundle(MapsContainer container, Restaurant restaurant) {
        this.container = container;
        this.restaurant = restaurant;
    }

    public MapsContainer getContainer() {
        return container;
    }

    public void setContainer(MapsContainer container) {
        this.container = container;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
