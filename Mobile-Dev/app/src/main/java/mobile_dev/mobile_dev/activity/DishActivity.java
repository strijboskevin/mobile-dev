package mobile_dev.mobile_dev.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import java.util.List;
import mobile_dev.mobile_dev.adapter.DishAdapter;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.container.RestaurantContainer;
import mobile_dev.mobile_dev.container.UserContainer;
import mobile_dev.mobile_dev.model.Dish;
import mobile_dev.mobile_dev.model.User;
import mobile_dev.mobile_dev.repository.DishRepository;

public class DishActivity extends AppCompatActivity {

    private GridView gridView;
    private final List<Dish> dishes = new DishRepository().all();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        this.user = ((UserContainer) getIntent().getSerializableExtra("user")).getUser();

        gridView = (GridView) findViewById(R.id.gridview);
        DishAdapter adapter = new DishAdapter(DishActivity.this, dishes);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                RestaurantContainer container = new RestaurantContainer(dishes.get(position).getRestaurants());
                Intent intent = new Intent(DishActivity.this, RestaurantListActivity.class);
                intent.putExtra("container", container);
                startActivity(intent);
            }
        });
    }
}
