package mobile_dev.mobile_dev.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.adapter.bundle.RestaurantBundle;
import mobile_dev.mobile_dev.google.DistanceCalculator;
import mobile_dev.mobile_dev.google.json.MapsContainer;
import mobile_dev.mobile_dev.model.Restaurant;

/**
 * Created by Fred on 3/10/2017.
 */

public class RestaurantListAdapter extends BaseAdapter {

    private List<Restaurant> restaurants;
    private Context context;
    private LayoutInflater inflater;
    private String json;
    private List<RestaurantBundle> restaurantBundles;

    public RestaurantListAdapter(Context context, List<Restaurant> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
        restaurantBundles = new ArrayList<RestaurantBundle>();
        createBundles();
    }

    private void createBundles() {
        int i;

        for (i=0; i < restaurants.size() ;i++) {
            String from = "Salvatorstraat 20 Hasselt";
            String to = restaurants.get(i).getAddress() + " " + restaurants.get(i).getCity().getName();
            new DistanceCalculator(from, to, this).calculate();
            MapsContainer mapsContainer = new Gson().fromJson(this.json, MapsContainer.class);
            restaurantBundles.add(i, new RestaurantBundle(mapsContainer, restaurants.get(i)));
        }

        sort();
    }

    private void sort() {
        int i, j;
        for (i=0; i < restaurantBundles.size()-1 ; i++) {
            for(j=1; j < restaurantBundles.size()-i ; j++) {
                int first, second;
                first = restaurantBundles.get(j).getContainer().getRows().get(0).getElements().get(0).getDistance().getValue();
                second = restaurantBundles.get(j-1).getContainer().getRows().get(0).getElements().get(0).getDistance().getValue();
                if (first < second) {
                    RestaurantBundle dummy = restaurantBundles.get(j-1);
                    restaurantBundles.set(j-1, restaurantBundles.get(j));
                    restaurantBundles.set(j, dummy);
                }
            }
        }
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getJson() { return json; }

    @Override
    public int getCount() {
        return restaurants.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int postion, View view, ViewGroup parent) {
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_restaurants_list_element, null);
        }

        TextView name = (TextView) view.findViewById(R.id.activity_restaurant_list_element_textview_name);
        TextView address = (TextView) view.findViewById(R.id.activity_restaurant_list_element_textview_address);
        TextView distance = (TextView) view.findViewById(R.id.activity_restaurant_list_element_textview_distance);
        TextView duration = (TextView) view.findViewById(R.id.activity_restaurant_list_element_textview_duration);
        String distanceText = restaurantBundles.get(postion).getContainer().getRows().get(0).getElements().get(0).getDistance().getText();
        String durationText = restaurantBundles.get(postion).getContainer().getRows().get(0).getElements().get(0).getDuration().getText();
        name.setText(restaurantBundles.get(postion).getRestaurant().getName());
        address.setText(restaurantBundles.get(postion).getRestaurant().getAddress() + " " + restaurants.get(postion).getCity().getPostalCode() + " " + restaurants.get(postion).getCity().getName());
        distance.setText(distanceText);
        duration.setText(durationText);

        return view;
    }
}
