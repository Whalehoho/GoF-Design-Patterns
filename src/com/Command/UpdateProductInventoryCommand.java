package com.Command;

import com.dataModel.HashMap;
import com.dataModel.Stack;

public class UpdateProductInventoryCommand implements Command {
    private HashMap<Integer, Integer> productInventory;
    private int productId;
    private int newInventory;
    private Stack<Integer> inventoryHistory;

    public UpdateProductInventoryCommand(HashMap<Integer, Integer> productInventory, int productId, int newInventory) {
        this.productInventory = productInventory;
        this.productId = productId;
        this.newInventory = newInventory;
        this.inventoryHistory = new Stack<>();
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
