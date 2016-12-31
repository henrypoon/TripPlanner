import java.util.ArrayList;


public class City {

	private String cName;
	private int tTime;
	private ArrayList<Edge> edges;
	/**
	 * Constructor for a new city
	 * @param cityName the data to be stored on this city
	 * @param heuristicCost the cost to get from this city to the goal state
	 */
	public City (String cityName, int transferTime) {
		cName = cityName;
		tTime = transferTime;
		edges = new ArrayList<Edge>();
	}

	/**
	 * Method to create a new edge between two cities
	 * TODO: check that the edge doesn't already exist - or maybe
	 * multiple edges is permitted?
	 * @param cityTo the city you are connecting to
	 * @param edgeCost the cost to traverse this edge
	 */
	public void createEdgeTo(City cityTo, int travelTime) {
		if (existanceCheck(cityTo) == false){
			Edge newEdge = new Edge(this, cityTo, travelTime);
			edges.add(newEdge);
		}

		// To make this graph undirected we need to add this
		// edge in reverse too.
		// NOTE: because this code is inside city.java I can
		// directly access the private data of cityTo
		// this is probably not very good programming sytle.
//		cityTo.edges.add(newEdge);

	}
	

	public Edge findEdge(City toCity){
		for(Edge e:edges){
			if(e.getCityTo().equals(toCity)){
				return e;
			}
		}
		return null;
	}
	

	
	public int getTransferTime() {
		return tTime;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public String getCityName() {
		return cName;
	}
	
	public int getTimeFromEdge(City cityTo){
		for (Edge e: this.getEdges()){
			if (e.getCityTo()==cityTo){
				return e.getTravelTime();
			}
		}
		return 0;
	}
	
	
	
	private boolean existanceCheck(City cityTo){
		for(Edge e: edges){
			if (e.getOtherEnd(this).getCityName().equals(cityTo)){
				return true;
			}
		}
		return false;
	}
	
	
}
