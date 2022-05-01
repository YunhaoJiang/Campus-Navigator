import java.util.List;
import javafx.scene.control.Button;

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
  public void displayPath();
  
  /**
   * Method that runs when a button is clicked
   */
  public void buttonClicked();
  
  /**
   * Method that is called when the user inserts a new location
   * @param locName name of the new location
   */
  public void insertNew(String locName);

  /**
   * Will contain all JavaFX code
   */
  public void start();
}
