package mobile_dev.mobile_dev.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.DishActivity;
import mobile_dev.mobile_dev.activity.RestaurantListActivity;
import mobile_dev.mobile_dev.activity.container.RestaurantContainer;
import mobile_dev.mobile_dev.activity.container.UserContainer;
import mobile_dev.mobile_dev.model.Dish;
import mobile_dev.mobile_dev.model.User;

public class DishAdapter extends BaseAdapter {

    private List<Dish> dishes;
    private Context context;
    private LayoutInflater inflater;
    private User user;

    public DishAdapter(Context context, List<Dish> dishes, User user) {
        this.context = context;
        this.dishes = dishes;
        this.user = user;
    }

    @Override
    public int getCount() {
        return dishes.size();
    }

    @Override
    public Object getItem(int position) {
        return dishes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int postion, View view, ViewGroup parent) {
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_dishes_list_element, null);
        }

        ImageView image = (ImageView) view.findViewById(R.id.gridview_element_imageview);
        TextView word = (TextView) view.findViewById(R.id.gridview_element_textview);
        Picasso.with(context).load(dishes.get(postion).getImage()).resize(300,300).into(image);
        word.setText(dishes.get(postion).getName());

        return view;
    }
}
