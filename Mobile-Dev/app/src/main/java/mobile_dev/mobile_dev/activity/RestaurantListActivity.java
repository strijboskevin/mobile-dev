package mobile_dev.mobile_dev.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import com.squareup.picasso.Picasso;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.adapter.RestaurantListAdapter;
import mobile_dev.mobile_dev.activity.container.MenuContainer;
import mobile_dev.mobile_dev.activity.container.RestaurantContainer;
import mobile_dev.mobile_dev.model.Restaurant;
import mobile_dev.mobile_dev.model.User;

/**
 * Created by kevin on 03/10/2017.
 */

public class RestaurantListActivity extends AppCompatActivity {

    @BindView(R.id.listview_restaurants) ListView listView;
    @BindView(R.id.activity_restaurant_list_image) ImageView image;
    private List<Restaurant> restaurants;
    private RestaurantListAdapter adapter;
    private User user;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_list);
        ButterKnife.bind(this);
        this.restaurants = ((RestaurantContainer) getIntent().getSerializableExtra("restaurants")).getRestaurants();
  //      this.user = ((UserContainer) getIntent().getSerializableExtra("user")).getUser();
        this.url = getIntent().getStringExtra("url");
        Picasso.with(this).load(url).into(image);
        adapter = new RestaurantListAdapter(RestaurantListActivity.this, restaurants);
        setListView();
    }

    private void setListView() {
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                MenuContainer menusContainer = new MenuContainer(restaurants.get(position).getMenus());
     //           UserContainer userContainer = new UserContainer(user);
                Intent intent = new Intent(RestaurantListActivity.this, MenuActivity.class);
                intent.putExtra("menus", menusContainer);
                intent.putExtra("url", url);
      //          intent.putExtra("user", userContainer);
                startActivity(intent);
            }
        });
    }

}
