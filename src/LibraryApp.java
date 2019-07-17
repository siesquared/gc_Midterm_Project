import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

		displayBooks();

		System.out.println("-----------------------------------------------------\n");

		userPick = Validator.getInt(scnr,
				" Main Menu \n 1.Checkout a book \n 2.Return a book \n 3.Donate a book \n 4.Exit \n");

		switch (userPick) {
		case 1:
			/* Checkout a book */
			String searchBy = Validator.getString(scnr, "Would you like to search a book by author or title? (A or T)");
			if (searchBy.equalsIgnoreCase("T")) {
				String titleToSearch = Validator.getString(scnr, "Enter the book title: ");
				searchByTitle(titleToSearch);
			} else if (searchBy.equalsIgnoreCase("A")) {
				// searchByAuthor();
				String authorToSearch = Validator.getString(scnr, "Enter the book author: ");
				searchByAuthor(authorToSearch);
			}
			break;
		case 2:
			/* Return a book */
			String titleToReturn = Validator.getString(scnr, "Enter the book title to return: ");
			processReturn(titleToReturn);
			break;
		case 3:
			/* Donate a book */
			String titleToDonate = Validator.getString(scnr, "Enter the book title to donate: ");
			String authorToDonate = Validator.getString(scnr, "Enter the book author to donate: ");
			processDonatedBook(titleToDonate, authorToDonate);
			break;

		default:
			/* Exit */
			System.out.println("Thank you for using GroupOne's Libaray! ");
			break;
		}

	}/* End of main */

	/*------------------------------------*/
	public static void displayBooks() {
		List<Book> books = new ArrayList<>();
		books = FileUtil.readFile();
		System.out.printf("%-27s %-26s %-15s %-15s\n", "Title", "Author", "Status", "Due Date");
		System.out.println("================================================================================");
		for (Book book : books) {
			System.out.printf("%-27s %-26s %-15s", book.getTitle(), book.getAuthor(), book.getStatus());
			if (book.getDueDate() != null) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MMMM");
				System.out.printf("%-15s", book.getDueDate().format(formatter));
			}
			System.out.println("");
		}
	}

	/*------------------------------------*/
	public static void searchByTitle(String titleToSearch) throws IOException {

		FileLinesHelper fileLinesHelper = new FileLinesHelper(filePath);
		fileLinesHelper.ensureFileExists();
		Map<String, ArrayList<String>> bookListInfo = new HashMap<>();
		List<Book> listOfBooksObjects = FileUtil.readFile();
		for (Book book : listOfBooksObjects) {
			ArrayList<String> authorStatus = new ArrayList<>(); /* title is key */
			authorStatus.add(book.getAuthor());
			authorStatus.add(book.getStatus());
			bookListInfo.put(book.getTitle(), authorStatus);

		}
		if (bookListInfo.containsKey(titleToSearch)) {
			if (bookListInfo.get(titleToSearch).get(1).equals("On-shelf")
					|| bookListInfo.get(titleToSearch).get(1).equals("Interlibrary-loan")) {
				System.out.println(titleToSearch + " found and it is available for pickup. Proceed to check out!");

				setADueDate(titleToSearch, listOfBooksObjects, false);
				changeStatus(titleToSearch, listOfBooksObjects, Status.CHECKEDOUT);

			} else if (bookListInfo.get(titleToSearch).get(1).equals("Checked-out")) {
				System.out.println(
						titleToSearch + " is not available becuase it is checked out. Check again after 2 weeks!");

			} else if (bookListInfo.get(titleToSearch).get(1).equals("Reserved")) {
				System.out.println(titleToSearch
						+ " is not available becuase it was requested as an Interlibrary-loan. Check again after 3 weeks!");
			}

		} else {

			System.out.println("Sorry, book is not available ");
		}
	}

	/*------------------------------------*/
	public static void searchByAuthor(String authorToSearch) throws IOException {

		FileLinesHelper fileLinesHelper = new FileLinesHelper(filePath);
		fileLinesHelper.ensureFileExists();
		Map<String, ArrayList<String>> bookListInfo = new HashMap<>();
		List<Book> listOfBooksObjects = FileUtil.readFile();
		for (Book book : listOfBooksObjects) {
			ArrayList<String> titleStatus = new ArrayList<>(); /* author is key */
			titleStatus.add(book.getTitle());
			titleStatus.add(book.getStatus());
			bookListInfo.put(book.getAuthor(), titleStatus);

		}
		if (bookListInfo.containsKey(authorToSearch)) {
			setADueDate(authorToSearch, listOfBooksObjects, true);
			System.out.println("Book by " + authorToSearch + " found. Proceed to check out!");
			changeStatus(authorToSearch, listOfBooksObjects, Status.CHECKEDOUT);
		} else {

			System.out.println("Sorry, book is not available ");
		}
	}

	/*------------------------------------*/
	public static void processReturn(String titleToReturn) throws IOException {
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
		if (bookListInfo.containsKey(titleToReturn)) {
			System.out.println("Thanks for returning our book!");
			resetADueDate(titleToReturn, listOfBooksObjects);
			if (bookListInfo.get(titleToReturn).get(1).equals("Reserved")) {
				changeStatus(titleToReturn, listOfBooksObjects, Status.INTERLIBRARY);
			} else {
				changeStatus(titleToReturn, listOfBooksObjects, Status.ONSHELF);
			}

		} else {
			System.out.println("Sorry, the book title can't be found in our database");
		}

	}

	/*------------------------------------*/
	public static void changeStatus(String keyToSearch, List<Book> listOfBooksObjects, Status status)
			throws IOException {

		FileLinesHelper fileLinesHelper = new FileLinesHelper(filePath);
		fileLinesHelper.ensureFileExists();

		for (Book book : listOfBooksObjects) {
			if (book.getTitle().equals(keyToSearch)) {
				if (book.getStatus().equals("Interlibrary-loan")) {
					System.out.println(
							"It is a Interlibrary-loan. We will contact you when the book is readyfor pickup!");
					book.setStatus(Status.RESERVED.toString());
				} else {
					book.setStatus(status.toString());
				}
			} else if (book.getAuthor().equals(keyToSearch)) {
				if (book.getStatus().equals("Interlibrary-loan")) {
					System.out.println(
							"It is a Interlibrary-loan. We will contact you when the book is readyfor pickup!");
					book.setStatus(Status.RESERVED.toString());
				} else {
					book.setStatus(status.toString());
				}
			}
		}
		FileUtil.rewriteFile(listOfBooksObjects); /* after changing status */
	}

	/*------------------------------------*/
	public static void processDonatedBook(String titleToDonate, String authorToDonate) throws IOException {
		FileLinesHelper fileLinesHelper = new FileLinesHelper(filePath);
		fileLinesHelper.ensureFileExists();
		Book donatedBook = new Book();
		donatedBook.setAuthor(authorToDonate);
		donatedBook.setTitle(titleToDonate);
		donatedBook.setStatus(Status.ONSHELF.toString());
		FileUtil.appendToFile(donatedBook);
		System.out.println("Thanks for donating a book!");
	}

	public static void setADueDate(String keyToSearch, List<Book> listOfBooksObjects, boolean author)
			throws IOException {
		LocalDate today = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MMMM");
		System.out.println("Today is: " + today.format(formatter));

		LocalDate next2Week = today.plus(2, ChronoUnit.WEEKS);
		System.out.println("Return the book by: " + next2Week);
		if (!author) {
			for (Book book : listOfBooksObjects) {
				if (book.getTitle().equals(keyToSearch)) {
					book.setDueDate(next2Week);
				}
			}
		} else if (author) {
			for (Book book : listOfBooksObjects) {
				if (book.getAuthor().equals(keyToSearch)) {
					book.setDueDate(next2Week);
				}
			}
			// FileUtil.rewriteFile(listOfBooksObjects); /* write back after setting the due
			// date */
		}
	}

	public static void resetADueDate(String titleToReturn, List<Book> listOfBooksObjects) {
		for (Book book : listOfBooksObjects) {
			if (book.getTitle().equals(titleToReturn)) {
				book.setDueDate(null);
			}
		}
	}

}
