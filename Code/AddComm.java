package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddComm {
	AlertBox alert = new AlertBox();
	CommentBank bank = new CommentBank();
	@FXML
	private TextArea textArea = new TextArea();
	@FXML
	private TextField ratingBox = new TextField();
	static String initialCom;

	public void start(String com, double xCord, double yCord) throws IOException {
		initialCom = com;
		Stage window = new Stage();
		window.initStyle(StageStyle.UNDECORATED);
		window.initModality(Modality.APPLICATION_MODAL);
		AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("AddComm.fxml"));
		Scene scene = new Scene(root, 700, 355);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		// Getting the center of parent screen
		double x = GetCenter.getX(xCord, xCord + 1000);
		double y = GetCenter.getY(yCord, yCord + 646);

		window.setScene(scene);
		window.show();

		// Changing icon for window
		ClassLoader load = Thread.currentThread().getContextClassLoader();
		window.getIcons().add(new Image(load.getResourceAsStream("title.png")));

		window.setX(x - 350);
		window.setY(y - 200);
	}

	@FXML
	public void initialize() {
		try {
			initialCom.substring(0, 1);
			textArea.setText(initialCom);
		} catch (Exception a) {
		}
	}

	@FXML
	public void done(ActionEvent event) {
		String comment = textArea.getText();
		try {
			int rating = Integer.parseInt(ratingBox.getText());
			if (comment.length() == 0 || rating < 0 || rating > 5) {
				AlertBox.str = "Invalid Comment / Rating";
				alert.alert();
			} else {
				CommentBank.newComment = comment;
				CommentBank.newRating = rating;
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.close();
				bank.addCommentFinal++;
				bank.addComment(event);
			}

		} catch (Exception e) {
			AlertBox.str = "Invalid Comment / Rating";
			alert.alert();
		}
	}

	@FXML
	public void insertName(ActionEvent event) {
		textArea.insertText(textArea.getCaretPosition(), "<FN> ");
		textArea.requestFocus();
	}

	@FXML
	public void insertPronoun(ActionEvent event) {
		textArea.insertText(textArea.getCaretPosition(), "<TSH> ");
		textArea.requestFocus();
	}

	@FXML
	public void exit(MouseEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		bank.undoDelete(new ActionEvent());
	}

	@FXML
	public void min(MouseEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

	double x, y;

	@FXML
	public void titleClicked(MouseEvent event) {
		x = event.getSceneX();
		y = event.getSceneY();
	}

	@FXML
	public void titleDrag(MouseEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setX(event.getScreenX() - x);
		stage.setY(event.getScreenY() - y);
	}

	@FXML
	public void highlight(MouseEvent event) {
		Scene scene = (Scene) ((Node) event.getSource()).getScene();
		scene.setCursor(Cursor.HAND);
	}

	@FXML
	public void unHighlight(MouseEvent event) {
		Scene scene = (Scene) ((Node) event.getSource()).getScene();
		scene.setCursor(Cursor.DEFAULT);
	}
}
