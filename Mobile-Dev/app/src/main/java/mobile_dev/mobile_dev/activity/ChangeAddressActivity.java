package mobile_dev.mobile_dev.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.container.UserContainer;
import mobile_dev.mobile_dev.model.User;

/**
 * Created by kevin on 06/10/2017.
 */

public class ChangeAddressActivity extends PreferenceActivity {

    private static User user;
    private static SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.user = (User)((UserContainer) getIntent().getSerializableExtra("user")).getUser();
        preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new Fragment()).commit();
    }

    public static class Fragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            EditTextPreference address = (EditTextPreference)findPreference("address");
            EditTextPreference postalCode = (EditTextPreference) findPreference("postalCode");
            address.setText(preferences.getString("address", user.getAddress()));
            postalCode.setText(preferences.getString("postalcode", user.getCity()));
        }

        @Override
        public void onStop() {
            super.onStop();
            String postalCode, address;
            postalCode = ((EditTextPreference) findPreference("postalCode")).getText();
            address = ((EditTextPreference) findPreference("address")).getText();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("address", address);
            editor.putString("postalCode", postalCode);
            editor.commit();
            System.out.println("test");
        }
    }
}
