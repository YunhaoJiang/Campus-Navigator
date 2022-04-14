import java.util.List;

public interface IGPSBackend {
	//protected Graph<ILocation> campusMap
	//a default constructor that takes a graph
	//and assigns it to campusMap, should be implemented
	
	// this methods uses the dijkstra algorithm to find the shortest path
	public List<ILocation> shortestPathFinder(ILocation start, ILocation end);

}
