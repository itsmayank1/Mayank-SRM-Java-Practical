import java.util.Scanner;

public class Main 
{
    private static final Scanner scanner = new Scanner(System.in);
    private static final InventoryManager manager = new InventoryManager();

    public static void main(String[] args) 
    {
        System.out.println("==========================================================");
        System.out.println("   ENTERPRISE INVENTORY MANAGEMENT & BILLING SYSTEM");
        System.out.println("         SRM Java Full Stack Practical Module 1");
        System.out.println("==========================================================");

        boolean running = true;

        while (running) 
        {
            displayMainMenu();
            int choice = readInt("Enter your choice (1-7): ");
            switch (choice) 
            {
                case 1:
                    productMenu();
                    break;
                case 2:
                    categoryMenu();
                    break;
                case 3:
                    stockMenu();
                    break;
                case 4:
                    sortingMenu();
                    break;
                case 5:
                    billingMenu();
                    break;
                case 6:
                    reportsMenu();
                    break;
                case 7:
                    System.out.println("\n[!] Thank you for using Inventory Management System. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("\n[!] Invalid selection. Please enter a valid number (1-7).");
            }
        }
        scanner.close();
    }

    // =========================================================
    // MAIN MENU
    // =========================================================

    private static void displayMainMenu() 
    {
        System.out.println("\n==========================================================");
        System.out.println("                        MAIN MENU");
        System.out.println("==========================================================");
        System.out.println("  1. Product Management       (Add, View, Search, Filter)");
        System.out.println("  2. Category Management      (Add, View, Update, Delete)");
        System.out.println("  3. Stock & Inventory Alerts (Add/Remove Stock, Alerts)");
        System.out.println("  4. Product Sorting Catalog  (By Name, Price, Stock)");
        System.out.println("  5. Point of Sale & Billing  (Create Sale, GST Invoice)");
        System.out.println("  6. Valuation & Reports      (Analytics, CSV/TXT Export)");
        System.out.println("  7. Exit System");
        System.out.println("==========================================================");
    }

    // =========================================================
    // PRODUCT MENU
    // =========================================================

    private static void productMenu() 
    {
        boolean back = false;
        while (!back) 
        {
            System.out.println("\n----------------------------------------------------------");
            System.out.println("                   PRODUCT MANAGEMENT");
            System.out.println("----------------------------------------------------------");
            System.out.println("  1. Add New Product");
            System.out.println("  2. View All Products");
            System.out.println("  3. Search Product by Name");
            System.out.println("  4. Filter Products by Category");
            System.out.println("  5. Filter Products by Price Range");
            System.out.println("  6. Update Product Information");
            System.out.println("  7. Delete Product");
            System.out.println("  8. Back to Main Menu");

            int choice = readInt("Enter choice (1-8): ");

            switch (choice) 
            {
                case 1:
                    addProduct();
                    break;
                case 2:
                    manager.displayProducts();
                    break;
                case 3:
                    searchProduct();
                    break;
                case 4:
                    filterCategory();
                    break;
                case 5:
                    filterPrice();
                    break;
                case 6:
                    updateProduct();
                    break;
                case 7:
                    deleteProduct();
                    break;
                case 8:
                    back = true;
                    break;
                default:
                    System.out.println("[!] Invalid choice. Select from 1 to 8.");
            }
        }
    }

    private static void addProduct() 
    {
        System.out.println("\n----------------- ADD NEW PRODUCT -----------------");
        int id = manager.getNextProductId();
        System.out.println("Assigned Product ID: " + id);

        String name = readString("Enter product name: ");
        String category = readString("Enter category: ");
        double price = readDouble("Enter price (\u20B9): ");
        int stock = readInt("Enter initial stock quantity: ");
        int reorder = readInt("Enter reorder alert threshold (e.g. 5): ");
        String supplier = readString("Enter supplier name: ");

        if (price < 0 || stock < 0 || reorder < 0) 
        {
            System.out.println("[!] Error: Numerical values cannot be negative.");
            return;
        }

        Product product = new Product(id, name, category, price, stock, reorder, supplier);
        if (manager.addProduct(product)) 
        {
            System.out.println("[+] Success: Product added successfully!");
        } 
        else 
        {
            System.out.println("[!] Failed: A product with this ID already exists.");
        }
    }

    private static void searchProduct() 
    {
        System.out.println("\n----------------- SEARCH PRODUCT -----------------");
        String name = readString("Enter product search keyword: ");
        manager.searchProduct(name);
    }

    private static void filterCategory() 
    {
        System.out.println("\n-------------- FILTER BY CATEGORY --------------");
        String category = readString("Enter category name to filter: ");
        manager.filterByCategory(category);
    }

    private static void filterPrice() 
    {
        System.out.println("\n------------- FILTER BY PRICE RANGE -------------");
        double min = readDouble("Enter minimum price (\u20B9): ");
        double max = readDouble("Enter maximum price (\u20B9): ");
        if (min > max) 
        {
            System.out.println("[!] Minimum price cannot exceed maximum price.");
            return;
        }
        manager.filterByPriceRange(min, max);
    }

    private static void updateProduct() 
    {
        System.out.println("\n---------------- UPDATE PRODUCT ----------------");
        int id = readInt("Enter Product ID to update: ");
        Product product = manager.findProductById(id);
        if (product == null) 
        {
            System.out.println("[!] Product not found.");
            return;
        }

        System.out.println("\nCurrent Product Record:");
        System.out.println(product);

        String name = readString("Enter new name: ");
        String category = readString("Enter new category: ");
        double price = readDouble("Enter new price (\u20B9): ");
        int reorder = readInt("Enter new reorder threshold: ");
        String supplier = readString("Enter new supplier name: ");

        if (price < 0 || reorder < 0) 
        {
            System.out.println("[!] Price and reorder threshold cannot be negative.");
            return;
        }

        if (manager.updateProduct(id, name, category, price, reorder, supplier)) 
        {
            System.out.println("[+] Success: Product updated successfully.");
        } 
        else 
        {
            System.out.println("[!] Error updating product.");
        }
    }

    private static void deleteProduct() 
    {
        System.out.println("\n---------------- DELETE PRODUCT ----------------");
        int id = readInt("Enter Product ID to delete: ");
        Product product = manager.findProductById(id);
        if (product == null) 
        {
            System.out.println("[!] Product not found.");
            return;
        }

        System.out.println(product);
        String confirmation = readString("Confirm deletion? (yes/no): ");
        if (confirmation.equalsIgnoreCase("yes")) 
        {
            if (manager.deleteProduct(id)) 
            {
                System.out.println("[+] Success: Product deleted successfully.");
            } 
            else 
            {
                System.out.println("[!] Error deleting product.");
            }
        } 
        else 
        {
            System.out.println("[-] Deletion cancelled.");
        }
    }

    // =========================================================
    // CATEGORY MENU
    // =========================================================

    private static void categoryMenu() 
    {
        boolean back = false;
        while (!back) 
        {
            System.out.println("\n----------------------------------------------------------");
            System.out.println("                   CATEGORY MANAGEMENT");
            System.out.println("----------------------------------------------------------");
            System.out.println("  1. Add Category");
            System.out.println("  2. View All Categories");
            System.out.println("  3. Update Category");
            System.out.println("  4. Delete Category");
            System.out.println("  5. Back to Main Menu");

            int choice = readInt("Enter choice (1-5): ");
            switch (choice) 
            {
                case 1:
                    addCategory();
                    break;
                case 2:
                    manager.displayCategories();
                    break;
                case 3:
                    updateCategory();
                    break;
                case 4:
                    deleteCategory();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("[!] Invalid choice. Select from 1 to 5.");
            }
        }
    }

    private static void addCategory() 
    {
        System.out.println("\n----------------- ADD CATEGORY -----------------");
        int id = manager.getNextCategoryId();
        System.out.println("Assigned Category ID: " + id);
        String name = readString("Enter category name: ");
        String description = readString("Enter category description: ");

        Category category = new Category(id, name, description);
        if (manager.addCategory(category)) 
        {
            System.out.println("[+] Success: Category added successfully.");
        } 
        else 
        {
            System.out.println("[!] Category with this ID already exists.");
        }
    }

    private static void updateCategory() 
    {
        System.out.println("\n---------------- UPDATE CATEGORY ----------------");
        int id = readInt("Enter Category ID to update: ");
        Category category = manager.findCategoryById(id);
        if (category == null) 
        {
            System.out.println("[!] Category not found.");
            return;
        }

        System.out.println("Current: " + category);
        String name = readString("Enter new category name: ");
        String description = readString("Enter new description: ");

        if (manager.updateCategory(id, name, description)) 
        {
            System.out.println("[+] Success: Category updated successfully.");
        } 
        else 
        {
            System.out.println("[!] Unable to update category.");
        }
    }

    private static void deleteCategory() 
    {
        System.out.println("\n---------------- DELETE CATEGORY ----------------");
        int id = readInt("Enter Category ID to delete: ");
        Category category = manager.findCategoryById(id);
        if (category == null) 
        {
            System.out.println("[!] Category not found.");
            return;
        }

        System.out.println(category);
        String confirmation = readString("Confirm deletion? (yes/no): ");
        if (!confirmation.equalsIgnoreCase("yes")) 
        {
            System.out.println("[-] Operation cancelled.");
            return;
        }

        if (manager.deleteCategory(id)) 
        {
            System.out.println("[+] Success: Category deleted successfully.");
        } 
        else 
        {
            System.out.println("[!] Cannot delete category: existing products are currently mapped to it.");
        }
    }

    // =========================================================
    // STOCK MENU
    // =========================================================

    private static void stockMenu() 
    {
        boolean back = false;
        while (!back) 
        {
            System.out.println("\n----------------------------------------------------------");
            System.out.println("                 STOCK MANAGEMENT & ALERTS");
            System.out.println("----------------------------------------------------------");
            System.out.println("  1. Add Stock (Restock Inward)");
            System.out.println("  2. Deduct / Write-off Stock");
            System.out.println("  3. View Restock Recommendations (Low Stock)");
            System.out.println("  4. View Out-of-Stock Products (0 Stock)");
            System.out.println("  5. Back to Main Menu");

            int choice = readInt("Enter choice (1-5): ");
            switch (choice) 
            {
                case 1:
                    addStock();
                    break;
                case 2:
                    removeStock();
                    break;
                case 3:
                    manager.displayLowStock();
                    break;
                case 4:
                    manager.displayOutOfStock();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("[!] Invalid choice. Select from 1 to 5.");
            }
        }
    }

    private static void addStock() 
    {
        System.out.println("\n------------------ ADD STOCK ------------------");
        int id = readInt("Enter Product ID: ");
        Product product = manager.findProductById(id);
        if (product == null) 
        {
            System.out.println("[!] Product not found.");
            return;
        }

        int quantity = readInt("Enter quantity to add: ");
        if (manager.addStock(id, quantity)) 
        {
            System.out.println("[+] Success: Stock added. Updated stock: " + product.getStock());
        } 
        else 
        {
            System.out.println("[!] Invalid quantity specified.");
        }
    }

    private static void removeStock() 
    {
        System.out.println("\n----------------- REMOVE STOCK -----------------");
        int id = readInt("Enter Product ID: ");
        Product product = manager.findProductById(id);
        if (product == null) 
        {
            System.out.println("[!] Product not found.");
            return;
        }

        System.out.println("Current stock: " + product.getStock());
        int quantity = readInt("Enter quantity to deduct: ");
        if (manager.removeStock(id, quantity)) 
        {
            System.out.println("[+] Success: Stock deducted. Remaining stock: " + product.getStock());
        } 
        else 
        {
            System.out.println("[!] Unable to remove stock. Ensure deduction does not exceed current stock.");
        }
    }

    // =========================================================
    // SORTING MENU
    // =========================================================

    private static void sortingMenu() 
    {
        boolean back = false;
        while (!back) 
        {
            System.out.println("\n----------------------------------------------------------");
            System.out.println("                 PRODUCT SORTING CATALOG");
            System.out.println("----------------------------------------------------------");
            System.out.println("  1. Sort by Product Name (A to Z)");
            System.out.println("  2. Sort by Price (Low to High)");
            System.out.println("  3. Sort by Price (High to Low)");
            System.out.println("  4. Sort by Stock (Lowest First - Urgent Restock)");
            System.out.println("  5. Sort by Stock (Highest First)");
            System.out.println("  6. Back to Main Menu");

            int choice = readInt("Enter choice (1-6): ");
            switch (choice) 
            {
                case 1:
                    manager.displaySortedByName();
                    break;
                case 2:
                    manager.displaySortedByPrice(true);
                    break;
                case 3:
                    manager.displaySortedByPrice(false);
                    break;
                case 4:
                    manager.displaySortedByStock(true);
                    break;
                case 5:
                    manager.displaySortedByStock(false);
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("[!] Invalid choice.");
            }
        }
    }

    // =========================================================
    // POINT OF SALE / BILLING MENU
    // =========================================================

    private static void billingMenu() 
    {
        System.out.println("\n----------------------------------------------------------");
        System.out.println("                 POINT OF SALE & BILLING");
        System.out.println("----------------------------------------------------------");
        String customer = readString("Enter Customer Name: ");
        int productId = readInt("Enter Product ID to sell: ");
        int qty = readInt("Enter purchase quantity: ");

        String result = manager.processSale(productId, qty, customer);
        System.out.println(result);
    }

    // =========================================================
    // VALUATION & REPORTS MENU
    // =========================================================

    private static void reportsMenu() 
    {
        boolean back = false;
        while (!back) 
        {
            System.out.println("\n----------------------------------------------------------");
            System.out.println("                VALUATION & EXPORT REPORTS");
            System.out.println("----------------------------------------------------------");
            System.out.println("  1. View Inventory Valuation & Statistics");
            System.out.println("  2. Export Catalog to CSV (inventory_export.csv)");
            System.out.println("  3. Export Summary Report to TXT (inventory_summary.txt)");
            System.out.println("  4. Back to Main Menu");

            int choice = readInt("Enter choice (1-4): ");
            switch (choice) 
            {
                case 1:
                    System.out.println(manager.generateValuationReport());
                    break;
                case 2:
                    if (manager.exportToCsv("inventory_export.csv")) 
                    {
                        System.out.println("[+] Success: Inventory successfully exported to inventory_export.csv");
                    }
                    break;
                case 3:
                    if (manager.exportReport("inventory_summary.txt")) 
                    {
                        System.out.println("[+] Success: Valuation summary saved to inventory_summary.txt");
                    }
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("[!] Invalid selection.");
            }
        }
    }

    // =========================================================
    // INPUT UTILITIES
    // =========================================================

    private static int readInt(String message) 
    {
        while (true) 
        {
            try 
            {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("[!] Error: Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String message) 
    {
        while (true) 
        {
            try 
            {
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine().trim());
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("[!] Error: Please enter a valid decimal number.");
            }
        }
    }

    private static String readString(String message) 
    {
        while (true) 
        {
            System.out.print(message);
            String val = scanner.nextLine().trim();
            if (!val.isEmpty()) 
            {
                return val;
            }
            System.out.println("[!] Error: Input cannot be empty.");
        }
    }
}