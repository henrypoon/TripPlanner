import java.util.ArrayList;

public class SimpleHeuristic implements Strategy {
	public int timeLeft(State currentState, TripPlanner tp){
		int shortestEdge = tp.getShortestEdge();
		ArrayList<Edge> unvisited = currentState.getUnvisited(tp);
		int time = unvisited.size()*shortestEdge;
		return time;
	}
	

	@Override
	public int addtionalTrips(State currentState) {
		
		return 0;
	}

	
}
