package org.mariqzw.domain.model;

public class OrderItem {
    private final String productId;
    private final int quantity;

    public OrderItem(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "[" + productId + " x " + quantity + "]";
    }
}
