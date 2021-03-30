package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MenuScreen extends LoginScreen {
	LoadComments load = new LoadComments();
	@FXML
	public Button creCom;
	@FXML
	public Button commentBank;

	public void startMenu(Stage window) throws IOException {

		AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("MenuScreen.fxml"));

		double x = GetCenter.getX(window.getX(), window.getX() + window.getWidth());
		double y = GetCenter.getY(window.getY(), window.getY() + window.getHeight());
		Scene scene = new Scene(root, 700, 530);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		window.setScene(scene);
		window.setX(x - 350);
		window.setY(y - 265);

		// This prevents any button to be highlighted
		root.requestFocus();
	}

	@FXML
	public void createComment(ActionEvent event) throws IOException {
		SubjectScreen view = new SubjectScreen();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		view.viewSubjects();
	}

	@FXML
	public void commentBank(ActionEvent event) throws IOException {
		ViewComments view = new ViewComments();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		view.showComment();
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
