package be.pxl.mdev.service;

import be.pxl.mdev.entity.Menu;
import be.pxl.mdev.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by kevin on 02/10/2017.
 */

@Service
@Transactional
public class MenuService implements IMenuService {

    @Autowired
    private MenuRepository repo;

    @Override
    public Menu find(int id) {
        return repo.findOne(id);
    }

    @Override
    public List<Menu> all() {
        return repo.findAll();
    }

    @Override
    public List<Menu> findByRestaurant(int restaurant) {
        return repo.findByRestaurant(restaurant);
    }
}
