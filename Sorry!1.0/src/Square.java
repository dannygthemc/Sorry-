

/*
 * This class defines a square on the bored 
 */
public class Square {
	
	private int location; //int used to identify square
	private Pawn space; //placeholder for a pawn
	private boolean isOccupied; //whether or not there is currently a pawn on the square
	private boolean isSlide; //used to represent that a slide starts on the given square
	private String colSlide; //the color associated with the slide
	private int sizeSlide; //the length of the slide
	
	/*
	 * constructor
	 * establishes location
	 * space is initially unoccupied 
	 */
	public Square(int loc){
		
		location = loc;
		isOccupied = false;
		isSlide = false;
		space = null;
	}
	
	/*
	 * setter for slide variables
	 * defines given square as the start of a slide
	 */
	public void setSlide(String col, int size){
		
		colSlide = col;
		sizeSlide = size;
		isSlide = true;
	}
	
	/*
	 * defines arrival of pawn to spot
	 */
	public void arrival(Pawn temp){
		
		space = temp;
		isOccupied = true;
	}
	
	/*
	 * defines pawn leaving the spot
	 */
	public void leave(){
		
		space = null;
		isOccupied = false;
	}
	
	/*
	 * getter for pawn on square
	 */
	public Pawn getPawn(){
		return space;
	}
	/*
	 * getter for location
	 */
	public int getLoc(){
		return location;
	}
	
	/*
	 * getter for isSlide
	 */
	public boolean isSlide(){
		return isSlide;
	}
	
	/*
	 * getter for isOccupied
	 */
	public boolean isOccupied(){
		return isOccupied;
	}
	
	/*
	 * getter for slide color
	 */
	public String colSlide(){
		return colSlide;
	}
	
	/*
	 * getter for slide size
	 */
	public int sizeSlide(){
		return sizeSlide;
	}

}
