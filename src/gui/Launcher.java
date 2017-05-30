package gui;

import dinningphilosopherproblem.PhilosopherProblem;

public class Launcher {
	public static void main(String[] agrs){
		
//		DinningGUI din = new DinningGUI("Philosopher", 720, 540);
		DiningDisplay din = new DiningDisplay();
		din.start();
		
	}

}
