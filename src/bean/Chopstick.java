package bean;

import bean.Philosopher.State;

public class Chopstick {
	public enum State {FREE,USING};
	private int id;
	private int idUse;
	private volatile State state;
	public Chopstick(int id){
		this.id =id;
		state = State.FREE;
	}
	public int getId() {
		return id;
	}
	public State getState() {
		return state;
	}
	public synchronized void setState(State state,int idUse) {
		this.idUse = idUse;
		this.state = state;
	}
	public void setState(State state){
		this.state = state;
		this.idUse = -1;
	}
	public int getIdUse(){
		return idUse;
	}
}
