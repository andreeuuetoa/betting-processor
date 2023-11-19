package util.outputdata;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResultFileWriterTest {
	@Test
	public void testWriteSampleDataToResultFile() {
		Path path = Paths.get("src", "test", "resources", "result", "test_file.txt");
		String sampleData = "Hello, world!";
		new ResultFileWriter(path).writeResultToFile(sampleData);
		assertDoesNotThrow(() -> {Files.newBufferedReader(path);});
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			String line = reader.readLine();
			assertEquals(sampleData, line);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
