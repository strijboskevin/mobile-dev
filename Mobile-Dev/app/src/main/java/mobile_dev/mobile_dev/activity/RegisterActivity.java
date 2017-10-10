package mobile_dev.mobile_dev.activity;

import android.content.Intent;
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
import mobile_dev.mobile_dev.activity.container.UserContainer;
import mobile_dev.mobile_dev.model.User;
import mobile_dev.mobile_dev.repository.UserRepository;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.username) EditText usernameEditText;
    @BindView(R.id.firstname) EditText firstnameEditText;
    @BindView(R.id.lastname) EditText lastnameEditText;
    @BindView(R.id.password) EditText passwordEditText;
    @BindView(R.id.mobilenumber) EditText mobilenumberEditText;
    @BindView(R.id.address) EditText addressEditText;
    @BindView(R.id.postalcode) EditText postalEditText;
    @BindView(R.id.registerButton) Button registerButton;
    String username, firstname, lastname, password, mobilenumber, address, postalcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.registerButton)
    public void attemptRegister(){
        getTextEditText();
        if (!(username.equals("") || firstname.equals("") || lastname.equals("") || password.equals("") || mobilenumber.equals("") || address.equals("") || postalcode.equals(""))) {
            if (postalcode.matches("[0-9]+") && postalcode.length() == 4) {
                UserRepository repo = new UserRepository();
                User user = new User();
                user = makeUser(user);
                repo.add(user);
                UserContainer container = new UserContainer(user);
                Intent intent = new Intent(RegisterActivity.this, DishActivity.class);
                intent.putExtra("user", container);
                startActivity(intent);
            } else
            {
                Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                postalEditText.startAnimation(shake);
            }
        } else
        {
            shake();
        }
    }

    public void getTextEditText() {
        username = usernameEditText.getText().toString();
        firstname = firstnameEditText.getText().toString();
        lastname = lastnameEditText.getText().toString();
        password = passwordEditText.getText().toString();
        password = convertToMD5(password);
        mobilenumber = mobilenumberEditText.getText().toString();
        address = addressEditText.getText().toString();
        postalcode = postalEditText.getText().toString();
    }

    public User makeUser(User user) {
        user.setUsername(username);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setPassWord(password);
        user.setMobileNr(mobilenumber);
        user.setAddress(address);
        user.setCity(postalcode);
        user.setRadius(20);
        return user;
    }

    private String convertToMD5(String toConvert) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(toConvert.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
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
