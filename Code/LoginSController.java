package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginSController {
	LoadComments load = new LoadComments();
	MenuScreen menu = new MenuScreen();
	@FXML
	public TextField userID;

	@FXML
	public PasswordField userPass;

	@FXML
	public Button logIn;

	// Verify Login
	@FXML
	public void checkAcc(ActionEvent event) throws IOException {

		String userName = userID.getText();
		String pass = userPass.getText();
		CheckAcc v = new CheckAcc();
		boolean check = v.verify(userName, pass);

		if (check) {
			LoadComments load = new LoadComments();
			load.load();
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			menu.startMenu(stage);
		} else {
			AlertBox alert = new AlertBox();
			AlertBox.str = "Invalid Credentials";
			alert.alert();
		}

	}

	public static String msg;

	@FXML
	public void exit(MouseEvent event) {
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
