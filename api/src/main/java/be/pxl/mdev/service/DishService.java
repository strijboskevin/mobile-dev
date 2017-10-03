package be.pxl.mdev.service;

import be.pxl.mdev.entity.Dish;
import be.pxl.mdev.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by kevin on 02/10/2017.
 */

@Service
@Transactional
public class DishService implements IDishService {

    @Autowired
    private DishRepository repo;

    @Override
    public Dish find(int dish) {
        return repo.findOne(dish);
    }

    @Override
    public List<Dish> all() {
        return repo.findAll();
    }
}
