package com.Facade;

import com.Command.Command;
import com.Command.ShowProductCatalogCommand;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.Command.AddNewProductCommand;
import com.Command.UpdateProductInventoryCommand;
import com.DataModel.Product;
import com.DataModel.SimpleBinarySearchTree;
import com.DataModel.SimpleHashMap;
import com.DataModel.SimpleStack;
import com.Command.SaveFileCommand;

public class VendorFacade {
    private SimpleBinarySearchTree<Product> productCatalog;
    private SimpleHashMap<Integer, Integer> productInventory;
    private SimpleStack<Command> commandHistory;

    public VendorFacade() {
        productCatalog = new SimpleBinarySearchTree<>();
        productInventory = new SimpleHashMap<>();
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

    public void addNewProduct(int id, String name, double price) {
        Product newProduct = new Product(id, name, price);
        Command addNewProductCommand = new AddNewProductCommand(productCatalog, newProduct);
        try {
            addNewProductCommand.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        commandHistory.push(addNewProductCommand);
    }

    public void updateProductInventory(int productId, int quantity) {
        Command updateProductInventoryCommand = new UpdateProductInventoryCommand(productInventory, productId, quantity);
        try {
            updateProductInventoryCommand.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        commandHistory.push(updateProductInventoryCommand);
    }

    public void saveFile() {
        Command saveFileCommand = new SaveFileCommand(productCatalog, productInventory);
        try {
            saveFileCommand.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        commandHistory.push(saveFileCommand);
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
