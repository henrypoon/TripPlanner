import java.util.ArrayList;

public class State implements Comparable<State> {

	private City currentCity;
	private int gCost;
	private State prevState;
	private int fCost;
	private int hCost;
	private int testTrips;
	private Strategy strategy;
	/**
	 * Constructor for a new state
	 * @param from the current City
	 * @param costSoFar the cost to this point so far
	 * @param pState the previous state
	 */
	public State(City from, int costSoFar, State pState) {
		currentCity = from;
		gCost = costSoFar;
		prevState = pState;
		hCost = 0;
		fCost = calculateFCost(); 
		strategy = new basicHeuristic();
	}

	
	public ArrayList<Edge> getVisited(TripPlanner tp){
		ArrayList<Edge> visitedList= new ArrayList<Edge>();
		ArrayList<Edge> tempList = (ArrayList<Edge>)(tp.getTrips()).clone();

		State temp = this;
		while (temp.getPreviousState() != null){
			City prevCity = temp.getPreviousState().getCity();
			for(Edge e: tempList){
				if (temp.getCity().equals(e.getCityTo()) && prevCity.equals(e.getCityFrom())){
					boolean exist = false;
					for(Edge k:visitedList){
						if (k.equals(e)){
							exist = true;
							break;
						} 
					}
					if (exist == false){
						visitedList.add(e);
					}
				}
			}
			temp = temp.getPreviousState();		
		}
		return visitedList;
	 }
	
	
	public ArrayList<Edge>  getUnvisited(TripPlanner tp){
		ArrayList<Edge> unvisited = (ArrayList<Edge>)((ArrayList<Edge>)tp.getTrips()).clone();
		unvisited.removeAll(this.getVisited(tp));
		return unvisited;
	}
	
	
	
	
	
	public int compareTo(State o) {
		return calculateFCost() - o.calculateFCost();
	}

	private int calculateFCost() {
		return gCost + hCost;
	}

	public State getPreviousState(){
		return prevState;
	}
	
	public void setTestTrips(int trips){
		this.testTrips = trips;
	}
	
	public int getTest(){
		return this.testTrips;
	}
	
	public City getCity() {
		return currentCity;
	}

	public int getGCost() {
		return gCost;
	}
	
	public int getfCost(){
		return fCost;
	}
	
	public void sethCost(int hCost){
		this.hCost = hCost;
	}

	public void printCurrentPath() {
		String toPrint = getPathString("");
		System.out.println(toPrint);
	}

	/**
	 * Recursive method to print the path out using previous states
	 * @param s the string to append to
	 * @return an appended string
	 */
	private String getPathString(String s) {
		String returnString = currentCity.getCityName().toString().concat(s);
		if (prevState != null) {
			return prevState.getPathString("\n"+prevState.getCity().getCityName()+" to "+returnString);
		}
		return returnString;
	}
	
	public void printCurrentPathAndCosts(){
		String toPrint = getPathString("");
		String s = "London"+"\n";
		toPrint = toPrint.substring(s.length());
		System.out.println("Cost = " + this.getGCost() + "\n" + toPrint);
	}
	
	private String getPathString1(String s) {
		String returnString = currentCity.getCityName().toString().concat(s);
		if (prevState != null) {
			return prevState.getPathString1("->"+returnString);
		}
		return returnString;
	}
	
	public void printTest(){
		String toPrint = getPathString1("");
		System.out.println(toPrint);
	}

}
