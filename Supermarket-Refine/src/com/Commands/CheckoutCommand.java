package com.Commands;

import com.DataModels.ShoppingCart;
import com.DataModels.Inventory;
import com.DataModels.Product;


public class CheckoutCommand implements Command {
    private Inventory inventory;
    private ShoppingCart shoppingCart;

    public CheckoutCommand(Inventory inventory, ShoppingCart shoppingCart) {
        this.inventory = inventory;
        this.shoppingCart = shoppingCart;
    }

    @Override
    public void execute() {
        System.out.println("Checking out...");
        System.out.println("Receipt");
        System.out.println("-------");
        System.out.println("ID\tName\tPrice");
        System.out.println("--------------------------------");
        for (Product product : shoppingCart.getCart()) {
            System.out.println(product.getId() + "\t" + product.getName() + "\t" + product.getPrice());
            inventory.minusOne(product.getId());
        }
        System.out.println("--------------------------------");
        System.out.println("Total: " + shoppingCart.getTotal());
        System.out.println("Thank you for shopping with us!");
        
        shoppingCart.clearCart();
        inventory.save();
    }

    @Override
    public boolean undo() {
        // No undo operation for this command
        return false;
    }

    
}
