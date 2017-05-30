package dining;

//Tham khảo tại: http://cs.nyu.edu/wies/teaching/ppc-14/material/DiningPhilosophers.java
import java.util.Random;

import bean.Philosopher;

public class DiningPhilosophersUsingMonitor implements DiningInterface{
	
	private Thread[] threadPhilo;
	Philosopher5[] philosophers;
	public static void main (String[] args) {
		Philosopher5[] philosophers = new Philosopher5[DiningState.numOfPhilosopher];
		
		// Monitor chắc chắn rằng các triết gia sẽ cầm lên cùng lúc 2 chiếc đĩa.
		PhilosopherMonitor monitor = new PhilosopherMonitor(DiningState.numOfPhilosopher);
		DiningState.setNumOfPhilosopher(DiningState.numOfPhilosopher);
		// Khởi tạo và chạy
		for (int i = 0; i < DiningState.numOfPhilosopher; i++) {
			philosophers[i] = new Philosopher5(i, monitor);
			new Thread(philosophers[i]).start();
		}
	}
	public DiningPhilosophersUsingMonitor(){
		philosophers = new Philosopher5[DiningState.numOfPhilosopher];
		threadPhilo = new Thread[DiningState.numOfPhilosopher];
		// Monitor chắc chắn rằng các triết gia sẽ cầm lên cùng lúc 2 chiếc đĩa.
		PhilosopherMonitor monitor = new PhilosopherMonitor(DiningState.numOfPhilosopher);
		DiningState.setNumOfPhilosopher(DiningState.numOfPhilosopher);
		// Khởi tạo và chạy
		for (int i = 0; i < DiningState.numOfPhilosopher; i++) {
			philosophers[i] = new Philosopher5(i, monitor);
			threadPhilo[i] = new Thread(philosophers[i]);
		}
	}
	@Override
	public void start() {
		for (Thread thread : threadPhilo) {
			thread.start();
		}
	}
	@Override
	public void stop() {
		for (Thread thread : threadPhilo) {
			thread.stop();
		}
	}
}

/**
 * Các triết gia thay đổi giữa các trạng thái thinking và eating. Khi eat thì triết gia cần cầm
 * đĩa bên trái sau đó đĩa bên phải. Các triết gia chia sẽ các đĩa với người bên cạnh, vì thế
 * không thể ăn cùng thời gian với người bên cạnh
 * @author Lê Minh Trung 13T4
 * @version 3/23/2017
 *
 */
class Philosopher5 implements Runnable {
	
	// Mỗi triết gia sẽ có một id để quản lý
	private int id;
	
	// Điều khiển việc cầm đĩa bằng phương pháp monitor
	private PhilosopherMonitor monitor;
	
	/**
	 * Constructs a new philosopher
	 * @param id Triết gia
	 * @param monitor Điều khiển việc cầm đĩa của các triết gia
	 */
	public Philosopher5 (int id, PhilosopherMonitor monitor) {
		this.id = id;
		this.monitor = monitor;
	}
	
	/**
	 * Thực hiện lặp đi lặp lại việc
	 * think -> pickUpChopstick -> eat -> putDownChopstick -> think (again)
	 */
	public void run() {
		try {
			while (true) {
				think();
				monitor.pickUpChopsticks(id);
				eat();
				monitor.putDownChopsticks(id);
			}
		} catch (InterruptedException e) {
			System.out.println("Triết gia " + id + " đã bị gián đoạn.\n");			
		}
	}

	/**
	 * Random thời gian suy nghĩ.
	 * @throws InterruptedException
	 */
	private void think() throws InterruptedException {
		System.out.println("Triết gia " + id + " đang suy nghĩ.\n");
		System.out.flush();
		Thread.sleep (DiningState.getTimeThinkingRandom());
	}
	
	/**
	 * Random thời gian ăn
	 * @throws InterruptedException
	 */
	private void eat() throws InterruptedException {
		System.out.println("Triết gia " + id + " đang ăn.\n");
		System.out.flush();
		Thread.sleep (DiningState.getTimeEatingRandom());
	}
}

/**
 * Class đảm bảo rằng chỉ cần đĩa khi không có triết gia nào bên cạnh đang ăn *
 */
class PhilosopherMonitor {
	// Các trạng thái của các triết gia
	private enum State {THINKING, HUNGRY, EATING};
	
	// Trạng thái của triết gia
	private State[] philosopherState;
	
	/**
	 * Khởi tạo ban đầu là tất cả các triết gia đều đang suy nghĩ
	 * @param numPhilosophers Tổng số các triết gia
	 */
	public PhilosopherMonitor (int numPhilosophers) {
		philosopherState = new State[numPhilosophers];
		for (int i = 0; i < philosopherState.length; i++) {
			philosopherState[i] = State.THINKING;
		}
	}
	
	/**
	 * Triết gia cầm lên cả 2 đĩa. hoặc đợi nếu triết gia bên cạnh đang ăn
	 * @param philosopherId the philosopher who wants to eat
	 * @throws InterruptedException the thread was interrupted
	 */
	public synchronized void pickUpChopsticks(int philosopherId) throws InterruptedException {
		// Cập nhật trạng thái: triết gia đang đói
		philosopherState[philosopherId] = State.HUNGRY; 		DiningState.setHungry(philosopherId);
		System.out.println("Triết gia " + philosopherId + " đang đói.\n");
		System.out.flush();
		
		// Đợi nếu có bất kì triết gia nào bên cạnh đang ăn
		while (someNeighborIsEating(philosopherId)) {
			wait();
		}
		
		// Cập nhật lại trạng thái: Triết gia đang nă
		philosopherState[philosopherId] = State.EATING; 				DiningState.setEating(philosopherId);
	}

	/**
	 * Nếu có triết gia bên cạnh đang ăn -> return true
	 * @param philosopherId the philosopher whose neighbors are checked
	 * @return true if either neighbor is currently eating
	 */
	private boolean someNeighborIsEating(int philosopherId) {
		// Kiểm tra phía tăng
		if (philosopherState[(philosopherId + 1) % philosopherState.length] == State.EATING){
			return true;
		}

		// Kiểm tra phía giảm
		if (philosopherState[(philosopherId + philosopherState.length - 1) % philosopherState.length] == State.EATING){
			return true;
		}
		
		// Không ăn đang ăn
		return false;
	}

	/**
	 * Đặt đĩa xuống và Thông báo tới các triết gia đang wait (notiftAll)
	 *  
	 * @param philosopherId id triết gia đang ăn
	 */
	public synchronized void putDownChopsticks(int philosopherId) {
		philosopherState[philosopherId] = State.THINKING;	
		DiningState.setThinking(philosopherId);
		notifyAll();
	}

}