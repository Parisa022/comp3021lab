package base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import base.Folder;
import base.Note;
import base.NoteBook;
import base.TextNote;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;


/**
 *
 * NoteBook GUI with JAVAFX
 *
 * COMP 3021
 *
 *
 * @author valerio
 *
 */
public class NoteBookWindow extends Application {

	/**
	 * TextArea containing the note
	 */
	final TextArea textAreaNote = new TextArea("");
	/**
	 * list view showing the titles of the current folder
	 */
	final ListView<String> titleslistView = new ListView<String>();
	/**
	 *
	 * Combobox for selecting the folder
	 *
	 */
	final ComboBox<String> foldersComboBox = new ComboBox<String>();
	/**
	 * This is our Notebook object
	 */
	NoteBook noteBook = null;
	/**
	 * current folder selected by the user
	 */
	String currentFolder = "";
	/**
	 * current search string
	 */
	String currentSearch = "";
	
	String currentNote = "";
	
	File currentFile;
	
	Stage stage;

	public static void main(String[] args) {
		launch(NoteBookWindow.class, args);
	}

	@Override
	public void start(Stage stage) {
		loadNoteBook();
		this.stage = stage;
		// Use a border pane as the root for scene
		BorderPane border = new BorderPane();
		// add top, left and center
		border.setTop(addHBox());
		border.setLeft(addVBox());
		border.setCenter(addGridPane());

		Scene scene = new Scene(border);
		stage.setScene(scene);
		stage.setTitle("NoteBook COMP 3021");
		stage.show();
	}

	/**
	 * This create the top section
	 *
	 * @return
	 */
	private HBox addHBox() {

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10); // Gap between nodes

		Button buttonLoad = new Button("Load");
		buttonLoad.setPrefSize(100, 20);
		buttonLoad.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Please Choose An File Which Contains a NoteBook Object!");
				
				FileChooser.ExtensionFilter extFilter  = new FileChooser.ExtensionFilter("Serialized Object file", "*.ser");
				fileChooser.getExtensionFilters().add(extFilter);
				
