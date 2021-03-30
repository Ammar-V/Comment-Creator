package application;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ConfirmBox {
	static ActionEvent delEvent;
	@FXML
	public Button confirm;
	@FXML
	public Button deny;
	public static boolean choice;
	public static int src = 0;
	Stage window = new Stage();

	public void confirm(double xCord, double yCord, double xDim, double yDim) throws IOException {

		window.initStyle(StageStyle.TRANSPARENT);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setResizable(false);

		AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("ConfirmBox.fxml"));
		Scene scene = new Scene(root, 300, 150);

		double x = GetCenter.getX(xCord, xCord + xDim);
		double y = GetCenter.getY(yCord, yCord + yDim);

		window.setScene(scene);
		window.show();
		window.setX(x - 150);
		window.setY(y - 105);
	}

	@FXML
	public void initialize() {
		Platform.runLater(() -> deny.requestFocus());
	}

	@FXML
	public void conf(ActionEvent event) {
		if (src == 1) {
			CommentBank.confirmNum = 1;
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
			CommentBank del = new CommentBank();
			del.deleteUnit(delEvent);
		} else if (src == 2) {
			ViewComments.confirmNum = 1;
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
			ViewComments del = new ViewComments();
			del.deleteSubject(event);
		}
	}

	@FXML
	public void deny(ActionEvent event) {
		CommentBank.confirmNum = 0;
		ViewComments.confirmNum = 0;
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@FXML
	public void exit(MouseEvent event) {
		CommentBank.confirmNum = 0;
		ViewComments.confirmNum = 0;
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
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
