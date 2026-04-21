import csv
from datetime import datetime

FILE = "expenses.csv"

# Add Expense
def add_expense():
    amount = input("Enter amount: ")
    category = input("Enter category (Food/Travel/Shopping/etc): ")
    date = datetime.now().strftime("%Y-%m-%d")

    with open(FILE, "a", newline="") as file:
        writer = csv.writer(file)
        writer.writerow([amount, category, date])

    print(" Expense added successfully!")

# View Expenses
def view_expenses():
    try:
        with open(FILE, "r") as file:
            reader = csv.reader(file)
            print("\n--- All Expenses ---")
            for row in reader:
                print(f"Amount: {row[0]}, Category: {row[1]}, Date: {row[2]}")
    except FileNotFoundError:
        print("No expenses found!")

# Total Expense
def total_expense():
    total = 0
    try:
        with open(FILE, "r") as file:
            reader = csv.reader(file)
            for row in reader:
                total += float(row[0])
        print(f"Total Expense: {total}")
    except FileNotFoundError:
        print("No data found!")

# Category wise Total
def category_wise():
    data = {}
    try:
        with open(FILE, "r") as file:
            reader = csv.reader(file)
            for row in reader:
                category = row[1]
                amount = float(row[0])

                if category in data:
                    data[category] += amount
                else:
                    data[category] = amount

        print("\n--- Category-wise Expense ---")
        for cat, amt in data.items():
            print(f"{cat}: {amt}")

    except FileNotFoundError:
        print("No data found!")

# Delete Expense
def delete_expense():
    try:
        with open(FILE, "r") as file:
            reader = list(csv.reader(file))
            if not reader:
                print("No expenses to delete")
                return
            print("\n---Expense List---")
            for i, row in enumerate(reader):
                print(f"{i}. Amount: {row[0]}, Category: {row[1]}, Date: {row[2]}")
                index = int(input("Enter index to delete :"))
                if index < 0 or index >= len(reader):
                    print("Invalid Index")
                    return
                reader.pop(index)
                with open(FILE, "w", newline="") as file:
                    writer = csv.writer(file)
                    writer.writerows(reader)
                print("Expense deleted successfully")
    except FileNotFoundError:
        print("No data found")
    except ValueError:
        print("Invalid input, Enter a number")


def monthly_expense():
    from datetime import datetime
    current_month = datetime.now().strftime("%Y-%m")

    total = 0

    try:
        with open(FILE, "r") as file:
            reader = csv.reader(file)

            print("\n--- Current Month Expenses ---")
            for row in reader:
                if row[2].startswith(current_month):
                    print(f"Amount: {row[0]}, Category: {row[1]}, Date: {row[2]}")
                    total += float(row[0])

        print(f"\nTotal this month: {total}")

    except FileNotFoundError:
        print("No data found!")


def show_graph():
    import matplotlib.pyplot as plt

    data = {}

    try:
        with open(FILE, "r") as file:
            reader = csv.reader(file)

            for row in reader:
                category = row[1]
                amount = float(row[0])

                if category in data:
                    data[category] += amount
                else:
                    data[category] = amount

        categories = list(data.keys())
        amounts = list(data.values())

        plt.bar(categories, amounts)
        plt.xlabel("Category")
        plt.ylabel("Amount")
        plt.title("Expense Analysis")
        plt.show()

    except FileNotFoundError:
        print("No data found!")

# Menu
def menu():
    while True:
        print("\n===== Expense Tracker =====")
        print("1. Add Expense")
        print("2. View Expenses")
        print("3. Total Expense")
        print("4. Category-wise Expense")
        print("5. Delete Expense")
        print("6. Monthly Expense")
        print("7. Show Graph")
        print("8. Exit")

        choice = input("Enter your choice: ")

        if choice == "1":
            add_expense()
        elif choice == "2":
            view_expenses()
        elif choice == "3":
            total_expense()
        elif choice == "4":
            category_wise()
        elif choice == "5":
            delete_expense()
        elif choice == "6":
            monthly_expense()
        elif choice == "7":
            show_graph()
        elif choice == "8":
            print("👋 Exiting...")
            break
        else:
            print("Invalid choice, try again!")

# Run program
menu()