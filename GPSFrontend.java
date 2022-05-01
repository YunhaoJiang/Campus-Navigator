import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Frontend of the application of GPS for the UW Madison Campus. Involves a GUI
 * interface that allows the user to insert new locations as well as navigate
 * from location to location through mouseclicks
 * 
 * @author Danny J
 *
 */
public class GPSFrontend extends Application implements IGPSFrontend {

	public boolean selectMode;
	public boolean insertMode;
	public String newPlaceName;
	public FDGPSBackend backend;
	private ArrayList<Button> selectedButtons;
	public IPlace source;
	public IPlace destination;
	public Scene scene;
	public Group group;

	public GPSFrontend() {
		source = null;
		destination = null;
		selectMode = false;
		insertMode = false;
		newPlaceName = "";
		selectedButtons = new ArrayList<Button>();
	}

	public void setBackend(FDGPSBackend backend) {
		this.backend = backend;
	}

	/**
	 * Creates and returns a list of buttons for each location in the backend
	 */
	@Override
	public List<Button> createButtons() {
		// Creates an empty list of buttons
		ArrayList<Button> buttonList = new ArrayList<Button>();
		// For every place currently in the backend
		for (IPlace p : backend.getAllPlaces()) {
			// Create a yellow button at the x and y coordinate specified by the place
			Button tempButton = new Button();
			tempButton.setId(p.getName().replaceAll("\\s", ""));
			tempButton.setTooltip(new Tooltip(p.getName()));
			tempButton.setMinSize(25, 25);
			tempButton.setLayoutX(p.getX());
			tempButton.setLayoutY(p.getY());
			tempButton.setStyle("-fx-background-radius: 12px;" + "-fx-background-color: yellow;");
			tempButton.setOnAction(e -> {
				buttonClicked(tempButton, p);
			});
			buttonList.add(tempButton);
		}
		return buttonList;
	}

	/**
	 * Returns the string of the path
	 */
	@Override
	public String displayPath() {
		String string = source.getName();
		List<IPlace> path = backend.shortestPathFinder(source, destination);
		for (int i = 1; i < path.size(); i++) {
			string = string + " -> " + path.get(i).getName();
		}
		return string;
	}

	/**
	 * Private method that connects the inputed place to the nearest place(s)
	 * currently in the backend
	 * 
	 * @param place the input that will be connected to the nearest place
	 */
	private void connectToClosest(IPlace place) {
		// Initial threshhold distance
		int threshHold = 50;
		// Creates empty list of places that will be closest to the input
		List<IPlace> closestPlaces = new ArrayList<IPlace>();
		// While the list is empty and the threshold isn't too big
		while (closestPlaces.isEmpty() && threshHold < 3000) {
			// Checks if any places stored inside the backend is closer than the threshhold
			// distance
			for (IPlace p : backend.mapGraph.vertices.keySet()) {
				// Adds it to the list if it is close and not the input place itself
				if (Math.abs(place.compareTo(p)) <= threshHold && !p.equals(place)) {
					closestPlaces.add(p);
				}
			}
			// Increase threshhold for greater search distance if the list is still empty by
			// the start of next loop
			threshHold += 50;
		}
		// If the closestPlace list is not empty
		if (!closestPlaces.isEmpty()) {
			// Connect the input and every place inside that list
			for (IPlace p : closestPlaces) {
				backend.mapGraph.insertEdge(place, p, Math.abs(place.compareTo(p)));
				backend.mapGraph.insertEdge(p, place, Math.abs(place.compareTo(p)));
				System.out.println("Connected: " + p.getName() + " & " + place.getName());
			}
		}
	}

	/**
	 * Method that runs when a location button is clicked and navigation mode is on
	 */
	@Override
	public void buttonClicked(Button button, IPlace place) {
		if (this.selectMode) {
			// Creates a button that says "Start Navigation
			Button submitButton = new Button("Start Navigation");
			// Adjusts the fonts
			submitButton.setMinSize(25, 40);
			submitButton.setFont(new Font("Arial", 15));
			this.selectedButtons.add(button);
			// If the current number of selected buttons is 1
			if (selectedButtons.size() == 1) {
				// Store this as the source place
				this.source = place;
				// Sets the layout of the submit button
				submitButton.setId("submitButton");
				submitButton.setLayoutX(1700);
				submitButton.setLayoutY(60);
				// If the submit button is clicked
				submitButton.setOnAction(e -> {
					// If the number of selected buttons is 2 remove this button
					// Change all currently selected buttons to yellow
					if (selectedButtons.size() == 2) {
						while (!selectedButtons.isEmpty()) {
							selectedButtons.remove(0)
									.setStyle("-fx-background-radius: 12px;" + "-fx-background-color: yellow;");
						}
						// Removes any previously displaying path
						group.getChildren().remove(group.lookup("#path"));
						// Creates and displays the new path
						Label path = new Label(displayPath());
						path.setId("path");
						path.setLayoutX(100);
						path.setLayoutY(1000);
						path.setFont(new Font("Arial Bold", 20));
						group.getChildren().add(path);
						// Remove the submit button
						group.getChildren().remove(group.lookup("#submitButton"));
					}
				});
				// Adds the submit button
				group.getChildren().add(submitButton);
				// Sets the clicked button to red
				button.setStyle("-fx-background-radius: 12px;" + "-fx-background-color: red;");
			} else if (selectedButtons.size() == 2) {
				// If two buttons are currently selected
				// Sets the clicked place as the destination
				this.destination = place;
				// Changes the second selected button to red
				button.setStyle("-fx-background-radius: 12px;" + "-fx-background-color: red;");
				this.selectMode = false;
			}
		}
	}

