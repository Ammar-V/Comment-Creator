package application;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewComments extends LoginScreen {
	LoadComments load = new LoadComments();
	@FXML
	private ChoiceBox<String> selectSubject;

	@FXML
	private Button back;
	@FXML
	private TextField createBox;
	@FXML
	private Button createButton;

	@FXML
	private Button next;
	public SimpleStringProperty selectedUnit;
	public static String subject;
	static Stage win;

	public void showComment() throws IOException {
		win = window;
		AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("CommentsFirst.fxml"));
		double x = GetCenter.getX(window.getX(), window.getX() + window.getWidth());
		double y = GetCenter.getY(window.getY(), window.getY() + window.getHeight());
		Scene scene = new Scene(root, 700, 530);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		window.setScene(scene);
		window.setX(x - 350);
		window.setY(y - 265);
	}

	@FXML
	void backButton(ActionEvent event) throws IOException {
		MenuScreen menu = new MenuScreen();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		menu.startMenu(stage);
	}

	static ObservableList<String> subjectsList = FXCollections.observableArrayList();

	@FXML
	public void initialize() {
		subjectsList.clear();
		for (int j = 0; j < LoadComments.subjects.size(); j++) {
			String subject = LoadComments.subjects.get(j);
			boolean exists = false;
			for (int i = 0; i < subjectsList.size(); i++) {
				if (subjectsList.get(i).equals(subject))
					exists = true;
			}
			if (exists == false)
				subjectsList.add(subject);

		}
		selectSubject.setItems(subjectsList);
		selectSubject.getSelectionModel().selectedItemProperty()
				.addListener((v, oldValue, newValue) -> subject = newValue);
	}

	String newSubject;

	@FXML
	public void createSubject(ActionEvent event) {
		Exception e = new Exception();
		newSubject = createBox.getText();
		try {
			newSubject.substring(0, 1);
			for (String subject : subjectsList) {
				if (subject.equals(newSubject)) {
					throw e;
				}
			}
			subjectsList.add(newSubject);
			selectSubject.getSelectionModel().select(newSubject);
			createBox.clear();
			Stage window = new Stage();
			window.setResizable(false);
			Label msg = new Label("Created!");
			Button button = new Button("OK");
			button.setOnAction(b -> window.close());
			button.setMinWidth(50);
			VBox layout = new VBox(20);
			layout.getChildren().addAll(msg, button);
			layout.setAlignment(Pos.CENTER);
			Scene scene = new Scene(layout, 200, 100);
			window.setScene(scene);
			window.showAndWait();
			LoadComments.subjects.add(newSubject);
			LoadComments.units.add("");
			LoadComments.comments.add("");
			LoadComments.ratings.add(null);
		} catch (Exception a) {
			AlertBox.str = "Invalid.";
			AlertBox alert = new AlertBox();
			alert.alert();
		}
	}

	public static int confirmNum = 0;

	@FXML
	public void deleteSubject(ActionEvent event) {

		if (confirmNum == 0) {
			try {
				subject.substring(0, 1);
				ConfirmBox conf = new ConfirmBox();
				ConfirmBox.src = 2;
				conf.confirm(win.getX(), win.getY(), win.getWidth(), win.getHeight());
			} catch (Exception e) {
			}
		} else {
			for (int j = 0; j < LoadComments.subjects.size(); j++) {
				if (LoadComments.subjects.get(j).equals(subject)) {
					LoadComments.subjects.remove(j);
					LoadComments.units.remove(j);
					LoadComments.comments.remove(j);
					LoadComments.ratings.remove(j);
				}
			}
			subjectsList.remove(subject);
			subject = "";
			confirmNum = 0;
			ConfirmBox.src = 0;
		}
	}

	@FXML
	public void nextButton(ActionEvent event) throws IOException {
		try {
			subject.substring(0, 1);
			CommentBank.selectedSubject = subject;
			openBank(event);
		} catch (Exception e) {
			AlertBox.str = "Select a subject.";
			AlertBox alert = new AlertBox();
			alert.alert();
		}
	}

	void openBank(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		CommentBank open = new CommentBank();
		try {
			open.openBank();
		} catch (IOException e) {
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