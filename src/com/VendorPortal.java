package com;

import java.io.*;
import java.util.Scanner;

import com.datastructureutils.*;
import com.ecommerceutils.Product;

public class VendorPortal {

    private static BinarySearchTree<Product> productCatalog;
    private static HashMap<Integer, Integer> productInventory;

    public static void main(String[] args) {
        // Initialize the custom data structures
        productCatalog = new BinarySearchTree<>();
        productInventory = new HashMap<>();

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
            System.out.println("1. Add product to catalog");
            System.out.println("2. Update product inventory");
            System.out.println("3. Save catalog and inventory to file");
            System.out.println("4. View current inventory");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter product ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter product name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter product price: ");
                    double price = scanner.nextDouble();
                    Product newProduct = new Product(id, name, price);
                    productCatalog.insert(newProduct);
                    System.out.println("Product added to catalog.");
                    break;
                case 2:
                    System.out.print("Enter product ID to update inventory: ");
                    int productId = scanner.nextInt();
                    System.out.print("Enter new inventory quantity: ");
                    int quantity = scanner.nextInt();
                    productInventory.put(productId, quantity);
                    System.out.println("Product inventory updated.");
                    break;
                case 3:
                    try {
                        saveDataToFile();
                        System.out.println("Catalog and inventory data saved to file.");
                    } catch (IOException e) {
                        System.out.println("Error saving data to file: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Current Inventory:");
                    for (int productIdKey : productInventory.keySet()) {
                        System.out.println("Product ID " + productIdKey + ": " + productInventory.get(productIdKey) + " units remaining");
                    }
                    break;
                case 5:
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
        try (BufferedReader reader = new BufferedReader(new FileReader("shop_data.txt"))) {
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
                        System.out.println("Product ID: " + product.getId() + " - "+ product.getName());
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
                        System.out.println("Product ID " + id + ": " + quantity + " units");
                    }
                }
            }
        }
    }

    // Method to save catalog and inventory data to a file
    private static void saveDataToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("shop_data.txt"))) {
            // Save product catalog (we use an in-order traversal for the Binary Search Tree)
            writer.write("Product Catalog:\n");
            productCatalog.inOrderTraversal(writer, (product, bw) -> {
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
}