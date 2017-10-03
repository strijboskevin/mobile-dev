package be.pxl.mdev.repository;

import be.pxl.mdev.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kevin on 02/10/2017.
 */

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {
}
