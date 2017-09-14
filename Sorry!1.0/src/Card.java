
/*
 * this class represents a card with a numeric value
 * the 0 card will be used to represent the "Sorry!" card
 */
public class Card {
	
	private int value; //card value
	
	/*
	 * constructor
	 * @param int num
	 */
	public Card(int num){
		value = num;
	}
	
	/*
	 * getter for value
	 */
	public int getVal(){
		return value;
	}



}
