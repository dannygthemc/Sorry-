import java.util.List;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/*
 * this class defines the playing board
 * Board is made up of Class Square groups
 */
public class Board {
	
	private Square[] mainBoard; //array of squares representing the outside edge of board
	
	//array of squares to represent safety-zone / home square for each color
	private Square[] homeRed; 
	private Square[] homeBlue;
	private Square[] homeGreen;
	private Square[] homeYellow;
	
	//array of squares which serve as starting square for each color
	private Square[] startRed;
	private Square[] startBlue;
	private Square[] startGreen;
	private Square[] startYellow;
	
	//mark the locations of home paths
	private int markHomeRed;
	private int markHomeBlue;
	private int markHomeGreen;
	private int markHomeYellow;
	
	//mark the locations of start spaces
	private int markStartRed;
	private int markStartBlue;
	private int markStartGreen;
	private int markStartYellow;
	
	//GUI components
	private JFrame window; // frame on which board is displayed
	private JPanel content; // panel which holds components
	private JLabel[] label; //array of labels to function as squares
	
	//Array of Labels for Start and Home sections
	private JLabel[] grnStart;
	private JLabel[] grnHm;
	private JLabel[] redStart;
	private JLabel[] redHm;
	private JLabel[] yellowStart;
	private JLabel[] yellowHm;
	private JLabel[] blueStart;
	private JLabel[] blueHm;
	
	//labels for pawns
	private JLabel[] redPawn ;
	private JLabel[] bluePawn;
	private JLabel[] greenPawn;
	private JLabel[] yellowPawn;
	
	//buttons for pawns
	private JButton[] redButt;
	private JButton[] blueButt;
	private JButton[] greenButt;
	private JButton[] yellowButt;
	
	
	
	/*
	 * constructor
	 */
	public Board(){
		
		//create an array of squares numbered from 0 to 59 for the main board
		//0 is in the bottom left corner, and squares increase by 1 in clockwise direction
		//around the edge of board
		mainBoard = new Square[60];
		
		
		for(int i=0; i<60; i++){
			mainBoard[i] = new Square(i);
		}
		
		//create arrays of squares to represent home paths 
		homeRed = new Square[6];
		homeBlue = new Square[6];
		homeGreen = new Square[6];
		homeYellow = new Square[6];
		
		for(int i=0; i<6; i++){
			homeRed[i] = new Square(i);
			homeBlue[i] = new Square(i);
			homeGreen[i] = new Square(i);
			homeYellow[i] = new Square(i);
		}
		
		//create and initiate arrays for Starting Square
		startRed = new Square[4];
		startBlue = new Square[4];
		startGreen = new Square[4];
		startYellow = new Square[4];
		
		for(int i=0; i<4; i++){
			startRed[i] = new Square(i);
			startBlue[i] = new Square(i);
			startGreen[i] = new Square(i);
			startYellow[i] = new Square(i);
		}
		
		
		
		//mark home and start locations
		
		markHomeGreen = 2;
		markStartGreen = 4;
		
		markHomeRed = 17;
		markStartRed = 19;
		
		markHomeBlue = 32;
		markStartBlue = 34;
		
		markHomeYellow = 47;
		markStartYellow = 49;
		
		//set Slide locations
		
		mainBoard[1].setSlide("Green", 3);
		mainBoard[9].setSlide("Green", 4);
		mainBoard[16].setSlide("Red", 3);
		mainBoard[24].setSlide("Red", 4);
		mainBoard[31].setSlide("Blue", 3);
		mainBoard[39].setSlide("Blue", 4);
		mainBoard[46].setSlide("Yellow", 3);
		mainBoard[54].setSlide("Yellow", 4);
		
		GUI();
		
		
	}
	
	/*
	 * check if designated spot is a slide not 
	 * of current player's color
	 * return jump of slide if spot is a slide
	 * return zero otherwise
	 */
	public int isSlide(int loc, String color){
		
		int result = 0;
		boolean slideCheck = mainBoard[loc].isSlide();
		String slideCol = mainBoard[loc].colSlide();
		boolean slideColCheck = false;
		if(slideCheck!=false){
			if(slideCol.equals(color)){//if color of slide matches color of player
				slideColCheck = true; 
			}
		}
		//if there is a slide and it's not the same color as player
		if(slideCheck == true && slideColCheck == false){
			result = mainBoard[loc].sizeSlide(); //get the jump of the slide
			
		}
		return result;
	}
	/*
	 * get List of all pawns along given slide path
	 */
	public List<Pawn> getSlidePawns(int start, int fin){
		List<Pawn> temp = new ArrayList<Pawn>();
		for(int i=start;i<=fin;i++){
			if(mainBoard[i].isOccupied()){
				temp.add(mainBoard[i].getPawn());
			}
		}
		return temp;
	}
	
	/*
	 * close the Jframe and deallocate memory
	 */
	public void close(){
		window.setVisible(false); //you can't see me!
		window.dispose(); //Destroy the JFrame object
	}
	
	/*
	 * method to update GUI when player has moved pawn on the mainBoard 
	 * @param int loc integer reference to location on main board
	 * @param int pawnNumb number used to identify which pawn of the specified color
	 * is being moved
	 * @param String color color of pawn being moved
	 * @param pawn to be added to Square
	 */
	public void updateMain(int loc,int pawnNumb, String color, Pawn pawn){
		
		pawn.setLocation(loc);
		
		if(color.equals("Green")){
			label[loc].add(greenPawn[pawnNumb]);
		}
		else if(color.equals("Red")){
			label[loc].add(redPawn[pawnNumb]);
		}
		else if(color.equals("Blue")){
			label[loc].add(bluePawn[pawnNumb]);
		}
		else if(color.equals("Yellow")){
			label[loc].add(yellowPawn[pawnNumb]);
		}
		label[loc].revalidate();
		label[loc].repaint();
		mainBoard[loc].arrival(pawn);
		
	}
	
	/*
	 * method to remove pawn from previous position on the main board
	 * @param int loc identifies space on mainboard
	 * @param int pawnNumb identifies which pawn
	 * @param String color color of pawn
	 */
	public void removeMain(int loc,int pawnNumb, String color){
		
		if(color.equals("Green")){
			label[loc].remove(greenPawn[pawnNumb]);
		}
		else if(color.equals("Red")){
			label[loc].remove(redPawn[pawnNumb]);
		}
		else if(color.equals("Blue")){
			label[loc].remove(bluePawn[pawnNumb]);
		}
		else if(color.equals("Yellow")){
			label[loc].remove(yellowPawn[pawnNumb]);
		}
		
		label[loc].revalidate();
		label[loc].repaint();
		mainBoard[loc].leave();
		
	}
	
