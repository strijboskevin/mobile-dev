package be.pxl.mdev.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

/**
 * Created by kevin on 02/10/2017.
 */

@Entity
@Table(name="Restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String address;
    @ManyToMany
    @JsonBackReference(value = "dishes_restaurants")
    private List<Dish> dishes;
    @OneToMany(mappedBy = "restaurant")
    @JsonManagedReference(value = "restaurants_menus")
    private List<Menu> menus;
    private String city;

    public Restaurant() {};

    public Restaurant(String name, String address, String city) {
        this.name = name;
        this.address = address;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
