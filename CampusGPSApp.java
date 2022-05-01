// --== CS400 Project three W3 ==--
// Name: Mojtaba Javid
// Role: BackEnd Developer
// CSL Username: mojtaba
// Email: javid2@wisc.edu
// Lecture #: 002 @1:00pm

import java.io.FileNotFoundException;
import java.util.List;

public class CampusGPSApp {

	public static void main(String[] args) {
		IPlaceLoader loader = new PlaceLoader(); //new Loader();
	    List<IPlace> places = null;
	    try {
	      places = loader.loadPlaces("Places.json");
	    } catch (FileNotFoundException e) {
	      System.out.println("Error File Not Found");
	      e.printStackTrace();
	    }
	    GPSBackend backend = new GPSBackend(); //new Backend();
	    for(IPlace place : places) backend.map.automaticInsertEdge(place);
	    GPSFrontend frontend = new GPSFrontend(); //new Frontend(backend);
	    frontend.setBackend(backend);
	    frontend.main(args);
	}

}
