package com.Commands;

import com.DataModels.Catalog;
import com.DataModels.Inventory;

public class ShowCatalogCommand implements Command {
    private Catalog catalog;
    private Inventory inventory;

    public ShowCatalogCommand(Catalog catalog, Inventory inventory) {
        this.catalog = catalog;
        this.inventory = inventory;
    }

    @Override
    public void execute() {
        System.out.printf("%-10s %-20s %-20s %-10s%n", "ID", "Name", "Unit Price", "Inventory");
        System.out.println("----------------------------------------------------------------");
        catalog.getCatalog().forEach((product) -> {
            System.out.printf("%-10d %-20s %-20.2f %-10d%n", product.getId(), product.getName(), product.getPrice(), inventory.getQuantity(product.getId()));
        });
    }

    @Override
    public boolean undo() {
        // No undo operation for this command
        return false;
    }

}

