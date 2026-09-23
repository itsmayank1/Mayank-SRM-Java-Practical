import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final String PRODUCT_FILE = "products.dat";
    private static final String CATEGORY_FILE = "categories.dat";

    // Save products (Object Serialization)
    public static void saveProducts(List<Product> products) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(PRODUCT_FILE))) {
            output.writeObject(products);
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }

    // Load products
    @SuppressWarnings("unchecked")
    public static List<Product> loadProducts() {
        File file = new File(PRODUCT_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(PRODUCT_FILE))) {
            return (List<Product>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Notice: Initializing fresh product data store.");
            return new ArrayList<>();
        }
    }

    // Save categories
    public static void saveCategories(List<Category> categories) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(CATEGORY_FILE))) {
            output.writeObject(categories);
        } catch (IOException e) {
            System.out.println("Error saving categories: " + e.getMessage());
        }
    }

    // Load categories
    @SuppressWarnings("unchecked")
    public static List<Category> loadCategories() {
        File file = new File(CATEGORY_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(CATEGORY_FILE))) {
            return (List<Category>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Notice: Initializing fresh category data store.");
            return new ArrayList<>();
        }
    }

    // Export products to CSV
    public static boolean exportToCsv(List<Product> products, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("ID,Name,Category,Price,Stock,ReorderLevel,Supplier,TotalValue");
            for (Product p : products) {
                writer.printf("%d,\"%s\",\"%s\",%.2f,%d,%d,\"%s\",%.2f%n",
                        p.getId(),
                        p.getName().replace("\"", "\"\""),
                        p.getCategory().replace("\"", "\"\""),
                        p.getPrice(),
                        p.getStock(),
                        p.getReorderLevel(),
                        p.getSupplier().replace("\"", "\"\""),
                        p.getTotalValue());
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error exporting to CSV: " + e.getMessage());
            return false;
        }
    }

    // Export text summary
    public static boolean exportTextFile(String content, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.print(content);
            return true;
        } catch (IOException e) {
            System.out.println("Error saving report text: " + e.getMessage());
            return false;
        }
    }
}