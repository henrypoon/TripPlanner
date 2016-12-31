
public interface Strategy {
	public int  timeLeft(State currentState, TripPlanner tripPlanner);
	public int addtionalTrips(State currentState);
}
