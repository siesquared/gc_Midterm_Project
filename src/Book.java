
public class Book {

	private String title;
	private String author;
	private String status;
	private boolean returned = true;

	public Book() {

	}

	public Book(String title, String author, String status, boolean returned) {
		super();
		this.title = title;
		this.author = author;
		this.returned = returned;
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}

	@Override
	public String toString() {
		return "Books [title=" + title + ", author=" + author + ", status= " + status + "]";
	}

}
