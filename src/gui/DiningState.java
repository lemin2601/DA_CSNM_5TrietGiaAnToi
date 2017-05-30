package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import dinningphilosopherproblem.ChopStick;
import dinningphilosopherproblem.Information;
import dinningphilosopherproblem.Philosopher;
import dinningphilosopherproblem.PhilosopherProblem;
import gfx.Assets;

public class DiningState{
	//information for render and tick
	private static Philosopher[] statePhilosopher;
	private static ChopStick[] stateChopStick;
	private PhilosopherProblem temp;
	int numberPhilosopher;
	
	//constructor
	public DiningState(PhilosopherProblem temp,int numberPhilosopher){
		this.temp = temp;
		this.numberPhilosopher = numberPhilosopher;
	}
	//update infor
	public void tick() {
		// cập nhật lại trạng thái philo & chopstick
		this.statePhilosopher  = temp.getPhilosopher();				
		this.stateChopStick = temp.getChopStick();
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

	
	public void renderBackground(Graphics g,int numberPhilosopher){
		
		  Graphics2D g2d = (Graphics2D) g;
		   // vẽ nền phía sau
		   drawBackground(g2d);
		   ANGLE_PHILOSOPHER = 360/numberPhilosopher;
		 //draw Table;
			g2d.drawImage(
					Assets.table,
					POINT_CENTER_TABLE - Assets.table.getHeight()/2,
					POINT_CENTER_TABLE - Assets.table.getHeight()/2,
					null);
			//draw Philosopher & food;
			for(int i =0;i<numberPhilosopher;i++){
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
				//Viet chứ
			
				g2d.rotate(Math.toRadians(-angle),POINT_CENTER_TABLE,POINT_CENTER_TABLE);
			}
	}
	private static final int POINT_CENTER_TABLE = 326;
	private static int ANGLE_PHILOSOPHER = 72;
	private void drawBackground(Graphics2D g2d){
		
	    
		ANGLE_PHILOSOPHER = 360/numberPhilosopher;
		//draw Table;
		g2d.drawImage(
				Assets.table,
				POINT_CENTER_TABLE - Assets.table.getHeight()/2,
				POINT_CENTER_TABLE - Assets.table.getHeight()/2,
				null);
		//draw Philosopher & food;
		for(int i =0;i<numberPhilosopher;i++){
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
			drawText(i+"",325,65,g2d);
			g2d.rotate(Math.toRadians(-angle),POINT_CENTER_TABLE,POINT_CENTER_TABLE);
			g2d.rotate(Math.toRadians((i-0.5)*ANGLE_PHILOSOPHER),POINT_CENTER_TABLE,POINT_CENTER_TABLE);		
			drawText(i+"",319,300,g2d);
			g2d.rotate(Math.toRadians(-(i-0.5)*ANGLE_PHILOSOPHER),POINT_CENTER_TABLE,POINT_CENTER_TABLE);
			
		}
		
	}
	
	private static Font serifFont = new Font("Serif", Font.BOLD, 36);
	
	private void drawText(String text, int x, int y,Graphics2D g2d){
	    g2d.setColor(Color.black);
	    g2d.setFont(serifFont);
	    g2d.drawString(text, x, y );
	}
	private void drawFace(Graphics2D g2d){
		if(statePhilosopher == null) return;
		for (Philosopher philo : statePhilosopher) {
			Information.StatePhilosopher state = philo.getState();
			Color color;
			switch (state) {
			case THINKING:
				color = Color.YELLOW; 
				break;
			case EATING:
				color = Color.GREEN;
				break;
			case WAITING:
				color = Color.RED;
				break;
					
			default:
				color = Color.WHITE;
				break;
			}
			// FACE
			g2d.setColor(color);
			g2d.fillOval(291,75, 80, 80);
			g2d.rotate(Math.toRadians(ANGLE_PHILOSOPHER),POINT_CENTER_TABLE,POINT_CENTER_TABLE);
		}
	}
	private void drawChopStick(Graphics2D g2d){
		int i = 0;
		for (ChopStick chopStick : stateChopStick) {
			Information.StateChopStick state = chopStick.getState();
			switch (state) {
			case FREE:
				g2d.rotate(Math.toRadians((i-0.5)*ANGLE_PHILOSOPHER),POINT_CENTER_TABLE,POINT_CENTER_TABLE);	
				g2d.drawImage(Assets.fork,319,190,null);	
				g2d.rotate(Math.toRadians(-(i-0.5)*ANGLE_PHILOSOPHER),POINT_CENTER_TABLE,POINT_CENTER_TABLE);
				break;
			case USING:
				int idUse = chopStick.getIdUser();
				if(idUse == chopStick.getId()){
					g2d.rotate(Math.toRadians(i*ANGLE_PHILOSOPHER),POINT_CENTER_TABLE,POINT_CENTER_TABLE);
					g2d.rotate(Math.toRadians(-90),POINT_CENTER_TABLE,POINT_CENTER_TABLE);		   
					g2d.drawImage(Assets.fork,450,260,null);
					g2d.rotate(Math.toRadians(-i*ANGLE_PHILOSOPHER+90),POINT_CENTER_TABLE,POINT_CENTER_TABLE);
				}else{
					g2d.rotate(Math.toRadians(+((i) %numberPhilosopher - 1)*ANGLE_PHILOSOPHER),POINT_CENTER_TABLE,POINT_CENTER_TABLE);
					g2d.rotate(Math.toRadians(+90),POINT_CENTER_TABLE,POINT_CENTER_TABLE);		   
					g2d.drawImage(Assets.fork,190,255,null);
					g2d.rotate(Math.toRadians(-((i) %numberPhilosopher - 1)*ANGLE_PHILOSOPHER-90),POINT_CENTER_TABLE,POINT_CENTER_TABLE);			
				}
				break;	
				
			}
			
			i++;
		}
		
	}
	
	public String getStatus(){
		String result =   "Name        State  | NumOfEat\n";
		for (Philosopher philosopher : statePhilosopher) {
			result+=      "Philo "+philosopher.getId() +":";
			switch(philosopher.getState()){
			case THINKING:
				result += " Thinking  | ";
				break;
			case EATING:
				result += " Eating    | ";
				break;
			case WAITING:
				result += " Waiting   | ";
				break;
			}
			result += philosopher.getNumOfCountEating()+"\n";	
		}
		return result;
	}
	
	int x = 0;
	int delta = 1;
	private void drawForkWithHand(int numPhilo,int numFork,Graphics2D g2d, boolean action){
		int angle = numPhilo *72 -25;
		if(numFork == numPhilo) {
			// tay trai
			 g2d.rotate(Math.toRadians(angle),272,277);
		     g2d.drawImage(Assets.fork,(action?385:385+x),290,null);
		     g2d.rotate(Math.toRadians(-angle),272,277);
		}else{
			g2d.rotate(Math.toRadians(-angle),272,277);
		    g2d.drawImage(Assets.fork,(action?150:150-x),285,null);
		    g2d.rotate(Math.toRadians(angle),272,277);
		}		
	}
	private void drawFork(int position,Graphics2D g2d){
		int angle = position * 72;
	    g2d.rotate(Math.toRadians(angle),272,277);
	    g2d.drawImage(Assets.fork,264, 348,null);
	    g2d.rotate(Math.toRadians(-angle),272,277);
		
	}
}
