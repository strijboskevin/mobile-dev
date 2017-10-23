package mobile_dev.mobile_dev.activity;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import mobile_dev.mobile_dev.R;
import mobile_dev.mobile_dev.activity.adapter.RestaurantListAdapter;
import mobile_dev.mobile_dev.activity.adapter.bundle.RestaurantBundle;
import mobile_dev.mobile_dev.activity.container.ICoordinatesConverterActivity;
import mobile_dev.mobile_dev.activity.container.RestaurantContainer;
import mobile_dev.mobile_dev.activity.container.UserContainer;
import mobile_dev.mobile_dev.google.CoordinatesConverter;
import mobile_dev.mobile_dev.google.DistanceCalculator;
import mobile_dev.mobile_dev.google.json.MapsContainer;
import mobile_dev.mobile_dev.model.City;
import mobile_dev.mobile_dev.model.Restaurant;
import mobile_dev.mobile_dev.model.User;
import mobile_dev.mobile_dev.repository.CityRepository;
import mobile_dev.mobile_dev.sqlite.SQLite;

public class RestaurantListActivity extends AppCompatActivity implements IActivity, IDistanceCalculatorActivity, ActivityCompat.OnRequestPermissionsResultCallback, ICoordinatesConverterActivity {

    @BindView(R.id.listview_restaurants)
    ListView listView;
    @BindView(R.id.activity_restaurant_list_image)
    ImageView image;

    private static List<Restaurant> restaurants;
    private List<RestaurantBundle> restaurantBundles;
    private List<City> cities = new ArrayList<City>();
    private List<MapsContainer> maps = new ArrayList<MapsContainer>();
    private RestaurantListAdapter adapter;
    private User user;
    private String url;
    private int threadCount = 0;
    private CityRepository cityRepo = new CityRepository(this);
    private SharedPreferences preferences;
    private City userCity;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private final int PERMISSION_REQUEST_RESULT = 1;
    private String from;
    private SQLite myDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_list);
        ButterKnife.bind(this);
        myDb = new SQLite(this);
        getData();
        Picasso.with(this).load(url).into(image);
        getCities();
        getUserCity();
        addRestaurantsToSQLiteDb();
        addCitiesToSQLiteDb();
    }

    private void addRestaurantsToSQLiteDb() {
        for (int i = 0; i < restaurants.size(); i++) {
            myDb.insertRestaurants(restaurants.get(i).getId(), restaurants.get(i).getAddress(), restaurants.get(i).getCity(), restaurants.get(i).getName());
        }
    }

    private void addCitiesToSQLiteDb() {
        for (int i = 0; i < cities.size(); i++) {
            myDb.insertCities(cities.get(i).getPostalCode(), cities.get(i).getName());
        }
    }

    private void getData() {
        this.restaurants = ((RestaurantContainer) getIntent().getSerializableExtra("restaurants")).getRestaurants();
        this.user = ((UserContainer) getIntent().getSerializableExtra("user")).getUser();
        this.url = getIntent().getStringExtra("url");
        this.restaurantBundles = new ArrayList<RestaurantBundle>();
        preferences = getSharedPreferences("prefs", MODE_PRIVATE);
    }

    private void getCities() {
        int i;

        for (i = 0; i < restaurants.size(); i++) {
            cityRepo.find(restaurants.get(i).getCity());
        }
    }

    private void getUserCity() {
        cityRepo.find(preferences.getString("postalCode", user.getCity()));
    }

    private void removeBundles() {
        int i;
        int radius = preferences.getInt("seekBarValue", user.getRadius());

        for (i = 0; i < restaurantBundles.size(); i++) {
            if (restaurantBundles.get(i).getContainer().getRows().get(0).getElements().get(0).getDistance().getValue() > radius * 1000) {
                restaurantBundles.remove(i);
            }
        }
    }

    private void createBundles() {
        int i;

        for (i = 0; i < restaurants.size(); i++) {
            restaurantBundles.add(new RestaurantBundle(maps.get(i), restaurants.get(i)));
        }
    }

    private void sort() {
        int i, j;
        for (i = 0; i < restaurantBundles.size() - 1; i++) {
            for (j = 1; j < restaurantBundles.size() - i; j++) {
                int first, second;
                first = restaurantBundles.get(j).getContainer().getRows().get(0).getElements().get(0).getDistance().getValue();
                second = restaurantBundles.get(j - 1).getContainer().getRows().get(0).getElements().get(0).getDistance().getValue();
                if (first < second) {
                    RestaurantBundle dummy = restaurantBundles.get(j - 1);
                    restaurantBundles.set(j - 1, restaurantBundles.get(j));
                    restaurantBundles.set(j, dummy);
                }
            }
        }
    }

    @Override
    public void setJson(String json) {
        City city = new Gson().fromJson(json, City.class);
        cities.add(city);
        this.threadCount += 1;

        if (this.threadCount == restaurants.size() + 1) {
            this.userCity = cities.get(cities.size() - 1);
            cities.remove(cities.size() - 1);
            threadCount = 0;
            from = preferences.getString("address", user.getAddress()) + " " + userCity.getName();
            calcDistances();
        }
    }

    private void calcDistances() {
        int i;

        for (i = 0; i < cities.size(); i++) {
            String to = restaurants.get(i).getAddress() + " " + cities.get(i).getName();
            new DistanceCalculator(from, to, this).calculate();
        }
    }

    public void setMapsContainer(String json) {
        MapsContainer mapsContainer = new Gson().fromJson(json, MapsContainer.class);
        maps.add(mapsContainer);
        threadCount += 1;

        if (threadCount == cities.size()) {
            createBundles();
            removeBundles();
            sort();
            adapter = new RestaurantListAdapter(RestaurantListActivity.this, restaurantBundles, user, url);
            listView.setAdapter(adapter);
            setLocationParams();
        }
    }

    private void setLocationParams() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double longitude, latitude;

                longitude = location.getLongitude();
                latitude = location.getLatitude();

                new CoordinatesConverter(longitude, latitude, RestaurantListActivity.this).convert();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RestaurantListActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_RESULT);
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_RESULT) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    public void setAddress(String json) {
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(json).getAsJsonObject();
        JsonObject results = obj.get("results").getAsJsonObject();
        this.from = results.get("formatted_address").getAsString();
        Toast.makeText(this, this.from, Toast.LENGTH_SHORT).show();
        calcDistances();
    }
}
