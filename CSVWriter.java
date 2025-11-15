import java.io.FileWriter;
import java.io.IOException;

/**
 * Simple helper to write experiment results to a CSV file.
 */
public class CSVWriter {

    private FileWriter writer;

    public CSVWriter(String filename) {
        try {
            writer = new FileWriter(filename);
            // Write CSV header
            writer.write("algorithm,inputType,n,avgMs\n");
        } catch (IOException e) {
            throw new RuntimeException("Failed to open CSV file for writing", e);
        }
    }

    public void writeRow(String algorithm, String inputType, int n, double avgMs) {
        try {
            String line = String.format("%s,%s,%d,%.3f\n", algorithm, inputType, n, avgMs);
            writer.write(line);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write row to CSV", e);
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to close CSV file", e);
        }
    }
}
