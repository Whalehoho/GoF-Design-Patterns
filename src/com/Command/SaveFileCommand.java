package com.Command;

import java.io.*;

import com.DataModel.SimpleBinarySearchTree;
import com.DataModel.SimpleHashMap;
import com.DataModel.Product;

public class SaveFileCommand implements Command {
    private SimpleBinarySearchTree<Product> productCatalog;
    private SimpleHashMap<Integer, Integer> productInventory;

    public SaveFileCommand(SimpleBinarySearchTree<Product> productCatalog, SimpleHashMap<Integer, Integer> productInventory) {
        this.productCatalog = productCatalog;
        this.productInventory = productInventory;
    }

    @Override
    public void execute() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("inventory.csv"))) {
            // Save product catalog (we use an in-order traversal for the Binary Search Tree)
            System.out.println("Updating product catalog and inventory in file...");
            writer.write("Product Catalog:\n");
            productCatalog.inOrderWriterTraversal(writer, (product, bw) -> {
                if (product instanceof Product) {
                    Product p = (Product) product;
                    bw.write(p.getId() + "," + p.getName() + "," + p.getPrice() + "\n");
                }
            });

            // Save product inventory
            writer.write("\nProduct Inventory:\n");
            for (Integer productId : productInventory.keySet()) {
                writer.write(productId + "," + productInventory.get(productId) + "\n");
            }
        }
    }

    @Override
    public boolean undo() {
        // No undo operation for this command
        return false;
    }
    
}
