// --== CS400 Project three W3 ==--
// Name: Mojtaba Javid
// Role: BackEnd Developer
// CSL Username: mojtaba
// Email: javid2@wisc.edu
// Lecture #: 002 @1:00pm

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;

public class BackendDeveloperTests {
	GPSBackend backend = new GPSBackend();
	BDPHGPSAlgorithm a = new BDPHGPSAlgorithm();
	IPlace A;
    IPlace B;
    IPlace C;
    IPlace D;
    IPlace E;
    IPlace F;
    
    /**
     * Instantiate graph from last week's shortest path activity.
     */
    @BeforeEach
    public void createGraph() {
    	
        A = new BDPHPlace("a", 1, 2);
        B = new BDPHPlace("b", 2, 2);
        C = new BDPHPlace("c", 3, 3);
        D = new BDPHPlace("d", 4, 4);
        E = new BDPHPlace("e", 1, 5);
        F = new BDPHPlace("f", 6, 2);
        
        backend.map.insertVertex(B);
        backend.map.insertVertex(C);
        backend.map.insertVertex(D);
        backend.map.insertVertex(E);
        
        a.insertVertex(A);
        a.insertVertex(B);
        a.insertVertex(C);
        a.insertVertex(D);
        a.insertVertex(E);
        a.insertVertex(F);
        
        // insert edges from Week 11. Shortest Path Activity
       // backend.map.insertEdge(A,B,6);
       // backend.map.insertEdge(A,C,2);
        //backend.map.insertEdge(A,D,5);
       
        backend.map.insertEdge(B,E,1);
        backend.map.insertEdge(B,C,2);
        backend.map.insertEdge(C,B,3);
        //backend.map.insertEdge(C,F,1);
        backend.map.insertEdge(D,E,3);
        
        //backend.map.insertEdge(E,A,4);
        //backend.map.insertEdge(F,A,1);
        //backend.map.insertEdge(F,D,1);
        
        a.insertEdge(A,B,6);
        a.insertEdge(A,C,2);
        a.insertEdge(A,D,5);
        a.insertEdge(B,E,1);
        a.insertEdge(B,C,2);
        a.insertEdge(C,B,3);
        a.insertEdge(C,F,1);
        a.insertEdge(D,E,3);
        a.insertEdge(E,A,4);
        a.insertEdge(F,A,1);
        a.insertEdge(F,D,1);
    }

    /**
     * Checks the Functionality of GPSAlgorithem through backend.
     */
    @Test
    public void test1() {
        assertTrue(backend.map.getPathCost(C , E) == 4);
    }
    
    /**
     * testing the functionality
     * of the first add method
     */
	@Test
    public void test2(){
        backend.addPlace(A, a.vertices.get(A).edgesLeaving);
        assertTrue(backend.map.getPathCost(A , E) == 6);
    }
    
    /**
     * test method to confirm the functionality
     * of the first add method
     */
	@Test
    public void test3(){
		backend.addPlace(A, a.vertices.get(A).edgesLeaving);
        assertTrue(backend.map.getPathCost(A , B) == 5);
    }
    
	/**
     * testing the functionality
     * of the second add method
     */
	@Test
    public void test4() {
		backend.addPlace(A, a.vertices.get(A).edgesLeaving);
    	backend.addPlace(a.vertices.get(F));
        assertEquals(A, backend.map.shortestPath(F, B).get(1));
    }
    
    /**
     * testing the functionality
     * of the shortest path method
     */
    @Test
    public void test5() {
		backend.addPlace(A, a.vertices.get(A).edgesLeaving);
    	backend.addPlace(a.vertices.get(F));
    	String t = "[";
    	for (int i=0; i<backend.shortestPathFinder(A, D).size(); i++) {
    		t = t + backend.shortestPathFinder(A, D).get(i).getName();
    	}
    	t = t+"]";
    	
        assertEquals("[afd]", t);
    }
    
}
