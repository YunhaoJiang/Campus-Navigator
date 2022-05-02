import java.util.LinkedList;
import java.util.List;

public interface IGPSBackend {
  //protected Graph<IPlace> campusMap
  //a default constructor that takes a graph
  //and assigns it to campusMap, should be implemented

  public void addPlace(IPlace place,
      LinkedList<Map.Edge> edgeList); // adds place to backend database

  public void addPlace(Map.Vertex place);

  public void addPlace(IPlace place);

  // this methods uses the dijkstra algorithm to find the shortest path
  public List<IPlace> shortestPathFinder(IPlace start, IPlace end);

  public List<IPlace> getAllPlaces();

  void intialize(List<IPlace> places);
}
