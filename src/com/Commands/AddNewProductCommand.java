package com.Commands;

import com.DataModels.Product;
import com.DataModels.SimpleBinarySearchTree;

public class AddNewProductCommand implements Command {
    private SimpleBinarySearchTree<Product> productCatalog;
    private Product product;

    public AddNewProductCommand(SimpleBinarySearchTree<Product> productCatalog, Product product) {
        this.productCatalog = productCatalog;
        this.product = product;
    }

    @Override
    public void execute() {
        System.out.println("Adding new product to catalog...");
        productCatalog.insert(product);
    }

    @Override
    public boolean undo() {
        System.out.println("Undoing add product operation...");
        productCatalog.delete(product);
        return true;
    }
    
}
