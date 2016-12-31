import java.util.ArrayList;
import java.util.Collections;

public class basicHeuristic implements Strategy {
	
	private ArrayList<Edge> visited;
	private ArrayList<Edge> unvisited;
		
	public int timeLeft(State currentState, TripPlanner tp){
		int time = 0;

		visited = currentState.getVisited(tp);
		unvisited = currentState.getUnvisited(tp);
		int totalTransferTime = countTransferTime(currentState);
		int addtionalTrip = addtionalTrips(currentState);		
		for(Edge e:unvisited){
			time = time + e.getTravelTime();
		}		
		
	
		
		return time + totalTransferTime + addtionalTrip*tp.getShortestEdge();
	} 
	
	
	
	
	private int countTransferTime(State currentState){
		int time = 0;
		for(Edge e: unvisited){
			int startTransferTime = e.getCityFrom().getTransferTime();		
			int endTransferTime = e.getCityTo().getTransferTime();
			if(!addEndCity(e.getCityTo())){
				endTransferTime = 0;
			}
			time = time + startTransferTime + endTransferTime;
		}
		

		return time;
	}
	
	private boolean addEndCity(City endCity){
		for(Edge e : unvisited){
			if(e.getCityFrom().equals(endCity)){
				return false;
			}
			
		}
		return true;
	}
	
	public int addtionalTrips(State currentState){

		int trips = 0;
		for(Edge i: unvisited){
			boolean connected = false;
			for (Edge j: unvisited){
				if (i.getCityTo().equals(j.getCityFrom())){
					connected = true;
					break;
				}
			}
			if (connected == false){
				trips++;
			}
		}
		
		for(Edge e: unvisited){
			if (e.getCityFrom().equals(currentState.getCity()));
			trips--;
			break;
		}
		trips++;
		
		return trips;
	}
	
	
}

