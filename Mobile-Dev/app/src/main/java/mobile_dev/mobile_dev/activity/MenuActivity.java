package mobile_dev.mobile_dev.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.adapter.MenuAdapter;
import mobile_dev.mobile_dev.activity.container.MenuContainer;
import mobile_dev.mobile_dev.model.Menu;
import mobile_dev.mobile_dev.model.User;

/**
 * Created by kevin on 04/10/2017.
 */

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.listview_menus) ListView listView;
    @BindView(R.id.activity_menus_list_image) ImageView image;
    private List<Menu> menus;
    private MenuAdapter adapter;
    private User user;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus_list);
        ButterKnife.bind(this);
        this.menus = ((MenuContainer)getIntent().getSerializableExtra("menus")).getMenus();
        this.url = getIntent().getStringExtra("url");
        Picasso.with(this).load(url).into(image);
  //      this.user = ((UserContainer)getIntent().getSerializableExtra("user")).getUser();
        adapter = new MenuAdapter(MenuActivity.this, menus);
        setListView();
    }

    private void setListView() {
        listView.setAdapter(adapter);
    }
}
