package com.Commands;

import java.util.List;
import com.DataModels.SimpleTrie;

public class SearchProductCommand implements Command {
    private SimpleTrie productSearchTrie;
    private String input;

    public SearchProductCommand(SimpleTrie productSearchTrie, String input) {
        this.productSearchTrie = productSearchTrie;
        this.input = input;
    }

    @Override
    public void execute() {
        System.out.println("Searching for products with prefix <" + input +">...");
        List<String> results = productSearchTrie.getWordsWithPrefix(input);
        if(results.isEmpty()){
            System.out.println("No products found.");
            return;
        }
        System.out.print("Results => ");
        for (int i = 0; i < results.size(); i++) {
            if (i > 0) {
            System.out.print(", ");
            }
            System.out.print(results.get(i));
        }
        System.out.println();
    }

    @Override
    public boolean undo() {
        return false;
    }
    
}
