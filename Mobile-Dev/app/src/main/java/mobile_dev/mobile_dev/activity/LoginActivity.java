package mobile_dev.mobile_dev.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.*;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.*;
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

public class LoginActivity extends AppCompatActivity implements IActivity {

    @BindView(R.id.registerButton) Button registerButton;
    @BindView(R.id.email) AutoCompleteTextView emailTextView;
    @BindView(R.id.password) EditText passwordEditText;
    @BindView(R.id.loginButton) Button loginButton;

    private String json;
    private String passWord;
    private User user;
    private Gson gson = new Gson();
    private UserRepository repo = new UserRepository(this);

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
        String userName = emailTextView.getText().toString();
        passWord = passwordEditText.getText().toString();
        passWord = convertToMD5(passWord);
        repo.find(userName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
     //   setFacebookLoginButton();
    }

 /*   private void setFacebookLoginButton() {
        fbLoginButton = (LoginButton) findViewById(R.id.login_button);
        textView = (TextView) findViewById(R.id.textView);
        fbLoginButton.setReadPermissions(Arrays.asList(
                "public_profile"));
        callbackManager = CallbackManager.Factory.create();
        Log.v("Output", "Test");
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Bundle bFacebookData = getFacebookData(object);
                                textView.setText("Accessed onSuccesMethod");
                            }
                        });
                textView.setText("Accessed registerCallback");
               *Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name");
                request.setParameters(parameters);
                request.executeAsync();
                textView.setText(parameters.getString("last_name") + " " + parameters.getString("first_name"));*/
                /*Intent intent = new Intent(LoginActivity.this, DishActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {

                textView.setText("Login cancelled");
            }

            @Override
            public void onError() {
                    FacebookException error) {
            }
        });
    }  */

 /*   private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;
        }
        catch(JSONException e) {
            Log.d("error","Error parsing JSON");
        }
        return null;
    } */

    private void shake() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        emailTextView.startAnimation(shake);
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

