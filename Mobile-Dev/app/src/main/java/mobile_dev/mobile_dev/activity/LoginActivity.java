package mobile_dev.mobile_dev.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.github.rtoshiro.secure.SecureSharedPreferences;
import com.google.gson.Gson;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.container.UserContainer;
import mobile_dev.mobile_dev.model.User;
import mobile_dev.mobile_dev.repository.UserRepository;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.registerButton) Button registerButton;
    @BindView(R.id.username) AutoCompleteTextView usernameTextView;
    @BindView(R.id.password) EditText passwordEditText;
    @BindView(R.id.loginButton) Button loginButton;
    @BindView(R.id.rememberMeCheckBox) CheckBox rememberMeCheckBox;

    private String passWord;
    private User user;
    private Gson gson = new Gson();

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
        new UserRepository(new ICallback() {
            @Override
            public void execute(String json) {
                if (!json.equals("")) {
                    LoginActivity.this.user = gson.fromJson(json, User.class);
                    Intent intent = new Intent(LoginActivity.this, DishActivity.class);
                    intent.putExtra("user", new UserContainer(LoginActivity.this.user));
                    startActivity(intent);
                } else {
                    shake();
                }
            }
        }).find(userName);
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

