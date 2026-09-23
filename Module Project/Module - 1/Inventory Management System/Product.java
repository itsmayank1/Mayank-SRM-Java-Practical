import java.io.Serializable;

public class Product implements Serializable {

    private static final long serialVersionUID = 2L;

    private int id;
    private String name;
    private String category;
    private double price;
    private int stock;
    private int reorderLevel;
    private String supplier;

    public Product(int id, String name, String category, double price, int stock) {
        this(id, name, category, price, stock, 5, "Standard Supplier");
    }

    public Product(int id, String name, String category, double price, int stock, int reorderLevel, String supplier) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.reorderLevel = reorderLevel > 0 ? reorderLevel : 5;
        this.supplier = (supplier != null && !supplier.trim().isEmpty()) ? supplier.trim() : "Standard Supplier";
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public String getSupplier() {
        return supplier;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public double getTotalValue() {
        return price * stock;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %-4d | Name: %-18s | Category: %-14s | Price: \u20B9%-8.2f | Stock: %-4d | Reorder: %-3d | Supplier: %s",
                id, name, category, price, stock, reorderLevel, supplier
        );
    }
}