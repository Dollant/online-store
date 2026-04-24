package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Store {

    static HashMap<String, Product> inventory = new HashMap<>();

    static ArrayList<String> cart = new ArrayList<>();

    static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {
        loadInventory();
        showHomeScreen();
    }

    public static void loadInventory() {
        String fileName = "src/main/resources/products.csv";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            reader.readLine();

            while ((line = reader.readLine()) !=null) {
                if (line.trim().isEmpty()) continue;

                String[] tokens = line.split("\\|");
                String sku = tokens[0].trim();
                String name = tokens[1].trim();
                double price = Double.parseDouble(tokens[2].trim());
                String department = tokens[3].trim();

                inventory.put(sku, new Product(sku, name, price, department));
            }
            reader.close();

        } catch (IOException e) {
            System.out.println("Error loading inventory" + e.getMessage());
        }
    }

    public static void showHomeScreen() {
        String choice = "";
    }
}
