import java.util.ArrayList;
import java.util.List;

public class FDGPSBackend implements IGPSBackend{

	public CS400Graph<IPlace> mapGraph = new CS400Graph<IPlace>();
	public FDPlace vanVleck = new FDPlace("Van Vleck Hall", 1080, 390);
	public FDPlace lakeShore = new FDPlace("Lakeshore Dorms", 440, 225);
	public FDPlace humanities = new FDPlace("Humanities", 1425, 460);
	
	@Override
	public List<IPlace> shortestPathFinder(IPlace start, IPlace end) {
		List<IPlace> list = new ArrayList<IPlace>();
		if(start.getName().equals("Lakeshore Dorms") && end.getName().equals("Humanities")) {
			list.add(lakeShore);
			list.add(vanVleck);
			list.add(humanities);
		} else if (start.getName().equals("Lakeshore Dorms") && end.getName().equals("Van Vleck Hall")) {
			list.add(lakeShore);
			list.add(vanVleck);
		}
		return list;
	}
	
	public ArrayList<IPlace> getAllPlaces(){
		ArrayList<IPlace> places = new ArrayList<IPlace>();
		mapGraph.insertVertex(vanVleck);
		mapGraph.insertVertex(lakeShore);
		mapGraph.insertVertex(humanities);
		mapGraph.insertEdge(lakeShore, vanVleck, lakeShore.compareTo(vanVleck));
		mapGraph.insertEdge(vanVleck, lakeShore, vanVleck.compareTo(lakeShore));
		mapGraph.insertEdge(vanVleck, humanities, vanVleck.compareTo(humanities));
		mapGraph.insertEdge(humanities, vanVleck, humanities.compareTo(vanVleck));
		places.add(vanVleck);
		places.add(humanities);
		places.add(lakeShore);
		return places;
	}

}
