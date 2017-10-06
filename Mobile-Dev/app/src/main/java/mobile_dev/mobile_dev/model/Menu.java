package mobile_dev.mobile_dev.model;

import java.io.Serializable;

/**
 * Created by kevin on 03/10/2017.
 */

public class Menu implements Serializable {

    private int id;
    private String name;
    private int price;

    public Menu(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Menu() {};

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

}
