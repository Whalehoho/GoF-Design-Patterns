package com.Commands;

import com.DataModels.SimpleBinarySearchTree;
import com.DataModels.SimpleLinkedList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.DataModels.LinkedListNode;
import com.DataModels.SimpleHashMap;
import com.DataModels.Product;

public class CheckoutCommand implements Command {
    private SimpleBinarySearchTree<Product> productCatalog;
    private SimpleHashMap<Integer, Integer> productInventory;
    private SimpleLinkedList<Product> shoppingCart;

    public CheckoutCommand(SimpleBinarySearchTree<Product> productCatalog, SimpleHashMap<Integer, Integer> productInventory, SimpleLinkedList<Product> shoppingCart) {
        this.productCatalog = productCatalog;
        this.productInventory = productInventory;
        this.shoppingCart = shoppingCart;
    }

    @Override
    public void execute() {
        System.out.println("Checking out...");
        LinkedListNode<Product> current = shoppingCart.getHead();

        while(current != null){
            Product product = current.getValue();
            int productId = product.getId();
            int quantity = 1; // Assuming quantity is always 1

            // Check if the product exists in the catalog and if there’s enough stock
            Product foundProduct = productCatalog.search(productId, Product::getId);

            // Check if the product id exists in the inventory
            if (!productInventory.containsKey(productId)) {
                System.out.println("Product ID " + productId + " not found in inventory.");
                current = current.getNext();
                continue;
            }

            if (foundProduct != null) {
                if (productInventory.get(productId) >= quantity) {
                    // Update the inventory
                    productInventory.put(productId, productInventory.get(productId) - quantity);
                    System.out.println("Checked out " + quantity + " unit(s) of " + foundProduct.getName());
                } else {
                    System.out.println("Insufficient stock for " + foundProduct.getName());
                }
            } else {
                System.out.println("Product not found: " + product.getName());
            }

            current = current.getNext();
        }

        shoppingCart.clear();
        System.out.println("Checkout complete. Shopping cart is now empty.");

        // Update the text file with the new inventory after processing the cart
        try {
            updateInventoryInFile();
        } catch (IOException e) {
            System.out.println("Error updating inventory in file: " + e.getMessage());
        }
    }

    @Override
    public boolean undo() {
        // No undo operation for this command
        return false;
    }

    // Method to update the product inventory in the file after processing the orders
    private void updateInventoryInFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("inventory.csv"))) {
            // Write the product catalog
            // System.out.println("Updating product catalog and inventory in file...");
            writer.write("Product Catalog:\n");
            productCatalog.inOrderWriterTraversal(writer, (product, bw) -> {
                if (product instanceof Product) {
                    Product p = (Product) product;
                    bw.write(p.getId() + "," + p.getName() + "," + p.getPrice() + "\n");
                }
            });
            writer.write("\nProduct Inventory:\n");

            // Write the updated inventory
            for (int id : productInventory.keySet()) {
                writer.write(id + "," + productInventory.get(id) + "\n");
            }
        }
    }
    
}
