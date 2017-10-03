package be.pxl.mdev.repository;

import be.pxl.mdev.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kevin on 02/10/2017.
 */
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query("from Restaurant r where dish_id =:dish_id")
    List<Restaurant> findByDish(@Param("dish_id") int dish_id);

}
