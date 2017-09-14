import java.util.List;
import java.awt.Color;
import java.util.ArrayList;

/*
 * this class defines a Player for the game
 */
public class Player {

	private Pawn[] pawns; //holds pawns that belong to the player
	private String color; //color of player
	private Color colorColor; //the actual data for the color at hand
	private int score; //number of pawns player has gotten home
	
	/*
	 * constructor
	 * player's color defined
	 * score initialized to zero
	 * instantiates list to hold pawns
	 * @param color
	 */
	public Player(String col, Color col2){
		color = col;
		colorColor = col2;
		score = 0;
		pawns = new Pawn[4];
	}
	
	/*
	 * store pawns
	 */
	public void storePawns(Pawn one, Pawn two, Pawn three, Pawn four){
		
		pawns[0] = one;
		pawns[1] = two;
		pawns[2] = three;
		pawns[3] = four;
	}
	
	
	/*
	 * getter for color
	 */
	public String getColor(){
		return color;
	}
	
	/*
	 * getter for score
	 */
	public int getScore(){
		return score;
	}
	
	/*
	 * method to update score
	 */
	public void pointScored(){
		score++;
	}

	public Color getColorColor() {
		return colorColor;
	}

	public void setColorColor(Color colorColor) {
		this.colorColor = colorColor;
	}
}
