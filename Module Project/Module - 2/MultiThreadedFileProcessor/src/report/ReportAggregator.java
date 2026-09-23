package report;

import model.SalesRecord;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

/**
 * ReportAggregator - Aggregates data from multi-threaded CSV processors
 * and computes comprehensive analytical summaries and export files.
 */
public class ReportAggregator {

    private final List<SalesRecord> allRecords = new ArrayList<>();

    public void addRecords(List<SalesRecord> records) {
        synchronized (allRecords) {
            allRecords.addAll(records);
        }
    }

    public void generateReport(String outputFilePath) {
        int totalRecords = allRecords.size();
        double totalRevenue = 0;
        int totalQuantity = 0;

        Map<String, Double> revenueByCategory = new TreeMap<>();
        Map<String, Integer> quantityByCategory = new TreeMap<>();
        Map<String, Integer> quantityByProduct = new TreeMap<>();
        Map<String, Double> revenueByProduct = new TreeMap<>();

        SalesRecord highestTransaction = null;
        double maxTransactionValue = -1.0;

        for (SalesRecord r : allRecords) {
            double rev = r.getTotalRevenue();
            totalRevenue += rev;
            totalQuantity += r.getQuantity();

            revenueByCategory.merge(r.getCategory(), rev, Double::sum);
            quantityByCategory.merge(r.getCategory(), r.getQuantity(), Integer::sum);
            quantityByProduct.merge(r.getProduct(), r.getQuantity(), Integer::sum);
            revenueByProduct.merge(r.getProduct(), rev, Double::sum);

            if (rev > maxTransactionValue) {
                maxTransactionValue = rev;
                highestTransaction = r;
            }
        }

        // Top selling product by quantity
        String topProductByQty = quantityByProduct.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> e.getKey() + " (" + e.getValue() + " units)")
                .orElse("N/A");

        // Top revenue product
        String topProductByRev = revenueByProduct.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> String.format("%s ($%,.2f)", e.getKey(), e.getValue()))
                .orElse("N/A");

        // Top category by revenue
        String topCategory = revenueByCategory.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        String separator = "=".repeat(62);
        String report = buildReport(separator, totalRecords, totalQuantity, totalRevenue,
                topProductByQty, topProductByRev, topCategory, highestTransaction,
                revenueByCategory, quantityByCategory);

        // Print to Console
        System.out.println(report);

        // Write to text report file
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {
            writer.print(report);
            System.out.println("Text report saved to : " + outputFilePath);
        } catch (Exception e) {
            System.err.println("Could not write report file: " + e.getMessage());
        }

        // Export category summary to CSV
        String csvSummaryPath = "sales_summary.csv";
        try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvSummaryPath))) {
            csvWriter.println("Category,UnitsSold,RevenueUSD,PercentageOfTotalRevenue");
            for (Map.Entry<String, Double> entry : revenueByCategory.entrySet()) {
                String cat = entry.getKey();
                double rev = entry.getValue();
                int qty = quantityByCategory.getOrDefault(cat, 0);
                double pct = totalRevenue > 0 ? (rev / totalRevenue) * 100.0 : 0.0;
                csvWriter.printf("\"%s\",%d,%.2f,%.2f%%%n", cat, qty, rev, pct);
            }
            System.out.println("CSV summary saved to  : " + csvSummaryPath);
        } catch (Exception e) {
            System.err.println("Could not write CSV summary: " + e.getMessage());
        }
    }

    private String buildReport(
            String separator,
            int totalRecords,
            int totalQuantity,
            double totalRevenue,
            String topProductByQty,
            String topProductByRev,
            String topCategory,
            SalesRecord highestTransaction,
            Map<String, Double> revenueByCategory,
            Map<String, Integer> quantityByCategory
    ) {
        StringBuilder sb = new StringBuilder();

        sb.append("\n").append(separator).append("\n");
        sb.append("         EXECUTIVE SALES AGGREGATED REPORT\n");
        sb.append("        SRM Java Full Stack Practical Module 2\n");
        sb.append(separator).append("\n\n");

        sb.append("  Total Sales Records Processed : ").append(totalRecords).append("\n");
        sb.append(String.format("  Total Physical Quantity Sold  : %,d units\n", totalQuantity));
        sb.append(String.format("  Total Gross Revenue           : $%,.2f\n", totalRevenue));
        sb.append(String.format("  Average Revenue / Transaction : $%,.2f\n",
                totalRecords > 0 ? totalRevenue / totalRecords : 0.0));
        sb.append("\n");
        sb.append("  Top-Selling Product (Units)   : ").append(topProductByQty).append("\n");
        sb.append("  Top Product by Total Revenue  : ").append(topProductByRev).append("\n");
        sb.append("  Leading Sales Category        : ").append(topCategory).append("\n");

        if (highestTransaction != null) {
            sb.append(String.format("  Highest Single Sale Deal      : %s (%d units @ $%.2f = $%,.2f)\n",
                    highestTransaction.getProduct(),
                    highestTransaction.getQuantity(),
                    highestTransaction.getPrice(),
                    highestTransaction.getTotalRevenue()));
        }

        sb.append("\n").append(separator).append("\n");
        sb.append("  CATEGORY BREAKDOWN & MARKET SHARE:\n");
        sb.append(separator).append("\n");
        sb.append(String.format("  %-18s | %-12s | %-15s | %-10s\n", "Category", "Units Sold", "Total Revenue", "Share (%)"));
        sb.append("  " + "-".repeat(58) + "\n");

        final double finalTotalRev = totalRevenue;
        revenueByCategory.forEach((cat, rev) -> {
            int qty = quantityByCategory.getOrDefault(cat, 0);
            double pct = finalTotalRev > 0 ? (rev / finalTotalRev) * 100.0 : 0.0;
            sb.append(String.format("  %-18s | %,10d  | $%,13.2f | %6.2f%%\n", cat, qty, rev, pct));
        });

        sb.append(separator).append("\n");

        return sb.toString();
    }
}
