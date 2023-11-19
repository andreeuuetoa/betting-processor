package util.outputdata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResultFileWriter {
	private final Path resultFilePath;

	public ResultFileWriter(Path path) {
		resultFilePath = path;
	}

	public void writeResultToFile(String result) {
		try (BufferedWriter writer = Files.newBufferedWriter(resultFilePath)) {
			File newResultsFile = new File(resultFilePath.toString());
			newResultsFile.createNewFile();
			writer.write(result);
		} catch (IOException e) {
			throw new RuntimeException("Could not create new file in path: " + resultFilePath + " .");
		}
	}
}
