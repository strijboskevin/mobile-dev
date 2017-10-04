package mobile_dev.mobile_dev.container;

import java.io.Serializable;
import java.util.List;
import mobile_dev.mobile_dev.model.Restaurant;

/**
 * Created by kevin on 03/10/2017.
 */

public class RestaurantContainer implements Serializable {

    private List<Restaurant> restaurants;

    public RestaurantContainer(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public List<Restaurant> getRestaurants() { return this.restaurants; }

}
