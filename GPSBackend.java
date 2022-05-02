// --== CS400 Project three W3 ==--
// Name: Mojtaba Javid
// Role: BackEnd Developer
// CSL Username: mojtaba
// Email: javid2@wisc.edu
// Lecture #: 002 @1:00pm

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class GPSBackend implements IGPSBackend {
  protected Map map;

  public GPSBackend() {
    this.map = new Map();
  }

  /**
   * Method that adds a new place to the database
   *
   * @param place, edgeList to be added and connected in the graph
   */
  @Override
  public void addPlace(IPlace place, LinkedList<Map.Edge> edgeList) {
    if (place == null || edgeList == null)
      return;
    map.insertVertex(place);
    for (int i = 0; i < edgeList.size(); i++) {
      map.insertEdge(place, edgeList.get(i).target.data, edgeList.get(i).weight);
      map.insertEdge(edgeList.get(i).target.data, place, edgeList.get(i).weight);
    }
  }

  //overloading method for add which takes an vertex as param
  public void addPlace(Map.Vertex place) {
    this.addPlace(place.data, place.edgesLeaving);
  }

  //overloading method for add which takes an vertex as param
  public void addPlace(IPlace place) {
    this.map.automaticInsertEdge(place);
    //System.out.println("addPlace, current map size: " + map.vertices.keySet().size());
  }

  /**
   * Returns the shortest path between start and end.
   * Uses Dijkstra's algorithm from GPSAlgorithem class.
   *
   * @param start the data item in the starting vertex for the path
   * @param end   the data item in the destination vertex for the path
   * @return list of data item in vertices in order on the shortest path between vertex
   * with data item start and vertex with data item end, including both start and end
   * @throws NoSuchElementException when no path from start to end can be found
   *                                including when no vertex containing start or end can be found
   */
  @Override
  public List<IPlace> shortestPathFinder(IPlace start, IPlace end) {
    return map.shortestPath(start, end);
  }

  public List<IPlace> getAllPlaces() {
    ArrayList<IPlace> result = new ArrayList<>();
    for (Map.Vertex vertex : map.vertices.values()) {
      result.add(vertex.data);
    }
    return result;
  }

  @Override
  public void intialize(List<IPlace> places) {
    int currentIndex = 0;
    for (int i = currentIndex; i < 2; i++) {
      this.map.insertVertex(places.get(i)); // manual-insert carson and waters
      currentIndex++;
    }
    this.addPlace(places.get(currentIndex)); // auto-insert human ecology
    currentIndex++;
    this.map.insertVertex(places.get(currentIndex)); // manual-insert four lakes
    currentIndex++;
    this.addPlace(places.get(currentIndex)); // auto-insert Babcock
    currentIndex++;
    this.map.insertVertex(places.get(currentIndex)); // manual-insert the memorial union
    currentIndex++;
    this.addPlace(places.get(currentIndex)); // auto-insert the chadd
    currentIndex++;
    this.addPlace(places.get(currentIndex)); // auto-insert the veterinary hospital
    currentIndex++;
    this.addPlace(places.get(currentIndex)); // auto-insert the hub-madison
    currentIndex++;
    this.map.insertVertex(places.get(currentIndex)); // manual-insert the Camp Randall
    currentIndex++;
    this.map.insertVertex(places.get(currentIndex)); // manual-insert the Camp Randall

    this.map.insertEdge(places.get(4), places.get(7),
        map.calculateWeight(places.get(4), places.get(7)));
    this.map.insertEdge(places.get(3), places.get(0),
        map.calculateWeight(places.get(3), places.get(0)));
    this.map.insertEdge(places.get(5), places.get(1),
        map.calculateWeight(places.get(5), places.get(1)));
    this.map.insertEdge(places.get(2), places.get(6),
        map.calculateWeight(places.get(2), places.get(6)));
    this.map.insertEdge(places.get(2), places.get(4),
        map.calculateWeight(places.get(2), places.get(4)));
    this.map.insertEdge(places.get(4), places.get(9),
        map.calculateWeight(places.get(4), places.get(9)));
    this.map.insertEdge(places.get(6), places.get(10),
        map.calculateWeight(places.get(6), places.get(10)));
    this.map.removeEdge(places.get(4), places.get(6));

    this.map.automaticInsertEdge(places.get(11));
    this.map.removeEdge(places.get(9), places.get(11));
    this.map.insertEdge(places.get(2), places.get(11),
        map.calculateWeight(places.get(2), places.get(11)));
  }
}
