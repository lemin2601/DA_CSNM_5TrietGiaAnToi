package dining;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import bean.Chopstick;
import bean.Philosopher;
import gfx.Assets;

public class DiningState implements DiningInterface{
	public static enum Method{ MONITOR,SEMAPHORE,NORMAL};
	public static Method method = Method.MONITOR;
	public static int numOfPhilosopher =5;
	public static int timeThinkingRandom = 3000;
	public static int timeEatingRandom =2000;
	public static int timeWaitingRandom =500;
	
	public static Philosopher[] philosophers;
	public static Chopstick[] chopsticks;
	private static Random rd = new Random();
	private DiningInterface DiningLaucher;
	public static void setNumOfPhilosopher(int num){
		numOfPhilosopher = num;
		philosophers = new Philosopher[num];
		chopsticks = new Chopstick[num];
		for(int i = 0;i<num;i++){
			philosophers[i] = new Philosopher(i);
			chopsticks[i] = new Chopstick(i);
		}
	}
	public static void setEating(int id){
		philosophers[id].setState(Philosopher.State.EATING);
		chopsticks[id].setState(Chopstick.State.USING,id);
		chopsticks[(id+1)% numOfPhilosopher].setState(Chopstick.State.USING,id);
	}
	public static void setHungry(int id){
		philosophers[id].setState(Philosopher.State.HUNGRY);
	}
	public static void setThinking(int id){
		philosophers[id].setState(Philosopher.State.THINKING);
		chopsticks[id].setState(Chopstick.State.FREE);
		chopsticks[(id+1)% numOfPhilosopher].setState(Chopstick.State.FREE);
	}
	public static void setChopstickState(Chopstick.State state,int idUse,int idChopstick){
		chopsticks[idChopstick].setState(state,idUse);
	}
	public static void setChopstickRelease(int idChopstick){
		chopsticks[idChopstick].setState(Chopstick.State.FREE);
	}
	public static int getTimeThinkingRandom(){
		return rd.nextInt(timeThinkingRandom);
	}
	public static int getTimeEatingRandom(){
		return rd.nextInt(timeEatingRandom);
	}
	public static int getTimeWaitingRandom(){
		return rd.nextInt(timeWaitingRandom);
	}



	//constructor
	public DiningState(Method method){
		switch(method){
		case MONITOR:
			DiningLaucher = new DiningPhilosophersUsingMonitor();
			break;
		case SEMAPHORE:
			DiningLaucher = new DiningPhilosophersUsingSemaphore();
			break;
		case NORMAL:
			DiningLaucher = new DiningPhilosophersUsingNormal();
			break;
		default:
			break;
		}
	}
	//update infor
	public void tick() {

	}
	//draw on canvas
	public void render(Graphics g) {
		   Graphics2D g2d = (Graphics2D) g;
		   // vẽ nền phía sau
		   drawBackground(g2d);
		   // vẽ khuôn mặt
		   drawFace(g2d);
		   // vẽ đĩa
		   drawChopStick(g2d);
	}	

	private static final int POINT_CENTER_TABLE = 326;
	private static int ANGLE_PHILOSOPHER = 72;
	private void drawBackground(Graphics2D g2d){
			
		ANGLE_PHILOSOPHER = 360/numOfPhilosopher;
		//draw Table;
		g2d.drawImage(
				Assets.table,
				POINT_CENTER_TABLE - Assets.table.getHeight()/2,
				POINT_CENTER_TABLE - Assets.table.getHeight()/2,
				null);
		//draw Philosopher & food;
		for(int i =0;i<numOfPhilosopher;i++){
			int angle = i*ANGLE_PHILOSOPHER;
			g2d.rotate(Math.toRadians(angle),POINT_CENTER_TABLE,POINT_CENTER_TABLE);
			//BODY
			g2d.drawImage(
					Assets.people,
					254 ,
					70,
					null);
			//FOOD
			g2d.drawImage(
					Assets.food,
					276,
					165,
					null);
			drawText(i+"",325,55,g2d,serifFont);
			g2d.rotate(Math.toRadians(-angle),POINT_CENTER_TABLE,POINT_CENTER_TABLE);
			g2d.rotate(Math.toRadians((i-0.5)*ANGLE_PHILOSOPHER),POINT_CENTER_TABLE,POINT_CENTER_TABLE);		
			drawText(i+"",319,300,g2d,serifFont);
			g2d.rotate(Math.toRadians(-(i-0.5)*ANGLE_PHILOSOPHER),POINT_CENTER_TABLE,POINT_CENTER_TABLE);			
		}		
	}
	
