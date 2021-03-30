package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox extends LoginScreen {
	@FXML
	public Button confirm;
	@FXML
	public Label message;

	public static String str;

	public void alert() {
		Stage alertBox = new Stage();
		alertBox.initModality(Modality.APPLICATION_MODAL);
		alertBox.setTitle("ERROR");
		alertBox.setResizable(false);
		AnchorPane root = null;
		try {
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("AlertBox.fxml"));
		} catch (IOException e) {
		}
		Scene scene = new Scene(root, 300, 100);
		alertBox.setScene(scene);

		ClassLoader load = Thread.currentThread().getContextClassLoader();
		alertBox.getIcons().add(new Image(load.getResourceAsStream("error.png")));
		double x = GetCenter.getX(window.getX(), window.getX() + window.getWidth());
		double y = GetCenter.getY(window.getY(), window.getY() + window.getHeight());
		alertBox.show();

		alertBox.setX(x - 150);
		alertBox.setY(y - 100);
	}

	@FXML
	private void close(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@FXML
	public void initialize() {
		message.setText(str);
	}

}
