package com.Commands;

import com.DataModels.Inventory;


public class UpdateInventoryCommand implements Command {
    private Inventory inventory;
    private int productId;
    private int newInventory;
    private int previousInventory;

    public UpdateInventoryCommand(Inventory inventory, int productId, int newInventory) {
        this.inventory = inventory;
        this.productId = productId;
        this.newInventory = newInventory;
        this.previousInventory = inventory.getQuantity(productId);
    }

    @Override
    public void execute() {
        System.out.println("Updating product inventory...");
        inventory.setQuantity(productId, newInventory);
    }

    @Override
    public boolean undo() {
        System.out.println("Undoing update product inventory...");
        inventory.setQuantity(productId, previousInventory);
        return true;
    }
}
