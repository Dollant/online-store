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
}
