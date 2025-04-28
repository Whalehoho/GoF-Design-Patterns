package com;

import java.io.*;
import java.util.Scanner;

import com.Facades.Facade;

public class BuyerPortal {

    public static void main(String[] args) {
        // Initialize the facade
        Facade facade = new Facade();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nMenu:");
            System.out.println("0. Show product catalog");
            System.out.println("1. Add product to cart");
            System.out.println("2. View shopping cart");
            System.out.println("3. Undo last action");
            System.out.println("4. Checkout");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    facade.showCatalog();
                    break;
                case 1:
                    System.out.print("Enter product ID to add to cart: ");
                    int productId = scanner.nextInt();
                    facade.addToCart(productId);
                    break;
                case 2:
                    facade.viewCart();
                    break;
                case 3:
                    facade.undoLastAction();
                    break;
                case 4:
                    facade.checkout();
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