package mobile_dev.mobile_dev.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

import mobile_dev.mobile_dev.activity.IActivity;
import mobile_dev.mobile_dev.activity.RestaurantListActivity;
import mobile_dev.mobile_dev.model.*;
import mobile_dev.mobile_dev.repository.UserRepository;

public class SQLite extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "MDev";

    // Table Names
    private static final String TABLE_CITIES = "cities";
    private static final String TABLE_DISHES = "dishes";
    private static final String TABLE_MENUS = "menus";
    private static final String TABLE_RESTAURANTS = "restaurants";
    private static final String TABLE_RESTAURANTS_DISHES = "restaurant_dishes";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_RESTAURANT_ID = "restaurant_id";
    private static final String KEY_ADDRESS = "address";

    // TABLE_CITIES - column names
    private static final String KEY_POSTAL_CODE = "postal_code";

    // TABLE_DISHES - column names
    private static final String KEY_IMAGE = "image";

    // TABLE_MENUS - column names
    private static final String KEY_PRICE = "price";

    // TABLE_RESTAURANTS - column names
    private static final String KEY_CITY = "city";

    // TABLE_RESTAURANTS_DISHES - column names
    private static final String KEY_DISHES_ID = "dishes_id";


    // Table Create Statements
    // TABLE_CITIES - create statement
    private static final String CREATE_TABLE_CITIES = "CREATE TABLE "
            + TABLE_CITIES + "(" + KEY_POSTAL_CODE + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT)";

    // TABLE_DISHES - create statement
    private static final String CREATE_TABLE_DISHES = "CREATE TABLE " + TABLE_DISHES
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_IMAGE + " TEXT,"
            + KEY_NAME + " TEXT)";

    // TABLE_MENUS - create statement
    private static final String CREATE_TABLE_MENUS = "CREATE TABLE "
            + TABLE_MENUS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT," + KEY_PRICE + " INTEGER)";

    // TABLE_RESTAURANTS - create statement
    private static final String CREATE_TABLE_RESTAURANTS = "CREATE TABLE "
            + TABLE_RESTAURANTS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_ADDRESS + " TEXT," + KEY_CITY + " TEXT," + KEY_NAME + " TEXT)";

    // TABLE_RESTAURANTS_DISHES - create statement
    private static final String CREATE_TABLE_RESTAURANTS_DISHES = "CREATE TABLE "
            + TABLE_RESTAURANTS_DISHES + "(" + KEY_RESTAURANT_ID + " INTEGER PRIMARY KEY,"
            + KEY_DISHES_ID + " INTEGER)";

    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_CITIES);
        db.execSQL(CREATE_TABLE_DISHES);
        db.execSQL(CREATE_TABLE_MENUS);
        db.execSQL(CREATE_TABLE_RESTAURANTS);
        //db.execSQL(CREATE_TABLE_RESTAURANTS_DISHES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISHES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS_DISHES);

        // create new tables
        onCreate(db);
    }

    public boolean insertCities(String postal_code, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();;
        contentValues.put(KEY_POSTAL_CODE, postal_code);
        contentValues.put(KEY_NAME, name);
        long result = db.insert(TABLE_CITIES, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertDishes(int id, String image, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, id);
        contentValues.put(KEY_IMAGE, image);
        contentValues.put(KEY_NAME, name);
        long result = db.insert(TABLE_DISHES, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertMenus(int id, String name, int price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, id);
        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_POSTAL_CODE, price);
        long result = db.insert(TABLE_MENUS, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertRestaurants(int id, String address, String city, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, id);
        contentValues.put(KEY_ADDRESS, address);
        contentValues.put(KEY_CITY, city);
        contentValues.put(KEY_NAME, name);
        long result = db.insert(TABLE_RESTAURANTS, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
}
