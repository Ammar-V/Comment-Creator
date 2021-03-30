package application;

public class GetComCol {
	private int rating;
	private String comment;

	public GetComCol() {
		this.comment = "";
		this.rating = 0;
	}

	public GetComCol(String comment, int rating) {
		this.comment = comment;
		this.rating = rating;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
