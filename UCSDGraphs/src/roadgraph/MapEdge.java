package roadgraph;

import geography.GeographicPoint;

public class MapEdge {
	
	GeographicPoint startPoint = null;
	GeographicPoint endPoint = null;
	String roadName = null;
	String roadType = null;
	double distance = 0;
	
	MapEdge(GeographicPoint startPoint, GeographicPoint endPoint){
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		roadName = new String();
		roadType = new String();
		distance = endPoint.distance(startPoint);
	}

	public GeographicPoint getStartPoint() {
		return startPoint;
	}

	public GeographicPoint getEndPoint() {
		return endPoint;
	}

	public String getRoadName() {
		return roadName;
	}

	public String getRoadType() {
		return roadType;
	}

	public double getDistance() {
		return distance;
	}

	public void setStartPoint(GeographicPoint startPoint) {
		this.startPoint = startPoint;
	}

	public void setEndPoint(GeographicPoint endPoint) {
		this.endPoint = endPoint;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

}
