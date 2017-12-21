package com.example.owner.fatih_kamal_mapd711_onlinepurchase.model;

public class OrdersModel {

    OrdersModel() {
    }

    OrdersModel(String ID, String custID, String prodID, String empID, String orderDate,
                String status){
        this._ID = ID;
        this.COLUMN_customerId = custID;
        this.COLUMN_productId = prodID;
        this.COLUMN_employeeId = empID;
        this.COLUMN_orderDate = orderDate;
        this.COLUMN_status = status;
    }

    public String _ID = "orderId";
    public String COLUMN_customerId = "customerId";
    public String COLUMN_productId = "productId";
    public String COLUMN_employeeId = "employeeId";
    public String COLUMN_orderDate = "orderDate";
    public String COLUMN_status = "status";

}