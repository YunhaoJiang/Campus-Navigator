// --== CS400 Project three W3 ==--
// Name: Sangho Jeon
// Role: Data Wrangler
// CSL Username: sangho
// Email: sjeon36@wisc.edu
// Lecture #: 002 @1:00pm

import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class DataWranglerTests {

	@Test
	void test1() throws FileNotFoundException {
		PlaceLoader pl = new PlaceLoader();
		List<IPlace> lstPlace = pl.loadPlaces("Places.json");
		assertTrue(lstPlace.size() == 12);
	}

	@Test
	void test2() throws FileNotFoundException {
		PlaceLoader pl = new PlaceLoader();
		List<IPlace> lstPlace = pl.loadPlaces("Places.json");

		assertTrue(lstPlace.get(0).getName().equalsIgnoreCase("Carson's Market"));
	}

	@Test
	void test3() throws FileNotFoundException {
		PlaceLoader pl = new PlaceLoader();
		List<IPlace> lstPlace = pl.loadPlaces("Places.json");


		assertTrue(lstPlace.get(0).getX() == 536);
	}

	@Test
	void test4() throws FileNotFoundException {
		PlaceLoader pl = new PlaceLoader();
		List<IPlace> lstPlace = pl.loadPlaces("Places.json");
		System.out.println(lstPlace.get(0).getY());

		assertTrue(lstPlace.get(0).getY() == 250);
	}

	@Test
	void test5() throws FileNotFoundException {
		PlaceLoader pl = new PlaceLoader();
		List<IPlace> lstPlace = pl.loadPlaces("Places.json");

		assertTrue(lstPlace.get(1).getName().equalsIgnoreCase("Liz's Market"));
	}

	// additional test to see if it works with other's code
	@Test
	void test6() throws FileNotFoundException {
		PlaceLoader pl = new PlaceLoader();
		List<IPlace> lstPlace = pl.loadPlaces("Places.json");
		Map map = new Map();
		Place place1 = new Place(lstPlace.get(1).getName(), lstPlace.get(1).getX(), lstPlace.get(1).getY());
		Place place2 = new Place(lstPlace.get(2).getName(), lstPlace.get(2).getX(), lstPlace.get(2).getY());
		assertEquals(124, map.calculateWeight(place1, place2));
	}

	// additional test to see if it works with other's code
	@Test
	public void test7() throws FileNotFoundException {
		PlaceLoader pl = new PlaceLoader();
		List<IPlace> lstPlace = pl.loadPlaces("Places.json");

		Map map = new Map();
		Place place1 = new Place(lstPlace.get(1).getName(), lstPlace.get(1).getX(), lstPlace.get(1).getY());
		Place place2 = new Place(lstPlace.get(2).getName(), lstPlace.get(2).getX(), lstPlace.get(2).getY());
		map.automaticInsertEdge(place1);
		map.automaticInsertEdge(place2);
		System.out.println(map.vertices.get(place1).edgesLeaving.size());
		System.out.println(map.vertices.get(place2).edgesLeaving.size());

		assertEquals(1, map.vertices.get(place1).edgesLeaving.size());
		assertEquals(0, map.vertices.get(place2).edgesLeaving.size());


	}

	// additional test for Backend to check getPathCost method
	@Test
	public void test8() throws FileNotFoundException {
		PlaceLoader pl = new PlaceLoader();
		List<IPlace> lstPlace = pl.loadPlaces("Places.json");

		Map map = new Map();
		Place place1 = new Place(lstPlace.get(1).getName(), lstPlace.get(1).getX(), lstPlace.get(1).getY());
		Place place2 = new Place(lstPlace.get(2).getName(), lstPlace.get(2).getX(), lstPlace.get(2).getY());
		GPSBackend backend = new GPSBackend();
		backend.addPlace(place1);
		backend.addPlace(place2);
		assertTrue(backend.map.getPathCost(place1, place2) == 124);


	}

	// additional test for Backend to check shortestPath Method
	@Test
	public void test9() throws FileNotFoundException {
		PlaceLoader pl = new PlaceLoader();
		List<IPlace> lstPlace = pl.loadPlaces("Places.json");

		Map map = new Map();
		Place place1 = new Place(lstPlace.get(1).getName(), lstPlace.get(1).getX(), lstPlace.get(1).getY());
		Place place2 = new Place(lstPlace.get(2).getName(), lstPlace.get(2).getX(), lstPlace.get(2).getY());
		GPSBackend backend = new GPSBackend();
		backend.addPlace(place1);
		backend.addPlace(place2);

		String name;
		name = backend.map.shortestPath(place1, place2).get(0).getName();
		name = name + " , " + backend.map.shortestPath(place1, place2).get(1).getName();
      	 assertEquals("Liz's Market , Human Ecology", name );
		}





}
