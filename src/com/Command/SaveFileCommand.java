package com.Command;

import com.dataModel.BinarySearchTree;
import com.dataModel.HashMap;
import com.dataModel.Product;
import java.io.*;

public class SaveFileCommand implements Command {
    private BinarySearchTree<Product> productCatalog;
    private HashMap<Integer, Integer> productInventory;

    public SaveFileCommand(BinarySearchTree<Product> productCatalog, HashMap<Integer, Integer> productInventory) {
        this.productCatalog = productCatalog;
        this.productInventory = productInventory;
    }

    @Override
    public void execute() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("inventory.txt"))) {
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
