package com.Commands;

import com.DataModels.SimpleLinkedList;
import com.DataModels.Product;

public class ViewCartCommand implements Command{
    private SimpleLinkedList<Product> shoppingCart;

    public ViewCartCommand(SimpleLinkedList<Product> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public void execute() {
        System.out.println("Shopping Cart:");
        shoppingCart.print();
    }

    @Override
    public boolean undo() {
        return false;
    }
}
