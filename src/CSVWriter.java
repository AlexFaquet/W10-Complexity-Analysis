package src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Simple CSV writer for experiment results.
 * Writes a header row on creation and appends subsequent rows.
 */
public class CSVWriter {

    private final PrintWriter out;

    /**
     * Creates a CSVWriter and opens the target file for writing.
     * If the file already exists, it will be overwritten.
     * The parent directory is created if it does not exist.
     *
     * @param path path to the CSV file (e.g. "results/results.csv")
     */
    public CSVWriter(String path) {
        try {
            File file = new File(path);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            this.out = new PrintWriter(new FileWriter(file, false));
            // Header row: algorithm,inputType,n,ms,trial
            out.println("algorithm,inputType,n,ms,trial");
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("Failed to open CSV file for writing: " + path, e);
        }
    }

    /**
     * Writes a single result row.
     *
     * @param algorithm  algorithm name (e.g. "selection", "merge", "quick")
     * @param inputType  input type (e.g. "random", "sorted", "reversed", "nearlySorted")
     * @param n          input size
     * @param ms         time in milliseconds for this single trial
     * @param trial      trial number (1..TRIALS)
     */
    public void writeRow(String algorithm, String inputType, int n, double ms, int trial) {
        out.printf("%s,%s,%d,%.6f,%d%n", algorithm, inputType, n, ms, trial);
        out.flush();
    }

    /**
     * Close the CSV writer.
     */
    public void close() {
        out.close();
    }
}