	@Override
	public void start(Stage stage) {
		setBackend(new FDGPSBackend());
		group = new Group();
		scene = new Scene(group, 1920, 1080);
		scene.setFill(Color.RED);
		stage.setScene(scene);
		stage.setTitle("UW Madison School Map");
		Image mapImage;
		try {
			// Configures the image options
			mapImage = new Image(
					new FileInputStream("C:\\Users\\Danny J\\eclipse-workspace\\CS400Proj3\\src\\map.png"));
			ImageView imageView = new ImageView(mapImage);
			imageView.setX(96);
			imageView.setY(108);
			imageView.setFitHeight(864);
			imageView.setFitWidth(1728);
			// When clicked
			imageView.setOnMouseClicked(e -> {
				if (this.insertMode) {
					// If it's in insertMode and the name is not empty
					if (!newPlaceName.equals("")) {
						// Creates a new location and button at mouse location
						double mouseX = e.getX();
						double mouseY = e.getY();
						Button newButton = new Button();
						newButton.setId(newPlaceName);
						newButton.setTooltip(new Tooltip(newPlaceName));
						newButton.setMinSize(25, 25);
						newButton.setLayoutX(mouseX);
						newButton.setLayoutY(mouseY);
						IPlace newPlace = new FDPlace(newPlaceName, (int) mouseX, (int) mouseY);
						backend.mapGraph.insertVertex(newPlace);
						// Also connects the new place to the nearest place
						connectToClosest(newPlace);
						newButton.setStyle("-fx-background-radius: 12px;" + "-fx-background-color: yellow;");
						newButton.setOnAction(f -> {
							buttonClicked(newButton, newPlace);
						});
						group.getChildren().add(newButton);
						group.getChildren().remove(group.lookup("#enteredName"));
						newPlaceName = "";
						// resets insertMode
						this.insertMode = false;
					}
				}
			});
			group.getChildren().add(imageView);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		// Label that welcomes the user
		Label welcome = new Label("Welcome to the UW Madison campus map!");
		welcome.setFont(new Font("Arial Bold", 30));
		welcome.setLayoutX(96);
		welcome.setId("welcome");

		// Label that asks the user the question
		Label question = new Label("Would you like to:");
		question.setFont(new Font("Arial", 20));
		question.setLayoutX(96);
		question.setLayoutY(60);
		question.setId("question");

		// Navigate button
		Button navigate = new Button("Navigate");
		navigate.setFont(new Font("Arial", 20));
		// When clicked, the selectMode becomes true
		navigate.setOnAction(e -> this.selectMode = true);
		navigate.setMinSize(100, 60);
		navigate.setLayoutX(275);
		navigate.setLayoutY(40);
		navigate.setId("navigate");

		// "or" label
		Label or = new Label("or");
		or.setFont(new Font("Arial", 20));
		or.setLayoutX(425);
		or.setLayoutY(60);
		or.setId("or");

		// Add Location button
		Button add = new Button("Add Location");
		add.setId("add");
		add.setFont(new Font("Arial", 20));
		add.setOnAction(e -> {
			// If insertMode is true
			this.insertMode = true;
			// Creates a label with prompt that asks the user to enter the name of the new
			// location
			Label enterNamePrompt = new Label("Enter the name for the new location: ");
			enterNamePrompt.setId("enterNamePrompt");
			enterNamePrompt.setMinSize(40, 90);
			enterNamePrompt.setLayoutX(650);
			enterNamePrompt.setLayoutY(30);
			enterNamePrompt.setFont(new Font("Arial Bold", 20));
			group.getChildren().add(enterNamePrompt);

			// Creates a textfield in which the user enters the new location's name
			TextField enterName = new TextField();
			enterName.setId("enterName");
			enterName.setLayoutX(1000);
			enterName.setLayoutY(65);
			enterName.setOnAction(f -> {
				// If the text ends up being empty, name the new location "Unnamed"
				if (!enterName.getText().equals("")) {
					newPlaceName = enterName.getText();
					// Otherwise name the location as whatever is entered
				} else if (enterName.getText().equals("")) {
					newPlaceName = "Unnamed";
				}
				Label enteredName = new Label("Select location for: " + "\"" + newPlaceName + "\"");
				enteredName.setMinSize(40, 75);
				enteredName.setFont(new Font("Arial", 15));
				enteredName.setId("enteredName");
				enteredName.setLayoutX(enterName.getLayoutX());
				enteredName.setLayoutY(30);
				// Will remove the prompt and textfield once the name has been entered
				group.getChildren().add(enteredName);
				group.getChildren().remove(group.lookup("#enterName"));
				group.getChildren().remove(group.lookup("#enterNamePrompt"));
			});
			group.getChildren().add(enterName);
		});
		add.setMinSize(100, 60);
		add.setLayoutX(475);
		add.setLayoutY(40);

		// Adds all the buttons and labels
		group.getChildren().add(welcome);
		group.getChildren().add(question);
		group.getChildren().add(navigate);
		group.getChildren().add(or);
		group.getChildren().add(add);

		// Adds all the buttons that represent the places
		for (Button b : createButtons()) {
			group.getChildren().add(b);
		}
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch();
	}

}
