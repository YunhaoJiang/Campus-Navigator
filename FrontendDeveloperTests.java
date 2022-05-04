// --== CS400 Project Three File Header ==--
//// Name: Danny Jiang
// CSL Username: danny
// Email: cjiang88@wisc.edu
// Lecture #: 002

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import edu.wisc.cs.cs400.JavaFXTester;

public class FrontendDeveloperTests extends JavaFXTester {
	public FrontendDeveloperTests() {
		super(GPSFrontend.class);
	}

	/**
	 * Checks if the correct path from Lakeshore Dorms to Humanities is displayed correctly
	 */
	@Test
	public void test1() {
		// lookup buttons to click
		Button lakeShore = lookup("#LakeshoreDorms").query();
		Button humanities = lookup("#Humanities").query();
		Button navigate = lookup("#navigate").query();

		interact(() -> {
			// Clicks navigate
			navigate.fireEvent(new ActionEvent());
			// Clicks lakeShore
			lakeShore.fireEvent(new ActionEvent());
			// Clicks humanities
			humanities.fireEvent(new ActionEvent());
			Button submit = lookup("#submitButton").query();
			// Clicks submitButton
			submit.fireEvent(new ActionEvent());
		});
		Label path = lookup("#path").query();
		// Checks the path printed out
		assertEquals("Lakeshore Dorms -> Van Vleck Hall -> Humanities", path.getText());
	}

	/**
	 * Checks if the correct path from Lakeshore Dorms to Van Vleck hall is displayed correctly
	 */
	@Test
	public void test2() {
		// lookup buttons to click
		Button lakeShore = lookup("#LakeshoreDorms").query();
		Button vanVleck = lookup("#VanVleckHall").query();
		Button navigate = lookup("#navigate").query();

		interact(() -> {
			// Clicks navigate
			navigate.fireEvent(new ActionEvent());
			// Clicks lakeShore
			lakeShore.fireEvent(new ActionEvent());
			// Clicks vanVleck
			vanVleck.fireEvent(new ActionEvent());
			Button submit = lookup("#submitButton").query();
			// Clicks submitButton
			submit.fireEvent(new ActionEvent());
		});
		Label path = lookup("#path").query();
		// Checks the path printed out
		assertEquals("Lakeshore Dorms -> Van Vleck Hall", path.getText());
	}
	
	/**
	 * Checks if the button turns red when clicked while in select mode
	 */
	@Test
	public void test3() {
		// lookup buttons to click
		Button lakeShore = lookup("#LakeshoreDorms").query();
		Button navigate = lookup("#navigate").query();

		interact(() -> {
			// Clicks navigate
			navigate.fireEvent(new ActionEvent());
			// Clicks lakeShore
			lakeShore.fireEvent(new ActionEvent());
		});
		
		assertEquals("-fx-background-radius: 12px;" + "-fx-background-color: red;", lakeShore.getStyle());
	}
	
	/**
	 * Checks all the tooltips of the default locations
	 */
	@Test
	public void test4() {
		// lookup buttons to click
		Button lakeShore = lookup("#LakeshoreDorms").query();
		Button humanities = lookup("#Humanities").query();
		Button vanVleck = lookup("#VanVleckHall").query();
		
		// Checks the tooltip of all default buttons
		assertEquals("Lakeshore Dorms", lakeShore.getTooltip().getText());
		assertEquals("Humanities", humanities.getTooltip().getText());
		assertEquals("Van Vleck Hall", vanVleck.getTooltip().getText());
	}
	
	/**
	 * Checks to see if the appropriate label and location name is set when adding a new location
	 */
	@Test
	public void test5() {
		// lookup buttons to click
		Button add = lookup("#add").query();

		interact(() -> {
			// Clicks add Location button
			add.fireEvent(new ActionEvent());
			TextField enterName = lookup("#enterName").query();
			enterName.fireEvent(new ActionEvent());
			// Presses enter
			type(KeyCode.ENTER);
		});
		Label enteredName = lookup("#enteredName").query();
		// Checks the label printed out
		assertEquals("Select location for: \"Unnamed\"", enteredName.getText());
	}
	
	/**
	 * Checks if the correct path from Memorial Union to Kohl Center is displayed correctly
	 */
	@Test
	public void test6() {
		// lookup buttons to click
		Button memU= lookup("#MemorialUnion").query();
		Button kohl = lookup("#KohlCenter").query();
		Button navigate = lookup("#navigate").query();

		interact(() -> {
			// Clicks navigate
			navigate.fireEvent(new ActionEvent());
			// Clicks Memorial Union
			memU.fireEvent(new ActionEvent());
			// Clicks Kohl Center
			kohl.fireEvent(new ActionEvent());
			Button submit = lookup("#submitButton").query();
			// Clicks submitButton
			submit.fireEvent(new ActionEvent());
		});
		Label path = lookup("#path").query();
		// Checks the path printed out
		assertEquals("Memorial Union -> Chadbourne -> Kohl Center", path.getText());
	}
	
	/**
	 * Checks if the an exception is thrown when two yellow nodes are chosen for navigation
	 */
	@Test
	public void test7() {
		// lookup buttons to click
		Button randall = lookup("#CampRandall").query();
		Button uSouth = lookup("#UnionSouth").query();
		Button navigate = lookup("#navigate").query();
		assertThrows(Exception.class, () -> {
			interact(() -> {
			// Clicks navigate
			navigate.fireEvent(new ActionEvent());
			// Clicks Memorial Union
			randall.fireEvent(new ActionEvent());
			// Clicks Kohl Center
			uSouth.fireEvent(new ActionEvent());
			Button submit = lookup("#submitButton").query();
			// Clicks submitButton
			submit.fireEvent(new ActionEvent());
		});
		});
		
	}
	@Test
	public void otherTest1() {
		Map map = new Map();
		// Creates 3 hypothetical location to test out the weight calculation function
	    IPlace loc1 = new Place("loc1",0,0);
	    IPlace loc2 = new Place("loc2",0,15);
	    IPlace loc3 = new Place("loc3",3,19);
	    // Insert the locations into the map and connect them
	    // Assigning weights with the function
	    map.insertVertex(loc1);
	    map.insertVertex(loc2);
	    map.insertVertex(loc3);
	    map.insertEdge(loc1,loc2,map.calculateWeight(loc1,loc2));
	    map.insertEdge(loc2,loc3,map.calculateWeight(loc2,loc3));
	    // The total weight should be 15 + 5 = 20
	    assertEquals(20,map.calculateTime(loc1,loc3,1));
	}
	@Test
	public void otherTest2() {
		Map map = new Map();
		// Creates two locations that are 50 pixels apart
	    IPlace loc1 = new Place("loc1",0,0);
	    IPlace loc2 = new Place("loc2",30,40);
	    // The weight of the edge between these two locations should be 50
	    int weight = map.calculateWeight(loc1, loc2);
	    assertEquals(50, weight);
	}
	


}
