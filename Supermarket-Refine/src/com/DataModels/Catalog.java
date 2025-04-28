package com.DataModels;


import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Catalog {
    private List<Product> products;

    public Catalog() {
        products = new ArrayList<>();
        loadCatalog();
    }

    public List<Product> getCatalog() {
        return products;
    }

    public void setCatalog(List<Product> products) {
        this.products = products;
    }

    public Product getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public void add(Product product) {
        products.add(product);
    }

    public void remove(Product product) {
        products.remove(product);
    }

    public void showCatalog() {
        System.out.println("\nCatalog Data:");
        System.out.printf("%-10s %-20s %-20s%n", "ID", "Name", "Unit Price");
        System.out.println("----------------------------------------------------------------");
        products.forEach((product) -> {
            System.out.printf("%-10d %-20s %-20.2f%n", product.getId(), product.getName(), product.getPrice());
        });
    }

    public void save() {
        try (FileWriter writer = new FileWriter("catalog.csv")) {
            writer.append("ID,Name,Unit Price\n");
            for (Product product : products) {
                writer.append(String.valueOf(product.getId()))
                      .append(',')
                      .append(product.getName())
                      .append(',')
                      .append(String.valueOf(product.getPrice()))
                      .append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCatalog() {
        List<Product> loadedProducts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("catalog.csv"))) {
            String line;
            reader.readLine(); // Skip header line
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                int id = Integer.parseInt(fields[0]);
                String name = fields[1];
                double price = Double.parseDouble(fields[2]);
                loadedProducts.add(new Product(id, name, price));
            }
            this.products = loadedProducts;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
