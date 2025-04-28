package com.Commands;

import com.DataModels.Catalog;
import com.DataModels.ShoppingCart;
import com.DataModels.Product;

public class AddCartCommand implements Command{
    private Catalog catalog;
    private ShoppingCart shoppingCart;
    private int productId;

    public AddCartCommand(Catalog catalog, ShoppingCart shoppingCart, int productId) {
        this.catalog = catalog;
        this.shoppingCart = shoppingCart;
        this.productId = productId;
    }

    @Override
    public void execute() {
        Product p = catalog.getProductById(productId);
        if (p != null) {
            shoppingCart.add(p);
            System.out.println("Adding " + p.getName() + " to cart...");
        } else {
            System.out.println("Product with ID " + productId + " not found in catalog.");
        }
    }

    @Override
    public boolean undo() {
        Product p = catalog.getProductById(productId);
        if (p != null) {
            shoppingCart.removeLast();
            System.out.println("Removing " + p.getName() + "from cart...");
            return true;
        } else {
            return false;
        }
    }
}

