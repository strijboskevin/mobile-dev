package mobile_dev.mobile_dev.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.container.UserContainer;
import mobile_dev.mobile_dev.model.User;
import mobile_dev.mobile_dev.repository.UserRepository;

/**
 * Created by kevin on 06/10/2017.
 */

public class ChangeAddressActivity extends AppCompatActivity {

    @BindView(R.id.activity_change_address_address_input) EditText address;
    @BindView(R.id.activity_change_address_postal_code_input) EditText postalCode;
    @BindView(R.id.activity_change_address_button) Button button;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_address);
        ButterKnife.bind(this);
        this.user = (User)((UserContainer) getIntent().getSerializableExtra("user")).getUser();
        address.setText(user.getAddress());
        postalCode.setText(user.getCity());
        setButton();
    }

    private void setButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newAdress = address.getText().toString();
                String newPostalCode = postalCode.getText().toString();
                user.setAddress(newAdress);
                user.setCity(newPostalCode);
                UserRepository repo = new UserRepository();
                repo.update(user);
                Intent intent = new Intent(ChangeAddressActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
