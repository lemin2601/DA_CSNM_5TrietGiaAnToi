package dinningphilosopherproblem;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import ThamKhao_NotMe.Fork.STATE;

public class ChopStick {
	Lock up = new ReentrantLock();
	
	private Information.StateChopStick state;
	private int id;
	private int idUse;
	
	public synchronized boolean take(int who) throws InterruptedException{		
		if (up.tryLock(10, TimeUnit.MILLISECONDS)) {
	        System.out.println("ChopStick "+ id+ "take Bởi :" +who);
	        this.state = Information.StateChopStick.USING;
	        this.idUse = who;
	        return true;
	      }
		  System.out.println("ChopStick "+ id+ "đang đượ sử dụng" +who) ;
	      return false;
	}
	public synchronized void release(){	
		this.state = Information.StateChopStick.FREE;
		System.out.println("ChopStick "+ id+ "release") ;
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		up.unlock();
		
	}
	public ChopStick(int id){
		this.id  = id;
		this.state= Information.StateChopStick.FREE;	
	}
	public int getId(){return id;}
	public Information.StateChopStick getState(){return state;}
	public int getIdUser(){return idUse;}
	
	/*private volatile boolean semaphore;	// Trạng thái tài nguyên hiện tại
	private volatile int idPhilo;		// Id Philo sử dụng tài nguyên
	private static int numOfSemaphore; // Tổng số tài nguyên của Chopstick
	public ChopStick(int numOfSemaphore){
		this.semaphore = false; // nhàn rỗi
		this.idPhilo = -1;// chưa ăn chiếm giữ
	    this.numOfSemaphore = numOfSemaphore; // tổng số tài nguyên hiện có
	}
	/// passeer (vượt qua) 	/// probeer (thử)	/// pakken (nằm lấy)	
	///Dijkstra  ->  portmanteau prolaag (probeer te verlagen -> Kiểm tra để giảm)
	///Ngôn ngữ lập trình: wait, take, pend
	public synchronized void P(int idPhilo){
		// chờ cho đến khi semaphore được nhàn rỗi
		if(semaphore) return;
		// thực hiện khi nhận được lệnh nhàn rỗi
		this.semaphore = true;
		this.numOfSemaphore --;
		this.idPhilo = idPhilo;
	}
	///verhoog (tăng)
	///Ngôn ngữ lập trình: signal, release, post	
	public synchronized void V(int idPhilo){
		// giải phóng tài nguyên
		if(idPhilo == this.idPhilo){
			this.semaphore = false;
			this.numOfSemaphore ++;
			this.idPhilo = -1;
		}
	}*/
	
	
}
