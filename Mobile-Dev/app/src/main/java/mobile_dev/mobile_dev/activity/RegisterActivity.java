package mobile_dev.mobile_dev.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.model.User;
import mobile_dev.mobile_dev.repository.UserRepository;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.username) EditText usernameEditText;
    @BindView(R.id.firstname) EditText firstnameEditText;
    @BindView(R.id.lastname) EditText lastnameEditText;
    @BindView(R.id.password) EditText passwordEditText;
    @BindView(R.id.mobilenumber) EditText mobilenumberEditText;
    @BindView(R.id.address) EditText addressEditText;
    @BindView(R.id.city) EditText postalEditText;
    @BindView(R.id.registerButton) Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.registerButton)
    public void attemptRegister(){
        String username = usernameEditText.getText().toString();
        String firstname = firstnameEditText.getText().toString();
        String lastname = lastnameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String mobilenumber = mobilenumberEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String city = postalEditText.getText().toString();
        if (!(username.equals("") || firstname.equals("") || lastname.equals("") || password.equals("") || mobilenumber.equals("") || address.equals("") || city.equals(""))) {
            UserRepository container = new UserRepository();
            User user = new User();
            user.setUsername(username);
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setPassWord(password);
            user.setMobileNr(mobilenumber);
            user.setAddress(address);
            user.setCity(city);
            user.setRadius(20);
            container.add(user);
        } else
        {
            shake();
        }
    }


    private void shake() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        usernameEditText.startAnimation(shake);
        firstnameEditText.startAnimation(shake);
        lastnameEditText.startAnimation(shake);
        passwordEditText.startAnimation(shake);
        mobilenumberEditText.startAnimation(shake);
        addressEditText.startAnimation(shake);
        postalEditText.startAnimation(shake);
    }
}
