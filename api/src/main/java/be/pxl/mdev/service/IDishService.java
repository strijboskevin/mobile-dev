package be.pxl.mdev.service;

import be.pxl.mdev.entity.Dish;

import java.util.List;

/**
 * Created by kevin on 02/10/2017.
 */
public interface IDishService {

    public Dish find(int dish);
    public List<Dish> all();
}
