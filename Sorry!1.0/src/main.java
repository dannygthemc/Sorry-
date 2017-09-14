import java.util.List;
import java.util.ListIterator;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;

public class main {
	
	//defines pawn for gameplay
	static Pawn[] redPawns = new Pawn[4];
	static Pawn[] greenPawns = new Pawn[4];
	static Pawn[] yellowPawns = new Pawn[4];
	static Pawn[] bluePawns = new Pawn[4];
	
	//array of Players
	static Player[] players;
	
	//Board for game
	static Board board;
	
	//Deck for game
	static Deck deck;
	
	static Color blue;
	static Color red;
	static Color yellow;
	static Color green;
	
	//JFrames to display cards
	static JFrame sorry;
	static JFrame one;
	static JFrame two;
	static JFrame three;
	static JFrame four;
	static JFrame five;
	static JFrame seven;
	static JFrame eight;
	static JFrame ten;
	static JFrame eleven;
	static JFrame twelve;
	static JFrame cardBack;

	public static void main(String[] args) {
		
		//create UI manager
		UIManager UI=new UIManager();
		
		//define colors
		blue = new Color(8, 82, 150);
		red = new Color(255, 0, 0);
		yellow = new Color(255, 255, 0);
		green = new Color(146, 208, 80);
		//create the Board	
		board = new Board();
		
		//Create a deck
		deck = new Deck();
		
		//create list of colors to select from
		List<String> colorSelect = new ArrayList<String>();
		colorSelect.add("Red");
		colorSelect.add("Blue");
		colorSelect.add("Green");
		colorSelect.add("Yellow");
		
		//instantiate pawns
		for(int i=0;i<4;i++){
			redPawns[i] =new Pawn("Red", i);
			greenPawns[i] =new Pawn("Green", i);
			yellowPawns[i] =new Pawn("Yellow", i);
			bluePawns[i] =new Pawn("Blue", i);
		}
		
		//create visuals for cards
		createCards();
		
		JFrame frame = new JFrame();
		
		int numPlayer = 99;
		boolean wait = true;
		while(wait == true){
			//Prompt for player number select
			Object[] possibilities = {2, 3, 4};
			numPlayer = (int)JOptionPane.showInputDialog(
			                    frame,
			                    "How Many Players?\n",
			                    "Player Select",
			                    JOptionPane.PLAIN_MESSAGE,
			                    null,
			                    possibilities,
			                    "2");
			
			if(numPlayer == 99){ //nothing selected reloop
				
			}
			else{
				wait = false;
			}
			
		}
		
		//create desired number of players
		players = new Player[numPlayer];
		
		//allow players to select colors and instantiates Player objects accordingly
		for(int i=0;i<numPlayer;i++){
			
			Color colorColor = new Color(0);
			boolean wait2 = true;
			String s = null;
			while(wait2 == true){
				Object[] possibilities2 = colorSelect.toArray();
				s = (String)JOptionPane.showInputDialog(
				                    frame,
				                    "Player " + i + " "+
				                    "Select Your Colors\n",
				                    "Player " + i + "" ,
				                    JOptionPane.PLAIN_MESSAGE,
				                    null,
				                    possibilities2,
				                    "Select");
				colorSelect.remove(s);
				
			    wait2 = false;
				if(s.equals("Green")){
					colorColor = green;
				}
				else if(s.equals("Red")){
					colorColor = red;
				}
				else if(s.equals("Blue")){
					colorColor = blue;
				}
				else if(s.equals("Yellow")){
					colorColor = yellow;
				}
				else{
					wait2 = true;
				}
				
			}
			
			//create new player
			players[i] = new Player(s, colorColor);
			//assign pawns to player and put them on the board
			if(s.equals("Green")){
				players[i].storePawns(greenPawns[0], greenPawns[1], greenPawns[2], greenPawns[3]);
				for(int j=0;j<4;j++){
					board.updateStart(j, s, greenPawns[j]);
				}
			}
			else if(s.equals("Red")){
				players[i].storePawns(redPawns[0], redPawns[1], redPawns[2], redPawns[3]);
				for(int j=0;j<4;j++){
					board.updateStart(j, s, redPawns[j]);
				}
			}
			else if(s.equals("Blue")){
				players[i].storePawns(bluePawns[0], bluePawns[1], bluePawns[2], bluePawns[3]);
				for(int j=0;j<4;j++){
					board.updateStart(j, s, bluePawns[j]);
				}
			}
			else if(s.equals("Yellow")){
				players[i].storePawns(yellowPawns[0], yellowPawns[1], yellowPawns[2], yellowPawns[3]);
				for(int j=0;j<4;j++){
					board.updateStart(j, s, yellowPawns[j]);
				}
			}
			
		}
		board.makeVisible();
		
		boolean gameWon=false;
		while(gameWon==false){
			
			for(int i=0;i<numPlayer;i++){
				
				UI.put("OptionPane.background", players[i].getColorColor());
			    UI.put("Panel.background", players[i].getColorColor());
			    UI.put("OptionPane.messageFont", new FontUIResource(new Font(  
			            "Arial", Font.BOLD, 18)));

				JOptionPane.showMessageDialog(null, "Player " + i + " \n It's Your Turn");
				Card current = deck.draw();
				int cardsLeft = deck.cardsLeft(); //check remaining number of cards
				if(cardsLeft == 0){ //if no cards left
					Deck deck2 = new Deck();//reshuffle deck
					deck = deck2;
				}
				boolean reDraw = cardSelect(current.getVal(),i, players[i].getColor());
				if(players[i].getScore() == 4){
					JOptionPane.showMessageDialog(frame, "Game Over! \n"
							+ "Player " + i +" wins!");
					gameWon =true;
				}
				if(reDraw==true){
					JOptionPane.showMessageDialog(frame, "Drew a Two, You get to go again!");
					i--;
				}
			}
		}
		
		board.close();
		
		
		
		return;

	}
	
