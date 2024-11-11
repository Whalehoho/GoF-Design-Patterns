package com;

import java.io.*;
import java.util.Scanner;
import java.util.List;

import com.datastructureutils.*;
import com.ecommerceutils.Order;
import com.ecommerceutils.Product;

public class BuyerSelfCheckout {

    public static void main(String[] args) {
        // Initialize the custom data structures
        LinkedList<Product> shoppingCart = new LinkedList<>();
        Stack<String> userActions = new Stack<>();
        PriorityQueue<Order> orderQueue = new PriorityQueue<>();
        HashMap<Integer, Integer> productInventory = new HashMap<>(); // Inventory of products
        Trie productTrie = new Trie(); // Trie for product search

        // Initialize product catalog (Binary Search Tree)
        BinarySearchTree<Product> productCatalog = new BinarySearchTree<>();

        // Read the product catalog and inventory data from the text file
        try {
            loadDataFromFile(productCatalog, productInventory, productTrie);
            System.out.println("Catalog and inventory data loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nMenu:");
            System.out.println("1. Add product to cart");
            System.out.println("2. View shopping cart");
            System.out.println("3. Undo last action");
            System.out.println("4. Process orders");
            System.out.println("5. Search for products by name");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter product ID to add to cart: ");
                    int productId = scanner.nextInt();
                    addProductToCart(productId, shoppingCart, userActions, productCatalog);
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
                    processOrders(shoppingCart, orderQueue, productCatalog, productInventory);
                    break;
                case 5:
                    System.out.print("Enter product name to search: ");
                    scanner.nextLine(); // Consume newline
                    String productName = scanner.nextLine();
                    System.out.println("Search Results:");
                        List<String> results = productTrie.getWordsWithPrefix(productName);
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

        // Update the text file with the new inventory after processing the orders
        try {
            updateInventoryInFile(productCatalog, productInventory);
        } catch (IOException e) {
            System.out.println("Error updating inventory in file: " + e.getMessage());
        }

        scanner.close();
    }

    // Method to read product catalog and inventory data from the file
    private static void loadDataFromFile(BinarySearchTree<Product> productCatalog, HashMap<Integer, Integer> productInventory, Trie productTrie) throws IOException {
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
                        productTrie.insert(name); // Insert product name into Trie
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

    // Method to update the product inventory in the file after processing the orders
    private static void updateInventoryInFile(BinarySearchTree<Product> productCatalog, HashMap<Integer, Integer> productInventory) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("shop_data.txt"))) {
            // Write the product catalog
            System.out.println("Updating product catalog and inventory in file...");
            writer.write("Product Catalog:\n");
            productCatalog.inOrderTraversal(writer, (product, bw) -> {
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
    private static void addProductToCart(int productId, LinkedList<Product> shoppingCart, Stack<String> userActions, BinarySearchTree<Product> productCatalog) {
        Product product = productCatalog.search(productId, Product::getId);
        if (product != null) {
            shoppingCart.addFirst(product);
            userActions.push("Added " + product.getName() + " to cart");
        } else {
            System.out.println("Product with ID " + productId + " not found in catalog.");
        }
    }

    // Method to process orders
    private static void processOrders(LinkedList<Product> shoppingCart, PriorityQueue<Order> orderQueue, BinarySearchTree<Product> productCatalog, HashMap<Integer, Integer> productInventory) {
        // Create orders for each product in the shopping cart, with the most recent product getting higher priority
        int priority = 1;
        LinkedListNode<Product> currentNode = shoppingCart.getHead();
        while (currentNode != null) {
            Product product = currentNode.getValue();
            Order order = new Order(priority, product.getId(), 1, priority); // Create an order with the current priority

            // Add the order to the priority queue
            orderQueue.enqueue(order);
            priority++; // Decrease priority for the next product (lower priority)
            currentNode = currentNode.getNext(); // Move to the next product
        }

        // Process the orders based on their priority
        System.out.println("\nProcessing Orders:");
        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.dequeue();
            int productId = order.getProductId();
            int quantity = order.getQuantity();

            // Check if the product exists in the catalog and if there’s enough stock
            Product foundProduct = productCatalog.search(productId, Product::getId);

            if (foundProduct != null) {
                if (productInventory.get(productId) >= quantity) {
                    // Update the inventory
                    productInventory.put(productId, productInventory.get(productId) - quantity);
                    System.out.println("Processed Order ID: " + order.getOrderId() + " for " + quantity + " units of " + foundProduct.getName());
                } else {
                    System.out.println("Insufficient stock for Order ID: " + order.getOrderId());
                }
            } else {
                System.out.println("Product not found for Order ID: " + order.getOrderId());
            }
        }
    }
}