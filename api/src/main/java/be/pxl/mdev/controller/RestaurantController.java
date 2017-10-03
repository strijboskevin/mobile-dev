package be.pxl.mdev.controller;

import be.pxl.mdev.entity.Restaurant;
import be.pxl.mdev.service.IRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by kevin on 02/10/2017.
 */

@RestController
@RequestMapping(RestaurantController.BASE_URL)
public class RestaurantController {

    @Autowired
    private IRestaurantService service;

    public static final String BASE_URL = "/restaurants";

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable("id") int id) {
        HttpStatus status = HttpStatus.OK;

        Restaurant restaurant = service.find(id);

        if (restaurant == null)
            status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<Restaurant>(restaurant, status);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public List<Restaurant> getAllRestaurants() {
        return service.all();
    }

}
