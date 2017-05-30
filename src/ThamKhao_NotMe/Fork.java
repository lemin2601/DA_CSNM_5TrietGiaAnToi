package ThamKhao_NotMe;

public class Fork{
	public enum STATE {FREE,USING}
	private STATE state;
	public synchronized void get(){
		
		try {
			this.state = STATE.USING;
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public synchronized void release(){
		
		this.state = STATE.FREE;
		this.notify();
	}
	public Fork(){
		
		this.state= STATE.FREE;
		
	}
}
