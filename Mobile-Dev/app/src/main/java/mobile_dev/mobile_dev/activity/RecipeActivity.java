package mobile_dev.mobile_dev.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import mobile_dev.mobile_dev.Adapter.RecipeAdapter;
import mobile_dev.mobile_dev.R;

public class RecipeActivity extends AppCompatActivity {

    GridView gridView;

    String wordList[] = {"Indisch" , "Aardappelen", "Soep", "Veganistisch", "Vegetarisch", "Wok"};
    int images[] = {R.drawable.indian, R.drawable.potato, R.drawable.soup, R.drawable.vegan, R.drawable.vegetarian, R.drawable.wok };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        gridView = (GridView) findViewById(R.id.gridview);
        RecipeAdapter adapter = new RecipeAdapter(RecipeActivity.this, images, wordList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(RecipeActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
