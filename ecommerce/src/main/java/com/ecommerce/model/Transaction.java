package com.ecommerce.model;

public class Transaction {
    private final int id;
    private final String buyer_name;
    private final int product_id;
    private final int quantity;
    private final double total;
    
    public Transaction(int id, String buyer_name, int product_id, int quantity, double total) {
        this.id = id;
        this.buyer_name = buyer_name;
        this.product_id = product_id;
        this.quantity = quantity;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public String getBuyer() {
        return buyer_name;
    }

    public int getProductId() {
        return product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return total;
    }

    @Override
    public String toString() {
        return "Txn#" +id + " | " + buyer_name + " beli " + product_id + "  x" + quantity + " | Rp" + total;
    }
}
