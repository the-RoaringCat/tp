package seedu.goldencompass.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AliasStorageTest {

    @TempDir
    Path tempDir;

    @Test
    public void saveAndLoad_customAliases_success() {
        // 1. Setup paths and storage
        String testPath = tempDir.resolve("testAliases.txt").toString();
        AliasStorage storage = new AliasStorage(testPath);

        // 2. Create a mock Alias Map
        Map<String, String> originalMap = new HashMap<>();
        originalMap.put("ls", "list");        // Custom alias
        originalMap.put("st", "set-deadline"); // Custom alias
        originalMap.put("add", "add");        // Default (should NOT be saved)

        // 3. Save to temp file
        storage.save(originalMap);

        // 4. Load into a fresh map
        Map<String, String> loadedMap = new HashMap<>();
        storage.load(loadedMap);

        // 5. Assertions
        assertEquals(2, loadedMap.size(), "Should only load 2 custom aliases");
        assertEquals("list", loadedMap.get("ls"));
        assertEquals("set-deadline", loadedMap.get("st"));
        assertFalse(loadedMap.containsKey("add"), "Default commands should not be stored");
    }

    @Test
    public void load_emptyFile_returnsEmptyMap() {
        String testPath = tempDir.resolve("empty.txt").toString();
        AliasStorage storage = new AliasStorage(testPath);
        Map<String, String> map = new HashMap<>();

        storage.load(map);
        assertTrue(map.isEmpty(), "Loading non-existent file should result in empty map");
    }
}
