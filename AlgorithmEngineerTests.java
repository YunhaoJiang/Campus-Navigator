import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmEngineerTests {

  /**
   * Test does map correctly calculates the distance between two places
   */
  @Test
  public void test1() {
    Map map = new Map();
    IPlace place1 = new Place("node1",0,0);
    IPlace place2 = new Place("node2",3,4);
    assertEquals(5, map.calculateWeight(place1, place2));
  }

  /**
   * Test does map correctly calculates the travel time for a given Path
   */
  @Test
  public void test2() {
    Map map = new Map();
    IPlace place1 = new Place("node1",0,0);
    IPlace place2 = new Place("node2",3,4);
    IPlace place3 = new Place("node3",3,7);
    map.insertVertex(place1);
    map.insertVertex(place2);
    map.insertVertex(place3);
    map.insertEdge(place1,place2,map.calculateWeight(place1,place2));
    map.insertEdge(place2,place3,map.calculateWeight(place2,place3));
    assertEquals(4,map.calculateTime(place1,place3,2));
  }

  /**
   * Test does map automatically insert edges in NON-INTERSECTING places
   */
  @Test
  public void test3(){
    Map map = new Map();
    IPlace place1 = new Place("node1",0,0);
    IPlace place2 = new Place("node2",3,0);
    IPlace place3 = new Place("node3",1,2);
    map.automaticInsertEdge(place1);
    map.automaticInsertEdge(place2);
    map.automaticInsertEdge(place3);
    assertEquals(2,map.vertices.get(place1).edgesLeaving.size());
    assertEquals(1,map.vertices.get(place2).edgesLeaving.size());
    assertEquals(0,map.vertices.get(place3).edgesLeaving.size());
  }

  /**
   * Test does map automatically insert edges in INTERSECTING places
   */
  @Test
  public void test4(){
    Map map = new Map();
    IPlace place1 = new Place("node1",0,0);
    IPlace place2 = new Place("node2",3,0);
    IPlace place3 = new Place("node3",1,2);
    IPlace place4 = new Place("node4",5,2);
    map.automaticInsertEdge(place1);
    map.automaticInsertEdge(place2);
    map.automaticInsertEdge(place3);
    map.automaticInsertEdge(place4);
    assertEquals(2,map.vertices.get(place1).edgesLeaving.size());
    assertEquals(2,map.vertices.get(place2).edgesLeaving.size());
    assertEquals(1,map.vertices.get(place3).edgesLeaving.size());
    assertEquals(0,map.vertices.get(place4).edgesLeaving.size());
  }

  /**
   * Test does map automatically insert edges when the new place is in the middle of an existing
   * edges (eg. in the middle of an triangle)
   */
  @Test
  public void test5(){
    Map map = new Map();
    IPlace place1 = new Place("node1",0,0);
    IPlace place2 = new Place("node2",3,0);
    IPlace place3 = new Place("node3",1,2);
    IPlace place4 = new Place("node4",5,2);
    IPlace place5 = new Place("node5",3,1);
    map.automaticInsertEdge(place1);
    map.automaticInsertEdge(place2);
    map.automaticInsertEdge(place3);
    map.automaticInsertEdge(place4);
    map.automaticInsertEdge(place5);
    assertEquals(2,map.vertices.get(place1).edgesLeaving.size());
    assertEquals(3,map.vertices.get(place2).edgesLeaving.size());
    assertEquals(2,map.vertices.get(place3).edgesLeaving.size());
    assertEquals(1,map.vertices.get(place4).edgesLeaving.size());
    assertEquals(0,map.vertices.get(place5).edgesLeaving.size());
  }
}
