package com.example.owner.fatih_kamal_mapd711_onlinepurchase.model;

import android.provider.ContactsContract;

public class ProductsModel {

    ProductsModel(){}

    ProductsModel(String ID, String prodName, String price, String quantity, String cat){
        this._ID = ID;
        this.COLUMN_productName = prodName;;
        this.COLUMN_price = price;
        this.COLUMN_quantity = quantity;
        this.COLUMN_quantity_val = quantity;
        this.COLUMN_category = cat;
    }

    public String _ID = "productId";
    public String COLUMN_productName = "productName";
    public String COLUMN_price = "price";
    public String COLUMN_quantity = "quantity";
    public String COLUMN_quantity_val = "quantity_val";
    public String COLUMN_category = "category";

}