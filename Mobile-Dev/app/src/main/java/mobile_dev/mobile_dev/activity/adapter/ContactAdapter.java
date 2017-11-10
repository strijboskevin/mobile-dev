package mobile_dev.mobile_dev.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.model.Dish;
import mobile_dev.mobile_dev.model.User;

/**
 * Created by kevin on 10/11/2017.
 */

public class ContactAdapter extends BaseAdapter {

    private List<String> names;
    private Context context;
    private LayoutInflater inflater;

    public ContactAdapter(Context context, List<String> names) {
        this.context = context;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_contacts_element, null);
        }

        TextView name = (TextView) (view.findViewById(R.id.contacts_element_text));
        name.setText(this.names.get(position));

        return view;
    }
}
