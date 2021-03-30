package application;

import java.io.IOException;
import java.util.Stack;

import javafx.application.Platform;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CommentBank extends LoginScreen implements CommentMethods {
	// Object to call the class that stores the data into .txt file
	LoadComments load = new LoadComments();

	public static String selectedSubject;
	public static String selectedUnit = null;

	// Things required for adding new comments and using undo list.
	public static String newComment;
	public static int newRating;
	public static Stack<String> undoList = new Stack<>();

	@FXML
	private Button addUnit;
	@FXML
	private ChoiceBox<String> unitMenu = new ChoiceBox<>();

	// Main table
	@FXML
	private TableColumn<GetComCol, String> colComment = new TableColumn<GetComCol, String>("Comments");
	@FXML
	private TableColumn<GetComCol, Integer> colRating = new TableColumn<GetComCol, Integer>("Ratings");
	@FXML
	private TableView<GetComCol> table = new TableView<>();

	@FXML
	private TextField createUnit = new TextField();
	@FXML
	private Button deleteComment = new Button();
	String unitInput;
	AlertBox alert = new AlertBox();
	static Stage win;

	static ObservableList<String> unitsList = FXCollections.observableArrayList();

	@FXML
	void backButton(ActionEvent event) throws IOException {
		ViewComments view = new ViewComments();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		view.showComment();
	}

	public void openBank() throws IOException {
		win = window;

		AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("CommentsBank.fxml"));
		double x = GetCenter.getX(window.getX(), window.getX() + window.getWidth());
		double y = GetCenter.getY(window.getY(), window.getY() + window.getHeight());
		Scene scene = new Scene(root, 1000, 646);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		window.setScene(scene);
		window.setX(x - 500);
		window.setY(y - 323);
	}

	@SuppressWarnings("unchecked")
	@FXML
	public void initialize() {
		Platform.runLater(() -> unitMenu.requestFocus());
		/*
		 * Clear all the static lists to make sure when the user is using back and
		 * returning to the screen, the data doesn't overlap
		 */
		undoList.clear();
		unitsList.clear();

		// Importing all the units in the selected subject
		for (int j = 0; j < LoadComments.subjects.size(); j++) {
			if (LoadComments.subjects.get(j).equals(selectedSubject)) {
				String unit = LoadComments.units.get(j);
				boolean exists = false;
				for (String item : unitsList) {
					// Remove repetitions of units
					try {
						if (item.equals(unit))
							exists = true;
					} // Sometimes the units are null
					catch (Exception a) {
						continue;
					}
				}
				if (exists == false)
					unitsList.add(unit);
			}
		}
		// To remove nulls from the list
		try {
			for (String item : unitsList) {
				if (item.length() == 0) {
					unitsList.remove(item);
				}
			}
		} catch (Exception e1) {
		}
		// Setting the ChoiceBox menu to select units.
		unitMenu.setItems(unitsList);
		unitMenu.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			selectedUnit = newValue;
			undoList.clear();
			setCommentColumns();
		});

		// Setting up the table columns
		table.getColumns().addAll(colComment, colRating);
		table.setPlaceholder(new Label("Select Unit / Module"));
		colComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
		colComment.setMinWidth(582);
		colRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
		colRating.setMinWidth(105);
		colRating.setMaxWidth(105);
		colRating.setStyle("-fx-alignment: CENTER");

		// Interacting with the unit menu
		deleteComment.setOnAction(e -> delComment());
		addUnit.setOnAction(e -> addUnit());
	}

	// Using the word "refresh" for more readable code
	public void refresh() {
		setCommentColumns();
	}

	// Data lists behind the main table
	ObservableList<String> comments = FXCollections.observableArrayList();
	ObservableList<Integer> ratings = FXCollections.observableArrayList();

	// Adding data to the columns of the main table
	public void setCommentColumns() {
		// Clear lists to make sure that when the user switches units, the table is
		// cleared
		comments.clear();
		ratings.clear();
		table.getItems().clear();
		/*
		 * If the list is empty, then the table will say this. However, if the list is
		 * filled later this will not be seen by the user
		 */
		table.setPlaceholder(new Label("No Comments"));

		// Populating the data lists
		for (int j = 0; j < LoadComments.units.size(); j++) {
			String unit = LoadComments.units.get(j);
			try {
				if (unit.equals(selectedUnit)) {
					comments.add(shorten(LoadComments.comments.get(j)));
					ratings.add(LoadComments.ratings.get(j));
				}
			} catch (Exception e) {
				continue;
			}

		}
		table.setItems(getData());
	}

	// The data list behind the main table which include the two columns
	static ObservableList<GetComCol> data = FXCollections.observableArrayList();

	// Populating the data list with the two column data lists
	public ObservableList<GetComCol> getData() {
		data.clear();
		for (int j = 0; j < comments.size(); j++) {
			try {
				data.add(new GetComCol(comments.get(j), ratings.get(j)));
			} catch (Exception e) {
				continue;
			}
		}
		return data;
	}

	// Adding a new Unit to the subject
	@FXML
	public void addUnit() {
		unitInput = createUnit.getText();
		try {
			// These statements are used to catch any blank input
			unitInput.substring(0, 1);

			// If the subject already exists, the user cannot add another one
			boolean exists = false;
			for (String unit : unitsList) {
				// To handle the error given when the unitList is empty (When a new subject is
				// created)
				try {
					if (unit.equalsIgnoreCase(unitInput))
						exists = true;
				} catch (Exception e) {
					exists = false;
				}
			}
			if (exists) {
				AlertBox.str = "Already exists.";
				alert.alert();
			} else {
				LoadComments.subjects.add(selectedSubject);
				LoadComments.units.add(unitInput);
				LoadComments.comments.add("");
				LoadComments.ratings.add(null);
				unitsList.add(unitInput);
				createUnit.clear();

				// Small window to tell the user that the Unit has been added
				Stage window = new Stage();
				window.setResizable(false);
				Label msg = new Label("Created!");
				Button button = new Button("OK");
				button.setOnAction(e -> window.close());
				button.setMinWidth(50);
				VBox layout = new VBox(20);
				layout.getChildren().addAll(msg, button);
				layout.setAlignment(Pos.CENTER);
				Scene scene = new Scene(layout, 200, 100);
				window.setScene(scene);
				window.showAndWait();
				// Change screen to the created unit for easier use
				unitMenu.getSelectionModel().select(unitInput);
			}
			createUnit.setPromptText("Create Unit / Module");
		} catch (Exception e) {
			AlertBox.str = "Enter a valid Unit / Module name.";
			alert.alert();
		}
	}

	// This variable is used to communicate with the ConfirmBox class.
	public static int confirmNum = 0;

	@FXML
	public void deleteUnit(ActionEvent event) {
		try {
			selectedUnit.substring(0, 1);
			// Using the variable to determine the correct action related to user decision
			if (confirmNum == 0) {
				if (selectedUnit != null) {
					ConfirmBox confirm = new ConfirmBox();
					ConfirmBox.delEvent = event;
					ConfirmBox.src = 1;
					confirm.confirm(win.getX(), win.getY(), win.getWidth(), win.getHeight());
				}
			} else {
				for (int j = 0; j < LoadComments.units.size(); j++) {
					try {
						if (LoadComments.units.get(j).equals(selectedUnit)) {
							LoadComments.subjects.remove(j);
							LoadComments.units.remove(j);
							LoadComments.comments.remove(j);
							LoadComments.ratings.remove(j);
							/*
							 * This is EXTREMELY IMPORTANT. If a unit occupied element 0 and 1, and 0 was
							 * removed due to .remove(), then element 1 would move down to element 0 and
							 * then would be overlooked by the for loop. Setting the variable to -1 makes
							 * sure that for loop is looking through the unit list from the start every
							 * time.
							 */
							j = -1;
						}
					} catch (Exception e) {
						continue;
					}
				}
				unitsList.remove(selectedUnit);

				// This section is used to determine if the deleted unit is the last unit in the
				// subject
				// If it is, then populate the main data arrays with the subject name
				boolean exists = false;
				for (String subject : LoadComments.subjects)
					if (subject.equals(selectedSubject))
						exists = true;
				if (exists == false) {
					LoadComments.subjects.add(selectedSubject);
					LoadComments.units.add((String) null);
					LoadComments.comments.add(null);
					LoadComments.ratings.add(null);
				}

				// Resetting variables for the ConfirmBox class
				ConfirmBox.src = 0;
				confirmNum = 0;
				// Refreshing the ChoiceBox menu
				unitMenu.setItems(unitsList);
				// Refreshing the main table
				refresh();
			}
		} catch (Exception e) {
			AlertBox.str = "Select a valid Unit /Module first.";
			alert.alert();
		}

	}

	// Recursion is used to split a string into multiple lines for easier reading in
	// table cell
	public String shorten(String str) {
		if (str.length() <= 90)
			return str;
		else
			return str.substring(0, 90) + "\n" + shorten(str.substring(90, str.length()));
	}

	@SuppressWarnings("unlikely-arg-type")
	public void delComment() {
		try {
			ObservableList<GetComCol> selectedItems = table.getSelectionModel().getSelectedItems();
			GetComCol items = table.getSelectionModel().getSelectedItem();
			// Remove the items from the graphical table
			for (GetComCol item : data)
				if (item.equals(selectedItems))
					data.remove(data.indexOf(item));

			// Need to remove the "\n" that was added to print multiple lines in a table
			// cell
			String comment = items.getComment().replaceAll("\n", "");
			int index = LoadComments.comments.indexOf(comment);
			// Remove the items from the original list
			LoadComments.subjects.remove(index);
			LoadComments.units.remove(index);
			undoList.push(LoadComments.comments.remove(index));
			undoList.push(Integer.toString(LoadComments.ratings.remove(index)));
			refresh();
		} catch (Exception e) {
			// If the user tries to delete a comment without selecting something,
			// an error will be caught and nothing will be done.
		}
	}

	// Using a number variable allows for the calling of the same method from the
	// AddComm class
	public int addCommentFinal = 0;

	@FXML
	public void addComment(ActionEvent event) {
		if (addCommentFinal == 0) {
			try {
				// To make sure that a unit has been selected
				selectedUnit.substring(0, 1);

				AddComm add = new AddComm();
				add.start(null, win.getX(), win.getY());
			} catch (Exception e) {
				AlertBox.str = "Select a valid Unit / Module first.";
				alert.alert();
			}
		} else {

			// Add the new comment to the data lists
			LoadComments.subjects.add(selectedSubject);
			LoadComments.units.add(selectedUnit);
			LoadComments.comments.add(newComment + " ");
			LoadComments.ratings.add(newRating);

			// Add the new comment to the table
			data.add(new GetComCol(newComment + " ", newRating));
			refresh();

			// reset variable
			addCommentFinal = 0;
		}
	}

	@FXML
	public void editComment(ActionEvent event) {
		try {
			String editCom = table.getSelectionModel().getSelectedItem().getComment().replaceAll("\n", "");
			editCom.substring(0, 1);
			AddComm add = new AddComm();
			add.start(editCom, win.getX(), win.getY());

			int index = LoadComments.comments.indexOf(editCom);
			LoadComments.subjects.remove(index);
			LoadComments.units.remove(index);
			undoList.push(LoadComments.comments.remove(index));
			undoList.push(Integer.toString(LoadComments.ratings.remove(index)));
			data.remove(table.getSelectionModel().getSelectedItem());

		} catch (Exception e) {
		}
	}

	@FXML
	public void undoDelete(ActionEvent event) {
		if (!undoList.isEmpty()) {
			newRating = Integer.parseInt(undoList.pop());
			newComment = undoList.pop();
			addCommentFinal = 1;
			addComment(event);
		}
	}

	public void menu(ActionEvent event) throws IOException {
		MenuScreen mEnu = new MenuScreen();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		mEnu.startMenu(stage);
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