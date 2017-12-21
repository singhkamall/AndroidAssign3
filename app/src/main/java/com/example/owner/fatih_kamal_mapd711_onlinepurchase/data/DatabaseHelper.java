package com.example.owner.fatih_kamal_mapd711_onlinepurchase.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.owner.fatih_kamal_mapd711_onlinepurchase.data.PurchaseContract.ClerkEntry;
import com.example.owner.fatih_kamal_mapd711_onlinepurchase.data.PurchaseContract.CustomerEntry;
import com.example.owner.fatih_kamal_mapd711_onlinepurchase.data.PurchaseContract.OrderEntry;
import com.example.owner.fatih_kamal_mapd711_onlinepurchase.data.PurchaseContract.ProductEntry;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static  final String DATABASE_NAME = "OnlinePur.db";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_CLERK_CREATE =
            "CREATE TABLE " + ClerkEntry.TABLE_NAME + " (" +
                    ClerkEntry._ID + " INTEGER PRIMARY KEY, " +
                    ClerkEntry.COLUMN_userName + " TEXT, " +
                    ClerkEntry.COLUMN_password + " TEXT, " +
                    ClerkEntry.COLUMN_firstname + " TEXT, " +
                    ClerkEntry.COLUMN_lastname + " TEXT" +
            ")";

    private static final String TABLE_CUSTOMER_CREATE =
            "CREATE TABLE " + CustomerEntry.TABLE_NAME + " (" +
                    CustomerEntry._ID + " INTEGER PRIMARY KEY, " +
                    CustomerEntry.COLUMN_userName + " TEXT, " +
                    CustomerEntry.COLUMN_password + " TEXT, " +
                    CustomerEntry.COLUMN_firstname + " TEXT, " +
                    CustomerEntry.COLUMN_lastname + " TEXT, " +
                    CustomerEntry.COLUMN_address + " TEXT, " +
                    CustomerEntry.COLUMN_city + " TEXT, " +
                    CustomerEntry.COLUMN_postalCode + " TEXT" +
            ")";

    private static final String TABLE_PRODUCT_CREATE =
            "CREATE TABLE " + ProductEntry.TABLE_NAME + " (" +
                    ProductEntry._ID + " INTEGER PRIMARY KEY, " +
                    ProductEntry.COLUMN_productName + " TEXT, " +
                    ProductEntry.COLUMN_price + " TEXT, " +
                    ProductEntry.COLUMN_quantity + " TEXT, " +
                    ProductEntry.COLUMN_category + " TEXT" +
            ")";

    private static final String TABLE_ORDER_CREATE =
            "CREATE TABLE " + OrderEntry.TABLE_NAME + " (" +
                    OrderEntry._ID + " INTEGER PRIMARY KEY, " +
                    OrderEntry.COLUMN_customerId + " INTEGER, " +
                    OrderEntry.COLUMN_productId + " INTEGER, " +
                    OrderEntry.COLUMN_employeeId + " INTEGER, " +
                    OrderEntry.COLUMN_orderDate + " TEXT, " +
                    OrderEntry.COLUMN_status + " TEXT" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CLERK_CREATE);
        db.execSQL(TABLE_CUSTOMER_CREATE);
        db.execSQL(TABLE_ORDER_CREATE);
        db.execSQL(TABLE_PRODUCT_CREATE);

        OnCreateDummyRecords(db);
    }

    public void OnCreateDummyRecords(SQLiteDatabase db) {
        String ClerkRecord = "INSERT INTO " + ClerkEntry.TABLE_NAME +
                "(" +
                ClerkEntry._ID + ", " +
                ClerkEntry.COLUMN_userName + ", " +
                ClerkEntry.COLUMN_password + ", " +
                ClerkEntry.COLUMN_firstname + ", " +
                ClerkEntry.COLUMN_lastname +
                ") VALUES (NULL, \"fatih\", \"password\", \"Fatih\", \"Inan\")";
        db.execSQL(ClerkRecord);

        String CustomerRecord = "INSERT INTO " + CustomerEntry.TABLE_NAME +
                "(" +
                CustomerEntry._ID + ", " +
                CustomerEntry.COLUMN_userName + ", " +
                CustomerEntry.COLUMN_password + ", " +
                CustomerEntry.COLUMN_firstname + ", " +
                CustomerEntry.COLUMN_lastname + ", " +
                CustomerEntry.COLUMN_address + ", " +
                CustomerEntry.COLUMN_city + ", " +
                CustomerEntry.COLUMN_postalCode +
                ") VALUES (NULL, \"cust\", \"password\", \"Mike\", \"Peterson\", NULL, NULL, NULL)";
        db.execSQL(CustomerRecord);

        String ProductRecord_1 = "INSERT INTO " + ProductEntry.TABLE_NAME +
                "(" +
                ProductEntry._ID + ", " +
                ProductEntry.COLUMN_productName + ", " +
                ProductEntry.COLUMN_price + ", " +
                ProductEntry.COLUMN_quantity + ", " +
                ProductEntry.COLUMN_category +
                ") VALUES (NULL, \"Tea\", \"1\", \"1\", \"Grocery\")";
        db.execSQL(ProductRecord_1);

        String ProductRecord_2 = "INSERT INTO " + ProductEntry.TABLE_NAME +
                "(" +
                ProductEntry._ID + ", " +
                ProductEntry.COLUMN_productName + ", " +
                ProductEntry.COLUMN_price + ", " +
                ProductEntry.COLUMN_quantity + ", " +
                ProductEntry.COLUMN_category +
                ") VALUES (NULL, \"Bread\", \"2\", \"1\", \"Grocery\")";
        db.execSQL(ProductRecord_2);

        String ProductRecord_3 = "INSERT INTO " + ProductEntry.TABLE_NAME +
                "(" +
                ProductEntry._ID + ", " +
                ProductEntry.COLUMN_productName + ", " +
                ProductEntry.COLUMN_price + ", " +
                ProductEntry.COLUMN_quantity + ", " +
                ProductEntry.COLUMN_category +
                ") VALUES (NULL, \"Milk\", \"3\", \"1\", \"Grocery\")";
        db.execSQL(ProductRecord_3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + CustomerEntry.TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + ClerkEntry.TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + ProductEntry.TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + OrderEntry.TABLE_NAME);

        db.execSQL("ALTER TABLE " + OrderEntry.TABLE_NAME + " ADD COLUMN " + OrderEntry.COLUMN_quantity + " TEXT");
    }

    public Cursor GetData
            (String Table_Name, String[] Projection,
             String Selection, String[] SelectionArgs)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(Table_Name, Projection, Selection, SelectionArgs,
                null,null,null);

        return c;
    }

    public Object InsertRow(String TableName, ContentValues values)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Object id = db.insert(TableName, null, values);
        return id;
    }

    public void Update(String TableName, ContentValues values, String where, String[] whereAegs)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TableName, values, where, whereAegs);
    }

    public void DeleteRow(String TableName, String where, String[] whereArgs)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableName, where, whereArgs);
    }

}