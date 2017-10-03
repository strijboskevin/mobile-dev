package be.pxl.mdev.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by kevin on 02/10/2017.
 */

@Entity
@Table(name = "cities")
public class City {

    @Id
    private String postalCode;
    private String name;
    @OneToMany(mappedBy = "city")
    @JsonBackReference("cities_users")
    private List<User> users;
    @OneToMany(mappedBy = "city")
    @JsonBackReference("cities_restaurants")
    private List<Restaurant> restaurants;

    public City() {};

    public City(String postalCode, String name) {
        this.postalCode = postalCode;
        this.name = name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
