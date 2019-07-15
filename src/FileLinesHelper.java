
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileLinesHelper {

	private Path filePath;
	
	public FileLinesHelper(Path filePath) {
		this.filePath = filePath;
	}
	
	public FileLinesHelper(String filePath) {
		this(Paths.get(filePath));
	}
	
	public List<String> readFile() {
		// ** Example of reading a file into a list
		try {
			return Files.readAllLines(filePath);
		} catch (FileNotFoundException ex) {
			return new ArrayList<>();
		} catch (IOException ex) {
			ex.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	public void appendToFile(String line) throws IOException {
		ensureFileExists();
		
		// ** Example of adding to the end of a file
		// Create a list with the user's food in it
		List<String> linesToAdd = Arrays.asList(line);
		// Write those lines to the end of the file
		Files.write(filePath, linesToAdd, StandardOpenOption.APPEND);
	}
	
	public void rewriteFile(List<String> lines) throws IOException {
		ensureFileExists();
		
		// ** Example of rewriting a whole file
		Files.write(filePath, lines, StandardOpenOption.TRUNCATE_EXISTING);
	}
	
	public void ensureFileExists() throws IOException {
		if ( Files.notExists(filePath) ) {
			Files.createFile(filePath);
		}
	}
	
	public void clearFile() throws IOException {
		rewriteFile(Collections.emptyList());
	}
}