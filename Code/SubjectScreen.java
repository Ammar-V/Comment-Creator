package application;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SubjectScreen extends LoginScreen {
	LoadComments load = new LoadComments();
	public static String comSubject = "";
	@FXML
	private ChoiceBox<String> selectSubject = new ChoiceBox<String>();
	@FXML
	public Button back;
	@FXML
	private Button next;
	AlertBox alert = new AlertBox();

	public void viewSubjects() throws IOException {
		comSubject = "";
		AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("SubjectScreen.fxml"));
		double x = GetCenter.getX(window.getX(), window.getX() + window.getWidth());
		double y = GetCenter.getY(window.getY(), window.getY() + window.getHeight());
		Scene scene = new Scene(root, 700, 530);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene);
		window.setX(x - 350);
		window.setY(y - 265);
	}

	@FXML
	public void initialize() {
		ObservableList<String> subjectLists = FXCollections.observableArrayList();
		for (int j = 0; j < LoadComments.subjects.size(); j++) {
			String subject = LoadComments.subjects.get(j);
			boolean exists = false;
			for (int i = 0; i < subjectLists.size(); i++) {
				if (subjectLists.get(i).equals(subject))
					exists = true;
			}
			if (exists == false)
				subjectLists.add(subject);
		}

		selectSubject.setItems(subjectLists);
		selectSubject.getSelectionModel().selectedItemProperty()
				.addListener((v, oldValue, newValue) -> comSubject = newValue);
	}

	@FXML
	public void backButton(ActionEvent event) throws IOException {
		MenuScreen menu = new MenuScreen();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		menu.startMenu(stage);
	}

	@FXML
	public void nextFunction(ActionEvent event) throws IOException {
		try {
			comSubject.substring(0, 1);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			InfoComment info = new InfoComment();
			InfoComment.email = "";
			info.start();
		} catch (Exception e) {
			AlertBox.str = "Select a subject.";
			alert.alert();
		}
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
