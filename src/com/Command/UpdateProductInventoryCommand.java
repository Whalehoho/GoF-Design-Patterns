package com.Command;

import com.DataModel.SimpleHashMap;
import com.DataModel.SimpleStack;

public class UpdateProductInventoryCommand implements Command {
    private SimpleHashMap<Integer, Integer> productInventory;
    private int productId;
    private int newInventory;
    private SimpleStack<Integer> inventoryHistory;

    public UpdateProductInventoryCommand(SimpleHashMap<Integer, Integer> productInventory, int productId, int newInventory) {
        this.productInventory = productInventory;
        this.productId = productId;
        this.newInventory = newInventory;
        this.inventoryHistory = new SimpleStack<>();
    }

    @Override
    public void execute() {
        System.out.println("Updating product inventory...");
        inventoryHistory.push(productInventory.getOrDefault(productId, 0));
        productInventory.put(productId, newInventory);
    }

    @Override
    public boolean undo() {
        if (!inventoryHistory.isEmpty()) {
            System.out.println("Undoing update inventory operation...");
            int previousInventory = inventoryHistory.pop();
            productInventory.put(productId, previousInventory);
        }
        return true;
    }
}
