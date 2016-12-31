
public class Edge {

	private City from;
	private City to;
	private int tTime;

	/**
	 * Constructor for an edge
	 * @param CityFrom one end of the edge
	 * @param CityTo the other end of the edge
	 * @param edgeCost the cost to traverse the edge
	 */
	public Edge(City cityFrom, City cityTo, int travelTime) {
		from = cityFrom;
		to = cityTo;
		tTime = travelTime;
	}

	/**
	 * Method to return the other end of an edge.
	 * NOTE: this method does not check if this city actually contains
	 * the edge that is passed into it.
	 * @param city
	 * @return
	 */
	public City getCityFrom(){
		return from;
	}
	
	public City getCityTo(){
		return to;
	}
	
	
	public City getOtherEnd(City city) {
		if (from == city) {
			return to;
		}
		return from;
	}

	public int getTravelTime() {
		return tTime;
	}

}
