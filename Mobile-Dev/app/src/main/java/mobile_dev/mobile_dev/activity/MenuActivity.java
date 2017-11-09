package mobile_dev.mobile_dev.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import com.squareup.picasso.Picasso;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.adapter.MenuAdapter;
import mobile_dev.mobile_dev.activity.adapter.utils.OrderElement;
import mobile_dev.mobile_dev.activity.adapter.utils.OrderElementContainer;
import mobile_dev.mobile_dev.activity.container.MenuContainer;
import mobile_dev.mobile_dev.activity.container.UserContainer;
import mobile_dev.mobile_dev.model.Menu;
import mobile_dev.mobile_dev.model.User;
import mobile_dev.mobile_dev.sqlite.SQLite;
import mobile_dev.mobile_dev.util.GLOBALS;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.listview_menus) ListView listView;
    @BindView(R.id.activity_menus_list_image) ImageView image;
    @BindView(R.id.listview_menus_button) Button button;

    private List<Menu> menus;
    private MenuAdapter adapter;
    private User user;
    private String url;
    private SQLite myDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus_list);
        ButterKnife.bind(this);
        this.menus = ((MenuContainer)getIntent().getSerializableExtra(GLOBALS.MENUS)).getMenus();
        this.url = getIntent().getStringExtra(GLOBALS.URL);
        Picasso.with(this).load(url).into(image);
        this.user = ((UserContainer)getIntent().getSerializableExtra(GLOBALS.USER)).getUser();
        adapter = new MenuAdapter(MenuActivity.this, menus);
        myDb = new SQLite(this);
        setListView();
        setButton();
        addMenusToSQLiteDb();
    }

    private void setListView() {
        listView.setAdapter(adapter);
    }

    private void setButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<OrderElement> orderElements = adapter.getOrderElements();
                adjustOrderElements(orderElements);
                OrderElementContainer orderElementContainer = new OrderElementContainer(orderElements);
                Intent intent = new Intent(MenuActivity.this, OverViewActivity.class);
                intent.putExtra(GLOBALS.URL, url);
                UserContainer userContainer = new UserContainer(user);
                intent.putExtra(GLOBALS.USER, userContainer);
                intent.putExtra(GLOBALS.ORDERELEMENTS, orderElementContainer);
                startActivity(intent);
            }
        });
    }

    private void adjustOrderElements(List<OrderElement> orderElements) {
        int i;

        for (i=0; i < listView.getCount() ;i++) {
            View v = listView.getAdapter().getView(i, listView.getChildAt(i), listView);
            EditText edit = (EditText) v.findViewById(R.id.activity_menu_list_element_edittext);
            int amount = Integer.parseInt(edit.getText().toString());
            orderElements.get(i).setAmount(amount);
        }
    }

    private void addMenusToSQLiteDb() {
        for (int i = 0; i < menus.size(); i++) {
            myDb.insertMenus(menus.get(i).getId(), menus.get(i).getName(), menus.get(i).getPrice());
        }
    }
}