	/*
	 * method to update GUI when pawn is moved onto the start space 
	 * @param int loc integer reference to location on main board
	 * @param int pawnNumb number used to identify which pawn of the specified color
	 * is being moved
	 * @param String color color of pawn being moved
	 * @param pawn to be added to Square
	 */
	public void updateStart(int pawnNumb, String color, Pawn pawn){
		
		if(color.equals("Green")){
			startGreen[pawnNumb].arrival(pawn);
			int loc = pawnNumb;
			grnStart[loc].add(greenPawn[pawnNumb]);
			grnStart[loc].revalidate();
			grnStart[loc].repaint();
			
		}
		else if(color.equals("Red")){
			startRed[pawnNumb].arrival(pawn);;
			int loc = pawnNumb;
			redStart[loc].add(redPawn[pawnNumb]);
			redStart[loc].revalidate();
			redStart[loc].repaint();
			
		}
		else if(color.equals("Blue")){
			startBlue[pawnNumb].arrival(pawn);
			int loc = pawnNumb;
			blueStart[loc].add(bluePawn[pawnNumb]);
			blueStart[loc].revalidate();
			blueStart[loc].repaint();
		}
		else if(color.equals("Yellow")){
			startYellow[pawnNumb].arrival(pawn);
			int loc = pawnNumb;
			yellowStart[loc].add(yellowPawn[pawnNumb]);
			yellowStart[loc].revalidate();
			yellowStart[loc].repaint();
		}
		
		
	}
	
	/*
	 * method to remove pawn from previous position on Start space
	 * @param int loc identifies space on start space
	 * @param int pawnNumb identifies which pawn
	 * @param String color color of pawn
	 */
	public void removeStart(Pawn pawn, int pawnNumb, String color){
		
		if(color.equals("Green")){
			int loc = pawnNumb;
			startGreen[pawnNumb].leave();
			grnStart[loc].remove(greenPawn[pawnNumb]);
			grnStart[loc].revalidate();
			grnStart[loc].repaint();
		} else if(color.equals("Red")){
			int loc = pawnNumb;
			startRed[pawnNumb].leave();
			redStart[loc].remove(redPawn[pawnNumb]);
			redStart[loc].revalidate();
			redStart[loc].repaint();
			
		}else if(color.equals("Blue")){
			int loc = pawnNumb;
			startBlue[pawnNumb].leave();
			blueStart[loc].remove(bluePawn[pawnNumb]);
			blueStart[loc].revalidate();
			blueStart[loc].repaint();
		}else if(color.equals("Yellow")){
			int loc = pawnNumb;
			startYellow[pawnNumb].leave();
			yellowStart[loc].remove(yellowPawn[pawnNumb]);
			yellowStart[loc].revalidate();
			yellowStart[loc].repaint();
		}
		
	}
	
	/*
	 * get pawns currently in start
	 * corresponding to color of current player
	 */
	public List<Pawn> getStart(String color){
		List<Pawn> temp = new ArrayList<Pawn>();//create list
		//go through each start space of designated color
		//add any pawns on spaces to the list
		if(color.equals("Red")){
			for(int i=0;i<4;i++){
				if(startRed[i].getPawn()!=null){
					temp.add(startRed[i].getPawn());
				}
			}
		}
			
		if(color.equals("Blue")){
			for(int i=0;i<4;i++){
				if(startBlue[i].getPawn()!=null){
					temp.add(startBlue[i].getPawn());
				}
			}
			
		}
			
		if(color.equals("Yellow")){
			for(int i=0;i<4;i++){
				if(startYellow[i].getPawn()!=null){
					temp.add(startYellow[i].getPawn());
				}
			}
		}
			
		if(color.equals("Green")){
			for(int i=0;i<4;i++){
				if(startGreen[i].getPawn()!=null){
					temp.add(startGreen[i].getPawn());
				}
			}
		}
			
		
		
		return temp;
			
	}
	
	/*
	 * get pawns currently in home
	 * corresponding to color of current player
	 */
	public List<Pawn> getHomePawns(String color){
		List<Pawn> temp = new ArrayList<Pawn>();//create list
		//go through each home space of designated color
		//add any pawns on spaces to the list
		if(color.equals("Red")){
			for(int i=0;i<5;i++){
				if(homeRed[i].getPawn()!=null){
					temp.add(homeRed[i].getPawn());
				}
			}
		}
			
		if(color.equals("Blue")){
			for(int i=0;i<5;i++){
				if(homeBlue[i].getPawn()!=null){
					temp.add(homeBlue[i].getPawn());
				}
			}
			
		}
			
		if(color.equals("Yellow")){
			for(int i=0;i<5;i++){
				if(homeYellow[i].getPawn()!=null){
					temp.add(homeYellow[i].getPawn());
				}
			}
		}
			
		if(color.equals("Green")){
			for(int i=0;i<5;i++){
				if(homeGreen[i].getPawn()!=null){
					temp.add(homeGreen[i].getPawn());
				}
			}
		}
			
		return temp;
			
	}
	
	/*
	 * get all pawns along mainBoard of a different color than the current player
	 * @param String color the color of the current player
	 */
	public List<Pawn> getMainPawns(String color){
		List<Pawn> temp = new ArrayList<Pawn>();
		for(int i=0;i<60;i++){
			if(mainBoard[i].isOccupied()){
				Pawn pawn1 = mainBoard[i].getPawn();
				if(pawn1.getColor().equals(color)){ //if pawn is of color, do nothoing
					
				}
				else{ //if not of color, add to list
					temp.add(pawn1);
				}
			}
		}
		return temp;
	}
	
	/*
	 * get all pawns along mainBoard of the same color as the current player
	 * @param String color the color of the current player
	 */
	public List<Pawn> getMainPawnsPlayer(String color){
		List<Pawn> temp = new ArrayList<Pawn>();
		for(int i=0;i<60;i++){
			if(mainBoard[i].isOccupied()){
				Pawn pawn1 = mainBoard[i].getPawn();
				if(pawn1.getColor().equals(color)){ //if pawn is of color
					temp.add(pawn1); //add to list
				}
			}
		}
		return temp;
	}
	/*
	 * update index
	 */
	public int incIndex(int one, int two){
		
		int total = one;
		for(int i=0;i<two; i++){
			
			total = total + 1;
			if(total==60){
				total =0;
			}
			
			
			
		}
		return total;
	}
	/*
	 * update index
	 */
	public int decIndex(int one, int two){
		
		int total = one;
		for(int i=0;i<two; i++){
			
			total = total - 1;
			if(total==-1){
				total =59;
			}
			
			
			
		}
		return total;
	}
	/*
	 * get pawn at given location
	 */
	public Pawn getMainAt(int loc){
		return mainBoard[loc].getPawn();
	}
	
	
	
