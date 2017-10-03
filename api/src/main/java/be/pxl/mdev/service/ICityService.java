package be.pxl.mdev.service;

import be.pxl.mdev.entity.City;

import java.util.List;

/**
 * Created by kevin on 02/10/2017.
 */
public interface ICityService {

    public City find(String postalCode);
    public List<City> all();
}
