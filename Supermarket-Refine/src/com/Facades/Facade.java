package com.Facades;

import com.DataModels.Product;
import com.DataModels.Catalog;
import com.DataModels.Inventory;
import com.DataModels.ShoppingCart;
import com.Commands.Command;

import java.util.Stack;
import com.Commands.AddCatalogCommand;
import com.Commands.AddCartCommand;
import com.Commands.CheckoutCommand;
import com.Commands.ShowCatalogCommand;
import com.Commands.UpdateInventoryCommand;
import com.Commands.ViewCartCommand;
import com.Commands.SaveCatalogCommand;
import com.Commands.SaveInventoryCommand;



public class Facade {
    private Catalog catalog;
    private Inventory inventory;
    private ShoppingCart shoppingCart;
    private Stack<Command> commandHistory;

    public Facade() {
        catalog = new Catalog();
        inventory = new Inventory();
        shoppingCart = new ShoppingCart();
        commandHistory = new Stack<>();
    }

    public void showCatalog() {
        Command showCatalogCommand = new ShowCatalogCommand(catalog, inventory);
        showCatalogCommand.execute();
        commandHistory.push(showCatalogCommand);
    }

    public void addCatalog(int id, String name, double price) {
        Product product = new Product(id, name, price);
        Command addCatalogCommand = new AddCatalogCommand(catalog, product);
        addCatalogCommand.execute();
        commandHistory.push(addCatalogCommand);
    }

    public void updateInventory(int productId, int quantity) {
        Command updateInventoryCommand = new UpdateInventoryCommand(inventory, productId, quantity);
        updateInventoryCommand.execute();
        commandHistory.push(updateInventoryCommand);
    }

    public void addToCart(int productId) {
        int quantity = inventory.getQuantity(productId);
        if (quantity <= 0) {
            System.out.println("Product out of stock.");
            return;
        }
        Command addToCartCommand = new AddCartCommand(catalog, shoppingCart, productId);
        addToCartCommand.execute();
        commandHistory.push(addToCartCommand);
    }

    public void viewCart() {
        Command viewCartCommand = new ViewCartCommand(shoppingCart);
        viewCartCommand.execute();
        commandHistory.push(viewCartCommand);
    }

    public void checkout() {
        Command checkoutCommand = new CheckoutCommand(inventory, shoppingCart);
        checkoutCommand.execute();
        commandHistory.removeAllElements();
    }

    public void saveFile() {
        Command saveCatalogCommand = new SaveCatalogCommand(catalog);
        saveCatalogCommand.execute();

        Command saveInventoryCommand = new SaveInventoryCommand(inventory);
        saveInventoryCommand.execute();

        commandHistory.removeAllElements();
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

}
