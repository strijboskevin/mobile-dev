package mobile_dev.mobile_dev.activity.adapter.utils;

import java.io.Serializable;

import mobile_dev.mobile_dev.model.Menu;

/**
 * Created by kevin on 04/10/2017.
 */

public class OrderElement implements Serializable {

    private Menu menu;
    private int amount;

    public OrderElement(Menu menu, int amount) {
        this.menu = menu;
        this.amount = amount;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
