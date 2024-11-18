package com;

import java.io.*;
import java.util.Scanner;

import com.Facades.BuyerFacade;

public class BuyerSelfCheckoutPortal {

    public static void main(String[] args) {
        // Initialize the facade
        BuyerFacade buyerFacade = new BuyerFacade();

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
                    buyerFacade.showProductCatalog();
                    break;
                case 1:
                    System.out.print("Enter product ID to add to cart: ");
                    int productId = scanner.nextInt();
                    buyerFacade.addToCart(productId);
                    break;
                case 2:
                    buyerFacade.viewCart();
                    break;
                case 3:
                    buyerFacade.undoLastAction();
                    break;
                case 4:
                    buyerFacade.checkout();
                    break;
                case 5:
                    System.out.print("Enter product name to search: ");
                    scanner.nextLine(); // Consume newline
                    String productName = scanner.nextLine();
                    buyerFacade.searchProduct(productName);
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

}