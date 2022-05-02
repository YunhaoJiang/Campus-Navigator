import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
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
 */
public class GPSFrontend extends Application implements IGPSFrontend {

  public boolean selectMode;
  public boolean insertMode;
  public String newPlaceName;
  public IGPSBackend backend;
  private ArrayList<Button> selectedButtons;
  public IPlace source;
  public IPlace destination;
  public Scene scene;
  public Group group;

  private ArrayList<Line> currLines;

  public GPSFrontend() {
    source = null;
    destination = null;
    selectMode = false;
    insertMode = false;
    newPlaceName = "";
    selectedButtons = new ArrayList<Button>();
    currLines = new ArrayList<Line>();
  }

  public void setBackend(IGPSBackend backend) {
    System.out.println("Backend set");
    this.backend = backend;
  }

  public boolean isBackendNull() {
    return backend == null;
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
      if (p.getName().equals("Carson's Market") || p.getName().equals("Liz's Market") || p.getName()
          .equals("Four Lakes Market") || p.getName().equals("Memorial Union")) {
        tempButton.setStyle("-fx-background-radius: 12px;" + "-fx-background-color: green;");
      }
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
      Line line = new Line(path.get(i - 1).getX() + 13, path.get(i - 1).getY() + 15,
          path.get(i).getX() + 13, path.get(i).getY() + 15);
      line.setStroke(Color.BLUE);
      line.setStrokeWidth(10);
      string = string + " -> " + path.get(i).getName();
      group.getChildren().add(line);
      currLines.add(line);
    }

    return string;
  }

  /**
   * Private method that connects the inputed place to the nearest place(s)
   * currently in the backend
   *
   * @param place the input that will be connected to the nearest place
   */
	/*
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
*/

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
      submitButton.setFont(new Font("Arial", 25));
      this.selectedButtons.add(button);
      // If the current number of selected buttons is 1
      if (selectedButtons.size() == 1) {
        // Store this as the source place
        this.source = place;
        // Sets the layout of the submit button
        submitButton.setId("submitButton");
        submitButton.setLayoutX(1250);
        submitButton.setLayoutY(55);
        // If the submit button is clicked
        submitButton.setOnAction(e -> {
          // If the number of selected buttons is 2 remove this button
          // Change all currently selected buttons to yellow
          if (selectedButtons.size() == 2) {
            while (!selectedButtons.isEmpty()) {
              Button toRemove = selectedButtons.remove(0);
              String toRemoveName = toRemove.getTooltip().getText();
              if (toRemoveName.equals("Carson's Market") || toRemoveName.equals("Liz's " + "Market")
                  || toRemoveName.equals("Four Lakes Market") || toRemoveName.equals(
                  "Memorial Union")) {
                toRemove.setStyle("-fx-background-radius: 12px;" + "-fx-background-color: green;");
              } else {
                toRemove.setStyle("-fx-background-radius: 12px;" + "-fx-background-color: yellow;");
              }
            }
            // Removes any previously displaying path
            group.getChildren().remove(group.lookup("#path"));
            // Creates and displays the new path
            Label path = new Label(displayPath());
            path.setId("path");
            path.setLayoutX(30);
            path.setLayoutY(800);
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
    IPlaceLoader loader = new PlaceLoader(); //new Loader();
    List<IPlace> places = null;
    try {
      places = loader.loadPlaces("Places.json");
    } catch (FileNotFoundException e) {
      System.out.println("Error File Not Found");
      e.printStackTrace();
    }
    GPSBackend GPSBackend = new GPSBackend(); //new Backend();
    this.setBackend(GPSBackend);
    backend.intialize(places);

    group = new Group();
    scene = new Scene(group, 1500, 850);
    stage.setScene(scene);
    stage.setTitle("UW Madison Food Delivery Map");
    Image mapImage;
    try {
      // Configures the image options
      mapImage = new Image(new FileInputStream("map.png"));
      ImageView imageView = new ImageView(mapImage);
      imageView.setX(25);
      imageView.setY(108);
      imageView.setFitWidth(1450); // 1500-25-25
      imageView.setFitHeight(717); // 850-108-25
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
            IPlace newPlace = new Place(newPlaceName, (int) mouseX, (int) mouseY);
            backend.addPlace(newPlace);
            // Also connects the new place to the nearest place
            //connectToClosest(newPlace);
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
    Label welcome = new Label("Welcome to the UW Madison Food Delivery Map!");
    welcome.setFont(new Font("Arial Bold", 30));
    welcome.setLayoutX(20);
    welcome.setLayoutY(20);
    welcome.setId("welcome");

    // Label that asks the user the question
    Label question = new Label("Would you like to:");
    question.setFont(new Font("Arial", 25));
    question.setLayoutX(30);
    question.setLayoutY(70);
    question.setId("question");

    // Navigate button
    Button navigate = new Button("Navigate");
    navigate.setFont(new Font("Arial", 20));
    // When clicked, the selectMode becomes true
    navigate.setOnAction(e -> {
      this.selectMode = true;
      for (Line currLine : currLines) {
        group.getChildren().remove(currLine);
      }
    });
    navigate.setMinSize(100, 20);
    navigate.setLayoutX(250);
    navigate.setLayoutY(65);
    navigate.setId("navigate");

    // "or" label
    Label or = new Label("or");
    or.setFont(new Font("Arial", 25));
    or.setLayoutX(375);
    or.setLayoutY(70);
    or.setId("or");

    // Add Location button
    Button add = new Button("Add Location");
    add.setId("add");
    add.setFont(new Font("Arial", 20));
    add.setOnAction(e -> {
      // If insertMode is true
      this.insertMode = true;
      for (Line currLine : currLines) {
        group.getChildren().remove(currLine);
      }
      // Creates a label with prompt that asks the user to enter the name of the new
      // location
      Label enterNamePrompt = new Label("Enter the name for the new location: ");
      enterNamePrompt.setId("enterNamePrompt");
      enterNamePrompt.setMinSize(40, 90);
      enterNamePrompt.setLayoutX(600);
      enterNamePrompt.setLayoutY(40);
      enterNamePrompt.setFont(new Font("Arial Bold", 20));
      group.getChildren().add(enterNamePrompt);

      // Creates a textfield in which the user enters the new location's name
      TextField enterName = new TextField();
      enterName.setId("enterName");
      enterName.setLayoutX(950);
      enterName.setLayoutY(73);
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
        enteredName.setFont(new Font("Arial Bold", 20));
        enteredName.setId("enteredName");
        enteredName.setLayoutX(enterNamePrompt.getLayoutX());
        enteredName.setLayoutY(47);
        // Will remove the prompt and textfield once the name has been entered
        group.getChildren().add(enteredName);
        group.getChildren().remove(group.lookup("#enterName"));
        group.getChildren().remove(group.lookup("#enterNamePrompt"));
      });
      group.getChildren().add(enterName);
    });
    add.setMinSize(100, 20);
    add.setLayoutX(415);
    add.setLayoutY(65);

    // Adds all the buttons and labels
    group.getChildren().add(welcome);
    group.getChildren().add(question);
    group.getChildren().add(navigate);
    group.getChildren().add(or);
    group.getChildren().add(add);

    // System.out.println("Creating Buttons");
    // Adds all the buttons that represent the places
    for (Button b : createButtons()) {
      group.getChildren().add(b);
    }
    stage.show();
  }

  public void run() {
    launch();
  }

  public static void main(String[] args) {
    Application.launch();
  }

}
