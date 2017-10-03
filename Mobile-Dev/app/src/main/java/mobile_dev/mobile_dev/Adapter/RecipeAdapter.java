package mobile_dev.mobile_dev.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import mobile_dev.mobile_dev.R;

/**
 * Created by Fred on 3/10/2017.
 */

public class RecipeAdapter extends BaseAdapter {

    private int images[];
    private String words[];
    private Context context;
    private LayoutInflater inflater;

    public RecipeAdapter(Context context, int images[], String words[]) {
        this.context = context;
        this.images = images;
        this.words = words;
    }

    @Override
    public int getCount() {
        return words.length;
    }

    @Override
    public Object getItem(int position) {
        return words[position];
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
            gridView = inflater.inflate(R.layout.gridview_elements, null);
        }

        ImageView image = (ImageView) gridView.findViewById(R.id.gridview_element_imageview);
        TextView word = (TextView) gridView.findViewById(R.id.gridview_element_textview);

        image.setImageResource(images[postion]);

        word.setText(words[postion]);

        return gridView;
    }
}
