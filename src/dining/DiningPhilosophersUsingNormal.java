package dining;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import bean.Chopstick;
import dinningphilosopherproblem.Information;


public class DiningPhilosophersUsingNormal  implements DiningInterface {
	// The number of philosophers
	private static int NUM_PHILOSOPHERS = 5;
	
	
	public static void main (String[] args) {
		DiningState.setNumOfPhilosopher(NUM_PHILOSOPHERS);
		// Model each chopstick with a lock
		chopstick[] chopsticks = new chopstick[NUM_PHILOSOPHERS];
		
		for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
			chopsticks[i] = new chopstick(i);
		}
		
		// Create the philosophers and start each running in its own thread.
		Philosopher[] philosophers = new Philosopher[NUM_PHILOSOPHERS];
		
		for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
			philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[(i+1)%NUM_PHILOSOPHERS]);
			new Thread(philosophers[i]).start();
		}
	}
	chopstick[] chopsticks;
	Philosopher[] philosophers;
	Thread[] threadsphilo;
	public DiningPhilosophersUsingNormal() {
		NUM_PHILOSOPHERS = DiningState.numOfPhilosopher;
		chopsticks = new chopstick[NUM_PHILOSOPHERS];
		threadsphilo = new Thread[NUM_PHILOSOPHERS];
		philosophers = new Philosopher[NUM_PHILOSOPHERS];
		DiningState.setNumOfPhilosopher(NUM_PHILOSOPHERS);
		for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
			chopsticks[i] = new chopstick(i);
		}
		for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
			philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[(i+1)%NUM_PHILOSOPHERS]);
			threadsphilo[i] = new Thread(philosophers[i]);
		}
	}
	@Override
	public void start() {
		for (Thread thread : threadsphilo) {
			thread.start();
		}
	}
	@Override
	public void stop() {
		for (Thread thread : threadsphilo) {
			thread.stop();
		}
	}

}


class Philosopher implements Runnable {
	// Used to vary how long a philosopher thinks before eating and how long the
	// philosopher eats
	private Random numGenerator = new Random();
	
	// The philosopher's unique id
	private int id;
	
	// The chopsticks this philosopher may use
	private chopstick leftChopstick;
	private chopstick rightChopstick;
	
	/**
	 * Constructs a new philosopher
	 * @param id the unique id
	 * @param leftChopstick chopstick to the left
	 * @param rightChopstick chopstick to the right
	 */
	public Philosopher (int id, chopstick leftChopstick, chopstick rightChopstick) {
		this.id = id;
		this.leftChopstick = leftChopstick;
		this.rightChopstick = rightChopstick;
	}
	
	/**
	 * Repeatedly think, pick up chopsticks, eat and put down chopsticks
	 */
	public void run() {
		try {
			while (true) {
				think();
				DiningState.setHungry(id);
				pickUpLeftChopstick();
				//Thread.sleep (DiningState.getTimeWaitingRandom());
				pickUpRightChopstick();
				eat();
				putDownChopsticks();
			}
		} catch (InterruptedException e) {
			System.out.println("Philosopher " + id + " was interrupted.\n");			
		}
	}

	/**
	 * Lets a random amount of time pass to model thinking.
	 * @throws InterruptedException
	 */
	private void think() throws InterruptedException {
		DiningState.setThinking(id);
		System.out.println("Philosopher " + id + " is thinking.\n");
		System.out.flush();
		Thread.sleep (DiningState.getTimeThinkingRandom());
	}
	
	/** 
	 * Locks the left chopstick to signify that this philosopher is holding it
	 * @throws InterruptedException 
	 */
	private void pickUpLeftChopstick() throws InterruptedException {
		while(!leftChopstick.take(id)){};
		System.out.println("Philosopher " + id + " is holding 1 chopstick.\n");
		System.out.flush();
	}

	/** 
	 * Locks the right chopstick to signify that this philosopher is holding it
	 * @throws InterruptedException 
	 */
	private void pickUpRightChopstick() throws InterruptedException {
		while(!rightChopstick.take(id)){};				
		System.out.println("Philosopher " + id + " is holding 2 chopsticks.\n");
		System.out.flush();
	}

	/**
	 * Lets a random amount of time pass to model eating.
	 * @throws InterruptedException
	 */
	private void eat() throws InterruptedException {
		DiningState.setEating(id);
		System.out.println("Philosopher " + id + " is eating.\n");
		System.out.flush();
		Thread.sleep (DiningState.getTimeEatingRandom());
	}
	
	/**
	 * Releases the locks on both chopsticks to model putting them down so the
	 * other philosophers can use them.
	 */
	private void putDownChopsticks() {
		leftChopstick.release();	
		rightChopstick.release();
		System.out.println("Philosopher " + id + " laid down both chopsticks.\n");
		System.out.flush();
	}
}
class chopstick {
	Lock up = new ReentrantLock();	
	private int id;	
	public synchronized boolean take(int who) throws InterruptedException{		
		if (up.tryLock(10, TimeUnit.MILLISECONDS)) {
	       DiningState.setChopstickState(Chopstick.State.USING,who, id);
	        return true;
	      }
		  
	      return false;
	}
	public synchronized void release(){	
		DiningState.setChopstickRelease(id);
		up.unlock();
	}
	public chopstick(int id){
		this.id  = id;
		
	}
}