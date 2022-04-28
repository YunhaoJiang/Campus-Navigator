import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;

class DataWranglerTests {

	@Test
	void test1() throws FileNotFoundException {
		PlaceLoader pl = new PlaceLoader();
    	List<IPlace> lstPlace = pl.loadPlaces("Places.json");
    	assertTrue(lstPlace.size() == 5); 
	}
	
	@Test
	void test2() throws FileNotFoundException {
		PlaceLoader pl = new PlaceLoader();
    	List<IPlace> lstPlace = pl.loadPlaces("Places.json");
    	
    	assertTrue(lstPlace.get(0).getName().equalsIgnoreCase("Four Lakes Market")); 
	}
	
	@Test
	void test3() throws FileNotFoundException {
		PlaceLoader pl = new PlaceLoader();
    	List<IPlace> lstPlace = pl.loadPlaces("Places.json");
    	
    	
    	assertTrue(lstPlace.get(0).getX() == 245); 
	}
	
	@Test
	void test4() throws FileNotFoundException {
		PlaceLoader pl = new PlaceLoader();
    	List<IPlace> lstPlace = pl.loadPlaces("Places.json");
    	
    	assertTrue(lstPlace.get(0).getY() == 230); 
	}
	
	@Test
	void test5() throws FileNotFoundException {
		PlaceLoader pl = new PlaceLoader();
    	List<IPlace> lstPlace = pl.loadPlaces("Places.json");
    	
    	assertTrue(lstPlace.get(1).getName().equalsIgnoreCase("Carson's Market"));  
	}

}
