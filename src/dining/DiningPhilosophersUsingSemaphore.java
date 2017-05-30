package dining;

import java.util.concurrent.Semaphore;

import bean.Philosopher;

public class DiningPhilosophersUsingSemaphore implements DiningInterface{

  private static PhilosopherSemaphore[] philosophers = new PhilosopherSemaphore[DiningState.numOfPhilosopher];

  final static Semaphore mutex = new Semaphore(1);

  public static void main(String[] args) {
    
    // Initialize threads
    philosophers[0] = new PhilosopherSemaphore(0);
    for (int i = 1; i < DiningState.numOfPhilosopher; i++) {
      philosophers[i] = new PhilosopherSemaphore(i);
    }
    DiningState.setNumOfPhilosopher(DiningState.numOfPhilosopher);
    // Start the threads
    for (Thread t : philosophers) {
      t.start();
    }
  }
  public DiningPhilosophersUsingSemaphore(){
	   philosophers = new PhilosopherSemaphore[DiningState.numOfPhilosopher];
	  // Initialize threads
	    philosophers[0] = new PhilosopherSemaphore(0);
	    for (int i = 1; i < DiningState.numOfPhilosopher; i++) {
	      philosophers[i] = new PhilosopherSemaphore(i);
	    }
	    DiningState.setNumOfPhilosopher(DiningState.numOfPhilosopher);
	    
  }
  public static class PhilosopherSemaphore extends Thread {
	  
    private enum State {THINKING, HUNGRY, EATING};

    private final int id;
    private State state;
    private final Semaphore self;

    PhilosopherSemaphore(int id) {
    
      this.id = id;
      self = new Semaphore(0);
      state = State.THINKING;
    }
    
    private PhilosopherSemaphore right() {
      return philosophers[id == 0 ? DiningState.numOfPhilosopher - 1 : id - 1];
    }

    private PhilosopherSemaphore left() {
      return philosophers[(id + 1) % DiningState.numOfPhilosopher];
    }
    
    public void run() {
      try {
        while (true) {
          printState();
          switch(state) {
          case THINKING: 
            think();
            mutex.acquire();
            state = State.HUNGRY;DiningState.setHungry(id);
            break;
          case HUNGRY:
            // aquire both forks, i.e. only eat if no neighbor is eating
            // otherwise wait
            test(this);
            mutex.release();
            self.acquire();
            state = State.EATING;DiningState.setEating(id);
            break;
          case EATING:
            eat();
            mutex.acquire();
            state = State.THINKING;DiningState.setThinking(id);
            // if a hungry neighbor can now eat, nudge the neighbor.
            test(left());  
            test(right());
            mutex.release();
            break;          
          }
        }
      } catch(InterruptedException e) {}
    }

    static private void test(PhilosopherSemaphore p) {
      if (p.left().state != State.EATING && p.state == State.HUNGRY &&
          p.right().state != State.EATING) {
        p.state = State.EATING; DiningState.setEating(p.id);
        p.self.release();
      }
    }

    private void think() {
      try {
        Thread.sleep(DiningState.getTimeThinkingRandom());
      } catch (InterruptedException e) {}
    }
    private void eat() {
        try {
          Thread.sleep(DiningState.getTimeEatingRandom());
        } catch (InterruptedException e) {}
      }

    private void printState() {
      System.out.println("Philosopher " + id + " is " + state);
    }
  }
	@Override
	public void start() {
		// Start the threads
	    for (Thread t : philosophers) {
	      t.start();
	    }
	}
	@Override
	public void stop() {
		// Start the threads
	    for (Thread t : philosophers) {
	      t.stop();
	    }
	}
}
