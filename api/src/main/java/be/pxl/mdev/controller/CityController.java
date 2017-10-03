package be.pxl.mdev.controller;

import be.pxl.mdev.entity.City;
import be.pxl.mdev.service.ICityService;
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
@RequestMapping(CityController.BASE_URL)
public class CityController {

    @Autowired
    private ICityService service;

    public static final String BASE_URL = "/cities";

    @RequestMapping(value = "/get/{postal_code}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public ResponseEntity<City> getCityByPostalCode(@PathVariable("postal_code") String postal_code) {
        HttpStatus status = HttpStatus.OK;
        City city = service.find(postal_code);

        System.out.println("hehe");
        System.out.println(postal_code);

        if (city == null)
            status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<City>(city, status);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public List<City> getAllCities() {
        return service.all();
    }

}
