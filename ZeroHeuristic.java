
public class ZeroHeuristic implements Strategy{
	public int  timeLeft(State currentState, TripPlanner tp){
		return 0;
	}

	@Override
	public int addtionalTrips(State currentState) {
		// TODO Auto-generated method stub
		return 0;
	}
}
