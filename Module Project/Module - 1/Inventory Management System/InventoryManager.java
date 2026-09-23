import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InventoryManager {

    private List<Product> products;
    private List<Category> categories;

    public InventoryManager() {
        products = FileManager.loadProducts();
        categories = FileManager.loadCategories();

        // Seed with sample initial records if empty
        if (categories.isEmpty()) {
            addCategory(new Category(1, "Electronics", "Devices, gadgets, and computer accessories"));
            addCategory(new Category(2, "Stationery", "Office and classroom stationery"));
            addCategory(new Category(3, "Furniture", "Desks, chairs, and lab furniture"));
        }

        if (products.isEmpty()) {
            addProduct(new Product(101, "Dell Wireless Mouse", "Electronics", 650.00, 25, 5, "Logitech Tech"));
            addProduct(new Product(102, "Mechanical Keyboard", "Electronics", 2400.00, 12, 4, "Redragon India"));
            addProduct(new Product(103, "A4 Spiral Notebook", "Stationery", 85.00, 50, 15, "Classmate"));
            addProduct(new Product(104, "Ergonomic Office Chair", "Furniture", 5200.00, 6, 2, "Featherlite"));
            addProduct(new Product(105, "Gel Pens Pack (10x)", "Stationery", 120.00, 4, 10, "Reynolds"));
        }
    }

    // =========================================================
    // PRODUCT OPERATIONS
    // =========================================================

    public boolean addProduct(Product product) {
        if (findProductById(product.getId()) != null) {
            return false;
        }
        products.add(product);
        FileManager.saveProducts(products);
        return true;
    }

    public void displayProducts() {
        displayProductList(this.products, "CURRENT INVENTORY CATALOG");
    }

    private void displayProductList(List<Product> list, String header) {
        if (list.isEmpty()) {
            System.out.println("\n[!] No products found.");
            return;
        }
        System.out.println("\n================================ " + header + " ================================");
        for (Product product : list) {
            System.out.println(product);
        }
        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.printf("Total Count: %d products displayed.%n", list.size());
    }

    public Product findProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public void searchProduct(String name) {
        List<Product> results = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(product);
            }
        }
        displayProductList(results, "SEARCH RESULTS FOR: \"" + name + "\"");
    }

    public void filterByCategory(String category) {
        List<Product> results = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategory().equalsIgnoreCase(category.trim())) {
                results.add(product);
            }
        }
        displayProductList(results, "PRODUCTS IN CATEGORY: " + category);
    }

    public void filterByPriceRange(double min, double max) {
        List<Product> results = new ArrayList<>();
        for (Product product : products) {
            if (product.getPrice() >= min && product.getPrice() <= max) {
                results.add(product);
            }
        }
        displayProductList(results, String.format("PRODUCTS IN PRICE RANGE: \u20B9%.2f - \u20B9%.2f", min, max));
    }

    public boolean updateProduct(int id, String name, String category, double price, int reorderLevel, String supplier) {
        Product product = findProductById(id);
        if (product == null) {
            return false;
        }
        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);
        product.setReorderLevel(reorderLevel);
        product.setSupplier(supplier);
        FileManager.saveProducts(products);
        return true;
    }

    public boolean deleteProduct(int id) {
        Product product = findProductById(id);
        if (product == null) {
            return false;
        }
        products.remove(product);
        FileManager.saveProducts(products);
        return true;
    }

    // =========================================================
    // SORTING OPERATIONS
    // =========================================================

    public void displaySortedByName() {
        List<Product> copy = new ArrayList<>(products);
        copy.sort(Comparator.comparing(p -> p.getName().toLowerCase()));
        displayProductList(copy, "PRODUCTS SORTED BY NAME (A-Z)");
    }

    public void displaySortedByPrice(boolean ascending) {
        List<Product> copy = new ArrayList<>(products);
        if (ascending) {
            copy.sort(Comparator.comparingDouble(Product::getPrice));
            displayProductList(copy, "PRODUCTS SORTED BY PRICE (LOW TO HIGH)");
        } else {
            copy.sort((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
            displayProductList(copy, "PRODUCTS SORTED BY PRICE (HIGH TO LOW)");
        }
    }

    public void displaySortedByStock(boolean ascending) {
        List<Product> copy = new ArrayList<>(products);
        if (ascending) {
            copy.sort(Comparator.comparingInt(Product::getStock));
            displayProductList(copy, "PRODUCTS SORTED BY STOCK (LOWEST FIRST)");
        } else {
            copy.sort((p1, p2) -> Integer.compare(p2.getStock(), p1.getStock()));
            displayProductList(copy, "PRODUCTS SORTED BY STOCK (HIGHEST FIRST)");
        }
    }

    // =========================================================
    // STOCK & INVENTORY OPERATIONS
    // =========================================================

    public boolean addStock(int productId, int quantity) {
        Product product = findProductById(productId);
        if (product == null || quantity <= 0) {
            return false;
        }
        product.setStock(product.getStock() + quantity);
        FileManager.saveProducts(products);
        return true;
    }

    public boolean removeStock(int productId, int quantity) {
        Product product = findProductById(productId);
        if (product == null || quantity <= 0 || product.getStock() < quantity) {
            return false;
        }
        product.setStock(product.getStock() - quantity);
        FileManager.saveProducts(products);
        return true;
    }

    public void displayLowStock() {
        List<Product> lowStockList = new ArrayList<>();
        for (Product product : products) {
            if (product.getStock() <= product.getReorderLevel()) {
                lowStockList.add(product);
            }
        }
        displayProductList(lowStockList, "RESTOCK RECOMMENDATIONS (STOCK \u2264 REORDER LEVEL)");
    }

    public void displayOutOfStock() {
        List<Product> outOfStockList = new ArrayList<>();
        for (Product product : products) {
            if (product.getStock() == 0) {
                outOfStockList.add(product);
            }
        }
        displayProductList(outOfStockList, "OUT-OF-STOCK ITEMS (STOCK = 0)");
    }

    // =========================================================
    // BILLING & SALES TRANSACTIONS
    // =========================================================

    public String processSale(int productId, int quantity, String customerName) {
        Product product = findProductById(productId);
        if (product == null) {
            return "Error: Product ID not found.";
        }
        if (quantity <= 0) {
            return "Error: Quantity must be greater than zero.";
        }
        if (product.getStock() < quantity) {
            return "Error: Insufficient stock. Available: " + product.getStock() + " units.";
        }

        // Deduct inventory
        product.setStock(product.getStock() - quantity);
        FileManager.saveProducts(products);

        double subtotal = product.getPrice() * quantity;
        double tax = subtotal * 0.18; // 18% GST standard
        double grandTotal = subtotal + tax;

        StringBuilder invoice = new StringBuilder();
        invoice.append("\n========================================================\n");
        invoice.append("                TAX INVOICE / SALES RECEIPT             \n");
        invoice.append("========================================================\n");
        invoice.append(String.format("Customer Name : %s%n", customerName));
        invoice.append(String.format("Product Sold  : %s (ID: %d)%n", product.getName(), product.getId()));
        invoice.append(String.format("Category      : %s%n", product.getCategory()));
        invoice.append(String.format("Unit Price    : \u20B9%.2f%n", product.getPrice()));
        invoice.append(String.format("Quantity      : %d%n", quantity));
        invoice.append("--------------------------------------------------------\n");
        invoice.append(String.format("Subtotal      : \u20B9%.2f%n", subtotal));
        invoice.append(String.format("GST (18%%)     : \u20B9%.2f%n", tax));
        invoice.append("--------------------------------------------------------\n");
        invoice.append(String.format("GRAND TOTAL   : \u20B9%.2f%n", grandTotal));
        invoice.append(String.format("Remaining Stk : %d units%n", product.getStock()));
        invoice.append("========================================================\n");
        invoice.append("  Thank you for shopping! Keep this receipt for warranty. \n");
        invoice.append("========================================================\n");

        return invoice.toString();
    }

    // =========================================================
    // VALUATION & ANALYTICS
    // =========================================================

    public String generateValuationReport() {
        if (products.isEmpty()) {
            return "No products available to generate valuation report.";
        }

        double totalValuation = 0.0;
        int totalUnits = 0;
        Product mostExpensive = products.get(0);
        Product leastExpensive = products.get(0);
        int lowStockCount = 0;
        int outOfStockCount = 0;

        for (Product p : products) {
            totalValuation += p.getTotalValue();
            totalUnits += p.getStock();

            if (p.getPrice() > mostExpensive.getPrice()) {
                mostExpensive = p;
            }
            if (p.getPrice() < leastExpensive.getPrice()) {
                leastExpensive = p;
            }
            if (p.getStock() == 0) {
                outOfStockCount++;
            } else if (p.getStock() <= p.getReorderLevel()) {
                lowStockCount++;
            }
        }

        StringBuilder report = new StringBuilder();
        report.append("\n========================================================\n");
        report.append("           INVENTORY VALUATION & ANALYTICS REPORT        \n");
        report.append("========================================================\n");
        report.append(String.format("Total Unique SKUs        : %d%n", products.size()));
        report.append(String.format("Total Physical Units     : %d units%n", totalUnits));
        report.append(String.format("Total Inventory Valuation: \u20B9%,.2f%n", totalValuation));
        report.append(String.format("Registered Categories    : %d%n", categories.size()));
        report.append("--------------------------------------------------------\n");
        report.append(String.format("Most Expensive Product   : %s (\u20B9%.2f)%n", mostExpensive.getName(), mostExpensive.getPrice()));
        report.append(String.format("Lowest Priced Product    : %s (\u20B9%.2f)%n", leastExpensive.getName(), leastExpensive.getPrice()));
        report.append(String.format("Low Stock Alert Items    : %d item(s)%n", lowStockCount));
        report.append(String.format("Out of Stock Items       : %d item(s)%n", outOfStockCount));
        report.append("========================================================\n");

        return report.toString();
    }

    public boolean exportToCsv(String filename) {
        return FileManager.exportToCsv(this.products, filename);
    }

    public boolean exportReport(String filename) {
        String content = generateValuationReport();
        return FileManager.exportTextFile(content, filename);
    }

    // =========================================================
    // CATEGORY OPERATIONS
    // =========================================================

    public boolean addCategory(Category category) {
        if (findCategoryById(category.getId()) != null) {
            return false;
        }
        categories.add(category);
        FileManager.saveCategories(categories);
        return true;
    }

    public void displayCategories() {
        if (categories.isEmpty()) {
            System.out.println("\n[!] No categories available.");
            return;
        }
        System.out.println("\n======================= CATEGORY DIRECTORY =======================");
        for (Category category : categories) {
            System.out.println(category);
        }
        System.out.println("------------------------------------------------------------------");
    }

    public Category findCategoryById(int id) {
        for (Category category : categories) {
            if (category.getId() == id) {
                return category;
            }
        }
        return null;
    }

    public boolean updateCategory(int id, String name, String description) {
        Category category = findCategoryById(id);
        if (category == null) {
            return false;
        }
        category.setName(name);
        category.setDescription(description);
        FileManager.saveCategories(categories);
        return true;
    }

    public boolean deleteCategory(int id) {
        Category category = findCategoryById(id);
        if (category == null) {
            return false;
        }
        // Safety check: cannot delete category if associated products exist
        for (Product product : products) {
            if (product.getCategory().equalsIgnoreCase(category.getName())) {
                return false;
            }
        }
        categories.remove(category);
        FileManager.saveCategories(categories);
        return true;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public int getNextProductId() {
        int maxId = 100;
        for (Product product : products) {
            if (product.getId() > maxId) {
                maxId = product.getId();
            }
        }
        return maxId + 1;
    }

    public int getNextCategoryId() {
        int maxId = 0;
        for (Category category : categories) {
            if (category.getId() > maxId) {
                maxId = category.getId();
            }
        }
        return maxId + 1;
    }
}