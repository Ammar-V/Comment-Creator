package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class Print extends LoginScreen {
	CreateComment com = new CreateComment();
	LoadComments load = new LoadComments();
	InfoComment info = new InfoComment();
	ArrayList<String> comment = new ArrayList<>();
	ArrayList<String> ogCom = new ArrayList<>();
	String gender = InfoComment.gender;
	String name = InfoComment.name;
	String email = InfoComment.email;
	@FXML
	private HTMLEditor box;

	public Print() {
		for (GetComment comment : CreateComment.comments)
			this.comment.add(comment.getComment().replaceAll("\n", ""));
	}

	public void start() throws IOException {
		AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Print.fxml"));
		double x = GetCenter.getX(window.getX(), window.getX() + window.getWidth());
		double y = GetCenter.getY(window.getY(), window.getY() + window.getHeight());
		Scene scene = new Scene(root, 850, 568);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene);
		window.setX(x - (850 / 2));
		window.setY(y - (568 / 2));
		root.requestFocus();
	}

	@FXML
	public void initialize() {
		ogCom = comment;
		String finalComment = "";
		for (String com : comment) {
			finalComment += com.replaceAll("\n", "").replaceAll("<FN>", name).replaceAll("<TSH>", gender + " (↤ ! ↦)");
		}
		try {
			if (email.length() != 0)
				finalComment += "If you have any further questions, you can contact me at " + email + " .";
		} catch (Exception e) {
		}
		box.setHtmlText(finalComment);
	}

	@FXML
	public void copy(ActionEvent event) {
		ClipboardContent copy = new ClipboardContent();
		copy.putString(removeHTML(box.getHtmlText()).replaceAll(" \\(↤ ! ↦\\)", ""));
		Clipboard.getSystemClipboard().setContent(copy);
	}

	/*
	 * When retrieving text from an HTMLEDITOR, the string includes html tags. This
	 * method finds all the "<" brackets and stores the index in an array list.
	 * Next, a for loop finds all of the html tags in the string using the "<"
	 * indices. Finally, all of the html tags are replaced by "".
	 */
	public String removeHTML(String html) {
		ArrayList<Integer> htmlTags = new ArrayList<>();
		for (int j = 0; j < html.length(); j++) {
			String bracket;
			try {
				bracket = html.substring(j, j + 1);
			} catch (IndexOutOfBoundsException e) {
				bracket = html.substring(j);
			}
			if (bracket.equals("<") || bracket.equals(">")) {
				htmlTags.add(j);
			}
		}
		ArrayList<String> delItems = new ArrayList<>();
		for (int j = 0; j < htmlTags.size(); j++) {
			try {
				delItems.add(html.substring(htmlTags.get(j), htmlTags.get(j + 1) + 1));
			} catch (Exception e) {
			}
			j += 1;
		}
		for (String item : delItems) {
			html = html.replaceAll(item, "");
		}
		return html;
	}

	@FXML
	public void newCom(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		InfoComment info = new InfoComment();
		InfoComment.email = email;
		info.start(stage);
	}

	@FXML
	public void mainMenu(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		MenuScreen mEnu = new MenuScreen();
		mEnu.startMenu(stage);
	}

	@FXML
	public void backButton(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		com.start(ogCom);
	}

	@FXML
	public void exit(MouseEvent event) {
		load.upload();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
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

	@FXML
	public void manual(MouseEvent event) {
		OpenManual.openMan();
	}
}
