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
	    List<GPSAlgorithm.Vertex> places = null;
	    try {
	      places = loader.loadPlaces("tv_shows.csv");
	    } catch (FileNotFoundException e) {
	      System.out.println("Error File Not Found");
	      e.printStackTrace();
	    }
	    IGPSBackend backend = new GPSBackend(); //new Backend();
	    for(GPSAlgorithm.Vertex place : places) backend.addPlace(place);
	    IGPSFrontend frontend = new GPSFrontend(backend); //new Frontend(backend);
	    frontend.start();
	}

}
