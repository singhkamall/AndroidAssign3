package com.example.owner.fatih_kamal_mapd711_onlinepurchase.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.fatih_kamal_mapd711_onlinepurchase.Enums.OrderStatus;
import com.example.owner.fatih_kamal_mapd711_onlinepurchase.R;
import com.example.owner.fatih_kamal_mapd711_onlinepurchase.model.ProductOrderModel;
import com.example.owner.fatih_kamal_mapd711_onlinepurchase.model.ProductsModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CartViewAdapter extends BaseAdapter {

    ArrayList<ProductOrderModel> list;

    Context _c;

    public CartViewAdapter(Context c, String uid) {
        _c = c;
        ProductOrderModel poModel = new ProductOrderModel(c);

        list = poModel.GetAllOrdersByCustomer(uid);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.cart_row, viewGroup, false);

        final TextView orderId = (TextView) row.findViewById(R.id.c_order_id);
        final TextView prodName = (TextView) row.findViewById(R.id.c_product_name);
        final TextView proID = (TextView) row.findViewById(R.id.c_pid);
        final TextView proCat = (TextView) row.findViewById(R.id.c_product_category);
        final TextView proAvailability = (TextView) row.findViewById(R.id.c_product_availability);
        final TextView proAvailability_val = (TextView) row.findViewById(R.id.c_product_availability_val);
        final TextView proPrice = (TextView) row.findViewById(R.id.c_product_price);
        final EditText proQty = (EditText) row.findViewById(R.id.c_product_quantity);
        final ImageButton ibSave = (ImageButton) row.findViewById(R.id.ibSave);
        final TextView tvOrderDate = (TextView) row.findViewById(R.id.c_order_date);

        final ProductOrderModel temp = list.get(i);

        orderId.setText(temp.orderId);
        proID.setText(temp.productId);
        prodName.setText(temp.productName);
        proCat.setText(temp.category);
        proAvailability.setText("Available: " + temp.available_quantity);
        proAvailability_val.setText(temp.available_quantity_val);
        proPrice.setText("$" + temp.price);
        proQty.setText(temp.quantity);
        tvOrderDate.setText(temp.orderDate);

        long diffInMin = 0;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            Date strDate = sdf.parse(temp.orderDate);
            long diffInMillisec = Calendar.getInstance().getTime().getTime() - strDate.getTime();
            diffInMin = TimeUnit.MILLISECONDS.toMinutes(diffInMillisec);

            if (diffInMin > 5) {
                proQty.setEnabled(false);
                ibSave.setVisibility(View.GONE);
            }

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        final DatabaseHelper helper = new DatabaseHelper(_c);
        SharedPreferences sharePref = _c.getSharedPreferences("user", Context.MODE_PRIVATE);
        final String uid = sharePref.getString(_c.getResources().getString(R.string.sp_uid), "");

        return row;
    }
}