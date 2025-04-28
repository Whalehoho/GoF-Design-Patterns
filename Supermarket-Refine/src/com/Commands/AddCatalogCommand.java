package com.Commands;

import com.DataModels.Product;
import com.DataModels.Catalog;

public class AddCatalogCommand implements Command {
    private Catalog catalog;
    private Product product;

    public AddCatalogCommand(Catalog catalog, Product product) {
        this.catalog = catalog;
        this.product = product;
    }

    @Override
    public void execute() {
        System.out.println("Adding new product to catalog...");
        catalog.add(product);
    }

    @Override
    public boolean undo() {
        System.out.println("Undoing add product operation...");
        catalog.remove(product);
        return true;
    }
    
}
