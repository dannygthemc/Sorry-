
/*
 * this class defines the pawns that players move around the board
 */
public class Pawn {
	
	private String color; //color 
	private int location; //where on the board the pawn is located
	private int num; //the identifier for the pawn of the specified color
	private boolean isHome; //identifies whether or not Pawn is on home path
	/*
	 * constructor
	 * @param String color
	 */
	public Pawn(String col, int num){
		
		setColor(col);
		setLocation(0);
		setNum(num);
		isHome = false;
	}
	
	/*
	 *blank constructor
	 */
	public Pawn(){
		setColor(null);
		setLocation(0);
		setNum(0);
	}
	
	/*
	 * set isHome to true 
	 */
	public void arriveHome(){
		isHome = true;
	}
	
	/*
	 * set isHome to false
	 */
	public void leaveHome(){
		isHome = false;
	}
	/*
	 * getter for isHome
	 */
	public boolean isHome(){
		return isHome;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
