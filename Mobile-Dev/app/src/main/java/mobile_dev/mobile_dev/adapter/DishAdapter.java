package mobile_dev.mobile_dev.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.model.Dish;

/**
 * Created by Fred on 3/10/2017.
 */

public class DishAdapter extends BaseAdapter {

    private List<Dish> dishes;
    private Context context;
    private LayoutInflater inflater;

    public DishAdapter(Context context, List<Dish> dishes) {
        this.context = context;
        this.dishes = dishes;
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
    public View getView(int postion, View convertView, ViewGroup parent) {
        View gridView = convertView;

        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.gridview_element, null);
        }

        ImageView image = (ImageView) gridView.findViewById(R.id.gridview_element_imageview);
        TextView word = (TextView) gridView.findViewById(R.id.gridview_element_textview);

        Picasso.with(context).load(dishes.get(postion).getImage()).into(image);
        word.setText(dishes.get(postion).getName());

        return gridView;
    }
}
