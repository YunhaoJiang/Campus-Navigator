import java.util.List;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public interface IGPSFrontend {

  // IGPSFrontend(IGPSBackend backend);
  
  // Boolean value that determines if the user is currently selecting locations to be beginning/end
  // boolean selectMode;
  
  // Boolean value that determines if the user is currently in the process of adding a new vertex
  // boolean insertMode;
  
  /**
   * will obtain all vertices from backend and then create buttons for each vertices.
   * 
   * @return a list of all the buttons
   */
  public List<Button> createButtons();

  /**
   * Method that will display the shortest path (calculated through backend) from point A to B and
   * display it on JavaFX
   */
  public String displayPath();
  
  /**
   * Method that runs when a button is clicked
   */
  public void buttonClicked(Button button, IPlace place);
  
  /**
   * Will contain all JavaFX code
   */
  public void start(Stage stage);
}
