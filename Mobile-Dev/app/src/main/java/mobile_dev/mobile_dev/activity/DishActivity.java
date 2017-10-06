package mobile_dev.mobile_dev.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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

public class DishActivity extends AppCompatActivity {

    @BindView(R.id.gridview) GridView gridView;
    private final List<Dish> dishes = new DishRepository().all();
    private User user;
    private DishAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes_list);
        ButterKnife.bind(this);
        this.user = ((UserContainer) getIntent().getSerializableExtra("user")).getUser();
        adapter = new DishAdapter(DishActivity.this, dishes);
        setGridView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_change_address) {
            Intent intent = new Intent(DishActivity.this, ChangeAddressActivity.class);
            UserContainer userContainer = new UserContainer(this.user);
            intent.putExtra("user", userContainer);
            startActivity(intent);
        } else {
            Intent intent = new Intent(DishActivity.this, ChangeRadiusActivity.class);
            UserContainer userContainer = new UserContainer(this.user);
            intent.putExtra("user", userContainer);
            startActivity(intent);
        }

        return true;
    }

    private void setGridView() {
        gridView.setAdapter(adapter);
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
