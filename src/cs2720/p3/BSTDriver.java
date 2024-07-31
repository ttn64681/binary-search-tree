package cs2720.p3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Driver to test a binary search tree structure.
 */
public class BSTDriver {

    public static void main(String[] args) {
        // Checks if file is invalid
        if (args.length != 1) {
            System.out.println("Usage: java BSTDriver <input-file>");
            System.exit(1);
        } // if
        String file = args[0];

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter list type (i - int, d - double, s - std:string): ");
        String inputType = scanner.nextLine().trim();

        switch (inputType) {
        case "i":
            BST<Integer> intBST = new BST<>();
            processFile(file, intBST, scanner);
            break;
        case "d":
            BST<Double> doubleBST = new BST<>();
            processFile(file, doubleBST, scanner);
            break;
        case "s":
            BST<String> stringBST = new BST<>();
            processFile(file, stringBST, scanner);
            break;
        default:
            System.err.println("Invalid input type.");
            System.exit(1);
        } // switch
        scanner.close();
    } // main

    /**
     * Helper method to process file, parsing and adding its values to the appropriate BST.
     *
     * @param file the input file name
     * @param typeBST the BST of type T
     * @param scanner the scanner reader
     */
    private static <T extends Comparable<T>> void processFile(String file,
        BST<T> typeBST,
        Scanner scanner) {
        // try-with-resources statement, creating instance of BufferedReader to read file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // while line isn't null, parse info, adding the values to the typeBST
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("\\s+");
                for (String valueStr : values) {
                    T value = parseInfo(valueStr, typeBST);
                    if (value != null) {
                        typeBST.insert(value);
                    } // if
                } // for
            } // while
            choices(typeBST, scanner);
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } // try
    } // processFile

    /**
     * Helper method to parse the file info.
     *
     * @param valueStr the string value of the file to read
     * @param typeBST the BST of type T
     * @return T the item of type T
     */
    private static <T extends Comparable<T>> T parseInfo(String valueStr,
        BST<T> typeBST) {
        // try statement, if root is null, return first value, and make sure the following values
        // are the same type of value, which if confirmed is returned.
        try {
            if (typeBST.getRoot() == null) { // if root is null
                if (valueStr.matches("\\d+")) { // matches Integer
                    return (T) Integer.valueOf(valueStr);
                } else if (valueStr.matches("\\d+\\.\\d+")) { // matches Double
                    return (T) Double.valueOf(valueStr);
                } else { // matches String
                    return (T) valueStr;
                } // if
            } else {
                T rootValue = typeBST.getRoot().info; // establish root value
                if (rootValue instanceof Integer) { // if root is Integer
                    return (T) Integer.valueOf(valueStr);
                } else if (rootValue instanceof Double) { // if root is Double
                    return (T) Double.valueOf(valueStr);
                } else if (rootValue instanceof String) { // if root is String
                    return (T) valueStr;
                } // if
            } // if
        } catch (NumberFormatException e) {
            System.err.println("Invalid input format.\n");
        } // try
        return null;
    } // parseInfo

    /**
     * Helper method to display the user interface of choices and actions.
     *
     * @param typeBST the BST of type T
     * @param scanner the scanner reader
     */
    private static <T extends Comparable<T>> void choices(BST<T> typeBST, Scanner scanner) {
        printChoices();
        String choice = " ";

        while (!choice.equals("q")) {
            System.out.print("Enter a command: ");
            choice = scanner.nextLine();
            switch (choice) {
            case "i":
                typeBST.inOrder();
                System.out.print("\nEnter an item to insert: ");
                T itemInsert = parseInfo(scanner.nextLine(), typeBST);
                typeBST.insert(itemInsert);
                typeBST.inOrder();
                System.out.println();
                break;
            case "d":
                typeBST.inOrder();
                System.out.print("\nEnter an item to delete: ");
                T itemDelete = parseInfo(scanner.nextLine(), typeBST);
                typeBST.delete(itemDelete);
                typeBST.inOrder();
                System.out.println();
                break;
            case "p":
                typeBST.inOrder();
                System.out.println();
                break;
            case "s":
                typeBST.inOrder();
                System.out.print("\nEnter an item to search: ");
                T itemSearch = parseInfo(scanner.nextLine(), typeBST);
                if (typeBST.search(itemSearch)) {
                    System.out.println("Item is present in the tree.");
                } else {
                    System.out.println("Item is not present in the tree.");
                } // if
                break;
            case "l":
                typeBST.getNumLeafNodes();
                break;
            case "sp":
                typeBST.getSingleParent();
                break;
            case "c":
                typeBST.inOrder();
                System.out.print("\nEnter item to find cousins for: ");
                T itemCousins = parseInfo(scanner.nextLine(), typeBST);
                typeBST.getCousins(itemCousins);
                typeBST.inOrder();
                System.out.println();
                break;
            case "q":
                System.out.println("Quitting program.");
                break;
            default:
                System.out.println("Invalid command.");
            } // switch
        } // while
    } // choices

    /**
     * Helper method to print the user interface.
     */
    private static void printChoices() {
        System.out.println("Commands:");
        System.out.println("(i) - Insert Item");
        System.out.println("(d) - Delete Item");
        System.out.println("(p) - Print Tree");
        System.out.println("(s) - Search Item");
        System.out.println("(l) - Count Leaf Nodes");
        System.out.println("(sp) - Find Single Parents");
        System.out.println("(c) - Find Cousins");
        System.out.println("(q) - Quit program");
    } // printChoices

} // Drvier
