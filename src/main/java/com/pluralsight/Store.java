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

            System.out.println("\n1 ࣪ ִֶָ☾. Search / Filter Products");
            System.out.println("2 ࣪ ִֶָ☾. Add Product to Cart");
            System.out.println("3 ࣪ ִֶָ☾. Go back");
            System.out.print("\nChoose an option: ");
            choice = keyboard.nextLine().trim();

            switch (choice) {
                case "1":
                    searchProducts();
                    break;
                case "2":
                    addToCart();
                    break;
                case "3":
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    public static void searchProducts() {
        System.out.println("\nSearch by: 1★ Name    2★ Price    3★ Department");
        System.out.print("Choose: ");
        String filter = keyboard.nextLine().trim();

        System.out.print("Enter search term: ");
        String term = keyboard.nextLine().trim().toLowerCase();

        System.out.printf("%n%-8s %-40s %-10s %-15s%n", "SKU", "Name", "Price", "Department");
        System.out.println("-".repeat(75));

        for (String key : inventory.keySet()) {
            Product p = inventory.get(key);
            boolean match = false;

            switch (filter) {
                case "1":
                    match = p.getName().toLowerCase().contains(term);
                    break;
                case "2":
                    match = String.valueOf(p.getPrice()).contains(term);
                    break;
                case "3":
                    match = p.getDepartment().toLowerCase().contains(term);
                    break;
            }

            if (match) {
                System.out.printf("%-8s %-40s $%-9.2f %-15s%n",
                        p.getSku(), p.getName(), p.getPrice(), p.getDepartment());
            }
        }
    }

    public static void addToCart() {
        System.out.print("\nEnter SKU of product to add: ");
        String sku = keyboard.nextLine().trim().toUpperCase();

        if (inventory.containsKey(sku)) {
            cart.add(sku);
            System.out.println(inventory.get(sku).getName() + " added to cart!");
        } else {
            System.out.println("SKU not found! Please check the product list.");
        }
    }

    public static void showCartScreen() {
        String choice = "";

        while (!choice.equals("3")) {
            System.out.println("\n°❀⋆.ೃ࿔*:･°❀⋆.ೃ࿔*:･°❀⋆.ೃ࿔*:･°❀⋆.ೃ࿔*:･");
            System.out.println("\t  Your Cart    ");
            System.out.println("°❀⋆.ೃ࿔*:･°❀⋆.ೃ࿔*:･°❀⋆.ೃ࿔*:･°❀⋆.ೃ࿔*:･");

            if (cart.isEmpty()) {
                System.out.println(" Your cart is empty.");
            } else {
                HashMap<String, Integer> quantities = new HashMap<>();
                for (String sku : cart) {
                    quantities.put(sku, quantities.getOrDefault(sku, 0) + 1);
                }

                double total = 0;
                System.out.printf("%-8s %-40s %-6s %-10s%n", "SKU", "Name", "Qty", "Subtotal");
                System.out.println("-".repeat(70));

                for (String sku : quantities.keySet()) {
                    Product p = inventory.get(sku);
                    int qty = quantities.get(sku);
                    double subtotal = p.getPrice() * qty;
                    total += subtotal;
                    System.out.printf("%-8s %-40s %-6d $%.2f%n",
                            p.getSku(), p.getName(), qty, subtotal);
                }

                System.out.println("-".repeat(70));
                System.out.printf("%-55s $%.2f%n", "TOTAL:", total);
            }

            System.out.println("\n1 ࣪ ִֶָ☾. Check Out");
            System.out.println("2 ࣪ ִֶָ☾. Remove Product");
            System.out.println("3 ࣪ ִֶָ☾. Go back");
            System.out.print("\nChoose an option: ");
            choice = keyboard.nextLine().trim();

            switch (choice) {
                case "1":
                    checkOut();
                    break;
                case "2":
                    removeFromCart();
                    break;
                case "3":
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    public static void removeFromCart() {
        System.out.print("\nEnter SKU of product to remove: ");
        String sku = keyboard.nextLine().trim().toUpperCase();

        if (cart.contains(sku)) {
            cart.remove(sku);
            System.out.println("Item removed from cart.");
        } else {
            System.out.println("That SKU is not in your cart.");
        }
    }

    public static void checkOut() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Nothing to check out.");
            return;
        }

        double total = 0;
        HashMap<String, Integer> quantities = new HashMap<>();
        for (String sku : cart) {
            quantities.put(sku, quantities.getOrDefault(sku, 0) + 1);
        }
        for (String sku : quantities.keySet()) {
            total += inventory.get(sku).getPrice() * quantities.get(sku);
        }

        System.out.printf("%nYour total is: $%.2f%n", total);
        System.out.print("Enter payment amount: $");

        double payment = 0;
        try {
            payment = Double.parseDouble(keyboard.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid payment amount.");
            return;
        }

        if (payment < total) {
            System.out.printf("Insufficient payment. You need $%.2f more.%n", total - payment);
            return;
        }


    }
}
