package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginScreen extends Application {
	public static Stage window = new Stage();
	LoadComments load = new LoadComments();

	@Override
	public void start(Stage primaryStage) throws IOException {
		window = primaryStage;
		window.setResizable(false);
		window.initStyle(StageStyle.TRANSPARENT);
		AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
		Scene scene = new Scene(root, 500, 530);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene);
		window.show();
		// Changing icon for window
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		window.getIcons().add(new Image(loader.getResourceAsStream("title.png")));

		/*
		 * Use this if loading feature is needed while closing the application from the
		 * taskbar. window.setOnCloseRequest(e -> { load.upload(); System.exit(0); });
		 */
	}

	public static void main(String[] args) {
		launch(args);
	}
}