package be.pxl.mdev.controller;

import be.pxl.mdev.entity.Dish;
import be.pxl.mdev.service.IDishService;
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
@RequestMapping(DishController.BASE_URL)
public class DishController {

    @Autowired
    private IDishService service;

    public static final String BASE_URL = "/dishes";

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public ResponseEntity<Dish> getDishById(@PathVariable("id") int id) {
        HttpStatus status = HttpStatus.OK;
        Dish dish = service.find(id);

        if (dish == null)
            status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<Dish>(dish, status);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public List<Dish> getAllDishes() {
        return service.all();
    }

}
