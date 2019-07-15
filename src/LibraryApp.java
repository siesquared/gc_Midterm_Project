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
			if(searchBy.equalsIgnoreCase("T")) {
				String titleToSearch = Validator.getString(scnr, "Enter the book title: ");
				String searchResult = searchByTitle(titleToSearch);
				System.out.println(searchResult);
			}else if(searchBy.equalsIgnoreCase("A")) {
				//searchByAuthor();
			}
			break;
		case 2:
			/* Return a book */
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
		String searchResult =null;
		FileLinesHelper fileLinesHelper = new FileLinesHelper(filePath);
		fileLinesHelper.ensureFileExists();
		Map<String, ArrayList<String>> bookListInfo = new HashMap<>();
		List<Book> listOfBooksObjects = FileUtil.readFile();
		for (Book book: listOfBooksObjects) {
			ArrayList<String> authorStatus = new ArrayList<>();
			authorStatus.add(book.getAuthor()); 
			authorStatus.add(book.getStatus());
			bookListInfo.put(book.getTitle(), authorStatus);
			
			
		}
		if (bookListInfo.containsKey(titleToSearch)) {
			
			System.out.println(listOfBooksObjects.get(0));
			System.out.println("testing testing 1");
			
			searchResult = "Book found. Proceed to check out!";
		}else {
			System.out.println(listOfBooksObjects.get(1));
			System.out.println("testing testing 2");
			
			searchResult = "Sorry book Not available because it is " ;//+ bookListInfo.get(titleToSearch).get(2);
		}
		return searchResult;
	}

}
