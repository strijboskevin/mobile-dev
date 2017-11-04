package mobile_dev.mobile_dev.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.util.*;
import android.widget.Toast;

import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.*;
import com.github.rtoshiro.secure.SecureSharedPreferences;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.container.UserContainer;
import mobile_dev.mobile_dev.model.User;
import mobile_dev.mobile_dev.repository.UserRepository;

import static mobile_dev.mobile_dev.R.id.rememberMeCheckBox;
import static mobile_dev.mobile_dev.R.id.username;

public class LoginActivity extends AppCompatActivity implements IActivity {

    @BindView(R.id.registerButton) Button registerButton;
    @BindView(R.id.username) AutoCompleteTextView usernameTextView;
    @BindView(R.id.password) EditText passwordEditText;
    @BindView(R.id.loginButton) Button loginButton;
    @BindView(R.id.fbLoginButton) LoginButton fbLoginButton;
    @BindView(R.id.rememberMeCheckBox) CheckBox rememberMeCheckBox;

    private String json;
    private String passWord;
    private User user;
    private Gson gson = new Gson();
    private UserRepository repo = new UserRepository(this);
    private CallbackManager callbackManager;

    @Override
    public void setJson(String json) {
        this.json = json;
        this.user = gson.fromJson(json, User.class);
        Intent intent = new Intent(LoginActivity.this, DishActivity.class);
        intent.putExtra("user", new UserContainer(user));
        startActivity(intent);
    }

    @OnClick(R.id.registerButton)
    public void setRegisterButton() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.loginButton)
    public void attemptLogin() {
        String userName = usernameTextView.getText().toString();
        passWord = passwordEditText.getText().toString();
        passWord = convertToMD5(passWord);
        commitPreferencesIfCheckBoxIsCheckedElseCommitNull();
        repo.find(userName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        SecureSharedPreferences prefs = new SecureSharedPreferences(this);
        usernameTextView.setText(prefs.getString("username", ""));
        passwordEditText.setText(prefs.getString("password", ""));
        checkIfPreferencesAreSet();
        setFacebookLoginButton();
    }


    private void commitPreferencesIfCheckBoxIsCheckedElseCommitNull(){
        if (rememberMeCheckBox.isChecked())
        {
            SecureSharedPreferences prefs = new SecureSharedPreferences(this);
            SecureSharedPreferences.Editor editor = prefs.edit();
            editor.putString("username", String.valueOf(usernameTextView.getText()));
            editor.putString("password", String.valueOf(passwordEditText.getText()));
            editor.commit();
        }
        else {
            SecureSharedPreferences prefs = new SecureSharedPreferences(this);
            SecureSharedPreferences.Editor editor = prefs.edit();
            editor.putString("username", "");
            editor.putString("password", "");
            editor.commit();
        }
    }

    private void checkIfPreferencesAreSet() {
        SecureSharedPreferences prefs = new SecureSharedPreferences(this);
        if (!(prefs.getString("username", "").equals("") || prefs.getString("password", "").equals("")))
            rememberMeCheckBox.setChecked(true);
    }

    private void setFacebookLoginButton() {
        fbLoginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        callbackManager = CallbackManager.Factory.create();
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.v("LoginActivity Response ", response.toString());

                                try {
                                    String name = object.getString("name");
                                    user = new User();
                                    user.setUsername(name);
                                    repo.add(user);
                                    LoginManager.getInstance().logOut();
                                    Intent intent = new Intent(LoginActivity.this, DishActivity.class);
                                    intent.putExtra("user", new UserContainer(user));
                                    startActivity(intent);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
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

    private void shake() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        usernameTextView.startAnimation(shake);
        passwordEditText.startAnimation(shake);
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
}

