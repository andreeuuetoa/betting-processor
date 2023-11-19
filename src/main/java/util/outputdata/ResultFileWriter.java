package util.outputdata;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResultFileWriter {
	private Path resultFilePath;

	public ResultFileWriter(Path path) {
		resultFilePath = path;
	}

	public void writeResultToFile(String result) {
		try (BufferedWriter writer = Files.newBufferedWriter(resultFilePath)) {
			writer.write(result);
		} catch (IOException e) {
			System.out.println("IOException:" + e.getMessage());
		}
	}
}
