package bean;

import bean.Chopstick.State;

public class Philosopher {
	public enum State {THINKING, HUNGRY, EATING};
	private int id;
	private volatile State state;
	private int numOfCountEat;
	public Philosopher(int id){
		this.id =id;
		this.numOfCountEat  =0;
		state = State.THINKING;
	}
	public int getId() {
		return id;
	}
	public State getState() {
		return state;
	}
	public synchronized void setState(State state) {
		this.state = state;
		if(state == State.EATING) numOfCountEat ++;
	}
	public int getNumOfCountEat(){
		return numOfCountEat;
	}
}
