package be.pxl.mdev.controller;

import be.pxl.mdev.entity.Dish;
import be.pxl.mdev.entity.Menu;
import be.pxl.mdev.service.IDishService;
import be.pxl.mdev.service.IMenuService;
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
@RequestMapping(MenuController.BASE_URL)
public class MenuController {

    @Autowired
    private IMenuService service;

    public static final String BASE_URL = "/menus";

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public ResponseEntity<Menu> getMenuById(@PathVariable("id") int id) {
        HttpStatus status = HttpStatus.OK;

        Menu menu = service.find(id);

        if (menu == null)
            status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<Menu>(menu, status);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public List<Menu> getAllMenus() {
        return service.all();
    }

}
