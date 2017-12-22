package com.example.owner.fatih_kamal_mapd711_onlinepurchase.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.fatih_kamal_mapd711_onlinepurchase.Enums.OrderStatus;
import com.example.owner.fatih_kamal_mapd711_onlinepurchase.model.ProductOrderModel;
import com.example.owner.fatih_kamal_mapd711_onlinepurchase.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class OrdersViewAdapter extends BaseAdapter {

    ArrayList<ProductOrderModel> list;

    Context _c;

    public OrdersViewAdapter(Context c) {
        _c = c;
        ProductOrderModel poModel = new ProductOrderModel(c);

        list = poModel.GetAllOrders();
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
        View row = inflater.inflate(R.layout.all_orders_row, viewGroup, false);

        final TextView orderId = (TextView) row.findViewById(R.id.c_order_id);
        final TextView prodName = (TextView) row.findViewById(R.id.c_product_name);
        final TextView proID = (TextView) row.findViewById(R.id.c_pid);
        final TextView proCat = (TextView) row.findViewById(R.id.c_product_category);
        final TextView proPrice = (TextView) row.findViewById(R.id.c_product_price);
        final TextView proQty = (TextView) row.findViewById(R.id.quantity);
        final ImageButton ibDeliver = (ImageButton) row.findViewById(R.id.ibDeliver);
        final TextView tvOrderDate = (TextView) row.findViewById(R.id.c_order_date);
        final TextView tvCustomerName = (TextView) row.findViewById(R.id.customerName);
        final TextView tvStatus = (TextView) row.findViewById(R.id.status);

        final ProductOrderModel temp = list.get(i);

        orderId.setText(temp.orderId);
        proID.setText(temp.productId);
        prodName.setText(temp.productName);
        proCat.setText(temp.category);
        proPrice.setText("$" + temp.price);
        proQty.setText("Quantity: " + temp.quantity);
        tvCustomerName.setText("Customer: " + temp.customerName);
        tvStatus.setText(temp.status);
        tvOrderDate.setText(temp.orderDate);

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            Date strDate = sdf.parse(temp.orderDate);

            DateFormat destDf = new SimpleDateFormat("MMM dd HH:mm");

            tvOrderDate.setText("Order Date: " + destDf.format(strDate));

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        final DatabaseHelper helper = new DatabaseHelper(_c);
        SharedPreferences sharePref = _c.getSharedPreferences("user", Context.MODE_PRIVATE);
        final String uid = sharePref.getString(_c.getResources().getString(R.string.sp_uid), "");

        if (temp.status.equals(OrderStatus.Delivery.toString())) {
            ibDeliver.setVisibility(View.GONE);
        }

        ibDeliver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                ContentValues values = new ContentValues();
                values.put(PurchaseContract.OrderEntry.COLUMN_status, OrderStatus.Delivery.toString());
                String where = PurchaseContract.OrderEntry._ID + " = ?";
                String[] whereArgs = {temp.orderId};

                helper.Update(PurchaseContract.OrderEntry.TABLE_NAME, values, where, whereArgs);

                tvStatus.setText(OrderStatus.Delivery.toString());
                ibDeliver.setVisibility(View.GONE);

                Toast.makeText(_c, "Status updated succesfully", Toast.LENGTH_LONG).show();
            }
        });

        return row;
    }
}
