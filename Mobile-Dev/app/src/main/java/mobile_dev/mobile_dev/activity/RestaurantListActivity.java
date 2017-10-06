package mobile_dev.mobile_dev.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.adapter.RestaurantListAdapter;
import mobile_dev.mobile_dev.activity.adapter.bundle.RestaurantBundle;
import mobile_dev.mobile_dev.activity.container.MenuContainer;
import mobile_dev.mobile_dev.activity.container.RestaurantContainer;
import mobile_dev.mobile_dev.activity.container.UserContainer;
import mobile_dev.mobile_dev.google.DistanceCalculator;
import mobile_dev.mobile_dev.google.json.MapsContainer;
import mobile_dev.mobile_dev.model.Restaurant;
import mobile_dev.mobile_dev.model.User;
import mobile_dev.mobile_dev.repository.CityRepository;

/**
 * Created by kevin on 03/10/2017.
 */

public class RestaurantListActivity extends AppCompatActivity {

    @BindView(R.id.listview_restaurants) ListView listView;
    @BindView(R.id.activity_restaurant_list_image) ImageView image;
    private List<Restaurant> restaurants;
    private List<RestaurantBundle> restaurantBundles;
    private RestaurantListAdapter adapter;
    private User user;
    private String url;
    private String json;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_list);
        ButterKnife.bind(this);
        this.restaurants = ((RestaurantContainer) getIntent().getSerializableExtra("restaurants")).getRestaurants();
        this.user = ((UserContainer) getIntent().getSerializableExtra("user")).getUser();
        this.url = getIntent().getStringExtra("url");
        this.restaurantBundles = new ArrayList<RestaurantBundle>();
        Picasso.with(this).load(url).into(image);
        createBundles();
        removeBundles();
        sort();
        adapter = new RestaurantListAdapter(RestaurantListActivity.this, restaurantBundles, user);
        setListView();
    }

    private void setListView() {
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                MenuContainer menusContainer = new MenuContainer(restaurants.get(position).getMenus());
                UserContainer userContainer = new UserContainer(user);
                Intent intent = new Intent(RestaurantListActivity.this, MenuActivity.class);
                intent.putExtra("menus", menusContainer);
                intent.putExtra("url", url);
                intent.putExtra("user", userContainer);
                startActivity(intent);
            }
        });
    }

    private void removeBundles() {
        int i;

        for (i=0; i < restaurantBundles.size() ;i++) {
            if (restaurantBundles.get(i).getContainer().getRows().get(0).getElements().get(0).getDistance().getValue() > user.getRadius() * 1000) {
                restaurantBundles.remove(i);
            }
        }
    }

    private void createBundles() {
        int i;
        CityRepository cityRepo = new CityRepository();

        for (i=0; i < restaurants.size() ;i++) {
            String toName = cityRepo.find(restaurants.get(i).getCity()).getName();
            String fromName = cityRepo.find(user.getCity()).getName();
            String to = restaurants.get(i).getAddress() + " " + toName;
            String from = user.getAddress() + " " + fromName;
            new DistanceCalculator(from, to, this).calculate();
            MapsContainer mapsContainer = new Gson().fromJson(this.json, MapsContainer.class);
            restaurantBundles.add(i, new RestaurantBundle(mapsContainer, restaurants.get(i)));
        }
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

}
