package mobile_dev.mobile_dev.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import java.util.List;
import mobile_dev.mobile_dev.container.MenuContainer;
import mobile_dev.mobile_dev.model.Menu;

/**
 * Created by kevin on 04/10/2017.
 */

public class MenuActivity extends AppCompatActivity {

    private List<Menu> menus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.menus = ((MenuContainer)getIntent().getSerializableExtra("container")).getMenus();
    }
}
