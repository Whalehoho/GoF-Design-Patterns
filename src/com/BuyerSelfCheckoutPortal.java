package com;

import java.io.*;
import java.util.Scanner;

import com.dataModel.*;

import java.util.List;

public class BuyerSelfCheckoutPortal {

    private static LinkedList<Product> shoppingCart = new LinkedList<>();
    private static Stack<String> userActions = new Stack<>();
    private static HashMap<Integer, String> productNames = new HashMap<>();
    private static HashMap<Integer, Integer> productInventory = new HashMap<>();
    private static Trie productSearchTrie = new Trie();
    private static BinarySearchTree<Product> productCatalog = new BinarySearchTree<>();

    public static void main(String[] args) {
        // Initialize the custom data structures
        shoppingCart = new LinkedList<>();
        userActions = new Stack<>();
        productNames = new HashMap<>();
        productInventory = new HashMap<>(); // Inventory of products
        productSearchTrie = new Trie(); // Trie for product search

        // Initialize product catalog (Binary Search Tree)
        productCatalog = new BinarySearchTree<>();

        // Read the product catalog and inventory data from the text file
        try {
            loadDataFromFile();
            System.out.println("Catalog and inventory data loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nMenu:");
            System.out.println("0. Show product catalog");
            System.out.println("1. Add product to cart");
            System.out.println("2. View shopping cart");
            System.out.println("3. Undo last action");
            System.out.println("4. Checkout");
            System.out.println("5. Search for products by name");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    showProductCatalog();
                    break;
                case 1:
                    System.out.print("Enter product ID to add to cart: ");
                    int productId = scanner.nextInt();
                    addProductToCart(productId);
                    break;
                case 2:
                    System.out.println("Shopping Cart:");
                    shoppingCart.print();
                    break;
                case 3:
                    if (!userActions.isEmpty()) {
                        System.out.println("\nUndo last action:");
                        String lastAction = userActions.pop();
                        System.out.println("Action undone: " + lastAction);
                        shoppingCart.removeFirst(); // Remove the last added item
                    } else {
                        System.out.println("No actions to undo.");
                    }
                    break;
                case 4:
                    checkout();
                    break;
                case 5:
                    System.out.print("Enter product name to search: ");
                    scanner.nextLine(); // Consume newline
                    String productName = scanner.nextLine();
                    System.out.println("Search Results:");
                        List<String> results = productSearchTrie.getWordsWithPrefix(productName);
                        for (String result : results) {
                            System.out.println(result);
                        }
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        

        scanner.close();
    }

    // Method to read product catalog and inventory data from the file
    private static void loadDataFromFile() throws IOException {
    
        try (BufferedReader reader = new BufferedReader(new FileReader("inventory.txt"))) {
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
                        productSearchTrie.insert(name); // Insert product name into Trie
                        productNames.put(id, name); // Store product name for later use
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

    // Method to show the product catalog
    private static void showProductCatalog() {
        // Print the catalog and inventory data as a table
        System.out.println("\nCatalog and Inventory Data:");
        System.out.printf("%-10s %-20s %-10s%n", "ID", "Name", "Inventory");
        System.out.println("---------------------------------------------");
        for (int id : productNames.keySet()) {
            String name = productNames.get(id);
            int inventory = productInventory.getOrDefault(id, 0);
            System.out.printf("%-10d %-20s %-10d%n", id, name, inventory);
        }
    }


    // Method to update the product inventory in the file after processing the orders
    private static void updateInventoryInFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("inventory.txt"))) {
            // Write the product catalog
            System.out.println("Updating product catalog and inventory in file...");
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

    // Simulate adding product to the shopping cart from the catalog
    private static void addProductToCart(int productId) {
        Product product = productCatalog.search(productId, Product::getId);
        if (product != null) {
            shoppingCart.addFirst(product);
            userActions.push("Added " + product.getName() + " to cart");
        } else {
            System.out.println("Product with ID " + productId + " not found in catalog.");
        }
    }

    // Method to checkout and process the shopping cart
    private static void checkout() {
        System.out.println("\nChecking out:");
        LinkedListNode<Product> currentNode = shoppingCart.getHead();
        while (currentNode != null) {
            Product product = currentNode.getValue();
            int productId = product.getId();
            int quantity = 1; // Assuming 1 unit per product in the cart

            // Check if the product exists in the catalog and if there’s enough stock
            Product foundProduct = productCatalog.search(productId, Product::getId);
            
            // Check if the product id exists in the inventory
            if (!productInventory.containsKey(productId)) {
                System.out.println("Product ID " + productId + " not found in inventory.");
                currentNode = currentNode.getNext();
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

            currentNode = currentNode.getNext(); // Move to the next product
        }

        // Clear the shopping cart after checkout
        shoppingCart.clear();
        System.out.println("Checkout complete. Shopping cart is now empty.");

        // Update the text file with the new inventory after processing the cart
        try {
            updateInventoryInFile();
        } catch (IOException e) {
            System.out.println("Error updating inventory in file: " + e.getMessage());
        }
    }

}