				File file = fileChooser.showOpenDialog(stage);
				System.out.println(file.getAbsolutePath());
				if (file != null) {
					// To-do
					loadNoteBook(file);
					foldersComboBox.getItems().clear();
					for (Folder folder : noteBook.getFolders() ) {
						foldersComboBox.getItems().add(folder.getName());
					}
					updateListView();
				}
			}
		});
		
		Button buttonSave = new Button("Save");
		buttonSave.setPrefSize(100, 20);
		buttonSave.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				File file = getcurrentFilePath();
				if (noteBook.save(file.getAbsolutePath())) {
					alertmsg(AlertType.INFORMATION, "Successfully saved", "Your file has been saved to file "+file.getName());
				}
			}
		});
		

		Label label = new Label("Search :");

		TextField textSearch = new TextField();

		Button buttonSearch = new Button("Search");
		buttonSearch.setPrefSize(100, 20);
		buttonSearch.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				currentSearch = textSearch.getText();
	              textAreaNote.setText("");
	              updateListView();
				}
		});

		Button buttonRemove = new Button("Clear Search");
		buttonRemove.setPrefSize(100, 20);
		buttonRemove.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				currentSearch = "";
				textSearch.setText("");
				textAreaNote.setText("");

				ArrayList<String> titleList = new ArrayList<String>();
				if(currentFolder != null){
					for(Folder f: noteBook.getFolders())
						if(f.getName().equals(currentFolder)){
							for(Note n : f.getNotes())
								titleList.add(n.getTitle());
							ObservableList<String> combox2 = FXCollections.observableArrayList(titleList);
							titleslistView.setItems(combox2);
						}

				}
				
				textAreaNote.setText("");
			}
		});

		hbox.getChildren().addAll(buttonLoad, buttonSave, label, textSearch, buttonSearch,buttonRemove);

		return hbox;
	}
	
	
	private void alertmsg(AlertType type, String title, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.showAndWait().ifPresent(rs -> {
			if (rs==ButtonType.OK) {
				System.out.println("Pressed OK.");
			}
		});
	}

	/**
	 * this create the section on the left
	 *
	 * @return
	 */
	private VBox addVBox() {

		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10)); // Set all sides to 10
		vbox.setSpacing(8); // Gap between nodes

		// TODO: This line is a fake folder list. We should display the folders in noteBook variable! Replace this with your implementation
		for(Folder f :noteBook.getFolders())
		foldersComboBox.getItems().addAll(f.getName());

		foldersComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				currentFolder = t1.toString();
				// this contains the name of the folder selected
				// TODO update listview
				updateListView();

			}

		});

		foldersComboBox.setValue("-----");
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(10)); 
		hbox.setSpacing(8); 
		
		Button addFolder = new Button("Add a Folder");
		addFolder.setPrefSize(100, 20);
		addFolder.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				TextInputDialog dialog = new TextInputDialog("Add a Folder");
				dialog.setTitle("Input");
				dialog.setHeaderText("Add a new folder for your notebook:");
				dialog.setContentText("Please enter the name you want to create:");
				
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()) {
					String name = result.get();
					if (name.equals("")) {
						alertmsg(AlertType.WARNING, "Warning", "Please input an valid folder name");
					} else {
						boolean exists = false;
						for (Folder folder: noteBook.getFolders()) {
							if (folder.getName().equals(name)) {
								exists = true;
								break;
							}
						}
						if (exists) {
							alertmsg(AlertType.WARNING,"Warning","Please input an valid folder name");
						} else {
							noteBook.addFolder(name);
							foldersComboBox.getItems().add(name);
						}
					}
				}
			}
		});
		hbox.getChildren().add(foldersComboBox);
		hbox.getChildren().add(addFolder);

		
		Button addNote = new Button("Add a Note");
		addNote.setPrefSize(100, 20);
		addNote.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (currentFolder.equals("-----")) {
					alertmsg(AlertType.WARNING, "Warning", "Please choose a folder first!");
				} else {
					TextInputDialog dialog = new TextInputDialog("Add a Note");
					dialog.setTitle("Input");
					dialog.setHeaderText("Add a new note to current folder");
					dialog.setContentText("Please enter the name of your note:");
					
					Optional<String> result = dialog.showAndWait();
					if (result.isPresent()) {
						noteBook.createTextNote(currentFolder, result.get());
						alertmsg(AlertType.INFORMATION, "Successful!", "Insert note "+result.get()+" to folder "+currentFolder+" successfully!");
						updateListView();
					}
				}
			}
		});
		
		titleslistView.setPrefHeight(100);

		titleslistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				if (t1 == null)
					return;
				String title = t1.toString();
				// This is the selected title
				// TODO load the content of the selected note in
				// textAreNote
				currentNote = title;
				String content = "";
				ArrayList<Folder> fn = noteBook.getFolders();
				for (Folder f: fn) {
					if (currentFolder.equals(f.getName())) {
						for (Note n: f.getNotes()) {
							if (title.equals(n.getTitle())) {
								if (n instanceof TextNote) {
									content = ((TextNote) n).content;
									break;
								}
							}
						}
					}
				}
				textAreaNote.setText(content);
			}
		});
		vbox.getChildren().add(new Label("Choose folder: "));
		vbox.getChildren().add(hbox);
		vbox.getChildren().add(new Label("Choose note title"));
		vbox.getChildren().add(titleslistView);
		vbox.getChildren().add(addNote);

		return vbox;
	}

	private void updateListView() {
		ArrayList<String> list = new ArrayList<String>();

		// TODO populate the list object with all the TextNote titles of the
		// currentFolder
		for (Folder folder : noteBook.getFolders()) {
			if (currentSearch == "") {
				if (folder.getName().equals(currentFolder)) {
					for (Note note : folder.getNotes()) {
						list.add(note.getTitle());
					}
				}
			}else {
				List<Note> notes = new ArrayList<Note>();
				notes = folder.searchNotes(currentSearch);
				if (notes != null) {
					for (Note note : notes) {
						list.add(note.getTitle());
					}
				}
			}
		}

		ObservableList<String> combox2 = FXCollections.observableArrayList(list);
		titleslistView.setItems(combox2);
		textAreaNote.setText("");
	}

	/*
	 * Creates a grid for the center region with four columns and three rows
	 */
	private GridPane addGridPane() {
		
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(10)); 
		hbox.setSpacing(8);
		
		Button SaveNote = new Button("Save Note");
		SaveNote.setPrefSize(100, 20);
		SaveNote.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (currentNote == null || currentNote.equals("")) {
					alertmsg(AlertType.WARNING, "Warning", "Please select a folder and a note");
				} else {
					for (Folder f: noteBook.getFolders()) {
						for (Note n: f.getNotes()) {
							if (n.getTitle().equals(currentNote)) {
								if (n instanceof TextNote)
									((TextNote) n).setContent(textAreaNote.getText());
							}
						}
					}
				}
			}
		});
		
		Button deleteNote = new Button("Delete Note");
		deleteNote.setPrefSize(100, 20);
		deleteNote.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (currentNote == null || currentNote.equals("")) {
					alertmsg(AlertType.WARNING, "Warning", "Please select a folder and a note");
				} else {
					for (Folder f : noteBook.getFolders()) {
						if (f.getName().equals(currentFolder)) {
							for (Note n: f.getNotes()) {
								if (n.getTitle().equals(currentNote)) {
									if (f.removeNotes(currentNote))
										alertmsg(AlertType.INFORMATION, "Succeed!", "Your note has been successfully removed");
									updateListView();
								}
							}
						}
					}
				}
			}
		});
		ImageView saveView = new ImageView(new Image(new File("save.png").toURI().toString()));
		saveView.setFitHeight(18);
		saveView.setFitWidth(18);
		saveView.setPreserveRatio(true);
		
		ImageView deleteView = new ImageView(new Image(new File("delete.png").toURI().toString()));
		deleteView.setFitHeight(18);
		deleteView.setFitWidth(18);
		deleteView.setPreserveRatio(true);
		
		hbox.getChildren().add(saveView);
		hbox.getChildren().add(SaveNote);
		hbox.getChildren().add(deleteView);
		hbox.getChildren().add(deleteNote);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		textAreaNote.setEditable(true);
		textAreaNote.setMaxSize(450, 400);
		textAreaNote.setWrapText(true);
		textAreaNote.setPrefWidth(450);
		textAreaNote.setPrefHeight(400);
		// 0 0 is the position in the grid
		grid.add(hbox, 0, 0);
		grid.add(textAreaNote, 0, 1);

		return grid;
	}
	
	private void loadNoteBook(File file) {
		// TODO Auto-generated method stub
		this.currentFile = file;
		this.noteBook = new NoteBook(file.getAbsolutePath());
	}
	
	private File getcurrentFilePath() {
		return this.currentFile;
	}

	private void loadNoteBook() {
		NoteBook nb = new NoteBook();
		nb.createTextNote("COMP3021", "COMP3021 syllabus", "Be able to implement object-oriented concepts in Java.");
		nb.createTextNote("COMP3021", "course information",
				"Introduction to Java Programming. Fundamentals include language syntax, object-oriented programming, inheritance, interface, polymorphism, exception handling, multithreading and lambdas.");
		nb.createTextNote("COMP3021", "Lab requirement",
				"Each lab has 2 credits, 1 for attendence and the other is based the completeness of your lab.");

		nb.createTextNote("Books", "The Throwback Special: A Novel",
				"Here is the absorbing story of twenty-two men who gather every fall to painstakingly reenact what ESPN called ??????he most shocking play in NFL history?????? and the Washington Redskins dubbed the ??????hrowback Special??????: the November 1985 play in which the Redskins?????? Joe Theismann had his leg horribly broken by Lawrence Taylor of the New York Giants live on Monday Night Football. With wit and great empathy, Chris Bachelder introduces us to Charles, a psychologist whose expertise is in high demand; George, a garrulous public librarian; Fat Michael, envied and despised by the others for being exquisitely fit; Jeff, a recently divorced man who has become a theorist of marriage; and many more. Over the course of a weekend, the men reveal their secret hopes, fears, and passions as they choose roles, spend a long night of the soul preparing for the play, and finally enact their bizarre ritual for what may be the last time. Along the way, mishaps, misunderstandings, and grievances pile up, and the comforting traditions holding the group together threaten to give way. The Throwback Special is a moving and comic tale filled with pitch-perfect observations about manhood, marriage, middle age, and the rituals we all enact as part of being alive.");
		nb.createTextNote("Books", "Another Brooklyn: A Novel",
				"The acclaimed New York Times bestselling and National Book Award??????inning author of Brown Girl Dreaming delivers her first adult novel in twenty years. Running into a long-ago friend sets memory from the 1970s in motion for August, transporting her to a time and a place where friendship was everything??????ntil it wasn??????. For August and her girls, sharing confidences as they ambled through neighborhood streets, Brooklyn was a place where they believed that they were beautiful, talented, brilliant?????? part of a future that belonged to them. But beneath the hopeful veneer, there was another Brooklyn, a dangerous place where grown men reached for innocent girls in dark hallways, where ghosts haunted the night, where mothers disappeared. A world where madness was just a sunset away and fathers found hope in religion. Like Louise Meriwether?????? Daddy Was a Number Runner and Dorothy Allison?????? Bastard Out of Carolina, Jacqueline Woodson?????? Another Brooklyn heartbreakingly illuminates the formative time when childhood gives way to adulthood??????he promise and peril of growing up??????nd exquisitely renders a powerful, indelible, and fleeting friendship that united four young lives.");

		nb.createTextNote("Holiday", "Vietnam",
				"What I should Bring? When I should go? Ask Romina if she wants to come");
		nb.createTextNote("Holiday", "Los Angeles", "Peter said he wants to go next Agugust");
		nb.createTextNote("Holiday", "Christmas", "Possible destinations : Home, New York or Rome");
		noteBook = nb;

	}

}