// --== CS400 Project three W3 ==--
// Name: Mojtaba Javid
// Role: BackEnd Developer
// CSL Username: mojtaba
// Email: javid2@wisc.edu
// Lecture #: 002 @1:00pm

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class GPSBackend implements IGPSBackend{
	BDPHGPSAlgorithm map;

    public GPSBackend() {
            this.map = new BDPHGPSAlgorithm();}

    /**
     * Method that adds a new place to the database
     *
     * @param place, edgeList to be added and connected in the graph
     */
	@Override
	public void addPlace(IPlace place, LinkedList<BDPHGPSAlgorithm.Edge> edgeList) {
		if(place == null || edgeList == null) return;
		map.insertVertex(place);
		for(int i=0; i<edgeList.size(); i++) {
			map.insertEdge(place, edgeList.get(i).target.data, edgeList.get(i).weight);
			map.insertEdge(edgeList.get(i).target.data, place, edgeList.get(i).weight);}
	}
	
	//overloading method for add which takes an vertex as param
	public void addPlace(BDPHGPSAlgorithm.Vertex place) {
		this.addPlace(place.data, place.edgesLeaving);}

	/**
     * Returns the shortest path between start and end.
     * Uses Dijkstra's algorithm from GPSAlgorithem class.
     * @param start the data item in the starting vertex for the path
     * @param end the data item in the destination vertex for the path
     * @return list of data item in vertices in order on the shortest path between vertex 
     * with data item start and vertex with data item end, including both start and end 
     * @throws NoSuchElementException when no path from start to end can be found
     *     including when no vertex containing start or end can be found
     */
	@Override
	public List<IPlace> shortestPathFinder(IPlace start, IPlace end) {
		return map.shortestPath(start, end);
	}


}
