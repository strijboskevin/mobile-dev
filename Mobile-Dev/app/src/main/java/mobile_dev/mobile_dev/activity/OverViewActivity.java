package mobile_dev.mobile_dev.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.squareup.picasso.Picasso;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.adapter.OverViewAdapter;
import mobile_dev.mobile_dev.activity.adapter.utils.OrderElement;
import mobile_dev.mobile_dev.activity.adapter.utils.OrderElementContainer;
import mobile_dev.mobile_dev.activity.container.UserContainer;
import mobile_dev.mobile_dev.model.City;
import mobile_dev.mobile_dev.model.User;
import mobile_dev.mobile_dev.repository.CityRepository;

public class OverViewActivity extends AppCompatActivity {

    @BindView(R.id.overview_menus_list) ListView listView;
    @BindView(R.id.overview_menus_list_image) ImageView image;
    @BindView(R.id.overview_menus_textview_total_price) TextView total;
    private Button paypalButton;

    private List<OrderElement> orderElements;
    private OverViewAdapter adapter;
    private User user;
    private String url;
    private SharedPreferences preferences;
    private Intent paypalIntent;
    private int ppRequestCode = 999;
    private PayPalConfiguration ppConfig;
    private String paypalClientId = "AbA8LQ4s-CchWCS7V_mmrtVePqI5wRzUEURQiBOVQ5Qq1qzJ6NdAYDkhJvaRJy2VUc8xSWhS7DMFeZdh";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_menus_list);
        this.user = ((UserContainer)getIntent().getSerializableExtra("user")).getUser();
        ButterKnife.bind(this);
        this.paypalButton = (Button) (findViewById(R.id.paypalButton));
        this.orderElements = ((OrderElementContainer)getIntent().getSerializableExtra("orderElements")).getOrderElements();
        trimOrderElements();
        this.url = getIntent().getStringExtra("url");
        Picasso.with(this).load(url).into(image);
        this.user = ((UserContainer)getIntent().getSerializableExtra("user")).getUser();
        adapter = new OverViewAdapter(OverViewActivity.this, orderElements);
        listView.setAdapter(adapter);
        preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        ppConfig = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(paypalClientId);
        paypalIntent = new Intent(OverViewActivity.this, PayPalService.class);
        paypalIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, ppConfig);
        pay();
        startService(paypalIntent);
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

    public void pay() {
        this.paypalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayPalPayment payment = new PayPalPayment(new BigDecimal(calcTotal()), "EUR", "Paypal Payment", PayPalPayment.PAYMENT_INTENT_SALE);
                Intent intent = new Intent(OverViewActivity.this, PaymentActivity.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, ppConfig);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
                startActivityForResult(intent, ppRequestCode);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ppRequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null && confirm.getProofOfPayment().getState().equals("approved")){
                    Log.e("Payment", "confirmed");
                } else {
                    Log.e("Payment", "error");
                }
            }
        }
    }
}
