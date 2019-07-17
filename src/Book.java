import java.time.LocalDate;

public class Book {

	private String title;
	private String author;
	private String status;
	private LocalDate dueDate;

	public Book() {
		this.setDueDate(null);
	}

	public Book(String title, String author, String status) {
		super();
		this.title = title;
		this.author = author;
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Books [title=" + title + ", author=" + author + ", status= " + status + "]";
	}

	/**
	 * @return the dueDate
	 */
	public LocalDate getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

}
