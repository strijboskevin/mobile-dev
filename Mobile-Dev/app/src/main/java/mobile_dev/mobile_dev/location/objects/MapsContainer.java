package mobile_dev.mobile_dev.location.objects;

import java.util.List;

/**
 * Created by kevin on 03/10/2017.
 */

public class MapsContainer {

    private List<String> destination_addresses;
    private List<String> origin_addresses;
    private List<Row> rows;

    public MapsContainer(List<String> destination_addresses, List<String> origin_addresses, List<Row> rows) {
        this.destination_addresses = destination_addresses;
        this.origin_addresses = origin_addresses;
        this.rows = rows;
    }

    public MapsContainer() {};

    public List<String> getDestination_addresses() {
        return destination_addresses;
    }

    public void setDestination_addresses(List<String> destination_addresses) {
        this.destination_addresses = destination_addresses;
    }

    public List<String> getOrigin_addresses() {
        return origin_addresses;
    }

    public void setOrigin_addresses(List<String> origin_addresses) {
        this.origin_addresses = origin_addresses;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }
}
