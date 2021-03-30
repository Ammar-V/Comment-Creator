package application;

import javafx.event.ActionEvent;

public interface CommentMethods {
	public void addUnit(); // This method does not have an ActionEvent as the event is routed through a
							// Button.setOnAction();

	public void deleteUnit(ActionEvent event); // The ActionEvent comes from the ActionEvent sent
												// from the FXML Button

	public void addComment(ActionEvent event);

	public void delComment();

	public void editComment(ActionEvent event);

}
