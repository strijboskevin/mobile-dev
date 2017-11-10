package mobile_dev.mobile_dev.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.container.UserContainer;
import mobile_dev.mobile_dev.model.User;
import mobile_dev.mobile_dev.repository.UserRepository;

public class RegisterActivity extends AppCompatActivity{

    @BindView(R.id.username) EditText usernameEditText;
    @BindView(R.id.firstname) EditText firstnameEditText;
    @BindView(R.id.lastname) EditText lastnameEditText;
    @BindView(R.id.password) EditText passwordEditText;
    @BindView(R.id.mobilenumber) EditText mobilenumberEditText;
    @BindView(R.id.address) EditText addressEditText;
    @BindView(R.id.postalcode) EditText postalEditText;
    @BindView(R.id.registerButton) Button registerButton;
    @BindView(R.id.fbLoginButton) LoginButton fbLoginButton;

    private String username, firstname, lastname, password, mobilenumber, address, postalcode;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setFacebookLoginButton();
    }

    @OnClick(R.id.registerButton)
    public void attemptRegister(){
        getTextEditText();
        if (!(username.equals("") || firstname.equals("") || lastname.equals("") || password.equals("") || mobilenumber.equals("") || address.equals("") || postalcode.equals(""))) {
            if (mobilenumber.matches("[0-9]+") && mobilenumber.length() == 10) {
                if (postalcode.matches("[0-9]+") && postalcode.length() == 4) {
                    User user = new User();
                    user = makeUser(user);
                    addUser(user);
                    UserContainer container = new UserContainer(user);
                    Intent intent = new Intent(RegisterActivity.this, DishActivity.class);
                    intent.putExtra("user", container);
                    startActivity(intent);
                } else {
                    Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                    postalEditText.startAnimation(shake);
                }
            } else {
                Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                mobilenumberEditText.startAnimation(shake);
            }

        } else {
            shake();
        }
    }

    private void setFacebookLoginButton() {
        fbLoginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        callbackManager = CallbackManager.Factory.create();
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginResult.getAccessToken();
                Profile.getCurrentProfile();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.v("LoginActivity Response ", response.toString());

                                try {
                                    String first_name = object.getString("first_name");
                                    String last_name = object.getString("last_name");
                                    firstnameEditText.setText(first_name);
                                    lastnameEditText.setText(last_name);
                                    fbLoginButton.setVisibility(View.GONE);
                                    LoginManager.getInstance().logOut();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday, first_name, last_name");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void addUser(User user) {
        new UserRepository(new ICallback() {
            @Override
            public void execute(String json) {
                Toast.makeText(RegisterActivity.this, "Account created!", Toast.LENGTH_SHORT);
            }
        }).add(user);
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
