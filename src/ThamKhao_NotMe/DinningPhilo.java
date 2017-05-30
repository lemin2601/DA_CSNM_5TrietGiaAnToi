package ThamKhao_NotMe;

public class DinningPhilo {
	Fork[] fork;
	Philosopher[] philosopher;
	public DinningPhilo(int numberPhilo){
		fork = new Fork[numberPhilo];
		philosopher = new Philosopher[numberPhilo];
	}
	public static void main(String[] agrs){
		DinningPhilo tmp = new DinningPhilo(5);
		
	}
}
