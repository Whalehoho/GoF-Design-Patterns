package com.DataModels;

import java.util.LinkedList;


public class ShoppingCart {
    private LinkedList<Product> cart;

    public ShoppingCart() {
        cart = new LinkedList<>();
    }

    public LinkedList<Product> getCart() {
        return cart;
    }

    public void add(Product product) {
        cart.add(product);
    }

    public void removeLast() {
        if (!cart.isEmpty()) {
            cart.removeLast();
        }
    }

    public void showCart() {
        System.out.println("\nShopping Cart:");
        System.out.printf("%-10s %-20s %-20s%n", "ID", "Name", "Unit Price");
        System.out.println("----------------------------------------------------------------");
        cart.forEach((product) -> {
            System.out.printf("%-10d %-20s %-20.2f%n", product.getId(), product.getName(), product.getPrice());
        });
    }

    public double getTotal() {
        double total = 0;
        for (Product product : cart) {
            total += product.getPrice();
        }
        return total;
    }

    public void clearCart() {
        cart.clear();
    }


}
