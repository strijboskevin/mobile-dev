package mobile_dev.mobile_dev.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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

/**
 * Created by kevin on 04/10/2017.
 */

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.listview_menus) ListView listView;
    @BindView(R.id.activity_menus_list_image) ImageView image;
    @BindView(R.id.listview_menus_button) Button button;
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
        setButton();
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
                intent.putExtra("url", url);
                UserContainer userContainer = new UserContainer(user);
                intent.putExtra("user", userContainer);
                intent.putExtra("orderElements", orderElementContainer);
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
}
