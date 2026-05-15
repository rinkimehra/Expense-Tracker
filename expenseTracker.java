import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.nio.file.*;

public class expenseTracker {

    static final String FILE = "expenses.csv";
    static Scanner sc = new Scanner(System.in);

    // Add Expense
    static void addExpense() {
        try {
            System.out.print("Enter amount: ");
            String amount = sc.nextLine();

            System.out.print("Enter category (Food/Travel/Shopping/etc): ");
            String category = sc.nextLine();

            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            FileWriter fw = new FileWriter(FILE, true);
            fw.write(amount + "," + category + "," + date + "\n");
            fw.close();

            System.out.println("Expense added successfully!");

        } catch (IOException e) {
            System.out.println("Error writing file");
        }
    }

    // View Expenses
    static void viewExpenses() {
        try {
            File file = new File(FILE);

            if (!file.exists()) {
                System.out.println("No expenses found!");
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(FILE));

            String line;

            System.out.println("\n--- All Expenses ---");

            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");

                System.out.println("Amount: " + row[0]
                        + ", Category: " + row[1]
                        + ", Date: " + row[2]);
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    // Total Expense
    static void totalExpense() {
        double total = 0;

        try {
            File file = new File(FILE);

            if (!file.exists()) {
                System.out.println("No data found!");
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(FILE));

            String line;

            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                total += Double.parseDouble(row[0]);
            }

            br.close();

            System.out.println("Total Expense: " + total);

        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    // Category Wise Expense
    static void categoryWise() {
        HashMap<String, Double> data = new HashMap<>();

        try {
            File file = new File(FILE);

            if (!file.exists()) {
                System.out.println("No data found!");
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(FILE));

            String line;

            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");

                String category = row[1];
                double amount = Double.parseDouble(row[0]);

                data.put(category, data.getOrDefault(category, 0.0) + amount);
            }

            br.close();

            System.out.println("\n--- Category-wise Expense ---");

            for (String cat : data.keySet()) {
                System.out.println(cat + ": " + data.get(cat));
            }

        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    // Delete Expense
    static void deleteExpense() {
        try {
            File file = new File(FILE);

            if (!file.exists()) {
                System.out.println("No data found!");
                return;
            }

            List<String> lines = Files.readAllLines(Paths.get(FILE));

            if (lines.isEmpty()) {
                System.out.println("No expenses to delete");
                return;
            }

            System.out.println("\n--- Expense List ---");

            for (int i = 0; i < lines.size(); i++) {
                String[] row = lines.get(i).split(",");

                System.out.println(i + ". Amount: " + row[0]
                        + ", Category: " + row[1]
                        + ", Date: " + row[2]);
            }

            System.out.print("Enter index to delete: ");
            int index = Integer.parseInt(sc.nextLine());

            if (index < 0 || index >= lines.size()) {
                System.out.println("Invalid Index");
                return;
            }

            lines.remove(index);

            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE));

            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }

            bw.close();

            System.out.println("Expense deleted successfully");

        } catch (IOException e) {
            System.out.println("Error reading file");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, Enter a number");
        }
    }

    // Monthly Expense
    static void monthlyExpense() {
        String currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));

        double total = 0;

        try {
            File file = new File(FILE);

            if (!file.exists()) {
                System.out.println("No data found!");
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(FILE));

            String line;

            System.out.println("\n--- Current Month Expenses ---");

            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");

                if (row[2].startsWith(currentMonth)) {
                    System.out.println("Amount: " + row[0]
                            + ", Category: " + row[1]
                            + ", Date: " + row[2]);

                    total += Double.parseDouble(row[0]);
                }
            }

            br.close();

            System.out.println("\nTotal this month: " + total);

        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    // Menu
    static void menu() {
        while (true) {

            System.out.println("\n===== Expense Tracker =====");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Total Expense");
            System.out.println("4. Category-wise Expense");
            System.out.println("5. Delete Expense");
            System.out.println("6. Monthly Expense");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    addExpense();
                    break;

                case "2":
                    viewExpenses();
                    break;

                case "3":
                    totalExpense();
                    break;

                case "4":
                    categoryWise();
                    break;

                case "5":
                    deleteExpense();
                    break;

                case "6":
                    monthlyExpense();
                    break;

                case "7":
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice, try again!");
            }
        }
    }

    // Main Method
    public static void main(String[] args) {
        menu();
    }
}