package mobile_dev.mobile_dev.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.container.UserContainer;
import mobile_dev.mobile_dev.model.User;

public class ChangeRadiusActivity extends AppCompatActivity {

    @BindView(R.id.change_radius_seekbar) SeekBar seekBar;
    @BindView(R.id.change_radius_seekbar_text) TextView textView;
    private User user;
    private int radius = 0;
    private static SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
<<<<<<< HEAD
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radius_preferences);
=======
        super.onCreate(savedInstanceState);;
>>>>>>> b2de75e3f828053dfd887cc5d89f08a37f26bb9e
        this.user = ((UserContainer) getIntent().getSerializableExtra("user")).getUser();
        setContentView(R.layout.activity_change_radius);
        ButterKnife.bind(this);
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        seekBar.setProgress(prefs.getInt("seekBarValue", 0));
        textView.setText(prefs.getString("textViewValue", "0km"));
        setSeekBar();
    }

    private void setSeekBar() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int radius, boolean b) {
                textView.setText(radius + "km");
                prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("seekBarValue", radius);
                editor.putString("textViewValue", radius + "km");
                editor.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
