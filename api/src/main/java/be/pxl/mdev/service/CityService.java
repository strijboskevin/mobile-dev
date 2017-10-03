package be.pxl.mdev.service;

import be.pxl.mdev.entity.City;
import be.pxl.mdev.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by kevin on 02/10/2017.
 */

@Service
@Transactional
public class CityService implements ICityService {

    @Autowired
    private CityRepository repo;

    @Override
    public City find(String postalCode) {
        return repo.findOne(postalCode);
    }

    @Override
    public List<City> all() {
        return repo.findAll();
    }
}
