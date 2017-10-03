package be.pxl.mdev.service;

import be.pxl.mdev.entity.Restaurant;

import java.util.List;

/**
 * Created by kevin on 02/10/2017.
 */
public interface IRestaurantService {

    public Restaurant find (int id);
    public List<Restaurant> all();
    public List<Restaurant> findByDish(int dish);
}
