package mobile_dev.mobile_dev.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import mobile_dev.mobile_dev.activity.adapter.DishAdapter;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.container.RestaurantContainer;
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
   //     this.user = (User) getIntent().getSerializableExtra("user");
   //     this.user = ((UserContainer) getIntent().getSerializableExtra("user")).getUser();
        adapter = new DishAdapter(DishActivity.this, dishes);
        setGridView();
    }

    private void setGridView() {
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                RestaurantContainer restaurantsContainer = new RestaurantContainer(dishes.get(position).getRestaurants());
                //            UserContainer userContainer = new UserContainer(user);
                Intent intent = new Intent(DishActivity.this, RestaurantListActivity.class);
                intent.putExtra("restaurants", restaurantsContainer);
                //            intent.putExtra("user", userContainer);
                intent.putExtra("url", dishes.get(position).getImage());
                startActivity(intent);
            }
        });
    }
}
