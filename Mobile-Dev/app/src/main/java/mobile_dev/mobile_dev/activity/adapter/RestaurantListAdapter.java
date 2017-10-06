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
import mobile_dev.mobile_dev.model.User;
import mobile_dev.mobile_dev.repository.CityRepository;

/**
 * Created by Fred on 3/10/2017.
 */

public class RestaurantListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<RestaurantBundle> restaurantBundles;
    private User user;

    public RestaurantListAdapter(Context context, List<RestaurantBundle> restaurantBundles, User user) {
        this.context = context;
        this.restaurantBundles = restaurantBundles;
        this.user = user;
    }

    @Override
    public int getCount() {
        return restaurantBundles.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurantBundles.get(position);
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
        address.setText(restaurantBundles.get(postion).getRestaurant().getAddress() + " " + restaurantBundles.get(postion).getRestaurant().getCity());
        distance.setText(distanceText);
        duration.setText(durationText);

        return view;
    }
}
