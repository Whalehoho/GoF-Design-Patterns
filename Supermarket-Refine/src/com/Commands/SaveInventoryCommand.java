package com.Commands;

import com.DataModels.Inventory;

public class SaveInventoryCommand implements Command {
    private Inventory inventory;

    public SaveInventoryCommand(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void execute() {
        System.out.println("Saving inventory...");
        inventory.save();
    }

    @Override
    public boolean undo() {
        return false;
    }
    
}
