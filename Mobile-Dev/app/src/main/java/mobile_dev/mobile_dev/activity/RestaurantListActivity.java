package mobile_dev.mobile_dev.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.adapter.RestaurantListAdapter;
import mobile_dev.mobile_dev.container.MenuContainer;
import mobile_dev.mobile_dev.container.RestaurantContainer;
import mobile_dev.mobile_dev.model.Restaurant;

/**
 * Created by kevin on 03/10/2017.
 */

public class RestaurantListActivity extends AppCompatActivity {

    private List<Restaurant> restaurants;
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
        RestaurantContainer container = (RestaurantContainer) getIntent().getSerializableExtra("container");
        this.restaurants = container.getRestaurants();
        listView = (ListView) findViewById(R.id.listview_restaurants);
        RestaurantListAdapter adapter = new RestaurantListAdapter(RestaurantListActivity.this, restaurants);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                MenuContainer container = new MenuContainer(restaurants.get(position).getMenus());
                Intent intent = new Intent(RestaurantListActivity.this, MenuActivity.class);
                intent.putExtra("container", container);
                startActivity(intent);
            }
        });
    }

}
