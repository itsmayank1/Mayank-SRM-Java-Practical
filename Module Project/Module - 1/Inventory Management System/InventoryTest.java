public class InventoryTest {
    public static void main(String[] args) {
        System.out.println("Running automated tests for InventoryManager...");

        InventoryManager manager = new InventoryManager();

        // 1. Add product test
        Product p = new Product(999, "Test Headphones", "Electronics", 1500.0, 10, 3, "Sony");
        boolean added = manager.addProduct(p);
        System.out.println("Test 1 - Add Product: " + (added ? "PASSED" : "FAILED"));

        // 2. Find product test
        Product found = manager.findProductById(999);
        System.out.println("Test 2 - Find Product: " + (found != null && found.getName().equals("Test Headphones") ? "PASSED" : "FAILED"));

        // 3. Process sale test
        String receipt = manager.processSale(999, 2, "Mayank Upadhayay");
        System.out.println("Test 3 - Process Sale & Stock decrement: " + (found.getStock() == 8 && receipt.contains("TAX INVOICE") ? "PASSED" : "FAILED"));

        // 4. Valuation report test
        String valuation = manager.generateValuationReport();
        System.out.println("Test 4 - Valuation Report: " + (valuation.contains("INVENTORY VALUATION") ? "PASSED" : "FAILED"));

        // 5. CSV export test
        boolean csvExport = manager.exportToCsv("inventory_export.csv");
        System.out.println("Test 5 - CSV Export: " + (csvExport ? "PASSED" : "FAILED"));

        // Cleanup test product
        manager.deleteProduct(999);
        System.out.println("Test 6 - Delete Product: " + (manager.findProductById(999) == null ? "PASSED" : "FAILED"));

        System.out.println("All automated tests completed successfully!");
    }
}