	private static Font serifFont = new Font("Serif", Font.BOLD, 36);
	private static Font serifFontStatus = new Font("Serif", Font.BOLD, 20);
	
	private void drawText(String text, int x, int y,Graphics2D g2d){
	    g2d.setColor(Color.black);
	    FontMetrics fm = g2d.getFontMetrics();
	    int w = fm.stringWidth(text);
	    int h = fm.getAscent();
	    g2d.drawString(text, x - (w / 2), y + (h / 4));
	}
	private void drawText(String text, int x, int y,Graphics2D g2d, Font font){
	    g2d.setFont(font);
	    drawText(text,x,y,g2d);
	}
	private void drawFace(Graphics2D g2d){
		if(chopsticks == null) return;
		for (Philosopher philo : philosophers) {
			Philosopher.State state = philo.getState();
			Color color;
			String text = "";
			switch (state) {
			case THINKING:
				color = Color.YELLOW;
				text = "Think";
				break;
			case EATING:
				color = Color.GREEN;
				text = "Eat";
				break;
			case HUNGRY:
				color = Color.RED;
				text = "Hungry";
				break;
					
			default:
				color = Color.WHITE;
				break;
			}
			// FACE
			g2d.setColor(color);
			g2d.fillOval(291,75, 80, 80);
			drawText(text,330,115,g2d,serifFontStatus);			
			g2d.rotate(Math.toRadians(ANGLE_PHILOSOPHER),POINT_CENTER_TABLE,POINT_CENTER_TABLE);
		}
	}
	private void drawChopStick(Graphics2D g2d){
		int i = 0;
		for (Chopstick chopStick : chopsticks) {
			Chopstick.State state = chopStick.getState();
			switch (state) {
			case FREE:
				g2d.rotate(Math.toRadians((i-0.5)*ANGLE_PHILOSOPHER),POINT_CENTER_TABLE,POINT_CENTER_TABLE);	
				g2d.drawImage(Assets.fork,319,190,null);	
				g2d.rotate(Math.toRadians(-(i-0.5)*ANGLE_PHILOSOPHER),POINT_CENTER_TABLE,POINT_CENTER_TABLE);
				break;
			case USING:
				int idUse = chopStick.getIdUse();
				if(idUse == chopStick.getId()){
					g2d.rotate(Math.toRadians(i*ANGLE_PHILOSOPHER),POINT_CENTER_TABLE,POINT_CENTER_TABLE);
					g2d.rotate(Math.toRadians(-90),POINT_CENTER_TABLE,POINT_CENTER_TABLE);		   
					g2d.drawImage(Assets.fork,450,260,null);
					g2d.rotate(Math.toRadians(-i*ANGLE_PHILOSOPHER+90),POINT_CENTER_TABLE,POINT_CENTER_TABLE);
				}else{					
					g2d.rotate(Math.toRadians(+((i+1) %numOfPhilosopher -2)*ANGLE_PHILOSOPHER),POINT_CENTER_TABLE,POINT_CENTER_TABLE);
					g2d.rotate(Math.toRadians(+90),POINT_CENTER_TABLE,POINT_CENTER_TABLE);		   
					g2d.drawImage(Assets.fork,190,255,null);
					g2d.rotate(Math.toRadians(-((i+1) %numOfPhilosopher-2)*ANGLE_PHILOSOPHER-90),POINT_CENTER_TABLE,POINT_CENTER_TABLE);			
				}
			//	System.out.println("         "+idUse +"  "+ chopStick.getId());
				break;	
				
			}
			
			i++;
		}
		
	}
	
	public String getStatus(){
		String result =   "Name        State  | NumOfEat\n";
		for (Philosopher philosopher : philosophers) {
			result+=      "Philo "+philosopher.getId() +":";
			switch(philosopher.getState()){
			case THINKING:
				result += " Thinking  | ";
				break;
			case EATING:
				result += " Eating    | ";
				break;
			case HUNGRY:
				result += " Hungry    | ";
				break;
			}
			result += philosopher.getNumOfCountEat()+"\n";	
		}
		return result;
	}
	@Override
	public void start() {
		DiningLaucher.start();
	}
	@Override
	public void stop() {
		DiningLaucher.stop();
	}
	



















}
