package ThamKhao_NotMe;

public class Philosopher1 extends Thread{

	private Fork forkLeft, forkRight;
	private PhilosopherState state;
	private int id;
	public Philosopher1(int id, Fork left,Fork right){
		state = PhilosopherState.THINKING;
		forkLeft = left;
		forkRight = right;			
	}	
	public enum PhilosopherState{
		EATING,THINKING,WAIT;
	}
	public Fork getForkLeft() {
		return forkLeft;
	}
	public void setForkLeft(Fork forkLeft) {
		this.forkLeft = forkLeft;
	}
	public Fork getForkRight() {
		return forkRight;
	}
	public void setForkRight(Fork forkRight) {
		this.forkRight = forkRight;
	}
	public PhilosopherState getStatePhilo() {
		return state;
	}
	public void setState(PhilosopherState state) {
		this.state = state;
	}
	public int getNumPhilo() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