	/*
	 * method to initialize the JFrames that display the cards
	 */
	public static void createCards(){
		
		JPanel content;
		JLabel panelIm;
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		one = new JFrame("One Card");
		ImageIcon oneCard = new ImageIcon("Pictures/Cards/One Card.JPG");
		Image oneIm = oneCard.getImage(); // transform it 
		Image newOne = oneIm.getScaledInstance(350, 600,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		oneCard = new ImageIcon(newOne);  // transform it back
		content = new JPanel();
		panelIm = new JLabel(oneCard);
		panelIm.setBorder(border);
		content.add(panelIm);
		one.setContentPane(content);
		one.setSize(360, 610);
		one.setAlwaysOnTop(true);
		one.setLocation(1000, 200);
		one.setUndecorated(true);
		one.toBack();
		
		two = new JFrame("Two Card");
		ImageIcon twoCard = new ImageIcon("Pictures/Cards/Two Card.JPG");
		Image twoIm = twoCard.getImage(); // transform it 
		Image newtwo = twoIm.getScaledInstance(350, 600,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		twoCard = new ImageIcon(newtwo);  // transform it back
		content = new JPanel();
		panelIm = new JLabel(twoCard);
		panelIm.setBorder(border);
		content.add(panelIm);
		two.setContentPane(content);
		two.setSize(360, 610);
		two.setAlwaysOnTop(true);
		two.setLocation(1000, 200);
		two.setUndecorated(true);
		two.toBack();
		
		three = new JFrame("Three Card");
		ImageIcon threeCard = new ImageIcon("Pictures/Cards/Three Card.JPG");
		Image threeIm = threeCard.getImage(); // transform it 
		Image newthree = threeIm.getScaledInstance(350, 600,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		threeCard = new ImageIcon(newthree);  // transform it back
		content = new JPanel();
		panelIm = new JLabel(threeCard);
		panelIm.setBorder(border);
		content.add(panelIm);
		three.setContentPane(content);
		three.setSize(360, 610);
		three.setAlwaysOnTop(true);
		three.setLocation(1000, 200);
		three.setUndecorated(true);
		three.toBack();
		
		four = new JFrame("Four Card");
		ImageIcon fourCard = new ImageIcon("Pictures/Cards/Four Card.JPG");
		Image fourIm = fourCard.getImage(); // transform it 
		Image newfour = fourIm.getScaledInstance(350, 600,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		fourCard = new ImageIcon(newfour);  // transform it back
		content = new JPanel();
		panelIm = new JLabel(fourCard);
		panelIm.setBorder(border);
		content.add(panelIm);
		four.setContentPane(content);
		four.setSize(360, 610);
		four.setAlwaysOnTop(true);
		four.setLocation(1000, 200);
		four.setUndecorated(true);
		four.toBack();
		
		five = new JFrame("Five Card");
		ImageIcon fiveCard = new ImageIcon("Pictures/Cards/Five Card.JPG");
		Image fiveIm = fiveCard.getImage(); // transform it 
		Image newfive = fiveIm.getScaledInstance(350, 600,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		fiveCard = new ImageIcon(newfive);  // transform it back
		content = new JPanel();
		panelIm = new JLabel(fiveCard);
		panelIm.setBorder(border);
		content.add(panelIm);
		five.setContentPane(content);
		five.setSize(360, 610);
		five.setAlwaysOnTop(true);
		five.setLocation(1000, 200);
		five.setUndecorated(true);
		five.toBack();
		
		seven = new JFrame("Seven Card");
		ImageIcon sevenCard = new ImageIcon("Pictures/Cards/Seven Card.JPG");
		Image sevenIm = sevenCard.getImage(); // transform it 
		Image newseven = sevenIm.getScaledInstance(350, 600,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		sevenCard = new ImageIcon(newseven);  // transform it back
		content = new JPanel();
		panelIm = new JLabel(sevenCard);
		panelIm.setBorder(border);
		content.add(panelIm);
		seven.setContentPane(content);
		seven.setSize(360, 610);
		seven.setAlwaysOnTop(true);
		seven.setLocation(1000, 200);
		seven.setUndecorated(true);
		seven.toBack();
		
		eight = new JFrame("eight Card");
		ImageIcon eightCard = new ImageIcon("Pictures/Cards/Eight Card.JPG");
		Image eightIm = eightCard.getImage(); // transform it 
		Image neweight = eightIm.getScaledInstance(350, 600,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		eightCard = new ImageIcon(neweight);  // transform it back
		content = new JPanel();
		panelIm = new JLabel(eightCard);
		panelIm.setBorder(border);
		content.add(panelIm);
		eight.setContentPane(content);
		eight.setSize(360, 610);
		eight.setAlwaysOnTop(true);
		eight.setLocation(1000, 200);
		eight.setUndecorated(true);
		eight.toBack();
		
		ten = new JFrame("ten Card");
		ImageIcon tenCard = new ImageIcon("Pictures/Cards/Ten Card.JPG");
		Image tenIm = tenCard.getImage(); // transform it 
		Image newten = tenIm.getScaledInstance(350, 600,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		tenCard = new ImageIcon(newten);  // transform it back
		content = new JPanel();
		panelIm = new JLabel(tenCard);
		panelIm.setBorder(border);
		content.add(panelIm);
		ten.setContentPane(content);
		ten.setSize(360, 610);
		ten.setAlwaysOnTop(true);
		ten.setLocation(1000, 200);
		ten.setUndecorated(true);
		ten.toBack();
		
		eleven = new JFrame("eleven Card");
		ImageIcon elevenCard = new ImageIcon("Pictures/Cards/Eleven Card.JPG");
		Image elevenIm = elevenCard.getImage(); // transform it 
		Image neweleven = elevenIm.getScaledInstance(350, 600,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		elevenCard = new ImageIcon(neweleven);  // transform it back
		content = new JPanel();
		panelIm = new JLabel(elevenCard);
		panelIm.setBorder(border);
		content.add(panelIm);
		eleven.setContentPane(content);
		eleven.setSize(360, 610);
		eleven.setAlwaysOnTop(true);
		eleven.setLocation(1000, 200);
		eleven.setUndecorated(true);
		eleven.toBack();
		
		twelve = new JFrame("twelve Card");
		ImageIcon twelveCard = new ImageIcon("Pictures/Cards/Twelve Card.JPG");
		Image twelveIm = twelveCard.getImage(); // transform it 
		Image newtwelve = twelveIm.getScaledInstance(350, 600,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		twelveCard = new ImageIcon(newtwelve);  // transform it back
		content = new JPanel();
		panelIm = new JLabel(twelveCard);
		panelIm.setBorder(border);
		content.add(panelIm);
		twelve.setContentPane(content);
		twelve.setSize(360, 610);
		twelve.setAlwaysOnTop(true);
		twelve.setLocation(1000, 200);
		twelve.setUndecorated(true);
		twelve.toBack();
		
		sorry = new JFrame("sorry Card");
		ImageIcon sorryCard = new ImageIcon("Pictures/Cards/Sorry! Card.JPG");
		Image sorryIm = sorryCard.getImage(); // transform it 
		Image newsorry = sorryIm.getScaledInstance(350, 600,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		sorryCard = new ImageIcon(newsorry);  // transform it back
		content = new JPanel();
		panelIm = new JLabel(sorryCard);
		panelIm.setBorder(border);
		content.add(panelIm);
		sorry.setContentPane(content);
		sorry.setSize(360, 610);
		sorry.setAlwaysOnTop(true);
		sorry.setLocation(1000, 200);
		sorry.setUndecorated(true);
		sorry.toBack();
		
		
		
	}
	
	/*
	 * Method to move Pawn along the board, both on the main Board and on home paths 
	 * Used by cardSelect to fulfill card actions
	 * takes in color of player, and card info 
	 * returns boolean signifying whether or not a valid move was slected
	 * @param String player color corresponding to the player making the move
	 * @param int play integer identifier for player
	 * @param String cardNum string identifier of card, used for messages
	 * @param int card int identifier of card, used for movement
	 * @param boolean validChoiceMade used to loop until player has selected a valid option
	 */
	public static boolean movePawn(String player,int play, String cardNum, int card, boolean validChoiceMade, boolean backwards){
		
			JFrame frame = new JFrame();
			
			List<Pawn> temp = board.getMainPawnsPlayer(player);
			List<Pawn> temp2 = board.getHomePawns(player);
			
			//check if pawn has a valid move to make
			//if not, removes it from the list
			for(ListIterator<Pawn> iter = temp.listIterator(); iter.hasNext();){
				
				boolean valid = true;
				Pawn cur = iter.next();
				int startLoc =  cur.getLocation();
				int newLoc = 0;
				if(backwards == true){
					newLoc = board.decIndex(startLoc, card);
				}
				else{
				newLoc = board.incIndex(startLoc, card);
				}
				
				//check if pawn will pass home while moving
				//and checks if that new space along the home will be valid
				int homeMark = board.getHome(player);
				if(homeMark >= startLoc && homeMark < newLoc){
					int remainder = newLoc - homeMark; //get remaining amount of movement
					int homeLoc = remainder - 1;
					valid = board.isValidMoveHome(homeLoc, player);
					
				}
				else{
				valid = board.isValidMoveMain(newLoc, player);
				}
				
				if(valid != true){
					iter.remove();
				}
				
				
			}
			//check if pawn has a valid move to make
			//if not, removes it from the list
			for(ListIterator<Pawn> iter = temp2.listIterator(); iter.hasNext();){
				
				Pawn cur = iter.next();
				int startLoc =  cur.getLocation();
				int newLoc = 0;
				if(backwards == true){
					newLoc = startLoc - card;
				}
				else{
					newLoc = startLoc + card;
				}
				
				boolean valid = board.isValidMoveHome(newLoc, player);
				
				if(valid != true){
					iter.remove();
				}
				
				
			}
			
			Object[] possi = temp.toArray();
			Object[] possi2 = temp2.toArray();
			Object[] possibilities = new Object[possi.length + possi2.length];
			
			int counter = 0;
			for(int y=0;y<possi.length;y++){
				possibilities[counter] = possi[y];
				counter++;
				
			}
			for(int y=0;y<possi2.length;y++){
				possibilities[counter] = possi2[y];
				counter++;
				
			}
			//if no available moves
			//informs player and ends their turn
			if(possibilities.length == 0){
				JOptionPane.showMessageDialog(frame, "You have no Pawns on the Board to Move");
				validChoiceMade=true;
			}
			//if player has available moves
			else{
				//create array of names to choose from
				//which correspond to the pawns they're choosing from
				String[] possibilities2 = new String[possibilities.length];
				for(int k=0;k<possibilities.length;k++){
					Pawn poss = (Pawn) possibilities[k];
					possibilities2[k] = "Pawn " +poss.getNum();
					
				}
				String mover = null;
				boolean wait3 = true;
				Pawn mover2 = new Pawn();
				
				while(wait3 == true){
					
					//player selects pawn of theirs they wish to move
					mover = (String)JOptionPane.showInputDialog(
		                frame,
		                "You Can Move a Pawn " + cardNum +" Space(s)\n" +
		                "Select a pawn to move",
		                cardNum + " Card",
		                JOptionPane.PLAIN_MESSAGE,
		                null,
		                possibilities2,
		                "Select");
				
					if(mover == null){
						//do nothing and reloop
					}
					else{
						wait3 = false;
						//find correspondiong Pawn
						for(int k=0;k<possibilities2.length;k++){
							if(mover.equals(possibilities2[k])){
								mover2 = (Pawn) possibilities[k];
							}
							
						}
						
					}
					
					
				}
				
				
				
				int pawnSelect = mover2.getNum();
				int pawnLoc = mover2.getLocation();
				
				//check if selected pawn is in Home area
				//if so, try to use roll on it
				if(mover2.isHome()){
					int loc = 0;
					if(backwards == true){
						loc = pawnLoc - card;
					}
					else{
						loc = pawnLoc+card; //potential new location
					}
					
					if(loc>5){//if new loc is greater than 5
						JOptionPane.showMessageDialog(frame, "Invalid Move");
					}
					else if(loc <0){ //if new loc is outside home path
						validChoiceMade =true;
						int leftOvers = card - pawnLoc; //amount of spaces left to move backwards
						int homeSpace = board.getHome(player); //space to start at after leaving home
						int finalLoc = homeSpace - leftOvers;
						mover2.leaveHome();
						board.removeHome(pawnLoc, pawnSelect, player); //remove pawn from old spot
						board.updateMain(finalLoc, pawnSelect, player, mover2);
							
					}
					else{ //otherwise move along homepath
						validChoiceMade =true;
						board.removeHome(pawnLoc, pawnSelect, player); //remove pawn from old spot
						board.updateHome(loc, pawnSelect, player, mover2); //add pawn to new spot
						
						if(loc == 5){//if made it home
							players[play].pointScored(); //update point
							JOptionPane.showMessageDialog(frame, "Congratulations, point scored");
							board.removeHome(loc, pawnSelect, player);//remove pawn
						}
						
						
					}
				}
				//if still on main board, tries to move pawn along main board
				else{
					int loc =0;
					//get new location
					if(backwards == true){
						loc = board.decIndex(pawnLoc, card);
					}
					else{
						loc = board.incIndex(pawnLoc, card);
					}
					//check if home path will be passed during next move
					//if so, uses remainder of player's roll to move along homepath
					//if remaining roll would overshoot home space, have to go around again
					int homeMark = board.getHome(player); //get home loc
					//if home path starts between current loc and ending loc
					if(homeMark >= pawnLoc && homeMark < loc){
						int remainder = loc - homeMark; //get remaining amount of movement
						if(remainder > 5){//if remainder is greater than 5
							//invalid move
							JOptionPane.showMessageDialog(frame, "Invalid Move");
							
						}
						else{
							validChoiceMade = true;
							mover2.arriveHome();
							int homeLoc = remainder -1; //get new homeLoc
							board.removeMain(pawnLoc, pawnSelect, player); //remove moving pawn from main
							board.updateHome(homeLoc, pawnSelect, player, mover2); //update home loc
							if(homeLoc == 5){
								players[play].pointScored();
								board.removeHome(homeLoc, pawnSelect, player);
							}
							
							
						}
						
					}
					//if not passing homePath then just move along board
					else{
						//check that it's not currently occupied by player's own pawn
						if (board.isValidMoveMain(loc, player) == false || possibilities.length==0){
							JOptionPane.showMessageDialog(frame, "Invalid move");
							//restart the loop
						}	
						else{
							validChoiceMade =true;
							board.removeMain(pawnLoc, pawnSelect, player); //remove moving pawn from main
							//check if there's another pawn to be moved
							if(board.isOccupiedMain(loc)==true){
								Pawn pawn2 = board.getMainAt(loc);
								int pawn2Select = pawn2.getNum();
								String pawn2Color = pawn2.getColor();
								board.removeMain(loc, pawn2Select, pawn2Color); //remove other pawn from main
								board.updateStart(pawn2Select, pawn2Color, pawn2); //return other pawn to its home
							
							}
							
							board.updateMain(loc, pawnSelect, player, mover2); //put moving pawn in desired loc
							
							//check if new loc is start of a slide
							//and update board accordingly if it is
							int slideJump = board.isSlide(loc, player);
							if(slideJump!=0){
								JOptionPane.showMessageDialog(frame, "Landed on Slide");
								int loc2 = board.incIndex(loc, slideJump); //get end of slide
								List<Pawn> slidePawns = board.getSlidePawns(loc, loc2); //get list of all pawns along slide
								//for each Pawn in the list, return it home
								for(ListIterator<Pawn> iter = slidePawns.listIterator(); iter.hasNext();){
									Pawn cur = iter.next();
									board.removeMain(cur.getLocation(), cur.getNum(), cur.getColor()); //remove other pawn from main
									board.updateStart(cur.getNum(), cur.getColor(), cur); //return other pawn to its home
								}
								board.removeMain(loc, pawnSelect, player); //remove pawn from old spot
								board.updateMain(loc2, pawnSelect, player, mover2); //update pawn loc to end of slide
							}
						}
					}
					
				}
			
		}
		return validChoiceMade;
	}
	/*
	 * 
	 */
	public static boolean leaveStart(String player,int play, boolean validChoiceMade){
		
		JFrame frame = new JFrame();
		//get reference to space just outside start
		//corresponding to color of player
		int loc = board.getStartSpace(player);
		//get list of pawns
		List<Pawn> temp = board.getStart(player);
		Object[] possibilities = temp.toArray();
				
		//check that it's not currently occupied by player's own pawn
		if (board.isValidMoveMain(loc, player) == false || possibilities.length==0){
			JOptionPane.showMessageDialog(frame, "Invalid move");
			//restart the loop
		}
		else{
			validChoiceMade = true;
				
			//create array of names to choose from
			//that correspond to the pawns available
			String[] possibilities2 = new String[possibilities.length];
			for(int k =0;k<possibilities.length;k++){
				Pawn poss = (Pawn) possibilities[k];
				possibilities2[k] = "Pawn " + poss.getNum();
			}
				
				//player selects pawn of theirs they wish to move
			String mover = (String)JOptionPane.showInputDialog(
                    frame,
                    "Leave Start Selected\n" +
                    "Select a pawn to leave start",
                    "Move Pawn",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities2,
                    "Select");
			
			Pawn mover2 = new Pawn();
			//find corresponding pawn to selection
			for(int k =0;k<possibilities2.length;k++){
				if(mover.equals(possibilities2[k])){
					mover2 = (Pawn) possibilities[k];
				}
			}
			
			int pawnSelect = mover2.getNum();
			board.removeStart(mover2, pawnSelect, player); //remove moving pawn from start
			//check if there's another pawn to be moved
			if(board.isOccupiedMain(loc)==true){
				Pawn pawn2 = board.getMainAt(loc);
				int pawn2Select = pawn2.getNum();
				String pawn2Color = pawn2.getColor();
				board.removeMain(loc, pawn2Select, pawn2Color); //remove other pawn from main
				board.updateStart(pawn2Select, pawn2Color, pawn2); //return other pawn to its home
					
			}
			
			board.updateMain(loc, pawnSelect, player, mover2); //put moving pawn in desired loc
			
			
		}
		return validChoiceMade;
	
	}

	
	/*
	 * presents player with options corresponding to the card they drew
	 * @param int i value of card selected
	 * @param int play number of player
	 * @param String player current player's color
	 */
	static public boolean cardSelect(int i, int play, String player){
		
		boolean reDraw =false;
		
		JFrame frame  = new JFrame();
		
		//drew a zero
		//zero represents the Sorry! card
		//move a pawn from start to current location of another player's pawn
		if(i==0){
			
			sorry.setVisible(true);
			List<Pawn> temp = board.getStart(player);
			Object[] possibilities = temp.toArray();
			List<Pawn> temp2 = board.getMainPawns(player);
			Object[] possibilities2 = temp2.toArray();
			//if the player has no one left on start or has no one they can attack, do nothing
			if(possibilities.length == 0 || possibilities2.length == 0){
				JOptionPane.showMessageDialog(frame, "Drew a Sorry! Card \n"
						+ "But You Don't have an available move");
				
			}
			//otherwise, let them make their move
			else{
				//player selects pawn of theirs they wish to move
				
				//create array of names to choose from
				//that correspond to pawn available
				String[] possibilities3 = new String[possibilities.length];
				
				for(int k =0;k<possibilities3.length;k++){
					Pawn poss = (Pawn)possibilities[k];
					possibilities3[k] = "Pawn " + poss.getNum();
					
				}
				String mover = (String)JOptionPane.showInputDialog(
                    frame,
                    "Sorry! Card\n" +
                    "Select a pawn to move",
                    "Sorry! Card",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities3,
                    "Select");
				
				Pawn mover2 = new Pawn();
				for(int k =0;k<possibilities3.length;k++){
					if(mover.equals(possibilities3[k])){
						mover2 = (Pawn)possibilities[k];
					}
					
				}
				//player selects pawn on the board to replace
				
				//create array of names to choose from
				//that correspond to pawn available
				String[] possibilities4 = new String[possibilities2.length];
				
				for(int k =0;k<possibilities4.length;k++){
					Pawn poss = (Pawn)possibilities2[k];
					possibilities4[k] = "" + poss.getColor()+ " - Pawn " +poss.getNum();
					
				}
				String movee = (String)JOptionPane.showInputDialog(
                    frame,
                    "Sorry! Card\n" +
                    "Select a pawn to replace",
                    "Sorry! Card",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities4,
                    "Select");
				
				Pawn movee2 = new Pawn();
				for(int k =0;k<possibilities4.length;k++){
					if(movee.equals(possibilities4[k])){
						movee2 = (Pawn)possibilities2[k];
					}
					
				}
			
				int pawnSelect = mover2.getNum();
				int pawn2Select = movee2.getNum();
				String pawn2Color = movee2.getColor();
				int pawn2Loc = movee2.getLocation();
			
				board.removeStart(mover2, pawnSelect, player); //remove moving pawn from start
				board.removeMain(pawn2Loc, pawn2Select, pawn2Color); //remove other pawn from main
				board.updateMain(pawn2Loc, pawnSelect, player, mover2); //put moving pawn in previous spot of other pawn
				board.updateStart(pawn2Select, pawn2Color, movee2); //return other pawb to its hom
					
			}
			sorry.setVisible(false);
			sorry.dispose();
			
		}
		//drew a one
		//can move pawn from start position 
		//or move pawn one space
		else if(i==1){
			boolean validChoiceMade = false;
			while(validChoiceMade ==false){
				//off option
				
				one.setVisible(true);
				Object[] options = {"Leave Start",
				"Move "};
				int n = JOptionPane.showOptionDialog(frame,
					"You Can Move a Pawn from the Start Space" +
					"OR" + 
					"You Can Move a Pawn one space  ",
					"One Card",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,     //do not use a custom Icon
					options,  //the titles of buttons
					options[0]); //default button title
			
				//if Leave Start Option chosen
				if(n==JOptionPane.YES_OPTION){
				
					validChoiceMade = leaveStart(player, play, validChoiceMade);
				
				} //User selects move pawn option
				else if(n==JOptionPane.NO_OPTION){
					
					validChoiceMade = movePawn(player, play, "One", 1, validChoiceMade, false);
			}
		}
			one.setVisible(false);
			one.dispose();
	}
		//drew a two
		//can move pawn from start position 
		//or move pawn two spaces
		else if(i==2){
			reDraw =true;
			boolean validChoiceMade = false;
			while(validChoiceMade ==false){
				//off option
				
				two.setVisible(true);
				Object[] options = {"Leave Start",
				"Move "};
				int n = JOptionPane.showOptionDialog(frame,
					"You Can Move a Pawn from the Start Space" +
					"OR" + 
					"You Can Move a Pawn Two spaces  ",
					"Two Card",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,     //do not use a custom Icon
					options,  //the titles of buttons
					options[0]); //default button title
			
				//if Leave Start Option chosen
				if(n==JOptionPane.YES_OPTION){
						
					validChoiceMade = leaveStart(player, play, validChoiceMade);
				
				} //User selects move pawn option
				else if(n==JOptionPane.NO_OPTION){
					
					validChoiceMade = movePawn(player, play, "Two", 2, validChoiceMade, false);
				}
			}
			two.setVisible(false);
			two.dispose();
		}
		//drew a 3
		//can move pawn 3 spaces
		else if(i==3){
			boolean validChoiceMade =false;
			while(validChoiceMade==false){
				
				three.setVisible(true);
				
				validChoiceMade = movePawn(player, play, "Three", 3, validChoiceMade, false);
				
				
			}
			three.setVisible(false);
			three.dispose();
				
	  }
		//drew a 4
		//can move a pawn 4 spaces backwards
		else if(i==4){
			boolean validChoiceMade =false;
			while(validChoiceMade==false){
				
				four.setVisible(true);
				
				validChoiceMade = movePawn(player,play, "Four", 4, validChoiceMade, true);
					
			}
			four.setVisible(false);
			four.dispose();
				
				
		}
				
		//Drew a 5
		//move a pawn 5 spaces
		else if(i==5){
			boolean validChoiceMade =false;
			while(validChoiceMade==false){
				
				five.setVisible(true);
				
				validChoiceMade = movePawn(player, play, "Five", 5, validChoiceMade, false);
				
			}
			five.setVisible(false);
			five.dispose();
				
	  }
		//Drew a 7
		//Can move one Pawn 7 or split the seven between two pawns
		else if(i==7){
			boolean validChoiceMade = false;
			while(validChoiceMade ==false){
				
				seven.setVisible(true);
				//off option
				Object[] options = {"Move One Pawn",
				"Split "};
				int n = JOptionPane.showOptionDialog(frame,
					"You Can Move one Pawn 7 spaces" +
					"OR" + 
					"You Can Split the 7 spaces between two pawns ",
					"Seven Card",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,     //do not use a custom Icon
					options,  //the titles of buttons
					options[0]); //default button title
			
				//if Move one Pawn selected
				if(n==JOptionPane.YES_OPTION){
						
					
					validChoiceMade = movePawn(player, play, "Seven", 7, validChoiceMade, false);
				
				} //User selects split option
				else if(n==JOptionPane.NO_OPTION){
					
					int total = 7; //holds number of spaces left 
					
					//array used for messages
					String[] words = {"One", "Two", "Three", "Four", "Five", "Six", "Seven"};
					
						
					//select number of spaces to move first pawn
						
					//array of possible number of spaces
					String[] possibilities3 = new String[total];
					for(int j=0;j<total;j++){
							possibilities3[j] = "" +(j+1);
					}
					//player selects number of spaces they wish to move first pawn
					String mover3 = (String)JOptionPane.showInputDialog(
			                frame,
			                "You Can Move a Pawn " + words[total -1] + " Spaces\n" +
			                "Select number of spaces to move pawn",
			                "Seven Card",
			                JOptionPane.PLAIN_MESSAGE,
			                null,
			                possibilities3,
			                "Select");
						
					int mover4 = 0; //number of spaces to be moved
					for(int j=0;j<total;j++){
						String check = "" +(j+1);
						if(mover3.equals(check)){
							mover4 = j+1;
								
						}
					}
					
					validChoiceMade = movePawn(player, play, words[mover4-1], mover4, validChoiceMade, false);
						
						
					//use second half of movement
					
					total = total - mover4;
					
					//array of possible number of spaces
					String[] possibilities4 = new String[total];
					for(int j=0;j<total;j++){
							possibilities4[j] = "" +(j+1);
					}
					//player selects number of spaces they wish to move first pawn
					String mover4a = (String)JOptionPane.showInputDialog(
			                frame,
			                "You Can Move a Pawn " + words[total -1] + " Spaces\n" +
			                "Select number of spaces to move pawn",
			                "Seven Card",
			                JOptionPane.PLAIN_MESSAGE,
			                null,
			                possibilities4,
			                "Select");
						
					int mover5 = 0; //number of spaces to be moved
					for(int j=0;j<total;j++){
						String check = "" +(j+1);
						if(mover4a.equals(check)){
							mover5 = j+1;
								
						}
					}
					
					validChoiceMade = movePawn(player, play, words[mover5-1], mover5, validChoiceMade, false);
					
		       }
			}
			seven.setVisible(false);
			seven.dispose();
		
		}
		//drew an 8
		//can move a pawn 8 spaces
		else if(i==8){
			boolean validChoiceMade =false;
			while(validChoiceMade==false){
				
				eight.setVisible(true);
				
				validChoiceMade = movePawn(player, play, "Eight", 8, validChoiceMade, false);
			
			}
			eight.setVisible(false);
			eight.dispose();
			
		}
		//drew a 10
		//move 10 spaces forward or 1 space backwards
		else if(i==10){
			boolean validChoiceMade = false;
			while(validChoiceMade ==false){
				//off option
				ten.setVisible(true);
				Object[] options = {"Move Forward",
				"Move Backward "};
				int n = JOptionPane.showOptionDialog(frame,
					"You Can Move a Pawn 10 Spaces Forward" +
					"OR" + 
					"You Can Move a Pawn One Space Backwards  ",
					"Ten Card",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,     //do not use a custom Icon
					options,  //the titles of buttons
					options[0]); //default button title
			
				//if move forward Option chosen
				if(n==JOptionPane.YES_OPTION){
						
					validChoiceMade = movePawn(player, play, "Ten", 10, validChoiceMade, false);
				
				} //User selects move backward option
				else if(n==JOptionPane.NO_OPTION){
					
					validChoiceMade = movePawn(player, play, "Ten", 1, validChoiceMade, true);
				}
			}
			ten.setVisible(false);
			ten.dispose();
		}
		//Drew an 11
		//can move 11 spaces forward or switch places with another player's pawn
		else if(i==11){
			boolean validChoiceMade = false;
			while(validChoiceMade ==false){
				
				eleven.setVisible(true);
				//off option
				Object[] options = {"Move Forward",
				"Switch Places "};
				int n = JOptionPane.showOptionDialog(frame,
					"You Can Move a Pawn 11 Spaces Forward \n" +
					"OR \n" + 
					"You Can Switch Spaces With Another Player's Pawn  ",
					"Ten Card",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,     //do not use a custom Icon
					options,  //the titles of buttons
					options[0]); //default button title
			
				//if move forward Option chosen
				if(n==JOptionPane.YES_OPTION){
						
					validChoiceMade = movePawn(player, play, "Eleven", 11, validChoiceMade, false);
					
				} //User selects switch spaces option
				else if(n==JOptionPane.NO_OPTION){
					  
					List<Pawn> temp = board.getMainPawnsPlayer(player);
					Object[] possibilities = temp.toArray();
					List<Pawn> temp2 = board.getMainPawns(player);
					Object[] possibilities2 = temp2.toArray();
					//if the player has no one left on board or has no one they can attack, do nothing
					if(possibilities.length == 0 || possibilities2.length == 0){
						JOptionPane.showMessageDialog(frame, "There are either no pawns of yours on the board \n"
								+ "Or no other player's pawns to switch places with");
						validChoiceMade=true;
						
					}
					//otherwise, let them make their move
					else{
						validChoiceMade = true;
						//player selects pawn of theirs they wish to move
						
						//create array of names to choose from
						//that correspond to pawn available
						String[] possibilities3 = new String[possibilities.length];
						
						for(int k =0;k<possibilities3.length;k++){
							Pawn poss = (Pawn)possibilities[k];
							possibilities3[k] = "Pawn " + poss.getNum();
							
						}
						String mover = (String)JOptionPane.showInputDialog(
		                    frame,
		                    "You Can Switch Spaces With Another Player's Pawn \n" +
		                    "Select a pawn of yours to move",
		                    "Eleven Card",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    possibilities3,
		                    "Select");
						
						Pawn mover2 = new Pawn();
						for(int k =0;k<possibilities3.length;k++){
							if(mover.equals(possibilities3[k])){
								mover2 = (Pawn)possibilities[k];
							}
							
						}
						//player selects pawn on the board to replace
						
						//create array of names to choose from
						//that correspond to pawn available
						String[] possibilities4 = new String[possibilities2.length];
						
						for(int k =0;k<possibilities4.length;k++){
							Pawn poss = (Pawn)possibilities2[k];
							possibilities4[k] = "" + poss.getColor()+ " - Pawn " +poss.getNum();
							
						}
						String movee = (String)JOptionPane.showInputDialog(
		                    frame,
		                    "You Can Switch Spaces With Another Player's Pawn \n" +
		                    "Select another player's pawn to replace",
		                    "Eleven Card",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    possibilities4,
		                    "Select");
						
						Pawn movee2 = new Pawn();
						for(int k =0;k<possibilities4.length;k++){
							if(movee.equals(possibilities4[k])){
								movee2 = (Pawn)possibilities2[k];
							}
							
						}
					
						int pawnSelect = mover2.getNum();
						int pawn2Select = movee2.getNum();
						String pawn2Color = movee2.getColor();
						int pawn2Loc = movee2.getLocation();
						int pawnLoc = mover2.getLocation();
						
					
						board.removeMain(pawnLoc, pawnSelect, player); //remove moving pawn from main
						board.removeMain(pawn2Loc, pawn2Select, pawn2Color); //remove other pawn from main
						board.updateMain(pawn2Loc, pawnSelect, player, mover2); //put moving pawn in previous spot of other pawn
						board.updateMain(pawnLoc, pawn2Select, pawn2Color, movee2); //put other pawn where old pawn used to be
							
					}
				}
			}
			eleven.setVisible(false);
			eleven.dispose();
		}
		//Drew a 12
		//Can move a pawn 12 spaces
		else if(i==12){
			boolean validChoiceMade =false;
			while(validChoiceMade==false){
				
				twelve.setVisible(true);
				validChoiceMade = movePawn(player, play, "Twelve", 12, validChoiceMade, false);
			}
			twelve.setVisible(false);
			twelve.dispose();
		}
		
		return reDraw;
		
	}
  
}
