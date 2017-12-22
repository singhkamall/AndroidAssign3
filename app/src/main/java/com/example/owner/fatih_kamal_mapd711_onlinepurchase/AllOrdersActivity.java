package com.example.owner.fatih_kamal_mapd711_onlinepurchase;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.owner.fatih_kamal_mapd711_onlinepurchase.data.CartViewAdapter;
import com.example.owner.fatih_kamal_mapd711_onlinepurchase.data.OrdersViewAdapter;

public class AllOrdersActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);

        SharedPreferences sharePref = getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = sharePref.getString(getResources().getString(R.string.sp_user), "");
        boolean isCustomer = sharePref.getBoolean(getResources().getString(R.string.sp_is_customer), false);
        String uid = sharePref.getString(getResources().getString(R.string.sp_uid), "");

        TextView tvUsername = (TextView) findViewById(R.id.username);
        tvUsername.setText("Login as: " + username);

        listView = (ListView)findViewById(R.id.lAllOrders);

        listView.setAdapter(new OrdersViewAdapter(this));
    }
}
