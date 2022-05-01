// --== CS400 Project three W3 ==--
// Name: Mojtaba Javid
// Role: BackEnd Developer
// CSL Username: mojtaba
// Email: javid2@wisc.edu
// Lecture #: 002 @1:00pm

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;


public class BDPHGPSAlgorithm implements GraphADT<IPlace>{

	/**
     * Vertex objects group a data field with an adjacency list of weighted
     * directed edges that lead away from them.
     */
    protected class Vertex {
        public IPlace data; // vertex label or application specific data
        public LinkedList<Edge> edgesLeaving;

        public Vertex(IPlace data) {
            this.data = data;
            this.edgesLeaving = new LinkedList<>();}
    }

    /**
     * Edge objects are stored within their source vertex, and group together
     * their target destination vertex, along with an integer weight.
     */
    protected class Edge {
        public Vertex target;
        public int weight;

        public Edge(Vertex target, int weight) {
            this.target = target;
            this.weight = weight;}
    }
    
    protected Hashtable<IPlace, Vertex> vertices; // holds graph verticies, key=data
    public BDPHGPSAlgorithm() { vertices = new Hashtable<>(); }
    
	@Override
	public boolean insertVertex(IPlace data) {
		if(data == null) 
            throw new NullPointerException("Cannot add null vertex");
        if(vertices.containsKey(data)) return false; // duplicate values are not allowed
        vertices.put(data, new Vertex(data));
        return true;
	}

	@Override
	public boolean removeVertex(IPlace data) {
		if(data == null) throw new NullPointerException("Cannot remove null vertex");
        Vertex removeVertex = vertices.get(data);
        if(removeVertex == null) return false; // vertex not found within graph
        // search all vertices for edges targeting removeVertex 
        for(Vertex v : vertices.values()) {
            Edge removeEdge = null;
            for(Edge e : v.edgesLeaving)
                if(e.target == removeVertex)
                    removeEdge = e;
            // and remove any such edges that are found
            if(removeEdge != null) v.edgesLeaving.remove(removeEdge);
        }
        // finally remove the vertex and all edges contained within it
        return vertices.remove(data) != null;
	}

	@Override
	public boolean insertEdge(IPlace source, IPlace target, int weight) {
		if(source == null || target == null) 
            throw new NullPointerException("Cannot add edge with null source or target");
        Vertex sourceVertex = this.vertices.get(source);
        Vertex targetVertex = this.vertices.get(target);
        if(sourceVertex == null || targetVertex == null) 
            throw new IllegalArgumentException("Cannot add edge with vertices that do not exist");
        if(weight < 0) 
            throw new IllegalArgumentException("Cannot add edge with negative weight");
        // handle cases where edge already exists between these verticies
        for(Edge e : sourceVertex.edgesLeaving)
            if(e.target == targetVertex) {
                if(e.weight == weight) return false; // edge already exists
                else e.weight = weight; // otherwise update weight of existing edge
                return true;
            }
        // otherwise add new edge to sourceVertex
        sourceVertex.edgesLeaving.add(new Edge(targetVertex,weight));
        return true;
	}

	@Override
	public boolean removeEdge(IPlace source, IPlace target) {
		 if(source == null || target == null) throw new NullPointerException("Cannot remove edge with null source or target");
	        Vertex sourceVertex = this.vertices.get(source);
	        Vertex targetVertex = this.vertices.get(target);
	        if(sourceVertex == null || targetVertex == null) throw new IllegalArgumentException("Cannot remove edge with vertices that do not exist");
	        // find edge to remove
	        Edge removeEdge = null;
	        for(Edge e : sourceVertex.edgesLeaving)
	            if(e.target == targetVertex) 
	                removeEdge = e;
	        if(removeEdge != null) { // remove edge that is successfully found                
	            sourceVertex.edgesLeaving.remove(removeEdge);
	            return true;
	        }
	        return false;
	}

	@Override
	public boolean containsVertex(IPlace data) {
		if(data == null) throw new NullPointerException("Cannot contain null data vertex");
        return vertices.containsKey(data);
	}

	@Override
	public boolean containsEdge(IPlace source, IPlace target) {
		 if(source == null || target == null) throw new NullPointerException("Cannot contain edge adjacent to null data"); 
	     Vertex sourceVertex = vertices.get(source);
	     Vertex targetVertex = vertices.get(target);
	     if(sourceVertex == null) return false;
	     for(Edge e : sourceVertex.edgesLeaving)
	         if(e.target == targetVertex)
	             return true;
	     return false;
	}

