package com.ecommerceutils;

public class Order implements Comparable<Order> {
    private int orderId;
    private int productId;
    private int quantity;
    private int priority; // Lower number means higher priority

    public Order(int orderId, int productId, int quantity, int priority) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.priority = priority;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public int compareTo(Order other) {
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public String toString() {
        return "Order " + orderId + " for Product ID " + productId + " (Quantity: " + quantity + ", Priority: " + priority + ")";
    }
}
