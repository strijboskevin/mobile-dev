package mobile_dev.mobile_dev.activity.adapter.bundle;

import mobile_dev.mobile_dev.google.json.MapsContainer;
import mobile_dev.mobile_dev.model.Restaurant;

public class RestaurantBundle {

    private MapsContainer container;
    private Restaurant restaurant;

    public RestaurantBundle(MapsContainer container, Restaurant restaurant) {
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
