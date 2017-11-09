package mobile_dev.mobile_dev.activity;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
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
import mobile_dev.mobile_dev.util.GLOBALS;

public class RestaurantListActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    @BindView(R.id.listview_restaurants) ListView listView;
    @BindView(R.id.activity_restaurant_list_image) ImageView image;

    private static List<Restaurant> restaurants;
    private List<RestaurantBundle> restaurantBundles;
    private List<City> cities = new ArrayList<City>();
    private List<City> citiesSQLite = new ArrayList<City>();
    private List<MapsContainer> maps = new ArrayList<MapsContainer>();
    private RestaurantListAdapter adapter;
    private User user;
    private String url;
    private SharedPreferences preferences;
    private int threadCount = 0;
    private City userCity;
    private City city;
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
        adapter = new RestaurantListAdapter(RestaurantListActivity.this, restaurantBundles, user, url);
        listView.setAdapter(adapter);
        getCities();
        getUserCity();
        addRestaurantsToSQLiteDb();
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
        this.restaurants = ((RestaurantContainer) getIntent().getSerializableExtra(GLOBALS.RESTAURANTS)).getRestaurants();
        this.user = ((UserContainer) getIntent().getSerializableExtra(GLOBALS.USER)).getUser();
        this.user.setAddress(getSharedPreferences("prefs", MODE_PRIVATE).getString("address", user.getAddress()));
        this.user.setCity(getSharedPreferences("prefs", MODE_PRIVATE).getString("postalCode", user.getCity()));
        this.url = getIntent().getStringExtra(GLOBALS.URL);
        this.restaurantBundles = new ArrayList<RestaurantBundle>();
        preferences = getSharedPreferences(GLOBALS.PREFS, MODE_PRIVATE);
    }

    private void getCities() {
        int i;

        for (i = 0; i < restaurants.size(); i++) {
            findCity(restaurants.get(i).getCity());
        }
    }

    private void getUserCity() {
        findCity(preferences.getString(GLOBALS.POSTALCODE, user.getCity()));
    }

    private void removeBundles() {
        int i;
        int radius = preferences.getInt(GLOBALS.SEEKBARVALUE, user.getRadius());

        for (i = 0; i < restaurantBundles.size(); i++) {
            if (restaurantBundles.get(i).getContainer().getRows().get(0).getElements().get(0).getDistance().getValue() > radius * 1000) {
                restaurantBundles.remove(i);
                i -= 1;
            }
        }
    }

    private void createBundles() {
        int i;

        restaurantBundles.clear();

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

    public void findCity(String postalCode) {
        new CityRepository(new ICallback() {
            @Override
            public void execute(String json) {
                int i = 0;
                city = new Gson().fromJson(json, City.class);
                cities.add(city);
                addCitiesToSQLiteDb();
                Cursor res = myDb.getAllCities();

                while (res.moveToNext()) {
                    city = new City();
                    city.setPostalCode(res.getString(0));
                    city.setName(res.getString(1));
                    citiesSQLite.add(city);
                    i++;
                }

                RestaurantListActivity.this.threadCount += 1;

                if (RestaurantListActivity.this.threadCount == restaurants.size() + 1) {
                    RestaurantListActivity.this.userCity = cities.get(cities.size() - 1);
                    cities.remove(cities.size() - 1);
                    threadCount = 0;
                    from = preferences.getString(GLOBALS.ADDRESS, user.getAddress()) + " " + userCity.getName();
                    calcDistances();
                }
            }
        }).find(postalCode);

    }

    private void calcDistances() {
        int i;

        for (i = 0; i < cities.size(); i++) {
            String to = restaurants.get(i).getAddress() + " " + cities.get(i).getName();
            setDistance(from, to);
        }
    }

    public void setDistance(String form, String to) {
        new DistanceCalculator(from, to, new ICallback() {
            @Override
            public void execute(String json) {
                MapsContainer mapsContainer = new Gson().fromJson(json, MapsContainer.class);
                maps.add(mapsContainer);
                RestaurantListActivity.this.threadCount += 1;

                if (RestaurantListActivity.this.threadCount == cities.size()) {
                    createBundles();
                    removeBundles();
                    sort();
                    adapter.notifyDataSetChanged();
                    setLocationParams();
                }
            }
        }).calculate();
    }

    private void setLocationParams() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double longitude, latitude;

                longitude = location.getLongitude();
                latitude = location.getLatitude();

                setAddress(longitude, latitude);
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

<<<<<<< HEAD
    @Override
    public void setAddress(String json) {
        int start = json.indexOf("formatted_address");
        int end = json.indexOf("\",", start);
        String line = json.substring(start, end);
        from = line.substring(22, line.length());
        //Toast.makeText(this, this.from, Toast.LENGTH_SHORT).show();
        this.threadCount = 0;
        calcDistances();
=======
    private void setAddress(double longitude, double latitude) {
        new CoordinatesConverter(longitude, latitude, new ICallback() {
            @Override
            public void execute(String json) {
                int start = json.indexOf(GLOBALS.FORMATTED_ADDRESS);
                int end = json.indexOf("\",", start);
                String line = json.substring(start, end);
                from = line.substring(GLOBALS.FORMATTED_ADDRESS_LENGTH, line.length());
                RestaurantListActivity.this.threadCount = 0;
                maps.clear();
                restaurantBundles.clear();
                calcDistances();
                RestaurantListActivity.this.user.setAddress(from);
                RestaurantListActivity.this.user.setCity("");
            }
        }).convert();
>>>>>>> 7cf3302c2488800d4686c5563968c68a19db9155
    }
}
