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

        while (!choice.equals("3")) {
            System.out.println("\n°❀⋆.ೃ࿔*:･°❀⋆.ೃ࿔*:･°❀⋆.ೃ࿔*:･°❀⋆.ೃ࿔*:･");
            System.out.println("\t Welcome to Dolly Byte  ");
            System.out.println("°❀⋆.ೃ࿔*:･°❀⋆.ೃ࿔*:･°❀⋆.ೃ࿔*:･°❀⋆.ೃ࿔*:･");
            System.out.println("1 ࣪ ִֶָ☾. Display Products");
            System.out.println("2 ࣪ ִֶָ☾. Display Cart");
            System.out.println("3 ࣪ ִֶָ☾. Exit");
            System.out.print("\nChoose an option: ");
            choice = keyboard.nextLine().trim();

            switch (choice) {
                case "1":
                    showProductsScreen();
                    break;
                case "2":
                    showCartScreen();
                    break;
                case "3":
                    System.out.println("\nThanks for shopping! Goodbye (˶˃ ᵕ ˂˶) .ᐟ.ᐟ");
                    break;
                default:
                    System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        }
    }

    public static void showProductsScreen() {
        String choice = "";

        while (!choice.equals("3")) {
            System.out.println("\n°❀⋆.ೃ࿔*:･°❀⋆.ೃ࿔*:･°❀⋆.ೃ࿔*:･°❀⋆.ೃ࿔*:･");
            System.out.println("\t  Products    ");
            System.out.println("°❀⋆.ೃ࿔*:･°❀⋆.ೃ࿔*:･°❀⋆.ೃ࿔*:･°❀⋆.ೃ࿔*:･");

            System.out.printf("%-8s %-40s %-10s %-15s%n", "SKU", "Name", "Price", "Department");
            System.out.println("-".repeat(75));
            for (String key : inventory.keySet()) {
                Product p = inventory.get(key);
                System.out.printf("%-8s %-40s $%-9.2f %-15s%n",
                        p.getSku(), p.getName(), p.getPrice(), p.getDepartment());
            }
        }
    }
}
