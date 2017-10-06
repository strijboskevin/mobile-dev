package mobile_dev.mobile_dev.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.adapter.OverViewAdapter;
import mobile_dev.mobile_dev.activity.adapter.utils.OrderElement;
import mobile_dev.mobile_dev.activity.adapter.utils.OrderElementContainer;
import mobile_dev.mobile_dev.activity.container.UserContainer;
import mobile_dev.mobile_dev.model.User;
import mobile_dev.mobile_dev.repository.CityRepository;

/**
 * Created by kevin on 04/10/2017.
 */

public class OverViewActivity extends AppCompatActivity {

    @BindView(R.id.overview_menus_list) ListView listView;
    @BindView(R.id.overview_menus_textview) TextView textView;
    @BindView(R.id.overview_menus_list_image) ImageView image;
    @BindView(R.id.overview_menus_textview_total_price) TextView total;
    private List<OrderElement> orderElements;
    private OverViewAdapter adapter;
    private User user;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_menus_list);
        this.user = ((UserContainer)getIntent().getSerializableExtra("user")).getUser();
        ButterKnife.bind(this);
        this.orderElements = ((OrderElementContainer)getIntent().getSerializableExtra("orderElements")).getOrderElements();
        trimOrderElements();
        this.url = getIntent().getStringExtra("url");
        Picasso.with(this).load(url).into(image);
        this.user = ((UserContainer)getIntent().getSerializableExtra("user")).getUser();
        adapter = new OverViewAdapter(OverViewActivity.this, orderElements);
        listView.setAdapter(adapter);
        CityRepository cityRepository = new CityRepository();
        String cityName = cityRepository.find(this.user.getCity()).getName();
        textView.setText("De bestelling zal geleverd worden aan " + this.user.getAddress() + " te " + cityName + " op naam van " + this.user.getFirstName() + " " + this.user.getLastName() + ".");
        total.setText("Totaal: €" + String.valueOf(calcTotal()));
    }

    private void trimOrderElements() {
        List<OrderElement> newOrderElements = new ArrayList<OrderElement>();
        int i;

        for (i=0; i < orderElements.size() ;i++) {
            if (orderElements.get(i).getAmount() > 0)
                newOrderElements.add(orderElements.get(i));
        }

        this.orderElements = newOrderElements;
    }

    private int calcTotal() {
        int total, i;

        total = 0;

        for (i=0; i < orderElements.size() ;i++) {
            total += orderElements.get(i).getMenu().getPrice() * orderElements.get(i).getAmount();
        }

        return total;
    }

}
