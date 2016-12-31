import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class TripPlanner {
	private ArrayList<City> cities;
	private ArrayList<Edge> trips;
	private int nodesExpand;
	private Strategy strategy;
	
	
	public TripPlanner(){
		cities = new ArrayList<City>();
		trips = new ArrayList<Edge>();
		nodesExpand = 0;
		strategy = new basicHeuristic();
	}
		

	public static void main(String[] args) {

		TripPlanner tp = new TripPlanner();
		Scanner sc = null;
		try
		{
			sc = new Scanner(new FileReader(args[0]));
				tp.scanner(tp,sc);
	
		}
		  
	  
	  
		catch (FileNotFoundException e) {
			System.out.println(e);
		}
		finally
		{
			if (sc != null) sc.close();
		}
		tp.aStarSearch(tp.findCity("London"));
		
	}		
  

	private void scanner(TripPlanner tp,Scanner sc){
		String key;

		while (sc.hasNextLine() && sc.hasNext()){
			key = sc.next();
			if (key.equals("Transfer")){
				int tTime = sc.nextInt();
				String cityName = sc.next();
				City newCity = new City(cityName,tTime);
				tp.addCity(newCity);
			} else if (key.equals("Time")){
				int tTime = sc.nextInt();
				String from = sc.next();
				String to = sc.next();
				City cityFrom = findCity(from);
				City cityTo = findCity(to);
				addEdge(cityFrom,cityTo,tTime);
				addEdge(cityTo,cityFrom,tTime);
			} else if (key.equals("Trip")){
				String from = sc.next();
				String to = sc.next();
				City cityFrom = findCity(from);
				City cityTo = findCity(to);
				int travelTime = cityFrom.getTimeFromEdge(cityTo);
				addTrips(cityFrom,cityTo,travelTime);
				
			}
		}
	}


	public void aStarSearch(City from){
		State iniState = new State(from,0,null);
		nodesExpand = 0;
		PriorityQueue<State> stateQueue = new PriorityQueue<State>();
		stateQueue.add(iniState);
		while(stateQueue.size()>0){
			State currentState=stateQueue.poll();
			//System.out.print(currentState.getGCost()+" "+currentState.getTest()+" ");
			//currentState.printTest();
			nodesExpand++;
			ArrayList<Edge> visited = currentState.getVisited(this);
			ArrayList<Edge> unvisited = currentState.getUnvisited(this);
			if(visited.size()==this.getTrips().size()){
				System.out.println(nodesExpand+" nodes expanded");
				currentState.printCurrentPathAndCosts();
				break;
			}
			
			for (Edge e: unvisited){
				if (e.getCityFrom().equals(currentState.getCity())){
					int Gcost = 0;
					if (currentState.getPreviousState()==null){
						Gcost = e.getTravelTime();
					} else {
						Gcost = currentState.getGCost()+currentState.getCity().getTransferTime()+e.getTravelTime();
					}
					
					State newState = new State(e.getCityTo(),Gcost,currentState);
					int hCost = strategy.timeLeft(newState, this);
					newState.sethCost(hCost);
					newState.setTestTrips(strategy.addtionalTrips(newState));
				//	System.out.print(newState.getGCost()+" ");
				//	newState.printTest();
					stateQueue.add(newState);
				} else {
					int Gcost1 = 0;
					if (currentState.getPreviousState() == null){
						Gcost1 = currentState.getCity().findEdge(e.getCityFrom()).getTravelTime();
					} else {
						Gcost1 = currentState.getGCost()+currentState.getCity().getTransferTime()+currentState.getCity().findEdge(e.getCityFrom()).getTravelTime();
					}
					State newState1 = new State(e.getCityFrom(),Gcost1,currentState);
					int hCost = strategy.timeLeft(newState1, this);
					newState1.sethCost(hCost);
					newState1.setTestTrips(strategy.addtionalTrips(newState1));
					int Gcost2 = newState1.getGCost()+e.getCityFrom().getTransferTime()+e.getTravelTime();
					State newState2 = new State(e.getCityTo(),Gcost2,newState1);
					hCost = strategy.timeLeft(newState2, this);
					newState2.sethCost(hCost);
					newState1.setTestTrips(strategy.addtionalTrips(newState2));
				//	System.out.print(newState2.getGCost()+" ");
				//	newState2.printTest();
					stateQueue.add(newState2);
				}
			}
		}	
	
	}
	
	
	public ArrayList<Edge> getTrips(){
		return trips;
	}
	
	public ArrayList<City> getCityList(){
		return cities;
	}
	
	
	private void addCity(City newCity){
		cities.add(newCity);
	}

	private void addEdge(City cityFrom, City cityTo, int travelTime){
		cityFrom.createEdgeTo(cityTo, travelTime);
	}

	private void addTrips(City cityFrom,City cityTo,int travelTime){
		Edge newTrip = new Edge(cityFrom, cityTo, travelTime);
		trips.add(newTrip);
	}
	
	
	public int getShortestEdge(){
		int se = this.getCityList().get(0).getEdges().get(0).getTravelTime();
		for(City c: this.getCityList()){
			for (Edge e:c.getEdges()){
				if (e.getTravelTime()<se){
					se = e.getTravelTime();
				}
			}
		}
		return se;
	}

	private City findCity(String CityName){
		for (City i : cities){
			if(i.getCityName().equals(CityName)){
				return i;
			}
		}
		return null;
	}
}


