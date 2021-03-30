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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SpecificCom {
	AlertBox alert = new AlertBox();
	LoadComments load = new LoadComments();
	CreateComment com = new CreateComment();
	String selectedUnit;
	String selectedSubject;
	int rating;

	@FXML
	private TableView<GetComment> table = new TableView<>();
	@FXML
	private TableColumn<GetComment, String> commentCol = new TableColumn<GetComment, String>("Comments");

	public SpecificCom() {
		this.selectedSubject = CreateComment.selectedSubject;
		this.selectedUnit = CreateComment.selectedUnit;
		this.rating = CreateComment.rating;
	}

	Stage window = new Stage();
	static ObservableList<GetComment> data = FXCollections.observableArrayList();

	public void start(double xCord, double yCord) throws IOException {

		window.setResizable(false);
		window.initStyle(StageStyle.UNDECORATED);
		AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("SpecificCom.fxml"));
		data.clear();
		for (int j = 0; j < LoadComments.subjects.size(); j++) {
			try {
				if (LoadComments.subjects.get(j).equals(selectedSubject)) {
					if (LoadComments.units.get(j).equals(selectedUnit)) {
						if (LoadComments.ratings.get(j) == rating) {
							data.add(new GetComment(shorten(LoadComments.comments.get(j))));
						}
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
		if (data.size() == 0) {
			AlertBox.str = "No comments exist for this Unit / Module.";
			alert.alert();
		} else {
			Scene scene = new Scene(root, 700, 430);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			// Getting the center of parent screen
			double x = GetCenter.getX(xCord, xCord + 850);
			double y = GetCenter.getY(yCord, yCord + 648);

			window.setScene(scene);
			window.show();
			window.setX(x - 350);
			window.setY(y - 215);
		}

	}

	@FXML
	public void initialize() {
		commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));
		commentCol.setMinWidth(610);
		commentCol.setMaxWidth(610);
		commentCol.setSortable(false);
		table.getColumns().add(commentCol);
		table.setItems(data);

	}

	@FXML
	void done(ActionEvent event) {
		try {
			GetComment row = table.getSelectionModel().getSelectedItem();
			String comment = row.getComment();
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
			com.addSpecificComment(comment.replaceAll("\n", ""));
		} catch (Exception e) {
			AlertBox.str = "Select a comment.";
			alert.alert();
		}
	}

	// Recursion is used to split a string into multiple lines for easier reading in
	// table cell
	public String shorten(String str) {
		if (str.length() <= 95)
			return str;
		else
			return str.substring(0, 95) + "\n" + shorten(str.substring(95, str.length()));
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
}
