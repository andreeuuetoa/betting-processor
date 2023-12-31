package bettingprocessor.util.outputdata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResultFileWriter implements ResultWriter {
	private final Path resultFilePath;

	public ResultFileWriter(Path path) {
		resultFilePath = path;
	}

	public void writeResult(String result) {
		try (BufferedWriter writer = Files.newBufferedWriter(resultFilePath)) {
			File newResultsFile = new File(resultFilePath.toString());
			newResultsFile.createNewFile();
			writer.write(result);
		} catch (IOException ignored) {
        }
    }
}
