package com.Commands;

import com.DataModels.SimpleBinarySearchTree;
import com.DataModels.SimpleLinkedList;
import com.DataModels.Product;

public class AddToCartCommand implements Command{
    private SimpleBinarySearchTree<Product> productCatalog;
    private SimpleLinkedList<Product> shoppingCart;
    private int productId;

    public AddToCartCommand(SimpleBinarySearchTree<Product> productCatalog, SimpleLinkedList<Product> shoppingCart, int productId) {
        this.productCatalog = productCatalog;
        this.shoppingCart = shoppingCart;
        this.productId = productId;
    }

    @Override
    public void execute() {
        Product p = productCatalog.search(productId, Product::getId);
        if (p != null) {
            shoppingCart.addFirst(p);
            System.out.println("Adding " + p.getName() + " to cart...");
        } else {
            System.out.println("Product with ID " + productId + " not found in catalog.");
        }
    }

    @Override
    public boolean undo() {
        Product p = productCatalog.search(productId, Product::getId);
        if (p != null) {
            shoppingCart.removeFirst();
            System.out.println("Removing " + p.getName() + "from cart...");
            return true;
        } else {
            return false;
        }
    }
}
