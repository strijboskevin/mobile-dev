package mobile_dev.mobile_dev.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.model.Menu;

/**
 * Created by kevin on 04/10/2017.
 */

public class MenuAdapter extends BaseAdapter {

    private List<Menu> menus;
    private Context context;
    private LayoutInflater inflater;

    public MenuAdapter(Context context, List<Menu> menus) {
        this.context = context;
        this.menus = menus;
    }

    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public Object getItem(int i) {
        return menus.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_menus_list_element, null);
        }

        TextView name = (TextView) view.findViewById(R.id.activity_menu_list_element_textview);

        name.setText(menus.get(position).getName());

        return view;
    }

}
