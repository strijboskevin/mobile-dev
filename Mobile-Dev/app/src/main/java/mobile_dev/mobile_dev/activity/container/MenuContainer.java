package mobile_dev.mobile_dev.activity.container;

import java.io.Serializable;
import java.util.List;

import mobile_dev.mobile_dev.model.Menu;

public class MenuContainer implements Serializable {

    private List<Menu> menus;

    public MenuContainer(List<Menu> menus) {
        this.menus = menus;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

}
