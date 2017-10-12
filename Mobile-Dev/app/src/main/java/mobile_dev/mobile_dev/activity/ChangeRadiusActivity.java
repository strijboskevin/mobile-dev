package mobile_dev.mobile_dev.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.container.UserContainer;
import mobile_dev.mobile_dev.model.User;
import mobile_dev.mobile_dev.repository.UserRepository;

public class ChangeRadiusActivity extends AppCompatActivity implements IActivity {

    @BindView(R.id.change_radius_button) Button button;
    @BindView(R.id.change_radius_seekbar) SeekBar seekBar;
    @BindView(R.id.change_radius_seekbar_text) TextView text;
    private User user;
    private int radius = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_radius);
        this.user = ((UserContainer) getIntent().getSerializableExtra("user")).getUser();
        ButterKnife.bind(this);
        setButton();
        setSeekBar();
    }

    private void setButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserRepository repo = new UserRepository(this);
                user.setRadius(radius);
                repo.update(user);
                Intent intent = new Intent(ChangeRadiusActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setSeekBar() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                text.setText(String.valueOf(i) + "km");
                radius = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void setJson(String json) {
        
    }
}
