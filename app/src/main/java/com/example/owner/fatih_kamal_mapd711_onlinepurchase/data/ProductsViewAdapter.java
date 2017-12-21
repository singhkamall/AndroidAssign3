package com.example.owner.fatih_kamal_mapd711_onlinepurchase.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.fatih_kamal_mapd711_onlinepurchase.Enums.OrderStatus;
import com.example.owner.fatih_kamal_mapd711_onlinepurchase.model.ProductOrderModel;
import com.example.owner.fatih_kamal_mapd711_onlinepurchase.model.ProductsModel;
import com.example.owner.fatih_kamal_mapd711_onlinepurchase.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ProductsViewAdapter extends BaseAdapter {

    ArrayList<ProductsModel> list;

    Context _c;

    public ProductsViewAdapter(Context c) {
        _c = c;
        ProductOrderModel poModel = new ProductOrderModel(c);

        list = poModel.GetAllProducts();
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
        View row = inflater.inflate(R.layout.products_row, viewGroup, false);

        final TextView prodName = (TextView) row.findViewById(R.id.product_name);
        final TextView proID = (TextView) row.findViewById(R.id.pid);
        final TextView proCat = (TextView) row.findViewById(R.id.product_category);
        final TextView proAvailability = (TextView) row.findViewById(R.id.product_availability);
        final TextView proAvailability_val = (TextView) row.findViewById(R.id.product_availability_val);
        final TextView proPrice = (TextView) row.findViewById(R.id.product_price);
        final EditText proQty = (EditText) row.findViewById(R.id.product_quantity);
        final CheckBox cbCart = (CheckBox) row.findViewById(R.id.cbCart);

        final ProductsModel temp = list.get(i);

        proID.setText(temp._ID);
        prodName.setText(temp.COLUMN_productName);
        proCat.setText(temp.COLUMN_category);
        proAvailability.setText("Available: " + temp.COLUMN_quantity);
        proAvailability_val.setText(temp.COLUMN_quantity);
        proPrice.setText("$" + temp.COLUMN_price);

        final DatabaseHelper helper = new DatabaseHelper(_c);

        SharedPreferences sharePref = _c.getSharedPreferences("user", Context.MODE_PRIVATE);
        final String uid = sharePref.getString(_c.getResources().getString(R.string.sp_uid), "");

        cbCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (cbCart.isChecked()) {

                    // Show error if no quantity is there
                    if (proQty.getText().toString().trim().length() == 0) {
                        Toast.makeText(_c, "Add Quantity", Toast.LENGTH_SHORT).show();

                        cbCart.setChecked(false);
                        return;
                    }

                    // Show error if quantity is more than avilable items
                    int qty = Integer.valueOf(proQty.getText().toString());
                    int availableQty = Integer.valueOf(proAvailability_val.getText().toString());
                    if (qty > availableQty){
                        Toast.makeText(_c, "Quantity cannot be greater than available items", Toast.LENGTH_SHORT).show();

                        cbCart.setChecked(false);
                        return;
                    }

                    // Add to DB
                    ContentValues values = new ContentValues();
                    values.put(PurchaseContract.OrderEntry.COLUMN_customerId, uid);
                    values.put(PurchaseContract.OrderEntry.COLUMN_orderDate, String.valueOf(Calendar.getInstance().getTime()));
                    values.put(PurchaseContract.OrderEntry.COLUMN_productId, temp._ID);
                    values.put(PurchaseContract.OrderEntry.COLUMN_status, String.valueOf(OrderStatus.In_Process));
                    values.put(PurchaseContract.OrderEntry.COLUMN_quantity, proQty.getText().toString());

                    helper.InsertRow(PurchaseContract.OrderEntry.TABLE_NAME, values);

                    Toast.makeText(_c, "'" + prodName.getText().toString() + "'" + " added to cart", Toast.LENGTH_SHORT).show();

                    proQty.setEnabled(false);

                } else {

                    // Remove from DB

                    String where = PurchaseContract.OrderEntry.COLUMN_customerId + " = ? AND " +
                            PurchaseContract.OrderEntry.COLUMN_productId + " = ? ";
                    String[] whereArgs = {uid, proID.getText().toString()};

                    helper.DeleteRow(PurchaseContract.OrderEntry.TABLE_NAME, where, whereArgs);

                    Toast.makeText(_c, "'" + prodName.getText().toString() + "'" + " removed from cart", Toast.LENGTH_SHORT).show();

                    proQty.setEnabled(true);
                }
            }
        });

        return row;
    }
}