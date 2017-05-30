package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import dinningphilosopherproblem.Information;
import dinningphilosopherproblem.PhilosopherProblem;
import gfx.Assets;

public class DinningGUI implements Runnable{
	// màn hình hiển thị
	private Display display;
	private int width, height;
	private String title;
	//thread hiển thị
	private Thread thread;
	private boolean isrunning = false;
	// canvas vẽ hoạt ảnh
	private BufferStrategy bs;
	private Graphics g;
	// dining chưa thông tin của các philo
	DiningState state;
	
	PhilosopherProblem temp;
	public DinningGUI(String title, int width,int height){
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	public void run() {
		init();

		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		while(isrunning){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1){
				tick();
				render();
				ticks++;
				delta--;
			}
			
			if(timer >= 1000000000){
				System.out.println("Ticks and Frames: " + ticks);
				ticks = 0;
				timer = 0;
			}
		}
		stop();
	}
	// khởi tạo ban đầu
	private void init(){
		display = new Display(title, width, height);
		Assets.init();
		
		temp = (new PhilosopherProblem(Information.NUM_OF_PHILOSOPHER));
		state = new DiningState(temp,Information.NUM_OF_PHILOSOPHER);
		temp.start();
		
	}
	
	private void tick(){
		state.tick();
	}
	
	private void render(){
	    bs = display.getCanvas().getBufferStrategy();
	    if(bs == null){
	        display.getCanvas().createBufferStrategy(3);
	        return;
	    }
	    System.out.println(display.getCanvas().getHeight() + "  "+display.getCanvas().getWidth());
	    g = bs.getDrawGraphics();
		//Clear Screen
		g.clearRect(0, 0, width, height);
		//Draw Here!
	    state.render(g);
 
	    //End Drawing
	    bs.show();
	    g.dispose();
	}
	
	// start chương trình.
	public synchronized void start(){
		if(isrunning) return;
		isrunning = true;
		thread = new Thread(this);
		thread.start();
	}
	// stop chương trình
	public synchronized void stop(){
		if(!isrunning) return;
		isrunning  = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
