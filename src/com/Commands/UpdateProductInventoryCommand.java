package com.Commands;

import com.DataModels.SimpleHashMap;
import com.DataModels.SimpleStack;

public class UpdateProductInventoryCommand implements Command {
    private SimpleHashMap<Integer, Integer> productInventory;
    private int productId;
    private int newInventory;
    private int previousInventory;

    public UpdateProductInventoryCommand(SimpleHashMap<Integer, Integer> productInventory, int productId, int newInventory) {
        this.productInventory = productInventory;
        this.productId = productId;
        this.newInventory = newInventory;
        this.previousInventory = productInventory.getOrDefault(productId, 0);
    }

    @Override
    public void execute() {
        System.out.println("Updating product inventory...");
        productInventory.put(productId, newInventory);
    }

    @Override
    public boolean undo() {
        System.out.println("Undoing update product inventory...");
        productInventory.put(productId, previousInventory);
        return true;
    }
}
