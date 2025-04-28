package com.Commands;

import com.DataModels.Catalog;

public class SaveCatalogCommand implements Command {
    private Catalog catalog;

    public SaveCatalogCommand(Catalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public void execute() {
        System.out.println("Saving catalog...");
        catalog.save();
    }

    @Override
    public boolean undo() {
        return false;
    }
}
