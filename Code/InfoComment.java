package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InfoComment extends LoginScreen {
	LoadComments load = new LoadComments();
	AlertBox alert = new AlertBox();
	@FXML
	private Button back;
	@FXML
	private Button next;
	@FXML
	private RadioButton male;

	@FXML
	private RadioButton female;

	@FXML
	private RadioButton other;
	@FXML
	private TextField nameBox;
	@FXML
	private TextField emailBox;
	public static String name;
	public static String email;
	public static String gender;

	ToggleGroup genderGroup = new ToggleGroup();

	public void start() throws IOException {
		AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("InfoComment.fxml"));
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
		Platform.runLater(() -> nameBox.requestFocus());
		try {
			if (email.length() != 0)
				emailBox.setText(email);
		} catch (Exception e) {
		}
		male.setToggleGroup(genderGroup);
		female.setToggleGroup(genderGroup);
		other.setToggleGroup(genderGroup);
		other.setSelected(true);
	}

	@FXML
	void backButton(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SubjectScreen subjects = new SubjectScreen();
		subjects.viewSubjects();
	}

	@FXML
	void nextButton(ActionEvent event) throws IOException {
		try {
			name = nameBox.getText();
			email = emailBox.getText();
			name.substring(0, 1);
			if (male.isSelected())
				gender = "He";
			if (female.isSelected())
				gender = "She";
			if (other.isSelected())
				gender = "They";
			CreateComment comment = new CreateComment();
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			comment.start(new ArrayList<String>());
		} catch (NumberFormatException a) {
			AlertBox.str = "Invalid grade.";
			alert.alert();
		} catch (Exception e) {
			AlertBox.str = "Invalid information";
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
