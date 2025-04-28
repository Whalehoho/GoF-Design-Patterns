package com.Commands;

import com.DataModels.ShoppingCart;

public class ViewCartCommand implements Command{
    private ShoppingCart shoppingCart;

    public ViewCartCommand(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public void execute() {
        System.out.println("Shopping Cart:");
        shoppingCart.showCart();
    }

    @Override
    public boolean undo() {
        return false;
    }
}
