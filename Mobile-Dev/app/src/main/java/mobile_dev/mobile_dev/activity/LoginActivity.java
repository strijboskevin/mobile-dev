package mobile_dev.mobile_dev.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.*;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.registerButton) Button registerButton;
    @BindView(R.id.email) AutoCompleteTextView emailTextView;
    @BindView(R.id.password) EditText passwordEditText;
    @BindView(R.id.loginButton) Button loginButton;

    // UI references.
    private LoginButton fbLoginButton;
    private CallbackManager callbackManager;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setFacebookLoginButton();
    }

    @OnClick(R.id.loginButton)
    public void setEmailSignInButton() {
        attemptLogin();
    }

    @OnClick(R.id.registerButton)
    public void setRegisterButton() {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    private void setFacebookLoginButton() {
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
                                //Bundle bFacebookData = getFacebookData(object);
                                textView.setText("Accessed onSuccesMethod");
                            }
                        });
                textView.setText("Accessed registerCallback");
                /*Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name");
                request.setParameters(parameters);
                request.executeAsync();
                textView.setText(parameters.getString("last_name") + " " + parameters.getString("first_name"));*/
                /*Intent intent = new Intent(LoginActivity.this, DishActivity.class);
                startActivity(intent);*/
            }

            @Override
            public void onCancel() {
                textView.setText("Login cancelled");
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private Bundle getFacebookData(JSONObject object) {

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
    }

    private void attemptLogin() {
        UserRepository repo = new UserRepository();
        String userName = emailTextView.getText().toString();
        String passWord = passwordEditText.getText().toString();
        passWord = convertToMD5(passWord);
        User user = repo.find(userName);

        if (user != null && user.getPassWord().equals(passWord)) {
            UserContainer container = new UserContainer(user);
            Intent intent = new Intent(LoginActivity.this, DishActivity.class);
            intent.putExtra("user", container);
            startActivity(intent);
        } else {
            shake();
        }
    }

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

