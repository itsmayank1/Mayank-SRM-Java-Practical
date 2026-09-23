# Module 1: Console Inventory Management & Billing System

A pure console-based enterprise inventory and billing application developed in Java with robust file serialization and data export capabilities.

## Architecture & Design
- **Console Interface**: Interactive, menu-driven CLI using Java `Scanner` with comprehensive error boundary protection for non-numeric and negative inputs.
- **Data Persistence**: Java Object Serialization (`ObjectOutputStream` & `ObjectInputStream`) storing catalog data in `products.dat` and `categories.dat`.
- **Export Engine**: CSV and plain-text file generator for financial and audit reviews.

## Key Features & Advancements
1. **Product Management**:
   - Add new products with automated unique ID generation.
   - Categorization, supplier tracking, and reorder threshold levels.
   - Update, delete, and real-time product search.
2. **Advanced Filtering & Search**:
   - Filter products by Category name.
   - Filter products within a user-specified Price Range (min to max).
3. **Product Catalog Sorting**:
   - Sort alphabetically by name (A to Z).
   - Sort by unit price (Low to High / High to Low).
   - Sort by stock level (Lowest First for urgent restock / Highest First).
4. **Stock & Inventory Alert System**:
   - Inward stock replenishment.
   - Outward stock deduction / write-off.
   - Low-stock restock recommendations (items at or below reorder threshold).
   - Zero-stock / Out-of-stock monitor.
5. **Point of Sale (POS) & Billing Module**:
   - Process sales transactions with instant inventory stock updates.
   - Automated 18% GST / Tax calculation.
   - Formatted ASCII tax invoice receipt printed to console.
6. **Financial Valuation & Analytics**:
   - Computes total physical asset inventory valuation ($\sum \text{price} \times \text{stock}$).
   - Identifies highest and lowest priced SKUs and summary KPIs.
   - Export full inventory data to `inventory_export.csv` and valuation report to `inventory_summary.txt`.

## How to Compile & Run

### Method 1: Using Command Line
```bash
cd "Module Project/Module - 1/Inventory Management System"
javac *.java
java Main
```

### Method 2: One-Click Windows Execution
Double-click `run.bat` located inside the project directory.

### Method 3: Run Automated Verification Tests
```bash
javac InventoryTest.java
java InventoryTest
```
