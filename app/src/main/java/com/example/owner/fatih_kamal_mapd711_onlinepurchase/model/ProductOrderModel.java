package com.example.owner.fatih_kamal_mapd711_onlinepurchase.model;

import android.content.Context;
import android.database.Cursor;

import com.example.owner.fatih_kamal_mapd711_onlinepurchase.data.DatabaseHelper;
import com.example.owner.fatih_kamal_mapd711_onlinepurchase.data.PurchaseContract;

import java.util.ArrayList;
import java.util.List;

public class ProductOrderModel {

    private Context _context;

    public ProductOrderModel(Context context) {
        _context = context;
    }

    ProductOrderModel(String _productId, String _productName, String _price, String _available_quantity, String _category,
                      String orderId, String _customerId, String _employeeId, String _orderDate, String _status, String _quantity,
                      String _customerName) {
        this.productId = _productId;
        this.productName = _productName;
        this.price = _price;
        this.available_quantity = _available_quantity;
        this.available_quantity_val = _available_quantity;
        this.category = _category;
        this.orderId = orderId;
        this.customerId = _customerId;
        this.employeeId = _employeeId;
        this.orderDate = _orderDate;
        this.status = _status;
        this.quantity = _quantity;
        this.customerName = _customerName;
    }

    public String productId;
    public String productName;
    public String price;
    public String available_quantity;
    public String available_quantity_val;
    public String category;

    public String orderId;
    public String customerId;
    public String employeeId;
    public String orderDate;
    public String status;
    public String quantity;

    public String customerName;

    public ArrayList<ProductsModel> GetAllProducts() {
        DatabaseHelper helper = new DatabaseHelper(_context);

        String ProdTable = PurchaseContract.ProductEntry.TABLE_NAME;

        Cursor c = helper.GetData(ProdTable, null, null, null);

        ArrayList<ProductsModel> productsModels = new ArrayList<ProductsModel>();
        while (c.moveToNext()) {
            ProductsModel pm = new ProductsModel(
                    c.getString(c.getColumnIndex(PurchaseContract.ProductEntry._ID)),
                    c.getString(c.getColumnIndex(PurchaseContract.ProductEntry.COLUMN_productName)),
                    c.getString(c.getColumnIndex(PurchaseContract.ProductEntry.COLUMN_price)),
                    c.getString(c.getColumnIndex(PurchaseContract.ProductEntry.COLUMN_quantity)),
                    c.getString(c.getColumnIndex(PurchaseContract.ProductEntry.COLUMN_category))
            );
            productsModels.add(pm);
        }

        return productsModels;
    }

    public ArrayList<ProductOrderModel> GetAllOrdersByCustomer(String customerId) {
        DatabaseHelper helper = new DatabaseHelper(_context);

        String OrderTable = PurchaseContract.OrderEntry.TABLE_NAME;
        String Selection = PurchaseContract.OrderEntry.COLUMN_customerId + " = ?";
        String[] SelectionArgs = {customerId};

        Cursor c = helper.GetData(OrderTable, null, Selection, SelectionArgs);

        ArrayList<ProductsModel> allProucts = GetAllProducts();

        ArrayList<ProductOrderModel> ordersList = new ArrayList<>();

        while (c.moveToNext()) {

            String pid = c.getString(c.getColumnIndex(PurchaseContract.OrderEntry.COLUMN_productId));
            String oId = c.getString(c.getColumnIndex(PurchaseContract.OrderEntry._ID));
            String oCustId = c.getString(c.getColumnIndex(PurchaseContract.OrderEntry.COLUMN_customerId));
            String oEmpId = c.getString(c.getColumnIndex(PurchaseContract.OrderEntry.COLUMN_employeeId));
            String oDate = c.getString(c.getColumnIndex(PurchaseContract.OrderEntry.COLUMN_orderDate));
            String oStatus = c.getString(c.getColumnIndex(PurchaseContract.OrderEntry.COLUMN_status));
            String oQty = c.getString(c.getColumnIndex(PurchaseContract.OrderEntry.COLUMN_quantity));

            String pName = "", pPrice = "", pAvailable = "", pCat = "";
            for (ProductsModel pm : allProucts) {
                String _pid = pm._ID;

                if (_pid.toString().equals(pid.toString())) {
                    pName = pm.COLUMN_productName;
                    pPrice = pm.COLUMN_price;
                    pAvailable = pm.COLUMN_quantity_val;
                    pName = pm.COLUMN_productName;
                    pCat = pm.COLUMN_category;

                    break;
                }
            }

            ProductOrderModel pom =
                    new ProductOrderModel(pid, pName, pPrice, pAvailable, pCat, oId, oCustId, oEmpId, oDate, oStatus, oQty, null);
            ordersList.add(pom);
        }

        return ordersList;
    }

    public ArrayList<ProductOrderModel> GetAllOrders() {
        DatabaseHelper helper = new DatabaseHelper(_context);

        String OrderTable = PurchaseContract.OrderEntry.TABLE_NAME;
        String Selection = null;
        String[] SelectionArgs = {};

        Cursor c = helper.GetData(OrderTable, null, Selection, SelectionArgs);

        ArrayList<ProductsModel> allProucts = GetAllProducts();

        ArrayList<ProductOrderModel> ordersList = new ArrayList<>();

        // Getting ALl Customers
        Cursor cCustomers = helper.GetData(PurchaseContract.CustomerEntry.TABLE_NAME, null, null, null);

        while (c.moveToNext()) {

            String pid = c.getString(c.getColumnIndex(PurchaseContract.OrderEntry.COLUMN_productId));
            String oId = c.getString(c.getColumnIndex(PurchaseContract.OrderEntry._ID));
            String oCustId = c.getString(c.getColumnIndex(PurchaseContract.OrderEntry.COLUMN_customerId));
            String oEmpId = c.getString(c.getColumnIndex(PurchaseContract.OrderEntry.COLUMN_employeeId));
            String oDate = c.getString(c.getColumnIndex(PurchaseContract.OrderEntry.COLUMN_orderDate));
            String oStatus = c.getString(c.getColumnIndex(PurchaseContract.OrderEntry.COLUMN_status));
            String oQty = c.getString(c.getColumnIndex(PurchaseContract.OrderEntry.COLUMN_quantity));

            String pName = "", pPrice = "", pAvailable = "", pCat = "", customerName = "";
            // Populatin Product Information
            for (ProductsModel pm : allProucts) {
                String _pid = pm._ID;

                if (_pid.toString().equals(pid.toString())) {
                    pName = pm.COLUMN_productName;
                    pPrice = pm.COLUMN_price;
                    pAvailable = pm.COLUMN_quantity_val;
                    pName = pm.COLUMN_productName;
                    pCat = pm.COLUMN_category;

                    break;
                }
            }

            // Populatin Customer Information
            while (cCustomers.moveToNext()) {
                String _custID = cCustomers.getString(cCustomers.getColumnIndex(PurchaseContract.CustomerEntry._ID));

                if (_custID.equals(oCustId)) {
                    customerName = cCustomers.getString(cCustomers.getColumnIndex(PurchaseContract.CustomerEntry.COLUMN_firstname));

                    break;
                }
            }

            ProductOrderModel pom =
                    new ProductOrderModel(pid, pName, pPrice, pAvailable, pCat, oId, oCustId, oEmpId, oDate, oStatus, oQty, customerName);
            ordersList.add(pom);
        }

        return ordersList;
    }
}