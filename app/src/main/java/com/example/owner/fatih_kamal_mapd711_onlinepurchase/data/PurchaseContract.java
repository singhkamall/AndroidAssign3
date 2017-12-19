package com.example.owner.fatih_kamal_mapd711_onlinepurchase.data;

import android.provider.BaseColumns;

public class PurchaseContract {

    public static final class CustomerEntry implements BaseColumns {
        // Table name
        public static final String TABLE_NAME = "Customer";
        //column (field) names
        public static final String _ID = "customerId";
        public static final String COLUMN_userName = "userName";
        public static final String COLUMN_password = "password";
        public static final String COLUMN_firstname = "firstname";
        public static final String COLUMN_lastname = "lastname";
        public static final String COLUMN_address = "address";
        public static final String COLUMN_city = "city";
        public static final String COLUMN_postalCode = "postalCode";
    }

    public static final class ClerkEntry implements BaseColumns {
        // Table name
        public static final String TABLE_NAME = "Clerk";
        //column names
        public static final String _ID = "employeeId";
        public static final String COLUMN_userName = "userName";
        public static final String COLUMN_password = "password";
        public static final String COLUMN_firstname = "firstname";
        public static final String COLUMN_lastname = "lastname";
    }

    public static final class ProductEntry implements BaseColumns {
        // Table name
        public static final String TABLE_NAME = "Product";
        //column names
        public static final String _ID = "productId";
        public static final String COLUMN_productName = "productName";
        public static final String COLUMN_price = "price";
        public static final String COLUMN_quantity = "quantity";
        public static final String COLUMN_category = "category";
    }

    public static final class OrderEntry implements BaseColumns {
        // Table name
        public static final String TABLE_NAME = "Orders";
        //column names
        public static final String _ID = "orderId";
        public static final String COLUMN_customerId = "customerId";
        public static final String COLUMN_productId = "productId";
        public static final String COLUMN_employeeId = "employeeId";
        public static final String COLUMN_orderDate = "orderDate";
        public static final String COLUMN_status = "status";
    }

}