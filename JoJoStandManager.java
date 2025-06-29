import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**
 * JoJoStandManager
 * Manages JoJo's Bizarre Adventure Stands and their power levels.
 */

public class JoJoStandManager {
    // ArrayList to store Stand names
    private static ArrayList<String> standNames = new ArrayList<>();

    // Array to store power levels (assuming max 100 Stands)
    private static int[] powerLevels = new int[100];
    private static int count = 0;   // number of Stands
    public static void main(String[] args) {
        // Load existing data
        loadData();
        Scanner scanner = new Scanner(System.in);
        boolean running = true
        while (running) {
            // Menu
            System.out.println("\n=== JoJo stand power Level Manager ===");
            System.out.println("1. Add Stand");
            System.out.println("2. View All Stands");
            System.out.println("3. Search for a Stand");
            System.out.println("4. Remove a Stand");
            System.out.println("5. Exit");
            System.out.println("Choose an option: ");
            int choice = getValidInt(scanner);

            switch (choice) {
                case 1:
                    addStand(scanner);
                    break;
                case 2:
                    viewStands();
                    break;
                case 3:
                    searchStand(scanner);
                    break;
                case 4:
                    removeStand(scanner);
                    break;
                case 5:
                    saveData();
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }

    // Recursively get a valid integer input
    private static int getValidInt(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.print("Please enter a valid number: ");
            return getValidInt(scanner); // Recursion
        }
    }

    // Add a new Stand
    private static void addStand(Scanner scanner) {
        System.out.print("Enter Stand name: ");
        String name = scanner.nextLine();
        System.out.print("Enter power level (1-100): ");
        int power = getValidInt(scanner);
        standNames.add(name);
        powerLevels[count] = power;
        count++;
        System.out.println("Stand added!");
    }

    // View all Stands
    private static void viewStands() {
        if (count == 0) {
            System.out.println("No Stands yet.");
        } else {
            System.out.println("\nCurrent Stands:");
            for (int i = 0; i < count; i++) {
                System.out.println((i + 1) + ". " + standNames.get(i) + " - Power Level: " + powerLevels[i]);
            }
        }
    }

    // Search for a Stand
    private static void searchStand(Scanner scanner) {
        System.out.print("Enter Stand name to search: ");
        String search = scanner.nextLine();
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (standNames.get(i).equalsIgnoreCase(search)) {
                System.out.println("Found: " + standNames.get(i) + " - Power Level: " + powerLevels[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Stand not found.");
        }
    }

    // Remove a Stand
    private static void removeStand(Scanner scanner) {
        System.out.print("Enter Stand name to remove: ");
        String remove = scanner.nextLine();
        boolean removed = false;
        for (int i = 0; i < count; i++) {
            if (standNames.get(i).equalsIgnoreCase(remove)) {
                standNames.remove(i);
                // Shift powerLevels array
                for (int j = i; j < count - 1; j++) {
                    powerLevels[j] = powerLevels[j + 1];
                }
                count--;
                System.out.println("Stand removed.");
                removed = true;
                break;
            }
        }
        if (!removed) {
            System.out.println("Stand not found.");
        }
    }

    // Save data to files
    private static void saveData() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("stands.txt"));
            for (String name : standNames) {
                writer.println(name);
            }
            writer.close();

            PrintWriter writer2 = new PrintWriter(new FileWriter("powers.txt"));
            for (int i = 0; i < count; i++) {
                writer2.println(powerLevels[i]);
            }
            writer2.close();
            System.out.println("Data saved.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load data from files
    private static void loadData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("stands.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                standNames.add(line);
            }
            reader.close();

            BufferedReader reader2 = new BufferedReader(new FileReader("powers.txt"));
            int i = 0;
            while ((line = reader2.readLine()) != null) {
                powerLevels[i] = Integer.parseInt(line);
                i++;
            }
            count = standNames.size();
            reader2.close();
            System.out.println("Data loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}