	@Override
	public int getWeight(IPlace source, IPlace target) {
		 if(source == null || target == null) throw new NullPointerException("Cannot contain weighted edge adjacent to null data"); 
	        Vertex sourceVertex = vertices.get(source);
	        Vertex targetVertex = vertices.get(target);
	        if(sourceVertex == null || targetVertex == null) throw new IllegalArgumentException("Cannot retrieve weight of edge between vertices that do not exist");
	        for(Edge e : sourceVertex.edgesLeaving)
	            if(e.target == targetVertex)
	                return e.weight;
	        throw new NoSuchElementException("No directed edge found between these vertices");
	}

	protected Path dijkstrasShortestPath(IPlace start, IPlace end) {
    	Vertex Start = vertices.get(start);
    	if (Start == null) throw new NoSuchElementException("no path found");
    	
    	PriorityQueue<Path> frontier = new PriorityQueue<Path>();
    	List<Path> visitedP = new ArrayList<Path>();      //for visited paths
    	List<Vertex> visitedV = new ArrayList<Vertex>();  //for visited vertices
    	Path s = new Path(Start);
    	frontier.add(s);
    	
    	while(!frontier.isEmpty()) {
    		Path polled = frontier.poll();
    		visitedP.add(polled);
    		if(!visitedV.contains(polled.end)) {
    			for(int i=0; i<polled.end.edgesLeaving.size(); i++) {
    				Path connected = new Path(polled, polled.end.edgesLeaving.get(i));
    				if (!frontier.contains(connected) && !visitedP.contains(connected)) frontier.add(connected);}
    			visitedV.add(polled.end);
    			}
    		}
    	
    	Path result = null;
    	for(int i=0; i<visitedP.size(); i++) {
    		if(visitedP.get(i).end.data.equals(end) || visitedP.get(i).end.data == end) {
    			if (result==null) result=visitedP.get(i);
    			else { if(result.compareTo(visitedP.get(i)) > 0) result = visitedP.get(i);}
    		}
    	}
    	
    	if(result==null) throw new NoSuchElementException("no path found");
        return result;
    }
	
	@Override
	public List<IPlace> shortestPath(IPlace start, IPlace end) {
		return dijkstrasShortestPath(start,end).dataSequence;
	}

	protected class Path implements Comparable<Path> {
        public Vertex start; // first vertex within path
        public int distance; // sumed weight of all edges in path
        public List<IPlace> dataSequence; // ordered sequence of data from vertices in path
        public Vertex end; // last vertex within path

        /**
         * Creates a new path containing a single vertex.  Since this vertex is both
         * the start and end of the path, it's initial distance is zero.
         * @param start is the first vertex on this path
         */
        public Path(Vertex start) {
            this.start = start;
            this.distance = 0;
            this.dataSequence = new LinkedList<>();
            this.dataSequence.add(start.data);
            this.end = start;
        }

        /**
         * This extension constructor makes a copy of the path passed into it as an argument
         * without affecting the original path object (copyPath). The path is then extended
         * by the Edge object extendBy.
         * @param copyPath is the path that is being copied
         * @param extendBy is the edge the copied path is extended by
         */
		public Path(Path copyPath, Edge extendBy) {
            this.start = copyPath.start;
            this.distance = copyPath.distance + extendBy.weight;
            this.dataSequence = new LinkedList<>();
            this.dataSequence.addAll(copyPath.dataSequence);
            this.dataSequence.add(extendBy.target.data);
            this.end = extendBy.target;
        }

        /**
         * Allows the natural ordering of paths to be increasing with path distance.
         * When path distance is equal, the string comparison of end vertex data is used to break ties.
         * @param other is the other path that is being compared to this one
         * @return -1 when this path has a smaller distance than the other,
         *         +1 when this path has a larger distance that the other,
         *         and the comparison of end vertex data in string form when these distances are tied
         */
        public int compareTo(Path other) {
            int cmp = this.distance - other.distance;
            if(cmp != 0) return cmp; // use path distance as the natural ordering
            // when path distances are equal, break ties by comparing the string
            // representation of data in the end vertex of each path
            return this.end.data.toString().compareTo(other.end.data.toString());
        }
    }
	
	@Override
	public int getPathCost(IPlace start, IPlace end) {
		return dijkstrasShortestPath(start, end).distance;
	}

	@Override
	public boolean isEmpty() {
		 return vertices.size() == 0;
	}

	@Override
	public int getEdgeCount() {
		int edgeCount = 0;
        for(Vertex v : vertices.values())
            edgeCount += v.edgesLeaving.size();
        return edgeCount;
	}

	@Override
	public int getVertexCount() {
		 return vertices.size();
	}




}
