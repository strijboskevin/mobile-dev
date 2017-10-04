package mobile_dev.mobile_dev.activity.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnTextChanged;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.adapter.utils.OrderElement;
import mobile_dev.mobile_dev.model.Menu;

/**
 * Created by kevin on 04/10/2017.
 */

public class MenuAdapter extends BaseAdapter {

    private List<Menu> menus;
    private List<OrderElement> orderElements;
    private Context context;
    private LayoutInflater inflater;
    private EditText amount;

    public MenuAdapter(Context context, List<Menu> menus) {
        this.context = context;
        this.menus = menus;
        createOrderElements();
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
        amount = (EditText) view.findViewById(R.id.activity_menu_list_element_edittext);

        amount.setTag(orderElements.get(position));

        name.setText(orderElements.get(position).getMenu().getName());
        amount.setText(String.valueOf(orderElements.get(position).getAmount()));

        return view;
    }

    public List<OrderElement> getOrderElements() { return this.orderElements; }

    private void createOrderElements() {
        int i;
        orderElements = new ArrayList<OrderElement>();

        for (i=0; i < menus.size() ;i++) {
            orderElements.add(i, new OrderElement(menus.get(i), 0));
        }
    }

}
