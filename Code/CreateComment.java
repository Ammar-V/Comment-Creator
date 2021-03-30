package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CreateComment extends LoginScreen {
	LoadComments load = new LoadComments();
	AlertBox alert = new AlertBox();
	Stack<String> undoList = new Stack<>();
	@FXML
	private TableView<GetComment> table = new TableView<>();
	@FXML
	private TableColumn<GetComment, String> commentCol = new TableColumn<GetComment, String>("Comments");
	@FXML
	private TextField ratingBox = new TextField();
	@FXML
	private CheckBox specificCom = new CheckBox();
	@FXML
	private Label title;
	@FXML
	private ChoiceBox<String> unitMenu = new ChoiceBox<>();
	public static String selectedSubject;
	public static String selectedUnit;
	public static int rating;
	public static ObservableList<GetComment> comments = FXCollections.observableArrayList();
	static Stage win;

	public void start(ArrayList<String> ogCom) throws IOException {
		comments.clear();
		win = window;
		try {
			if (ogCom.size() != 0) {
				for (String com : ogCom)
					comments.add(new GetComment(shorten(com)));
			}
		} catch (Exception e) {
		}

		AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("CreateComment.fxml"));
		double x = GetCenter.getX(window.getX(), window.getX() + window.getWidth());
		double y = GetCenter.getY(window.getY(), window.getY() + window.getHeight());
		Scene scene = new Scene(root, 850, 648);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene);
		window.setX(x - 425);
		window.setY(y - 324);
		root.requestFocus();
	}

	ObservableList<String> unitsList = FXCollections.observableArrayList();

	@FXML
	void initialize() {
		selectedSubject = SubjectScreen.comSubject;
		try {
			title.setText(SubjectScreen.comSubject);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int j = 0; j < LoadComments.subjects.size(); j++) {
			if (LoadComments.subjects.get(j).equals(selectedSubject)) {
				String unit = LoadComments.units.get(j);
				boolean exists = false;
				for (int i = 0; i < LoadComments.units.size(); i++) {

					try {
						if (unitsList.get(i).equals(unit))
							exists = true;
					} catch (Exception a) {
						continue;
					}

				}
				if (exists == false)
					unitsList.add(unit);
			}
		}
		unitMenu.setItems(unitsList);

		table.getColumns().add(commentCol);
		table.setPlaceholder(new Label("Add Comments"));
		commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));
		commentCol.setMinWidth(483);
		commentCol.setMaxWidth(483);
		commentCol.setSortable(false);
		setColumn();
	}

	@FXML
	void deleteComment(ActionEvent event) throws IOException {
		try {
			ObservableList<GetComment> selectedItems = table.getSelectionModel().getSelectedItems();
			GetComment selected = table.getSelectionModel().getSelectedItem();
			undoList.add(selected.getComment().replaceAll("\n", ""));
			// Remove the items from the graphical table
			selectedItems.forEach(comments::remove);
			setColumn();
		} catch (Exception e) {
			// If the user tries to delete a comment without selecting something,
			// an error will be caught and nothing will be done.
		}
	}

	@FXML
	public void addComment(ActionEvent event) throws IOException {
		boolean specific = specificCom.isSelected();
		try {
			unitMenu.getSelectionModel().getSelectedItem().substring(0, 1);
			selectedUnit = unitMenu.getSelectionModel().getSelectedItem();
			rating = Integer.parseInt(ratingBox.getText());
			if (rating < 0 || rating > 5)
				throw new NumberFormatException();
			specificCom.setSelected(false);
			unitMenu.getSelectionModel().clearSelection();
			ratingBox.clear();
			if (specific == false)
				randomize();
			else {
				SpecificCom start = new SpecificCom();
				start.start(win.getX(), win.getY());

			}

		} catch (NumberFormatException e) {
			AlertBox.str = "Enter a valid rating.";
			alert.alert();
		} catch (Exception a) {
			AlertBox.str = "Select a unit.";
			alert.alert();
		}
	}

	public void setColumn() {
		table.setItems(comments);
	}

	@FXML
	public void undoDelete(ActionEvent event) {
		try {
			comments.add(new GetComment(shorten(undoList.pop())));
			setColumn();
		} catch (Exception e) {
		}
	}

	@FXML
	public void moveUp() {
		try {
			GetComment com = table.getSelectionModel().getSelectedItem();
			Collections.swap(comments, comments.indexOf(com), comments.indexOf(com) - 1);
			setColumn();
		} catch (Exception e) {
		}
	}

	@FXML
	public void moveDown() {
		try {
			GetComment com = table.getSelectionModel().getSelectedItem();
			Collections.swap(comments, comments.indexOf(com), comments.indexOf(com) + 1);
			setColumn();
		} catch (Exception e) {
		}
	}

	public void randomize() {
		try {
			ObservableList<String> random = FXCollections.observableArrayList();
			for (int j = 0; j < LoadComments.units.size(); j++) {
				try {
					if (LoadComments.units.get(j).equals(selectedUnit)) {
						if (LoadComments.ratings.get(j) == rating) {
							random.add(LoadComments.comments.get(j));
						}
					}
				} catch (Exception e) {
					continue;
				}
			}
			// To check if the list is empty
			random.get(0);
			FXCollections.shuffle(random);
			Set<String> randomHash = new HashSet<>();
			// Add the contents (Strings) of the table to a hash set
			// This will make sure that no repeat comments will be added
			for (GetComment com : comments) {
				randomHash.add(com.getComment());
			}
			// Check if the comment already exists in the table
			for (int j = 0; j < random.size(); j++) {
				if (randomHash.add(shorten(random.get(j)))) {
					comments.add(new GetComment(shorten(random.get(j))));
					break;
				}
			}
			setColumn();
		} catch (Exception e) {
			AlertBox.str = "No comments exist for this Unit / Module.";
			alert.alert();
		}
	}

	public void addSpecificComment(String comment) {
		comments.add(new GetComment(shorten(comment)));
		setColumn();
	}

	// Recursion is used to split a string into multiple lines for easier reading in
	// table cell
	public String shorten(String str) {
		if (str.length() <= 75)
			return str;
		else
			return str.substring(0, 75) + "\n" + shorten(str.substring(75, str.length()));
	}

	@FXML
	void backButton(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		InfoComment info = new InfoComment();
		info.start(stage);
	}

	@FXML
	void nextButton(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Print p = new Print();
		p.start();
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
