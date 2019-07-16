import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LibraryApp {
	static Scanner scnr = new Scanner(System.in);
	private static Path filePath = Paths.get("bookList.txt");

	public static void main(String[] args) throws IOException {

		int userPick = 0;

		System.out.println("Welcome to GroupOne's Libaray!\n");

		userPick = Validator.getInt(scnr,
				" Main Menu \n 1.Checkout a book \n 2.Return a book \n 3.Donate a book \n4.Exit \n");

		switch (userPick) {
		case 1:
			/* Checkout a book */
			String searchBy = Validator.getString(scnr, "Would you like to search a book by author or title? (A or T)");
			if (searchBy.equalsIgnoreCase("T")) {
				String titleToSearch = Validator.getString(scnr, "Enter the book title: ");
				String searchResult = searchByTitle(titleToSearch);
				System.out.println(searchResult);
			} else if (searchBy.equalsIgnoreCase("A")) {
				;// searchByAuthor();
			}
			break;
		case 2:
			/* Return a book */
			String titleToReturn = Validator.getString(scnr, "Enter the book title to return: ");
			processReturn(titleToReturn);
			break;
		case 3:
			/* Donate a book */
			break;

		default:
			/* Exit */
			break;
		}

	}

	public static String searchByTitle(String titleToSearch) throws IOException {
		String searchResult;
		FileLinesHelper fileLinesHelper = new FileLinesHelper(filePath);
		fileLinesHelper.ensureFileExists();
		Map<String, ArrayList<String>> bookListInfo = new HashMap<>();
		List<Book> listOfBooksObjects = FileUtil.readFile();
		for (Book book : listOfBooksObjects) {
			ArrayList<String> authorStatus = new ArrayList<>();
			authorStatus.add(book.getAuthor());
			authorStatus.add(book.getStatus());
			bookListInfo.put(book.getTitle(), authorStatus);

		}
		if (bookListInfo.containsKey(titleToSearch)) {

			searchResult = "Book found. Proceed to check out!";
		} else {

			searchResult = "Sorry, book is not available ";
		}
		return searchResult;
	}

	public static void processReturn(String titleToReturn) throws IOException {
		FileLinesHelper fileLinesHelper = new FileLinesHelper(filePath);
		fileLinesHelper.ensureFileExists();
		ArrayList<String> bookListInfo = new ArrayList<>();
		List<Book> listOfBooksObjects = FileUtil.readFile();
		for (Book book : listOfBooksObjects) {
			bookListInfo.add(book.getTitle());
		}
		if (bookListInfo.contains(titleToReturn)) {
			System.out.println("Thanks for returning our book!");
		} else {
			System.out.println("Sorry, the book title can't be found in our database");
		}
	}
}