	/*
	 * method to update GUI when player has moved pawn on the Home spaces
	 * @param int loc integer reference to location on home
	 * @param int pawnNumb number used to identify which pawn of the specified color
	 * is being moved
	 * @param String color color of pawn being moved
	 * @param pawn to be added to Square
	 */
	public void updateHome(int loc,int pawnNumb, String color, Pawn pawn){
		
		pawn.setLocation(loc);
		
		if(color.equals("Green")){
			grnHm[loc].add(greenPawn[pawnNumb]);
			grnHm[loc].revalidate();
			grnHm[loc].repaint();
			homeGreen[loc].arrival(pawn);
		}
		else if(color.equals("Red")){
			redHm[loc].add(redPawn[pawnNumb]);
			redHm[loc].revalidate();
			redHm[loc].repaint();
			homeRed[loc].arrival(pawn);
		}
		else if(color.equals("Blue")){
			blueHm[loc].add(bluePawn[pawnNumb]);
			blueHm[loc].revalidate();
			blueHm[loc].repaint();
			homeBlue[loc].arrival(pawn);
		}
		else if(color.equals("Yellow")){
			yellowHm[loc].add(yellowPawn[pawnNumb]);
			yellowHm[loc].revalidate();
			yellowHm[loc].repaint();
			homeYellow[loc].arrival(pawn);
		}

		
	}
	
	/*
	 * method to remove pawn from previous position on the home path
	 * @param int loc identifies space on mainboard
	 * @param int pawnNumb identifies which pawn
	 * @param String color color of pawn
	 */
	public void removeHome(int loc,int pawnNumb, String color){
		
		if(color.equals("Green")){
			grnHm[loc].remove(greenPawn[pawnNumb]);
			grnHm[loc].revalidate();
			grnHm[loc].repaint();
			homeGreen[loc].leave();
		}
		else if(color.equals("Red")){
			redHm[loc].remove(redPawn[pawnNumb]);
			redHm[loc].revalidate();
			redHm[loc].repaint();
			homeRed[loc].leave();
		}
		else if(color.equals("Blue")){
			blueHm[loc].remove(bluePawn[pawnNumb]);
			blueHm[loc].revalidate();
			blueHm[loc].repaint();
			homeBlue[loc].leave();
		}
		else if(color.equals("Yellow")){
			yellowHm[loc].remove(yellowPawn[pawnNumb]);
			yellowHm[loc].revalidate();
			yellowHm[loc].repaint();
			homeYellow[loc].leave();
		}
		
	}
	
	/*
	 * 
	 */
		
	
	
	/*
	 * code that initiates the GUI
	 */

	public void GUI(){
		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2); //defines border for square
		content = new JPanel();
		//create a content pain with a gridlayout of 16x16
		content.setLayout(new GridLayout(16, 16));
		
