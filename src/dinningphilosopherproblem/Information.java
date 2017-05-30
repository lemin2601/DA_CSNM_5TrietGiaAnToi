package dinningphilosopherproblem;

import java.util.Random;

public class Information {
	public enum StatePhilosopher{
		THINKING,EATING,WAITING;
	}
	public enum StateChopStick {FREE,USING}
	public static int numOfMethod = 0;
	public static int NUM_OF_PHILOSOPHER = 5;
	public static int timeThinkingRandom = 3000;
	public static int timeEatingRandom =2000;
	public static int timeWaitingRandom =500;
	public static Random rd = new Random();
	
	public static int getTimeThinkingRandom(){
		return rd.nextInt(timeThinkingRandom);
	}
	public static int getTimeEatingRandom(){
		return rd.nextInt(timeEatingRandom);
	}
	public static int getTimeWaitingRandom(){
		return rd.nextInt(timeWaitingRandom);
	}
}
