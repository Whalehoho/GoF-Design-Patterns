package com.DataModels;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Inventory {
    private Map<Integer, Integer> inventory;

    public Inventory() {
        inventory = new HashMap<>();
        loadInventory();
    }

    public Map<Integer, Integer> getInventory() {
        return inventory;
    }

    public void setQuantity(int productId, int quantity) {
        inventory.put(productId, quantity);
    }

    public int getQuantity(int productId) {
        return inventory.get(productId) == null ? 0 : inventory.get(productId);
    }

    public void minusOne(int productId) {
        int quantity = inventory.get(productId);
        inventory.put(productId, quantity - 1);
    }

    public void save() {
        try (FileWriter writer = new FileWriter("inventory.csv")) {
            for (Map.Entry<Integer, Integer> entry : inventory.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadInventory() {
        try (BufferedReader br = new BufferedReader(new FileReader("inventory.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int productId = Integer.parseInt(values[0]);
                int quantity = Integer.parseInt(values[1]);
                inventory.put(productId, quantity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