		//create coresponding JLabels for each of the grid square
		JLabel grid[][] = new JLabel[16][16];
		//establish a basic grid
		int rows = 16;
        int cols = 16;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                
                grid[r][c] = new JLabel();
                grid[r][c].setSize(15, 15);
                grid[r][c].setBackground(Color.WHITE);
                grid[r][c].setBorder(border);
                grid[r][c].setOpaque(true);
                content.add(grid[r][c]);
            }
        }
 
        //define Pawn Labels
        
        redPawn = new JLabel[4];
        greenPawn = new JLabel[4];
        yellowPawn = new JLabel[4];
        bluePawn = new JLabel[4];
        //RED PAWN
        ImageIcon pawn1 = new ImageIcon("Pictures/red/redPawn.png");
		Image pn1 = pawn1.getImage(); // transform it 
		Image newpn1 = pn1.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		pawn1 = new ImageIcon(newpn1);  // transform it back
		
		for(int i=0;i<4;i++){
			redPawn[i] = new JLabel("" +(i+1));
			redPawn[i].setIcon(pawn1);
			redPawn[i].setHorizontalAlignment(SwingConstants.CENTER);
			redPawn[i].setVerticalAlignment(SwingConstants.CENTER);
		}
		//BLUE PAWN
		ImageIcon pawn2 = new ImageIcon("Pictures/blue/bluePawn.png");
		Image pn2 = pawn2.getImage(); // transform it 
		Image newpn2 = pn2.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		pawn2 = new ImageIcon(newpn2);  // transform it back
		
		for(int i=0;i<4;i++){
			
			bluePawn[i] = new JLabel("" +i);
			bluePawn[i].setIcon(pawn2);
			bluePawn[i].setHorizontalAlignment(SwingConstants.CENTER);
			bluePawn[i].setVerticalAlignment(SwingConstants.CENTER);
		}
		
		
		//GREEN PAWN
		ImageIcon pawn3 = new ImageIcon("Pictures/green/greenPawn.png");
		Image pn3 = pawn3.getImage(); // transform it 
		Image newpn3 = pn3.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		pawn3 = new ImageIcon(newpn3);  // transform it back
		
		for(int i=0;i<4;i++){
		
			greenPawn[i] = new JLabel(""+i);
			greenPawn[i].setIcon(pawn3);
			greenPawn[i].setHorizontalAlignment(SwingConstants.CENTER);
			greenPawn[i].setVerticalAlignment(SwingConstants.CENTER);
		}
		
		//YELLOW PAWN
		ImageIcon pawn4 = new ImageIcon("Pictures/yellow/yellowPawn.png");
		Image pn4 = pawn4.getImage(); // transform it 
		Image newpn4 = pn4.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		pawn4 = new ImageIcon(newpn4);  // transform it back
		
		for(int i=0;i<4;i++){
		
			yellowPawn[i] = new JLabel(""+i);
			yellowPawn[i].setIcon(pawn4);
			yellowPawn[i].setHorizontalAlignment(SwingConstants.CENTER);
			yellowPawn[i].setVerticalAlignment(SwingConstants.CENTER);
		}
		
		//Create and initiate Pawn Buttons
		
		
		redButt = new JButton[4];
		blueButt = new JButton[4];
		greenButt = new JButton[4];
		yellowButt = new JButton[4];
		
		//red
		ImageIcon redButton = new ImageIcon("Pictures/red/redPawnButton.png");
		Image red = redButton.getImage(); // transform it 
		Image newRedButton = red.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		redButton = new ImageIcon(newRedButton);  // transform it back
		
		for(int i=0; i<4;i++){
			redButt[i] = new JButton(redButton);
			redButt[i].setPressedIcon(pawn1);
			redButt[i].setBorder(BorderFactory.createEmptyBorder());
			redButt[i].setContentAreaFilled(false);
			redButt[i].setFocusable(false);
		}
		
		//blue
		ImageIcon blueButton = new ImageIcon("Pictures/blue/bluePawnButton.png");
		Image blue = blueButton.getImage(); // transform it 
		Image newblueButton = blue.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		blueButton = new ImageIcon(newblueButton);  // transform it back
		
		for(int i=0; i<4;i++){
			blueButt[i] = new JButton(blueButton);
			blueButt[i].setPressedIcon(pawn2);
			blueButt[i].setBorder(BorderFactory.createEmptyBorder());
			blueButt[i].setContentAreaFilled(false);
			blueButt[i].setFocusable(false);
		}
		
		//green
		ImageIcon greenButton = new ImageIcon("Pictures/green/greenPawnButton.png");
		Image green = greenButton.getImage(); // transform it 
		Image newgreenButton = green.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		greenButton = new ImageIcon(newgreenButton);  // transform it back
		
		for(int i=0; i<4;i++){
			greenButt[i] = new JButton(greenButton);
			greenButt[i].setPressedIcon(pawn1);
			greenButt[i].setBorder(BorderFactory.createEmptyBorder());
			greenButt[i].setContentAreaFilled(false);
			greenButt[i].setFocusable(false);
		}
		
		//yellow
		ImageIcon yellowButton = new ImageIcon("Pictures/yellow/yellowPawnButton.png");
		Image yellow = yellowButton.getImage(); // transform it 
		Image newyellowButton = yellow.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		yellowButton = new ImageIcon(newyellowButton);  // transform it back
		
		for(int i=0; i<4;i++){
			yellowButt[i] = new JButton(yellowButton);
			yellowButt[i].setPressedIcon(pawn1);
			yellowButt[i].setBorder(BorderFactory.createEmptyBorder());
			yellowButt[i].setContentAreaFilled(false);
			yellowButt[i].setFocusable(false);
		}
		
		
		
		label = new JLabel[60]; //creates 60 labels to represent the 60 squares of the main board
	
		//Instantitat Label Arrays to represent start and home path areas
		grnStart = new JLabel[4];
		grnHm = new JLabel[6];
		redStart = new JLabel[4];
		redHm = new JLabel[6];
		yellowStart = new JLabel[4];
		yellowHm = new JLabel[6];
		blueStart = new JLabel[4];
		blueHm = new JLabel[6];
		
		//set basic properties of labels
		for(int i=0; i<60; i++){
			
			
			label[i] = new JLabel();
			label[i].setSize(15,15);
			label[i].setBackground(Color.WHITE);
			label[i].setBorder(border);
			label[i].setOpaque(true);
			label[i].setVerticalAlignment(SwingConstants.TOP);
			label[i].setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
			
		}
		
		//replace members of the basic grid with desired member of label
		int j = 240;
		int k = 0;
		
		for(int i=15; i>=0; i--){

			content.remove(grid[i][0]);
			content.add(label[k],j);
			j = (j-16);
			k++;
		}
		
		
		for(int i=1; i<=15; i++){

			content.remove(grid[0][i]);
			content.add(label[k],i);
			k++;
		}
		
		int j2 = 31;
		
		for(int i=1; i<=15; i++){

			content.remove(grid[i][15]);
			content.add(label[k],j2);
			j2 = (j2+16);
			k++;
		}
		
		int j3 = 254;
		
		for(int i=14; i>=1; i--){

			content.remove(grid[15][i]);
			content.add(label[k],j3);
			j3 = (j3-1);
			k++;
		}
		
		
		//Adjust Squares to represent start and home path areas
		
		//GREEN START
		
		Border border2 = BorderFactory.createLineBorder(Color.BLACK, 2);
		grnStart[0] = new JLabel();
		Color newGreen = new Color(146, 208, 80);
		
		grnStart[0].setSize(15,15);
		grnStart[0].setBackground(newGreen);
		grnStart[0].setBorder(border2);
		grnStart[0].setOpaque(true);
		content.remove(177);
		content.add(grnStart[0], 177);
		
		grnStart[1] = new JLabel();
		grnStart[1].setSize(15,15);
		grnStart[1].setBackground(newGreen);
		grnStart[1].setBorder(border2);
		grnStart[1].setOpaque(true);
		content.remove(178);
		content.add(grnStart[1], 178);
		
		grnStart[2] = new JLabel();
		grnStart[2].setSize(15,15);
		grnStart[2].setBackground(newGreen);
		grnStart[2].setBorder(border2);
		grnStart[2].setOpaque(true);
		content.remove(161);
		content.add(grnStart[2], 161);
		
		grnStart[3] = new JLabel();
		grnStart[3].setSize(15,15);
		grnStart[3].setBackground(newGreen);
		grnStart[3].setBorder(border2);
		grnStart[3].setOpaque(true);
		content.remove(162);
		content.add(grnStart[3], 162);
		
		//GREEN HOME PATH
		
		grnHm[0] = new JLabel();
		grnHm[0].setSize(15,15);
		grnHm[0].setBackground(newGreen);
		grnHm[0].setBorder(border2);
		grnHm[0].setOpaque(true);
		content.remove(209);
		content.add(grnHm[0], 209);
		
		grnHm[1] = new JLabel();
		grnHm[1].setSize(15,15);
		grnHm[1].setBackground(newGreen);
		grnHm[1].setBorder(border2);
		grnHm[1].setOpaque(true);
		content.remove(210);
		content.add(grnHm[1], 210);
		
		grnHm[2] = new JLabel();
		grnHm[2].setSize(15,15);
		grnHm[2].setBackground(newGreen);
		grnHm[2].setBorder(border2);
		grnHm[2].setOpaque(true);
		content.remove(211);
		content.add(grnHm[2], 211);
		
		grnHm[3] = new JLabel();
		grnHm[3].setSize(15,15);
		grnHm[3].setBackground(newGreen);
		grnHm[3].setBorder(border2);
		grnHm[3].setOpaque(true);
		content.remove(212);
		content.add(grnHm[3], 212);
		
		grnHm[4] = new JLabel();
		grnHm[4].setSize(15,15);
		grnHm[4].setBackground(newGreen);
		grnHm[4].setBorder(border2);
		grnHm[4].setOpaque(true);
		content.remove(213);
		content.add(grnHm[4], 213);
		
		//RED START
		
		redStart[0] = new JLabel();
		Color newRed = new Color(255, 0, 0);
		
		redStart[0].setSize(15,15);
		redStart[0].setBackground(newRed);
		redStart[0].setBorder(border2);
		redStart[0].setOpaque(true);
		content.remove(20);
		content.add(redStart[0], 20);
		
		redStart[1] = new JLabel();
		redStart[1].setSize(15,15);
		redStart[1].setBackground(newRed);
		redStart[1].setBorder(border2);
		redStart[1].setOpaque(true);
		content.remove(21);
		content.add(redStart[1], 21);
		
		redStart[2] = new JLabel();
		redStart[2].setSize(15,15);
		redStart[2].setBackground(newRed);
		redStart[2].setBorder(border2);
		redStart[2].setOpaque(true);
		content.remove(36);
		content.add(redStart[2], 36);
		
		redStart[3] = new JLabel();
		redStart[3].setSize(15,15);
		redStart[3].setBackground(newRed);
		redStart[3].setBorder(border2);
		redStart[3].setOpaque(true);
		content.remove(37);
		content.add(redStart[3], 37);
		
		//RED HOME PATH
		
		redHm[0] = new JLabel();
		redHm[0].setSize(15,15);
		redHm[0].setBackground(newRed);
		redHm[0].setBorder(border2);
		redHm[0].setOpaque(true);
		content.remove(18);
		content.add(redHm[0], 18);
		
		redHm[1] = new JLabel();
		redHm[1].setSize(15,15);
		redHm[1].setBackground(newRed);
		redHm[1].setBorder(border2);
		redHm[1].setOpaque(true);
		content.remove(34);
		content.add(redHm[1], 34);
		
		redHm[2] = new JLabel();
		redHm[2].setSize(15,15);
		redHm[2].setBackground(newRed);
		redHm[2].setBorder(border2);
		redHm[2].setOpaque(true);
		content.remove(50);
		content.add(redHm[2], 50);
		
		redHm[3] = new JLabel();
		redHm[3].setSize(15,15);
		redHm[3].setBackground(newRed);
		redHm[3].setBorder(border2);
		redHm[3].setOpaque(true);
		content.remove(66);
		content.add(redHm[3], 66);
		
		redHm[4] = new JLabel();
		redHm[4].setSize(15,15);
		redHm[4].setBackground(newRed);
		redHm[4].setBorder(border2);
		redHm[4].setOpaque(true);
		content.remove(82);
		content.add(redHm[4], 82);
		
		//BLUE START
		
		blueStart[0] = new JLabel();
		Color newBlue = new Color(8, 82, 150);
		
		blueStart[0].setSize(15,15);
		blueStart[0].setBackground(newBlue);
		blueStart[0].setBorder(border2);
		blueStart[0].setOpaque(true);
		content.remove(77);
		content.add(blueStart[0], 77);
		
		blueStart[1] = new JLabel();
		blueStart[1].setSize(15,15);
		blueStart[1].setBackground(newBlue);
		blueStart[1].setBorder(border2);
		blueStart[1].setOpaque(true);
		content.remove(78);
		content.add(blueStart[1], 78);
		
		blueStart[2] = new JLabel();
		blueStart[2].setSize(15,15);
		blueStart[2].setBackground(newBlue);
		blueStart[2].setBorder(border2);
		blueStart[2].setOpaque(true);
		content.remove(93);
		content.add(blueStart[2], 93);
		
		blueStart[3] = new JLabel();
		blueStart[3].setSize(15,15);
		blueStart[3].setBackground(newBlue);
		blueStart[3].setBorder(border2);
		blueStart[3].setOpaque(true);
		content.remove(94);
		content.add(blueStart[3], 94);
		
		//BLUE HOME PATH
		
		blueHm[0] = new JLabel();
		blueHm[0].setSize(15,15);
		blueHm[0].setBackground(newBlue);
		blueHm[0].setBorder(border2);
		blueHm[0].setOpaque(true);
		content.remove(46);
		content.add(blueHm[0], 46);
		
		blueHm[1] = new JLabel();
		blueHm[1].setSize(15,15);
		blueHm[1].setBackground(newBlue);
		blueHm[1].setBorder(border2);
		blueHm[1].setOpaque(true);
		content.remove(45);
		content.add(blueHm[1], 45);
		
		blueHm[2] = new JLabel();
		blueHm[2].setSize(15,15);
		blueHm[2].setBackground(newBlue);
		blueHm[2].setBorder(border2);
		blueHm[2].setOpaque(true);
		content.remove(44);
		content.add(blueHm[2], 44);
		
		blueHm[3] = new JLabel();
		blueHm[3].setSize(15,15);
		blueHm[3].setBackground(newBlue);
		blueHm[3].setBorder(border2);
		blueHm[3].setOpaque(true);
		content.remove(43);
		content.add(blueHm[3], 43);
		
		blueHm[4] = new JLabel();
		blueHm[4].setSize(15,15);
		blueHm[4].setBackground(newBlue);
		blueHm[4].setBorder(border2);
		blueHm[4].setOpaque(true);
		content.remove(42);
		content.add(blueHm[4], 42);
		
		//YELLOW START
		
		yellowStart[0] = new JLabel();
		Color newyellow = new Color(255, 255, 0);
		
		yellowStart[0].setSize(15,15);
		yellowStart[0].setBackground(newyellow);
		yellowStart[0].setBorder(border2);
		yellowStart[0].setOpaque(true);
		content.remove(218);
		content.add(yellowStart[0], 218);
		
		yellowStart[1] = new JLabel();
		yellowStart[1].setSize(15,15);
		yellowStart[1].setBackground(newyellow);
		yellowStart[1].setBorder(border2);
		yellowStart[1].setOpaque(true);
		content.remove(219);
		content.add(yellowStart[1], 219);
		
		yellowStart[2] = new JLabel();
		yellowStart[2].setSize(15,15);
		yellowStart[2].setBackground(newyellow);
		yellowStart[2].setBorder(border2);
		yellowStart[2].setOpaque(true);
		content.remove(234);
		content.add(yellowStart[2], 234);
		
		yellowStart[3] = new JLabel();
		yellowStart[3].setSize(15,15);
		yellowStart[3].setBackground(newyellow);
		yellowStart[3].setBorder(border2);
		yellowStart[3].setOpaque(true);
		content.remove(235);
		content.add(yellowStart[3], 235);
		
		//yellow HOME PATH
		
		yellowHm[0] = new JLabel();
		yellowHm[0].setSize(15,15);
		yellowHm[0].setBackground(newyellow);
		yellowHm[0].setBorder(border2);
		yellowHm[0].setOpaque(true);
		content.remove(237);
		content.add(yellowHm[0], 237);
		
		yellowHm[1] = new JLabel();
		yellowHm[1].setSize(15,15);
		yellowHm[1].setBackground(newyellow);
		yellowHm[1].setBorder(border2);
		yellowHm[1].setOpaque(true);
		content.remove(221);
		content.add(yellowHm[1], 221);
		
		yellowHm[2] = new JLabel();
		yellowHm[2].setSize(15,15);
		yellowHm[2].setBackground(newyellow);
		yellowHm[2].setBorder(border2);
		yellowHm[2].setOpaque(true);
		content.remove(205);
		content.add(yellowHm[2], 205);
		
		yellowHm[3] = new JLabel();
		yellowHm[3].setSize(15,15);
		yellowHm[3].setBackground(newyellow);
		yellowHm[3].setBorder(border2);
		yellowHm[3].setOpaque(true);
		content.remove(189);
		content.add(yellowHm[3], 189);
		
		yellowHm[4] = new JLabel();
		yellowHm[4].setSize(15,15);
		yellowHm[4].setBackground(newyellow);
		yellowHm[4].setBorder(border2);
		yellowHm[4].setOpaque(true);
		content.remove(173);
		content.add(yellowHm[4], 173);
		
		//Add images to represent HOME space
		
		//BLUE HOME
		
		ImageIcon imageHm1 = new ImageIcon("Pictures/blue/blueHome1.jpg");
		Image imHm1 = imageHm1.getImage(); // transform it 
		Image newimgHm1 = imHm1.getScaledInstance(55, 53,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageHm1 = new ImageIcon(newimgHm1);  // transform it back
		
		blueHm[5] = new JLabel();
		blueHm[5].setIcon(imageHm1);
		content.remove(40);
		content.add(blueHm[5], 40);
		
		ImageIcon imageHm2 = new ImageIcon("Pictures/blue/blueHome2.jpg");
		Image imHm2 = imageHm2.getImage(); // transform it 
		Image newimgHm2 = imHm2.getScaledInstance(55, 53,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageHm2 = new ImageIcon(newimgHm2);  // transform it back
		
		JLabel hm2 = new JLabel();
		hm2.setIcon(imageHm2);
		content.remove(41);
		content.add(hm2, 41);
		
		ImageIcon imageHm3 = new ImageIcon("Pictures/blue/blueHome3.jpg");
		Image imHm3 = imageHm3.getImage(); // transform it 
		Image newimgHm3 = imHm3.getScaledInstance(55, 53,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageHm3 = new ImageIcon(newimgHm3);  // transform it back
		
		JLabel hm3 = new JLabel();
		hm3.setIcon(imageHm3);
		content.remove(57);
		content.add(hm3, 57);
		
		ImageIcon imageHm4 = new ImageIcon("Pictures/blue/blueHome4.jpg");
		Image imHm4 = imageHm4.getImage(); // transform it 
		Image newimgHm4 = imHm4.getScaledInstance(55, 53,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageHm4 = new ImageIcon(newimgHm4);  // transform it back
		
		JLabel hm4 = new JLabel();
		hm4.setIcon(imageHm4);
		content.remove(56);
		content.add(hm4, 56);
		
		//RED HOME
		
		ImageIcon imageHm5 = new ImageIcon("Pictures/red/redHome1.jpg");
		Image imHm5 = imageHm5.getImage(); // transform it 
		Image newimgHm5 = imHm5.getScaledInstance(55, 53,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageHm5 = new ImageIcon(newimgHm5);  // transform it back
		
		redHm[5] = new JLabel();
		redHm[5].setIcon(imageHm5);
		content.remove(98);
		content.add(redHm[5], 98);
		
		ImageIcon imageHm6 = new ImageIcon("Pictures/red/redHome2.jpg");
		Image imHm6 = imageHm6.getImage(); // transform it 
		Image newimgHm6 = imHm6.getScaledInstance(55, 53,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageHm6 = new ImageIcon(newimgHm6);  // transform it back
		
		JLabel hm6 = new JLabel();
		hm6.setIcon(imageHm6);
		content.remove(99);
		content.add(hm6, 99);
		
		ImageIcon imageHm7 = new ImageIcon("Pictures/red/redHome3.jpg");
		Image imHm7 = imageHm7.getImage(); // transform it 
		Image newimgHm7 = imHm7.getScaledInstance(55, 53,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageHm7 = new ImageIcon(newimgHm7);  // transform it back
		
		JLabel hm7 = new JLabel();
		hm7.setIcon(imageHm7);
		content.remove(114);
		content.add(hm7, 114);
		
		ImageIcon imageHm8 = new ImageIcon("Pictures/red/redHome4.jpg");
		Image imHm8 = imageHm8.getImage(); // transform it 
		Image newimgHm8 = imHm8.getScaledInstance(55, 53,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageHm8 = new ImageIcon(newimgHm8);  // transform it back
		
		JLabel hm8 = new JLabel();
		hm8.setIcon(imageHm8);
		content.remove(115);
		content.add(hm8, 115);
		
		//GREEN HOME
		
		ImageIcon imageHm9 = new ImageIcon("Pictures/green/greenHome1.jpg");
		Image imHm9 = imageHm9.getImage(); // transform it 
		Image newimgHm9 = imHm9.getScaledInstance(55, 53,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageHm9 = new ImageIcon(newimgHm9);  // transform it back
		
		grnHm[5] = new JLabel();
		grnHm[5].setIcon(imageHm9);
		content.remove(198);
		content.add(grnHm[5], 198);
		
		ImageIcon imageHm10 = new ImageIcon("Pictures/green/greenHome2.jpg");
		Image imHm10 = imageHm10.getImage(); // transform it 
		Image newimgHm10 = imHm10.getScaledInstance(55, 53,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageHm10 = new ImageIcon(newimgHm10);  // transform it back
		
		JLabel hm10 = new JLabel();
		hm10.setIcon(imageHm10);
		content.remove(199);
		content.add(hm10, 199);
		
		ImageIcon imageHm11 = new ImageIcon("Pictures/green/greenHome3.jpg");
		Image imHm11 = imageHm11.getImage(); // transform it 
		Image newimgHm11 = imHm11.getScaledInstance(55, 53,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageHm11 = new ImageIcon(newimgHm11);  // transform it back
		
		JLabel hm11 = new JLabel();
		hm11.setIcon(imageHm11);
		content.remove(214);
		content.add(hm11, 214);
		
		ImageIcon imageHm12 = new ImageIcon("Pictures/green/greenHome4.jpg");
		Image imHm12 = imageHm12.getImage(); // transform it 
		Image newimgHm12 = imHm12.getScaledInstance(55, 53,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageHm12 = new ImageIcon(newimgHm12);  // transform it back
		
		JLabel hm12 = new JLabel();
		hm12.setIcon(imageHm12);
		content.remove(215);
		content.add(hm12, 215);
		
		//YELLOW HOME
		
		ImageIcon imageHm13 = new ImageIcon("Pictures/yellow/yellowHome1.jpg");
		Image imHm13 = imageHm13.getImage(); // transform it 
		Image newimgHm13 = imHm13.getScaledInstance(55, 53,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageHm13 = new ImageIcon(newimgHm13);  // transform it back
		
		yellowHm[5] = new JLabel();
		yellowHm[5].setIcon(imageHm13);
		content.remove(140);
		content.add(yellowHm[5], 140);
		
		ImageIcon imageHm14 = new ImageIcon("Pictures/yellow/yellowHome2.jpg");
		Image imHm14 = imageHm14.getImage(); // transform it 
		Image newimgHm14 = imHm14.getScaledInstance(55, 53,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageHm14 = new ImageIcon(newimgHm14);  // transform it back
		
		JLabel hm14 = new JLabel();
		hm14.setIcon(imageHm14);
		content.remove(141);
		content.add(hm14, 141);
		
		ImageIcon imageHm15 = new ImageIcon("Pictures/yellow/yellowHome3.png");
		Image imHm15 = imageHm15.getImage(); // transform it 
		Image newimgHm15 = imHm15.getScaledInstance(55, 54,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageHm15 = new ImageIcon(newimgHm15);  // transform it back
		
		JLabel hm15 = new JLabel();
		hm15.setIcon(imageHm15);
		content.remove(156);
		content.add(hm15, 156);
		
		ImageIcon imageHm16 = new ImageIcon("Pictures/yellow/yellowHome4.jpg");
		Image imHm16 = imageHm16.getImage(); // transform it 
		Image newimgHm16 = imHm16.getScaledInstance(56, 53,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageHm16 = new ImageIcon(newimgHm16);  // transform it back
		
		JLabel hm16 = new JLabel();
		hm16.setIcon(imageHm16);
		content.remove(157);
		content.add(hm16, 157);
		
		//adjust layouts to hold multiple pictures
		for(int i=0;i<6;i++){
			redHm[i].setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
			grnHm[i].setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
			blueHm[i].setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
			yellowHm[i].setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		}
		
		for(int i=0;i<4;i++){
			redStart[i].setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
			grnStart[i].setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
			blueStart[i].setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
			yellowStart[i].setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		}
		
		//Add images to represent slides
		//GREEN ARROWS
		
		ImageIcon image = new ImageIcon("Pictures/green/greenBottom.jpg");
		Image im = image.getImage(); // transform it 
		Image newimg = im.getScaledInstance(33, 40,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		image = new ImageIcon(newimg);  // transform it back
		
		label[1].setHorizontalAlignment(SwingConstants.RIGHT);
		label[1].setIcon(image);
		label[1].repaint();
		
		ImageIcon image2 = new ImageIcon("Pictures/green/greeMid.jpg");
		Image im2 = image2.getImage(); // transform it 
		Image newimg2 = im2.getScaledInstance(33, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		image2 = new ImageIcon(newimg2);  // transform it back
	
		label[2].setHorizontalAlignment(SwingConstants.RIGHT);
		label[2].setIcon(image2);
		label[2].repaint();
		
		label[3].setHorizontalAlignment(SwingConstants.RIGHT);
		label[3].setIcon(image2);
		label[3].repaint();
		
		ImageIcon image3 = new ImageIcon("Pictures/green/greenHead.jpg");
		Image im3 = image3.getImage(); // transform it 
		Image newimg3 = im3.getScaledInstance(33, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		image3 = new ImageIcon(newimg3);  // transform it back
		label[4].setHorizontalAlignment(SwingConstants.RIGHT);
		label[4].setIcon(image3);
		label[4].repaint();
		
		label[9].setHorizontalAlignment(SwingConstants.RIGHT);
		label[9].setIcon(image);
		label[9].repaint();
		
		label[10].setHorizontalAlignment(SwingConstants.RIGHT);
		label[10].setIcon(image2);
		label[10].repaint();
		
		label[11].setHorizontalAlignment(SwingConstants.RIGHT);
		label[11].setIcon(image2);
		label[11].repaint();
		
		label[12].setHorizontalAlignment(SwingConstants.RIGHT);
		label[12].setIcon(image2);
		label[12].repaint();
		
		label[13].setHorizontalAlignment(SwingConstants.RIGHT);
		label[13].setIcon(image3);
		label[13].repaint();
		
		//RED ARROWS
		
		ImageIcon image4 = new ImageIcon("Pictures/red/redBottom.jpg");
		Image im4 = image4.getImage(); // transform it 
		Image newimg4 = im4.getScaledInstance(52, 22,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		image4 = new ImageIcon(newimg4);  // transform it back
		
		label[16].setVerticalAlignment(SwingConstants.BOTTOM);
		label[16].setIcon(image4);
		label[16].repaint();
		
		ImageIcon image5 = new ImageIcon("Pictures/red/redMid.jpg");
		Image im5 = image5.getImage(); // transform it 
		Image newimg5 = im5.getScaledInstance(52, 23,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		image5 = new ImageIcon(newimg5);  // transform it back
	
		label[17].setVerticalAlignment(SwingConstants.BOTTOM);
		label[17].setIcon(image5);
		label[17].repaint();
		
		label[18].setVerticalAlignment(SwingConstants.BOTTOM);
		label[18].setIcon(image5);
		label[18].repaint();
		
		ImageIcon image6 = new ImageIcon("Pictures/red/redHead.jpg");
		Image im6 = image6.getImage(); // transform it 
		Image newimg6 = im6.getScaledInstance(50, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		image6 = new ImageIcon(newimg6);  // transform it back
		label[19].setVerticalAlignment(SwingConstants.BOTTOM);
		label[19].setIcon(image6);
		label[19].repaint();
		
		label[24].setVerticalAlignment(SwingConstants.BOTTOM);
		label[24].setIcon(image4);
		label[24].repaint();
		
		label[25].setVerticalAlignment(SwingConstants.BOTTOM);
		label[25].setIcon(image5);
		label[25].repaint();
		
		label[26].setVerticalAlignment(SwingConstants.BOTTOM);
		label[26].setIcon(image5);
		label[26].repaint();
		
		label[27].setVerticalAlignment(SwingConstants.BOTTOM);
		label[27].setIcon(image5);
		label[27].repaint();
		
		label[28].setVerticalAlignment(SwingConstants.BOTTOM);
		label[28].setIcon(image6);
		label[28].repaint();
		
		//BLUE ARROWS
		
		ImageIcon image7 = new ImageIcon("Pictures/blue/blueBottom.jpg");
		Image im7 = image7.getImage(); // transform it 
		Image newimg7 = im7.getScaledInstance(26, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		image7 = new ImageIcon(newimg7);  // transform it back
		
		label[31].setHorizontalAlignment(SwingConstants.LEFT);
		label[31].setIcon(image7);
		label[31].repaint();
		
		ImageIcon image8 = new ImageIcon("Pictures/blue/blueMid.jpg");
		Image im8 = image8.getImage(); // transform it 
		Image newimg8 = im8.getScaledInstance(26, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		image8 = new ImageIcon(newimg8);  // transform it back
	
		label[32].setHorizontalAlignment(SwingConstants.LEFT);
		label[32].setIcon(image8);
		label[32].repaint();
		
		label[33].setHorizontalAlignment(SwingConstants.LEFT);
		label[33].setIcon(image8);
		label[33].repaint();
		
		ImageIcon image9 = new ImageIcon("Pictures/blue/blueHead.jpg");
		Image im9 = image9.getImage(); // transform it 
		Image newimg9 = im9.getScaledInstance(32, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		image9 = new ImageIcon(newimg9);  // transform it back
		label[34].setHorizontalAlignment(SwingConstants.LEFT);
		label[34].setIcon(image9);
		label[34].repaint();
		
		label[39].setHorizontalAlignment(SwingConstants.LEFT);
		label[39].setIcon(image7);
		label[39].repaint();
		
		label[40].setHorizontalAlignment(SwingConstants.LEFT);
		label[40].setIcon(image8);
		label[40].repaint();
		
		label[41].setHorizontalAlignment(SwingConstants.LEFT);
		label[41].setIcon(image8);
		label[41].repaint();
		
		label[42].setHorizontalAlignment(SwingConstants.LEFT);
		label[42].setIcon(image8);
		label[42].repaint();
		
		label[43].setHorizontalAlignment(SwingConstants.LEFT);
		label[43].setIcon(image9);
		label[43].repaint();
		
		
		//YELLOW ARROWS
		
		ImageIcon image10 = new ImageIcon("Pictures/yellow/yellowBottom.jpg");
		Image im10 = image10.getImage(); // transform it 
		Image newimg10 = im10.getScaledInstance(50, 25,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		image10 = new ImageIcon(newimg10);  // transform it back
		
		label[46].setVerticalAlignment(SwingConstants.TOP);
		label[46].setIcon(image10);
		label[46].repaint();
		
		ImageIcon image11 = new ImageIcon("Pictures/yellow/yellowMid.jpg");
		Image im11 = image11.getImage(); // transform it 
		Image newimg11 = im11.getScaledInstance(50, 25,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		image11 = new ImageIcon(newimg11);  // transform it back
	
		label[47].setVerticalAlignment(SwingConstants.TOP);
		label[47].setIcon(image11);
		label[47].repaint();
		
		label[48].setVerticalAlignment(SwingConstants.TOP);
		label[48].setIcon(image11);
		label[48].repaint();
	
		ImageIcon image12 = new ImageIcon("Pictures/yellow/yellowHead.jpg");
		Image im12 = image12.getImage(); // transform it 
		Image newimg12 = im12.getScaledInstance(50, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		image12 = new ImageIcon(newimg12);  // transform it back
		label[49].setVerticalAlignment(SwingConstants.TOP);
		label[49].setIcon(image12);
		label[49].repaint();
		
		label[54].setVerticalAlignment(SwingConstants.TOP);
		label[54].setIcon(image11);
		label[54].repaint();
		
		label[55].setVerticalAlignment(SwingConstants.TOP);
		label[55].setIcon(image11);
		label[55].repaint();
		
		label[56].setVerticalAlignment(SwingConstants.TOP);
		label[56].setIcon(image11);
		label[56].repaint();
		
		label[57].setVerticalAlignment(SwingConstants.TOP);
		label[57].setIcon(image11);
		label[57].repaint();
		
		label[58].setVerticalAlignment(SwingConstants.TOP);
		label[58].setIcon(image12);
		label[58].repaint();
		
		
		//Set up Window
		window = new JFrame("Sorry! Board");
		window.setAlwaysOnTop(true);
		window.setContentPane(content);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.toBack();
		
	
		
		window.setSize(900, 900);
		window.setLocation(100, 100);
	}
	
	/*
	 * show board
	 */
	public void makeVisible(){
		window.setVisible(true);
	}
	
	/*
	 *check if the specified location on the board
	 *is occupied 
	 */
	public boolean isOccupiedMain(int loc){
		return mainBoard[loc].isOccupied();
	}
	/*
	 *check if the specified location on Home path of given color
	 *is occupied 
	 */
	public boolean isOccupiedHome(int loc, String color){
		
		boolean result = false;
		if(color.equals("Green"))
			result = homeGreen[loc].isOccupied();
		if(color.equals("Red"))
			result = homeRed[loc].isOccupied();
		if(color.equals("Yellow"))
			result = homeYellow[loc].isOccupied();
		if(color.equals("Blue"))
			result = homeBlue[loc].isOccupied();
		
		return result;
	}
	/*
	 * checks if a space is already occupied by a pawn of the same player
	 */
	public boolean isValidMoveMain(int loc, String color){
		boolean temp = true;
		if(isOccupiedMain(loc)==true && mainBoard[loc].getPawn().getColor().equals(color)){
			temp =false;
		}
		return temp;
	}
	/*
	 * checks if a space is already occupied by a pawn
	 * or is beyond the scope of the Home path
	 */
	public boolean isValidMoveHome(int loc, String color){
		boolean temp = true;
		if(loc >5){
			temp = false;
		}
		else if(isOccupiedHome(loc, color)==true){
			temp =false;
		}
		return temp;
	}
	/*
	 * returns int of Home space correspong to color of the player
	 */
	public int getHome(String color){
		int temp =0;
		if(color.equals("Green"))
			temp = markHomeGreen;
		if(color.equals("Red"))
			temp = markHomeRed;
		if(color.equals("Yellow"))
			temp = markHomeYellow;
		if(color.equals("Blue"))
			temp = markHomeBlue;
		
		return temp;
		
	}
	
	
	/*
	 * returns int of Start space correspong to color of the player
	 */
	public int getStartSpace(String color){
		int temp =0;
		if(color.equals("Green"))
			temp = markStartGreen;
		if(color.equals("Red"))
			temp = markStartRed;
		if(color.equals("Yellow"))
			temp = markStartYellow;
		if(color.equals("Blue"))
			temp = markStartBlue;
		
		return temp;
		
	}
	
	

	
	/*
	 * method to fill start squares with pawns
	 * @param Pawn one,two,three,four
	 */
	public void fillRed(Pawn one, Pawn two, Pawn three, Pawn four){
		
		startRed[0].arrival(one);
		startRed[1].arrival(two);
		startRed[2].arrival(three);
		startRed[3].arrival(four);
		
	}
	
	/*
	 * method to remove pawn from start square
	 */
	public void remRed(int num){
		
		startRed[num].leave();
	}
	
	/*
	 * method to fill start squares with pawns
	 * @param Pawn one,two,three,four
	 */
	public void fillBlue(Pawn one, Pawn two, Pawn three, Pawn four){
		
		startBlue[0].arrival(one);
		startBlue[1].arrival(two);
		startBlue[2].arrival(three);
		startBlue[3].arrival(four);
		
	}
	
	/*
	 * method to remove pawn from start square
	 */
	public void remBlue(int num){
		
		startBlue[num].leave();
	}
	
	/*
	 * method to fill start squares with pawns
	 * @param Pawn one,two,three,four
	 */
	public void fillGreen(Pawn one, Pawn two, Pawn three, Pawn four){
		
		startGreen[0].arrival(one);
		startGreen[1].arrival(two);
		startGreen[2].arrival(three);
		startGreen[3].arrival(four);
		
	}
	
	/*
	 * method to remove pawn from start square
	 */
	public void remGreen(int num){
		
		startGreen[num].leave();
	}
	
	/*
	 * method to fill start squares with pawns
	 * @param Pawn one,two,three,four
	 */
	public void fillYellow(Pawn one, Pawn two, Pawn three, Pawn four){
		
		startYellow[0].arrival(one);
		startYellow[1].arrival(two);
		startYellow[2].arrival(three);
		startYellow[3].arrival(four);
		
	}
	
	/*
	 * method to remove pawn from start square
	 */
	public void remYellow(int num){
		
		startYellow[num].leave();;
	}
	

}
