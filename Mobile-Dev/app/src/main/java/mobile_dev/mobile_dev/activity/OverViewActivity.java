package mobile_dev.mobile_dev.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.adapter.MenuAdapter;
import mobile_dev.mobile_dev.activity.adapter.OverViewAdapter;
import mobile_dev.mobile_dev.activity.adapter.utils.OrderElement;
import mobile_dev.mobile_dev.activity.adapter.utils.OrderElementContainer;
import mobile_dev.mobile_dev.activity.container.MenuContainer;
import mobile_dev.mobile_dev.model.Menu;
import mobile_dev.mobile_dev.model.User;

/**
 * Created by kevin on 04/10/2017.
 */

public class OverViewActivity extends AppCompatActivity {

    @BindView(R.id.overview_menus_list) ListView listView;
    @BindView(R.id.overview_menus_textview) TextView textView;
    @BindView(R.id.overview_menus_list_image) ImageView image;
    private List<OrderElement> orderElements;
    private OverViewAdapter adapter;
    private User user;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_menus_list);
        ButterKnife.bind(this);
        this.orderElements = ((OrderElementContainer)getIntent().getSerializableExtra("orderElements")).getOrderElements();
        this.url = getIntent().getStringExtra("url");
        Picasso.with(this).load(url).into(image);
        //      this.user = ((UserContainer)getIntent().getSerializableExtra("user")).getUser();
        adapter = new OverViewAdapter(OverViewActivity.this, orderElements);
        listView.setAdapter(adapter);
    }

}
