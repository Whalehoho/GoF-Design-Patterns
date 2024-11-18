package com.Command;

import com.DataModel.Product;
import com.DataModel.SimpleBinarySearchTree;
import com.DataModel.SimpleHashMap;

public class ShowProductCatalogCommand implements Command {
    private SimpleBinarySearchTree<Product> productCatalog;
    private SimpleHashMap<Integer, Integer> productInventory;

    public ShowProductCatalogCommand(SimpleBinarySearchTree<Product> productCatalog, SimpleHashMap<Integer, Integer> productInventory) {
        this.productCatalog = productCatalog;
        this.productInventory = productInventory;
    }

    @Override
    public void execute() {
        System.out.println("\nCatalog and Inventory Data:");
        System.out.printf("%-10s %-20s %-20s %-10s%n", "ID", "Name", "Unit Price", "Inventory");
        System.out.println("----------------------------------------------------------------");
        productCatalog.inOrderTraversal((product) -> product.getId(), (product, extractor) -> {
            Product p = (Product) product;
            int id = extractor.extractId(p);
            String name = p.getName();
            int inventory = productInventory.getOrDefault(id, 0);
            double unitPrice = p.getPrice();
            System.out.printf("%-10d %-20s %-20.2f %-10d%n", id, name, unitPrice, inventory);
        });
    }

    @Override
    public boolean undo() {
        // No undo operation for this command
        return false;
    }

}

