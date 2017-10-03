package be.pxl.mdev.repository;

import be.pxl.mdev.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kevin on 02/10/2017.
 */

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Query("from Menu m where restaurant_id =:restaurant_id")
    List<Menu> findByRestaurant(@Param("restaurant_id") int restaurant_id);
}
