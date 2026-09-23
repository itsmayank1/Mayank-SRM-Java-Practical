import config.ProcessorConfig;
import model.SalesRecord;
import processor.CsvFileProcessor;
import report.ReportAggregator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * ============================================================
 *  Main.java - Entry point for Multi-Threaded File Processor
 *  SRM Java Full Stack Practical - Module 2
 * ============================================================
 *
 * Demonstrates:
 *  - Multithreading with ExecutorService thread pool
 *  - Builder Design Pattern (ProcessorConfig)
 *  - Concurrent processing with Callable and Future<T>
 *  - Synchronization and Thread-safe data aggregation
 *  - Performance timing & benchmarking
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("==========================================================");
        System.out.println("   MULTI-THREADED CSV SALES DATA PROCESSOR ENGINE");
        System.out.println("         SRM Java Full Stack Practical Module 2");
        System.out.println("==========================================================\n");

        // -------------------------------------------------------
        // STEP 1: Build configuration using the Builder Pattern
        // -------------------------------------------------------
        int availableCores = Runtime.getRuntime().availableProcessors();
        int poolSize = Math.max(4, availableCores);

        ProcessorConfig config = new ProcessorConfig.Builder()
                .threadCount(poolSize)       // Adaptive thread pool matching system capabilities
                .inputFolder("data/")        // CSV files location
                .outputFile("report.txt")    // Text report output
                .skipHeader(true)            // Header detection
                .delimiter(',')              // CSV delimiter
                .build();

        System.out.println("Configuration initialized:");
        System.out.println("  -> Thread Pool Size : " + config.getThreadCount() + " concurrent worker threads");
        System.out.println("  -> Input Directory  : " + config.getInputFolder());
        System.out.println("  -> Output Report    : " + config.getOutputFile());
        System.out.println();

        // -------------------------------------------------------
        // STEP 2: Find all CSV files in the input folder
        // -------------------------------------------------------
        File folder = new File(config.getInputFolder());
        File[] csvFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

        if (csvFiles == null || csvFiles.length == 0) {
            System.err.println("[!] No CSV files found in: " + config.getInputFolder());
            System.err.println("Ensure data directory contains .csv files.");
            return;
        }

        System.out.println("Detected " + csvFiles.length + " sales batch file(s) for concurrent processing:\n");
        for (File f : csvFiles) {
            System.out.printf("  [FILE] %-25s (Size: %d bytes)%n", f.getName(), f.length());
        }
        System.out.println();

        // -------------------------------------------------------
        // STEP 3: Create ExecutorService & Benchmark Execution
        // -------------------------------------------------------
        ExecutorService executor = Executors.newFixedThreadPool(config.getThreadCount());
        List<Future<List<SalesRecord>>> futures = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        System.out.println("Dispatching worker threads to process files concurrently...\n");

        for (File csvFile : csvFiles) {
            CsvFileProcessor task = new CsvFileProcessor(csvFile.getPath(), config);
            Future<List<SalesRecord>> future = executor.submit(task);
            futures.add(future);
        }

        // -------------------------------------------------------
        // STEP 4: Collect results via Future.get()
        // -------------------------------------------------------
        ReportAggregator aggregator = new ReportAggregator();

        for (Future<List<SalesRecord>> future : futures) {
            try {
                List<SalesRecord> records = future.get();
                aggregator.addRecords(records);
            } catch (InterruptedException e) {
                System.err.println("[!] Thread interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                System.err.println("[!] Execution exception in worker: " + e.getCause().getMessage());
            }
        }

        // -------------------------------------------------------
        // STEP 5: Graceful Shutdown
        // -------------------------------------------------------
        executor.shutdown();
        long endTime = System.currentTimeMillis();
        long executionDuration = endTime - startTime;

        System.out.println("\nAll concurrent threads completed. Thread pool shut down.");
        System.out.println("Processing Benchmark: Completed in " + executionDuration + " ms (" + (executionDuration / 1000.0) + " seconds)\n");

        // -------------------------------------------------------
        // STEP 6: Generate Aggregated Multi-format Reports
        // -------------------------------------------------------
        System.out.println("Aggregating sales metrics and generating reports...");
        aggregator.generateReport(config.getOutputFile());

        System.out.println("\n[+] Batch processing finished successfully!");
    }
}
