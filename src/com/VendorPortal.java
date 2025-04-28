package com;

import java.util.Scanner;

import com.Facades.VendorFacade;

public class VendorPortal {

    private static VendorFacade vendorFacade;

    public static void main(String[] args) {
        // Initialize facade
        vendorFacade = new VendorFacade();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nMenu:");
            System.out.println("0. Show product catalog");
            System.out.println("1. Add product to catalog");
            System.out.println("2. Update product inventory");
            System.out.println("3. Save catalog and inventory to file");
            System.out.println("4. Undo last action");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    vendorFacade.showProductCatalog();
                    break;
                case 1:
                    System.out.print("Enter product ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter product name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter product price: ");
                    double price = scanner.nextDouble();
                    vendorFacade.addNewProduct(id, name, price); // Call to the facade
                    System.out.println("Product added to catalog.");
                    break;
                case 2:
                    System.out.print("Enter product ID to update inventory: ");
                    int productId = scanner.nextInt();
                    System.out.print("Enter new inventory quantity: ");
                    int quantity = scanner.nextInt();
                    vendorFacade.updateProductInventory(productId, quantity); // Call to the facade
                    System.out.println("Product inventory updated.");
                    break;
                case 3:
                    vendorFacade.saveFile(); // Call to the facade
                    System.out.println("Catalog and inventory data saved to file.");
                    break;
                case 4:
                    vendorFacade.undoLastAction(); // Call to the facade
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

}