package seedu.goldencompass.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class AliasStorage {
    private final String filePath;

    public AliasStorage(String filePath) {
        this.filePath = filePath;
    }

    public void save(Map<String, String> aliasMap) {
        try (FileWriter fw = new FileWriter(filePath)) {
            for (Map.Entry<String, String> entry : aliasMap.entrySet()) {
                // Only save custom aliases (where key != value)
                if (!entry.getKey().equals(entry.getValue())) {
                    fw.write(entry.getKey() + " | " + entry.getValue() + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving aliases.");
        }
    }

    public void load(Map<String, String> aliasMap) {
        File f = new File(filePath);
        if (!f.exists()) {
            return;
        }

        try (Scanner s = new Scanner(f)) {
            while (s.hasNext()) {
                String[] parts = s.nextLine().split(" \\| ");
                if (parts.length == 2) {
                    aliasMap.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading aliases.");
        }
    }
}
