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
import android.widget.Toast;

import com.google.gson.Gson;
import com.paypal.android.sdk.payments.*;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

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

public class OverViewActivity extends AppCompatActivity implements IActivity {

    @BindView(R.id.overview_menus_list) ListView listView;
    @BindView(R.id.overview_menus_textview) TextView textView;
    @BindView(R.id.overview_menus_list_image) ImageView image;
    @BindView(R.id.overview_menus_textview_total_price) TextView total;
    @BindView(R.id.paypalButton) Button paypalButton;

    private List<OrderElement> orderElements;
    private OverViewAdapter adapter;
    private User user;
    private String url;
    private SharedPreferences preferences;
    private static PayPalConfiguration ppConfig;
    String paypalClientId = "AaadvnDGI9Yjhx7cdwPjJYV_XwjJNGjpvFu4__NisSq7cudBv9XT2E9O6kqhf61D1xkmHWLZAipncFuP";

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
        CityRepository cityRepository = new CityRepository(this);
        cityRepository.find(this.user.getCity());
        preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        ppConfig = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(paypalClientId);
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

    @Override
    public void setJson(String json) {
        City city = new Gson().fromJson(json, City.class);
        textView.setText("De bestelling zal geleverd worden aan " + preferences.getString("address", user.getAddress()) + " te " + city.getName() + " op naam van " + this.user.getFirstName() + " " + this.user.getLastName() + ".");
        total.setText("Totaal: €" + String.valueOf(calcTotal()));
    }

    public void pay(View view) {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(calcTotal()), "EUR", "sample item",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, ppConfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));

                    if (confirm != null) {
                        try {

                            JSONObject jsonObject = new JSONObject(confirm.toJSONObject().toString(4));
                            JSONObject response = new JSONObject(jsonObject.getString("response"));

                            Toast.makeText(this, "Payment Successful transction Id:-" + response.getString("id"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }
}
