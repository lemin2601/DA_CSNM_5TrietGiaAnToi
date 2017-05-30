package dinningphilosopherproblem;

public class PhilosopherProblem implements Runnable{
	
	private static Philosopher[] philosopher;
	private static ChopStick[] chopStick;
	private Thread thread;
	private Thread threadPhilo[];
	private boolean isrunning = false;
	private int NUM_OF_PHILOSOPHER = Information.NUM_OF_PHILOSOPHER;
	public PhilosopherProblem(int numberPhilosopher){
		NUM_OF_PHILOSOPHER = numberPhilosopher;
	}
	public void run(){
		philosopher = new Philosopher[NUM_OF_PHILOSOPHER];
		chopStick = new ChopStick[NUM_OF_PHILOSOPHER];	
		threadPhilo = new Thread[NUM_OF_PHILOSOPHER];	
		
		for(int i=0;i<NUM_OF_PHILOSOPHER;i++){
			chopStick[i] = new ChopStick(i);
		}
		for(int i=0;i<NUM_OF_PHILOSOPHER;i++){
			philosopher[i] = new Philosopher(i,chopStick[i],chopStick[(i+1)%NUM_OF_PHILOSOPHER]);
		}
		for(int i=0;i<NUM_OF_PHILOSOPHER;i++){
			threadPhilo[i]  = new Thread(philosopher[i]);
		}	
		for(int i=0;i<NUM_OF_PHILOSOPHER;i++){
			threadPhilo[i].start();	
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public synchronized void start(){
		if(isrunning) return;
		isrunning = true;
		thread = new Thread(this);		
		thread.start();	
	}
	public synchronized void stop(){
		if(!isrunning) return;
		isrunning  = false;
		try {
			for(int i=0;i<NUM_OF_PHILOSOPHER;i++){
				threadPhilo[i].stop();	
			}
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] agrs){
		PhilosopherProblem tmp = new PhilosopherProblem(Information.NUM_OF_PHILOSOPHER);
		tmp.start();
	}
	

	public static Philosopher[] getPhilosopher() {
		return philosopher;
	}
	public static ChopStick[] getChopStick() {
		return chopStick;
	}
}
