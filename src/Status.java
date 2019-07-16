
public enum Status {

	CHECKEDOUT, ONSHELF, INTERLIBRARY, RESERVED;

	public String toString() {
		switch (this) {
		case CHECKEDOUT:
			return "Checked-out";
		case ONSHELF:
			return "On-shelf";
		case INTERLIBRARY:
			return "Interlibrary-loan";
		case RESERVED:
			return "Reserved";
		default:
			throw new IllegalArgumentException();
		}
	}
}