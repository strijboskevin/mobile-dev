package mobile_dev.mobile_dev.google.json;

import java.util.List;

/**
 * Created by kevin on 03/10/2017.
 */

public class Row {

    private List<Element> elements;

    public Row(List<Element> elements) {
        this.elements = elements;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }
}
