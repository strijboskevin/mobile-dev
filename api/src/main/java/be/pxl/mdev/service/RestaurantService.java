package be.pxl.mdev.service;

import be.pxl.mdev.entity.Restaurant;
import be.pxl.mdev.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by kevin on 02/10/2017.
 */

@Service
@Transactional
public class RestaurantService implements IRestaurantService {

    @Autowired
    private RestaurantRepository repo;

    @Override
    public Restaurant find(int id) {
        return repo.findOne(id);
    }

    @Override
    public List<Restaurant> all() {
        return repo.findAll();
    }

    @Override
    public List<Restaurant> findByDish(int dish) {
        return repo.findByDish(dish);
    }
}
