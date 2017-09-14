import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/*
 * defines a deck of cards that meets the 
 * specifications of a "Sorry!" deck
 */
public class Deck {
	
	private List<Card> cards; //ArrayList of cards functions as main dataStructure for the deck
	
	
	/*
	 * constrcutor
	 * creates appropriate number of each card value
	 * and adds them to the deck
	 */
	public Deck(){
		
		//creates the list
		cards = new ArrayList<Card>();
		
		//creates cards and adds them to the list
		cards.add(new Card(0));
		cards.add(new Card(0));
		cards.add(new Card(0));
		cards.add(new Card(0));
		cards.add(new Card(1));
		cards.add(new Card(1));
		cards.add(new Card(1));
		cards.add(new Card(1));
		cards.add(new Card(1));
		cards.add(new Card(2));
		cards.add(new Card(2));
		cards.add(new Card(2));
		cards.add(new Card(2));
		cards.add(new Card(3));
		cards.add(new Card(3));
		cards.add(new Card(3));
		cards.add(new Card(3));
		cards.add(new Card(4));
		cards.add(new Card(4));
		cards.add(new Card(4));
		cards.add(new Card(4));
		cards.add(new Card(5));
		cards.add(new Card(5));
		cards.add(new Card(5));
		cards.add(new Card(5));
		cards.add(new Card(7));
		cards.add(new Card(7));
		cards.add(new Card(7));
		cards.add(new Card(7));
		cards.add(new Card(8));
		cards.add(new Card(8));
		cards.add(new Card(8));
		cards.add(new Card(8));
		cards.add(new Card(10));
		cards.add(new Card(10));
		cards.add(new Card(10));
		cards.add(new Card(10));
		cards.add(new Card(11));
		cards.add(new Card(11));
		cards.add(new Card(11));
		cards.add(new Card(11));
		cards.add(new Card(12));
		cards.add(new Card(12));
		cards.add(new Card(12));
		cards.add(new Card(12));
	}
	
	/*
	 * method to draw a card
	 */
	public Card draw(){
		
		//pick random number from zero to the end of the remaining deck
		int rando = new Random().nextInt(cards.size());
		
		//draw the card at the randomly chosen location
		Card draw = cards.remove(rando);
		
		return draw;
	}
	
	/*
	 * getter for size of deck
	 */
	public int cardsLeft(){
		
		return cards.size();
	}

}
