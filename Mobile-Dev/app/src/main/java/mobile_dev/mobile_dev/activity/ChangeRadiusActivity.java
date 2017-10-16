package mobile_dev.mobile_dev.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.container.UserContainer;
import mobile_dev.mobile_dev.model.User;
import mobile_dev.mobile_dev.repository.UserRepository;

public class ChangeRadiusActivity extends AppCompatActivity {

    @BindView(R.id.change_radius_seekbar) SeekBar seekBar;
    @BindView(R.id.change_radius_seekbar_text) TextView textView;
    private User user;
    private int radius = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_radius);
        this.user = ((UserContainer) getIntent().getSerializableExtra("user")).getUser();
        ButterKnife.bind(this);
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        seekBar.setProgress(prefs.getInt("seekBarValue", 0));
        textView.setText(prefs.getString("textViewValue", "0km"));
        setSeekBar();
    }

    private void setSeekBar() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int radius, boolean b) {
                textView.setText(radius + "km");
                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
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
