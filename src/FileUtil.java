import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	// Modify path to file here.
	private static FileLinesHelper linesHelper = new FileLinesHelper("bookList.txt");

	// Modify this method as necessary to convert a line of text from the file to a
	// new item instance
	private static Book convertLineToItem(String line) {
		String[] parts = line.split(",");
		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MMMM");
		Book book = new Book();
		book.setTitle(parts[0]);
		book.setAuthor(parts[1]);
		book.setStatus(parts[2]);
		if (parts.length == 4 && !parts[3].equals("null")) {
			book.setDueDate(LocalDate.parse(parts[3]));
		}
		return book;
	}

	// Modify this method as necessary to convert an item instance to a line of text
	// in the file
	private static String convertItemToLine(Book book) {
		return String.format("%s,%s,%s,%s", book.getTitle(), book.getAuthor(), book.getStatus(), book.getDueDate());
	}

	public static List<Book> readFile() {
		List<String> lines = linesHelper.readFile();
		List<Book> items = new ArrayList<>(lines.size());
		for (String line : lines) {
			items.add(convertLineToItem(line));
		}
		return items;
//		Or with streams...
//		return linesHelper.readFile().stream().map(PlayerFileUtil::convertLineToItem).collect(Collectors.toList());
	}

	public static void rewriteFile(List<Book> items) throws IOException {
		List<String> lines = new ArrayList<>(items.size());
		for (Book item : items) {
			lines.add(convertItemToLine(item));
		}
		linesHelper.rewriteFile(lines);
//		Or with streams...
//		linesHelper.rewriteFile(items.stream().map(PlayerFileUtil::convertItemToLine).collect(Collectors.toList()));
	}

	public static void appendToFile(Book item) throws IOException {
		String line = convertItemToLine(item);
		linesHelper.appendToFile(line);
	}
}