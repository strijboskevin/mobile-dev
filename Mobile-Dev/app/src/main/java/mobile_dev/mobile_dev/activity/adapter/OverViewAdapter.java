package mobile_dev.mobile_dev.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.adapter.utils.OrderElement;

/**
 * Created by kevin on 04/10/2017.
 */

public class OverViewAdapter extends BaseAdapter {
    private List<OrderElement> orderElements;
    private Context context;
    private LayoutInflater inflater;

    public OverViewAdapter(Context context, List<OrderElement> orderElements) {
        this.context = context;
        this.orderElements = orderElements;
    }

    @Override
    public int getCount() {
        return orderElements.size();
    }

    @Override
    public Object getItem(int i) {
        return orderElements.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_menu_overview_list_element, null);
        }

        TextView menu = (TextView) view.findViewById(R.id.menu_overview_list_element_menu);
        TextView amount = (TextView) view.findViewById(R.id.menu_overview_list_element_amount);

        menu.setText(orderElements.get(position).getMenu().getName());
        amount.setText(String.valueOf(orderElements.get(position).getAmount()));

        return view;
    }
}
