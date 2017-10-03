package be.pxl.mdev.service;

import be.pxl.mdev.entity.Menu;

import java.util.List;

/**
 * Created by kevin on 02/10/2017.
 */
public interface IMenuService {

    public Menu find(int id);
    public List<Menu> all();
    public List<Menu> findByRestaurant(int restaurant);
}
