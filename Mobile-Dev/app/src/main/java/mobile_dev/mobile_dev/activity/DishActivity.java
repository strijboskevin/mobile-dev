package mobile_dev.mobile_dev.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import mobile_dev.mobile_dev.activity.adapter.DishAdapter;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.container.RestaurantContainer;
import mobile_dev.mobile_dev.activity.container.UserContainer;
import mobile_dev.mobile_dev.model.Dish;
import mobile_dev.mobile_dev.model.User;
import mobile_dev.mobile_dev.repository.DishRepository;

public class DishActivity extends AppCompatActivity implements IActivity {

    @BindView(R.id.gridview) GridView gridView;
    @BindView(R.id.drawerlayout) DrawerLayout drawerLayout;
    @BindView(R.id.navigationview) NavigationView navigationView;

    private List<Dish> dishes;
    private User user;
    private DishRepository repo = new DishRepository(this);

    private ActionBarDrawerToggle actionBarDrawerToggle;

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            if (item.getItemId() == R.id.menu_change_address) {
                Intent intent = new Intent(DishActivity.this, ChangeAddressActivity.class);
                UserContainer userContainer = new UserContainer(this.user);
                intent.putExtra("user", userContainer);
                startActivity(intent);
            } else if (item.getItemId() == R.id.menu_change_radius){
                Intent intent = new Intent(DishActivity.this, ChangeRadiusActivity.class);
                UserContainer userContainer = new UserContainer(this.user);
                intent.putExtra("user", userContainer);
                startActivity(intent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setJson(String json) {
        this.dishes = new Gson().fromJson(json, new TypeToken<List<Dish>>(){}.getType());
        DishAdapter adapter = new DishAdapter(DishActivity.this, dishes, user);
        gridView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes_list);
        ButterKnife.bind(this);
        this.user = ((UserContainer) getIntent().getSerializableExtra("user")).getUser();
        repo.all();
<<<<<<< HEAD
        setGridView();
=======
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setNavigationItemSelectedListener();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_change_address) {
                    Intent intent = new Intent(DishActivity.this, ChangeAddressActivity.class);
                    UserContainer userContainer = new UserContainer(user);
                    intent.putExtra("user", userContainer);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.menu_change_radius){
                    Intent intent = new Intent(DishActivity.this, ChangeRadiusActivity.class);
                    UserContainer userContainer = new UserContainer(user);
                    intent.putExtra("user", userContainer);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    private void setNavigationItemSelectedListener() {

>>>>>>> a831924ceb42526cd7767b3e61b30d52655a486f
    }

    private void setGridView() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                RestaurantContainer restaurantsContainer = new RestaurantContainer(dishes.get(position).getRestaurants());
                UserContainer userContainer = new UserContainer(user);
                Intent intent = new Intent(DishActivity.this, RestaurantListActivity.class);
                intent.putExtra("restaurants", restaurantsContainer);
                intent.putExtra("user", userContainer);
                intent.putExtra("url", dishes.get(position).getImage());
                startActivity(intent);
            }
        });

    }
}
