package com.Facades;

import com.Commands.Command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.Commands.AddToCartCommand;
import com.Commands.ViewCartCommand;
import com.Commands.CheckoutCommand;
import com.Commands.SearchProductCommand;
import com.Commands.ShowProductCatalogCommand;
import com.DataModels.Product;
import com.DataModels.SimpleBinarySearchTree;
import com.DataModels.SimpleLinkedList;
import com.DataModels.SimpleHashMap;
import com.DataModels.SimpleStack;
import com.DataModels.SimpleTrie;


public class BuyerFacade {
    private SimpleBinarySearchTree<Product> productCatalog;
    private SimpleHashMap<Integer, Integer> productInventory;
    private SimpleLinkedList<Product> shoppingCart;
    private SimpleTrie productSearchTrie;
    private SimpleStack<Command> commandHistory;

    public BuyerFacade() {
        productCatalog = new SimpleBinarySearchTree<>();
        productInventory = new SimpleHashMap<>();
        shoppingCart = new SimpleLinkedList<>();
        productSearchTrie = new SimpleTrie();
        commandHistory = new SimpleStack<>();
        try {
            loadFile();
            System.out.println("Catalog and inventory data loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        }
    }

    public void showProductCatalog() {
        Command showProductCatalogCommand = new ShowProductCatalogCommand(productCatalog, productInventory);
        try {
            showProductCatalogCommand.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        commandHistory.push(showProductCatalogCommand);
    }

    public void addToCart(int productId) {
        Command addToCartCommand = new AddToCartCommand(productCatalog, shoppingCart, productId);
        try {
            addToCartCommand.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        commandHistory.push(addToCartCommand);
    }

    public void viewCart() {
        Command viewCartCommand = new ViewCartCommand(shoppingCart);
        try {
            viewCartCommand.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        commandHistory.push(viewCartCommand);
    }

    public void undoLastAction() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.pop();
            while (!lastCommand.undo()) {
                if(commandHistory.isEmpty()) {
                    System.out.println("No commands to undo.");
                    break;
                }
                lastCommand = commandHistory.pop();
            }
        } else {
            System.out.println("No commands to undo.");
        }
    }

    public void checkout(){
        Command checkoutCommand = new CheckoutCommand(productCatalog, productInventory, shoppingCart);
        try {
            checkoutCommand.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        commandHistory.clear(); // After checkout, actions cannot be undone, so clear the history
    }

    public void searchProduct(String query) {
        Command searchProductCommand = new SearchProductCommand(productSearchTrie, query);
        try {
            searchProductCommand.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        commandHistory.push(searchProductCommand);
    }

    private void loadFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("inventory.csv"))) {
            String line;
            boolean readingInventory = false;
            // Read product catalog from the file
            System.out.println("Reading product catalog from file...");
            while ((line = reader.readLine()) != null) {
                if (line.equals("Product Inventory:")) {
                    readingInventory = true;
                    break;  // Stop when we hit the inventory section
                }
    
                // Assuming product data format is: ID,Name,Price
                if (!line.equals("Product Catalog:") && !line.trim().isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        int id = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        double price = Double.parseDouble(parts[2].trim());
                        Product product = new Product(id, name, price);
                        productCatalog.insert(product);
                        productSearchTrie.insert(name);
                    }
                }
            }
    
            // Read product inventory from the file
            if (readingInventory) {
                System.out.println("Reading product inventory from file...");
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    // Assuming inventory data format is: ID,Quantity
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        int id = Integer.parseInt(parts[0].trim());
                        int quantity = Integer.parseInt(parts[1].trim());
                        productInventory.put(id, quantity);
                    }
                }
            }
        }
    }
}
