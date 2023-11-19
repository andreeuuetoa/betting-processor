package util.outputdata;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ResultFileWriterTest {
	@Test
	public void testWriteSampleDataWithNewLinesToResultFile() {
		Path path = Paths.get("src", "test", "resources", "result", "test_file.txt");
		String sampleData = "Hello, world!\nNew line!";
		new ResultFileWriter(path).writeResultToFile(sampleData);
		assertDoesNotThrow(() -> {Files.newBufferedReader(path);});
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			StringBuilder dataInFile = new StringBuilder();
			while (true) {
				String line = reader.readLine();
				if (line == null) break;
				if (!dataInFile.isEmpty()) {
					dataInFile.append("\n");
				}
				dataInFile.append(line);
			}
			assertEquals(sampleData, dataInFile.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
