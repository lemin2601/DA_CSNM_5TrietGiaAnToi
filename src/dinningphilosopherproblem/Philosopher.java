package dinningphilosopherproblem;

import java.util.Random;

public class Philosopher implements Runnable{

	private int id;
	private ChopStick left,right;
	private int numOfCountEating;
	private Information.StatePhilosopher state = Information.StatePhilosopher.THINKING;

	public Philosopher(int id, ChopStick left,ChopStick right){
		this.id = id;
		this.left = left;
		this.right = right;		
		this.numOfCountEating =0;
	}
	@Override
	public void run() {
		while(true){		
			Think();
			while(state != Information.StatePhilosopher.EATING)
				WaitChopStick();
		}
	}
	private void Think(){
		try {
			state = Information.StatePhilosopher.THINKING;
			Thread.sleep(Information.getTimeThinkingRandom());			
			System.out.println("Philo:" +id+ " thinking");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void WaitChopStick(){
		state = Information.StatePhilosopher.WAITING;		
		try {
			if (left.take(id)) {
			    if (right.take(id)) {
			      // Eat some.
			     // state = STATE.EATING;
			      Eat();
			      // Finished.			      
			      right.release();			     
			    }else{
			    	Thread.sleep(Information.getTimeWaitingRandom());
			    }
			    // Finished.
			    left.release();
			  }
		} catch (InterruptedException e) {
			e.printStackTrace();
		}				
	}
	private void Eat(){
		try {
			numOfCountEating ++;
			state = Information.StatePhilosopher.EATING;
			Thread.sleep(Information.getTimeEatingRandom());		
			System.out.println("Philo:" +id+ " eating");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public Information.StatePhilosopher getState(){
		return state;
	}
	public int getId(){
		return id;
	}
	public int getNumOfCountEating(){
		return numOfCountEating;
	}
	
}
