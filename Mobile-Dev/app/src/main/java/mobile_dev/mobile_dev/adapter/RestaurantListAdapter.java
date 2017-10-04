package mobile_dev.mobile_dev.adapter;

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
import mobile_dev.mobile_dev.adapter.bundle.Bundle;
import mobile_dev.mobile_dev.location.DistanceCalculator;
import mobile_dev.mobile_dev.location.objects.MapsContainer;
import mobile_dev.mobile_dev.model.Restaurant;

/**
 * Created by Fred on 3/10/2017.
 */

public class RestaurantListAdapter extends BaseAdapter {

    private List<Restaurant> restaurants;
    private Context context;
    private LayoutInflater inflater;
    private String json;
    private List<Bundle> bundles;

    public RestaurantListAdapter(Context context, List<Restaurant> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
        bundles = new ArrayList<Bundle>();
        createBundles();
    }

    private void createBundles() {
        int i;

        for (i=0; i < restaurants.size() ;i++) {
            String from = "Salvatorstraat 20 Hasselt";
            String to = restaurants.get(i).getAddress() + " " + restaurants.get(i).getCity().getName();
            new DistanceCalculator(from, to, this).calculate();
            MapsContainer mapsContainer = new Gson().fromJson(this.json, MapsContainer.class);
            bundles.add(i, new Bundle(mapsContainer, restaurants.get(i)));
        }

        sort();
    }

    private void sort() {
        int i, j;
        for (i=0; i < bundles.size()-1 ;i++) {
            for(j=1; j < bundles.size()-i ;j++) {
                int first, second;
                first = bundles.get(j).getContainer().getRows().get(0).getElements().get(0).getDistance().getValue();
                second = bundles.get(j-1).getContainer().getRows().get(0).getElements().get(0).getDistance().getValue();
                if (first < second) {
                    Bundle dummy = bundles.get(j-1);
                    bundles.set(j-1, bundles.get(j));
                    bundles.set(j, dummy);
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
    public View getView(int postion, View convertView, ViewGroup parent) {
        View gridView = convertView;

        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.activity_restaurant_list_element, null);
        }

        TextView name = (TextView) gridView.findViewById(R.id.activity_restaurant_list_element_textview_name);
        TextView address = (TextView) gridView.findViewById(R.id.activity_restaurant_list_element_textview_address);
        TextView distance = (TextView) gridView.findViewById(R.id.activity_restaurant_list_element_textview_distance);
        TextView duration = (TextView) gridView.findViewById(R.id.activity_restaurant_list_element_textview_duration);
        String distanceText = bundles.get(postion).getContainer().getRows().get(0).getElements().get(0).getDistance().getText();
        String durationText = bundles.get(postion).getContainer().getRows().get(0).getElements().get(0).getDuration().getText();
        name.setText(bundles.get(postion).getRestaurant().getName());
        address.setText(bundles.get(postion).getRestaurant().getAddress() + " " + restaurants.get(postion).getCity().getPostalCode() + " " + restaurants.get(postion).getCity().getName());
        distance.setText(distanceText);
        duration.setText(durationText);

        return gridView;
    }
}
