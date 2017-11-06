package mobile_dev.mobile_dev.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.MenuActivity;
import mobile_dev.mobile_dev.activity.RestaurantListActivity;
import mobile_dev.mobile_dev.activity.adapter.bundle.RestaurantBundle;
import mobile_dev.mobile_dev.activity.container.MenuContainer;
import mobile_dev.mobile_dev.activity.container.UserContainer;
import mobile_dev.mobile_dev.google.DistanceCalculator;
import mobile_dev.mobile_dev.google.json.MapsContainer;
import mobile_dev.mobile_dev.model.Restaurant;
import mobile_dev.mobile_dev.model.User;
import mobile_dev.mobile_dev.repository.CityRepository;

public class RestaurantListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<RestaurantBundle> restaurantBundles;
    private User user;
    private int position;
    private String url;

    public RestaurantListAdapter(Context context, List<RestaurantBundle> restaurantBundles, User user, String url) {
        this.context = context;
        this.restaurantBundles = restaurantBundles;
        this.user = user;
        this.url = url;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
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

        this.position = postion;

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

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuContainer menusContainer = new MenuContainer(restaurantBundles.get(position).getRestaurant().getMenus());
                UserContainer userContainer = new UserContainer(user);
                Intent intent = new Intent(context, MenuActivity.class);
                intent.putExtra("menus", menusContainer);
                intent.putExtra("url", url);
                intent.putExtra("user", userContainer);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
