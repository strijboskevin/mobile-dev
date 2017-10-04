package mobile_dev.mobile_dev.activity.adapter.utils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kevin on 04/10/2017.
 */

public class OrderElementContainer implements Serializable {

    private List<OrderElement> orderElements;

    public OrderElementContainer(List<OrderElement> orderElements) {
        this.orderElements = orderElements;
    }

    public List<OrderElement> getOrderElements() {
        return orderElements;
    }

    public void setOrderElements(List<OrderElement> orderElements) {
        this.orderElements = orderElements;
    }
}
