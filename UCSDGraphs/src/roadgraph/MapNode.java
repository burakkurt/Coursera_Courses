package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

public class MapNode implements Comparable<MapNode>{
	
	private GeographicPoint location = null;
	private List<MapEdge> edges = null;
	private double distance = Double.POSITIVE_INFINITY;
	
	MapNode(GeographicPoint location){
		this.location = location;
		edges = new ArrayList<MapEdge>();
	}
	
	public void addEdges(MapEdge newEdge){
		this.edges.add(newEdge);
	}

	public GeographicPoint getLocation() {
		return location;
	}

	public List<MapEdge> getEdges() {
		return edges;
	}

	public void setLocation(GeographicPoint location) {
		this.location = location;
	}
	
	public void setEdges(List<MapEdge> edges) {
		this.edges = edges;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double d) {
		this.distance = d;
	}
	
	@Override
	public int compareTo(MapNode otherNode) {
		// TODO Auto-generated method stub
		
		if(this.getDistance() == otherNode.getDistance())
			return 0;
		else if(this.getDistance() > otherNode.getDistance())
			return 1;
		else
			return -1;
	}

}
