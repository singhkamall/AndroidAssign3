package com.example.owner.fatih_kamal_mapd711_onlinepurchase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.owner.fatih_kamal_mapd711_onlinepurchase.data.ProductsViewAdapter;

public class ProductActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        SharedPreferences sharePref = getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = sharePref.getString(getResources().getString(R.string.sp_user), "");
        boolean isCustomer = sharePref.getBoolean(getResources().getString(R.string.sp_is_customer), false);
        String uid = sharePref.getString(getResources().getString(R.string.sp_uid), "");

        TextView tvUsername = (TextView) findViewById(R.id.username);
        tvUsername.setText("Login as: " + username);

        listView = (ListView) findViewById(R.id.lProducts);

        listView.setAdapter(new ProductsViewAdapter(this));
    }

    public void btnOrderView_click(View view) {
        Intent i = new Intent(this, CartActivity.class);
        startActivity(i);
    }
}