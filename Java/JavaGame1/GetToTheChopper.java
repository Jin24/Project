import java.applet.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.Font;
import java.lang.Object;
import java.util.Random;



public class GetToTheChopper extends Applet implements Runnable, MouseListener, MouseMotionListener, KeyListener, ActionListener
{

    // GLOBAL VARIABLES REQUIRED
    int width, height;
    Thread t = null;
    boolean threadSuspended;
    Image backbuffer;
    Graphics backg;
    boolean mouseEntered;


    // GLOBAL VARIABLES PROGRAM SPECIFIC
    BufferedImage backgroundImg = null;
    BufferedImage arrowsImg1 = null;
    Image helicopter_Titlescreen;
    BufferedImage helicopter_Titlescreen2 = null;
    BufferedImage loadingImg = null;
    BufferedImage appleImg = null;
    BufferedImage pearImg = null;
    BufferedImage bananaImg = null;
    BufferedImage watermelonImg = null;
    BufferedImage basketImg = null;
    Image mushroomImg;
    Image endingImg;
    Image arrowsPlayImg;
    Image endingImg2;
    Image queenImg;
    Image lostImg;
    int timer = 500;
    int levels = 1;
    int doorSpeed = 10;
    Font fontTitle = new Font ("ALGERIAN", 1, 55);
    Font fontLevels = new Font ("ARIAL", 1, 40);
    Font fontInstructions = new Font ("SANS SARIF", 1, 30);
    Font fontLoading = new Font ("Bohema", 1, 40);
    Font fontGame = new Font ("Comic Sans MS", 1, 30);
    Font fontTimer = new Font ("Times New Roman", 1, 15);
    int xHelicopter = 4;
    int helicopterSpeed = 2;
    int xMinigame1;
    int yMinigame1;
    int levelSelector = 1;
    int count2 = 0;
    private int speedX = 0;
    private int speedY = 0;
    String showTimer;
    String levelNumber = "";
    int runOnce = 0;
    int timesRan = 0;
    int dodgeBlocksSpeed = 1;
    int dodgeBlocksSpeed2 = 2;
    int timesRan1 = 0;
    int timesRan2 = 0;
    boolean end;
    boolean lost;
    AudioClip audioClip;
    int game1timer;
    int lives = 3;
    int clearLeftDoor = 400;
    int clearLeftDoor1 = 2;
    int clearRightDoor = 4;
    String showTimerGame1;
    int chooseGame;
    String s = "";
    String wordEntered = "";
    String backspace = "";
    int xMinigame2, yMinigame2;
    boolean backspacePressed;
    boolean enterPressed;



    //BUTTONS
    Button startButton;
    Button instructionsButton;
    Button quitButton;
    Button backButton;
    Button musicOnButton;
    Button musicOffButton;
    boolean startPressed;
    boolean instructionsPressed;
    boolean quitButtonPressed;
    boolean backButtonPressed;



    /**
     * This function will initialize all your important variables.
     * Load your images in here.
     * Set up your Backbuffer
     * Add your event listeners
     */
    public void init ()
    {


	setLayout (null);
	// Set required globals
	width = getSize ().width;
	height = getSize ().height;
	setBackground (Color.white);

	// Initialize your program specific variables and load images

	try
	{
	    backgroundImg = ImageIO.read (new File ("Images/background_MainMenu.jpg"));
	    arrowsImg1 = ImageIO.read (new File ("Images/arrows.png"));
	    helicopter_Titlescreen = getImage (getCodeBase (), "Images/helicopter.GIF");
	    loadingImg = ImageIO.read (new File ("Images/loadingPic.jpg"));
	    endingImg = getImage (getCodeBase (), "Images/walking.gif");
	    arrowsPlayImg = getImage (getCodeBase (), "Images/arrows2.gif");
	    endingImg2 = getImage (getCodeBase (), "Images/endingImg.gif");
	    //IMPORTING MUSIC
	    audioClip = getAudioClip (getCodeBase (), "Music/Music.wav");

	    //Fruits for Minigame 1
	    appleImg = ImageIO.read (new File ("Images/apple.jpg"));
	    pearImg = ImageIO.read (new File ("Images/pear.jpg"));
	    watermelonImg = ImageIO.read (new File ("Images/watermelon.png"));
	    bananaImg = ImageIO.read (new File ("Images/banana.jpg"));
	    basketImg = ImageIO.read (new File ("Images/basket.jpg"));
	    mushroomImg = getImage (getCodeBase (), "Images/mushroom.gif");
	    queenImg = getImage (getCodeBase (), "Images/queen.gif");
	    lostImg = getImage (getCodeBase (), "Images/lost.gif");
	}
	catch (IOException e)
	{
	}





	startPressed = false;
	instructionsPressed = false;
	quitButtonPressed = false;
	backButtonPressed = false;
	end = false;
	lost = false;

	// Set up your backbuffer
	backbuffer = createImage (width, height);
	backg = backbuffer.getGraphics ();
	backg.setColor (Color.white);

	// Add event listeners
	addMouseListener (this);
	addKeyListener (this);
	addMouseMotionListener (this);

	//BUTTONS
	startButton = new Button ("Play");
	instructionsButton = new Button ("Instructions");
	quitButton = new Button ("Quit");
	backButton = new Button ("Back");
	musicOnButton = new Button ("MUSIC ON");
	musicOffButton = new Button ("MUSIC OFF");




	startButton.setBounds (300, 200, 200, 50);
	instructionsButton.setBounds (300, 300, 200, 50);
	quitButton.setBounds (300, 400, 200, 50);
	backButton.setBounds (20, 20, 200, 50);
	musicOnButton.setBounds (20, 510, 70, 20);
	musicOffButton.setBounds (20, 550, 70, 20);


	startButton.addActionListener (this);
	instructionsButton.addActionListener (this);
	quitButton.addActionListener (this);
	backButton.addActionListener (this);
	musicOnButton.addActionListener (this);
	musicOffButton.addActionListener (this);
	add (startButton);
	add (instructionsButton);
	add (quitButton);
	add (musicOnButton);
	add (musicOffButton);



    }


    /**
     * This function will handle all the game logic
     * and updates important variables.
     */
    public void run ()
    {
	try
	{

	    while (true)
	    {

		// Here's where the thread does some work.
		// TRY to Separate the updating of variables
		// from the drawing of the objects.

		// -------UPDATE VARIABLES----



		// -------DRAWING--------------
		// We always draw to 'backg'.
		// This is our hidden screen that gets
		// pasted onto the main display.

		//minigame2 ();

		if (true)
		{

		    backg.setColor (Color.white);
		    backg.fillRect (0, 0, 800, 600); // clear the screen


		    //MAIN MENU

		    backg.drawImage (backgroundImg, 0, 0, null);
		    backg.drawImage (helicopter_Titlescreen, xHelicopter, 460, null);
		    backg.setColor (Color.CYAN); //Sets the color of the font
		    backg.setFont (fontTitle); //Sets the font
		    backg.drawString ("GET TO THE CHOPPER !", 100, 100); //Outputs the string


		    //HELICOPTER FLYING AROUND


		}

		xHelicopter += helicopterSpeed;

		if (xHelicopter >= 800)
		{
		    xHelicopter = -400;
		}




		//START BUTTON
		if (startPressed)
		{

		    //TESTING THE ENDING
		    //ending ();


		    backg.setColor (Color.DARK_GRAY);
		    backg.fillRect (0, 0, 800, 600);
		    if (runOnce < 1)
		    {

			runOnce++;
			loading ();
		    }


		    if (levels < 10)
		    {
			elevatorOpen ();
			elevatorClose ();
			count2 = 0;
			levels++;
			timer = timer - (15 * timesRan);
		    }


		    else if (levels == 10)
		    {
			ending ();
			startPressed = false;

		    }

		}


		//INSTRUCTIONS BUTTON
		if (instructionsPressed)
		{

		    backg.drawImage (backgroundImg, 0, 0, null);
		    backg.drawImage (arrowsImg1, 590, 450, null);
		    backg.setFont (fontInstructions);
		    backg.setColor (Color.BLACK);
		    backg.drawString ("Objective of the Game: Complete all the levels in ", 50, 150);
		    backg.drawString ("the allotted time in order to reach the top of the ", 50, 200);
		    backg.drawString ("building and escape. ", 50, 250);
		    backg.drawString ("Controls: A mouse and a keyboard with arrow keys.", 40, 350);
		    backg.drawString ("Follow specific instructions for each level on ", 40, 400);
		    backg.drawString ("the screen.", 40, 450);
		    backg.drawString ("Good Luck! ... you're gonna need it.", 100, 500);




		}

		//QUIT BUTTON
		if (quitButtonPressed)
		{
		    //QUIT
		    System.exit (0);

		}

		//BACK BUTTON
		if (backButtonPressed)
		{
		    remove (backButton);
		    backg.drawImage (backgroundImg, 0, 0, null);
		    backg.setColor (Color.CYAN);
		    backg.setFont (fontTitle);
		    backg.drawString ("GET TO THE CHOPPER !", 100, 100);
		    backButtonPressed = false;
		    startPressed = false;
		    instructionsPressed = false;
		}


		/**
		 * DONT TOUCH THE REST OF THIS METHOD
		 */
		// Now the thread checks to see if it should suspend itself
		if (threadSuspended)
		{
		    synchronized (this)
		    {
			while (threadSuspended)
			{
			    wait ();
			}
		    }
		}
		repaint ();
		t.sleep (17);   // interval given in milliseconds



	    }


	}



	catch (InterruptedException e)
	{
	}

    }


    public void loading ()
    {
	try
	{
	    int countLoading = 0;
	    int countLoading2 = 0;

	    while (true)
	    {
		//"LOADING..."
		backg.setColor (Color.WHITE);
		backg.setFont (fontLoading);
		backg.drawString ("LOADING...", 300, 200);
		backg.drawImage (loadingImg, 480, 280, null);



		//LOADING SYMBOL
		backg.setColor (Color.RED);

		if (countLoading == 20)
		{
		    backg.fillArc (350, 250, 100, 100, 90, 90);
		}

		else if (countLoading == 40)
		{
		    backg.fillArc (350, 250, 100, 100, 180, 90);
		}

		else if (countLoading == 60)
		{
		    backg.fillArc (350, 250, 100, 100, 270, 90);

		}

		else if (countLoading == 80)
		{
		    backg.fillArc (350, 250, 100, 100, 0, 90);

		}

		else if (countLoading == 100)
		{
		    backg.setColor (Color.DARK_GRAY);
		    backg.fillRect (0, 0, 800, 600);
		    countLoading = 0;
		    countLoading2 += 1;
		}

		countLoading++;

		if (countLoading2 >= 1)
		{
		    break;
		}





		//DO NOT TOUCH
		if (threadSuspended)
		{
		    synchronized (this)
		    {
			while (threadSuspended)
			{
			    wait ();
			}
		    }
		}
		repaint ();
		t.sleep (17);   // interval given in milliseconds
	    }
	}


	catch (InterruptedException e)
	{
	}
    }


    public void elevatorOpen ()
    {

	try
	{

	    //CLEARING OPENING DOORS SCREEN
	    backg.setColor (Color.DARK_GRAY);
	    backg.fillRect (0, 0, 800, 600);
	    //Variables to slide the elevator doors
	    int leftElevatorx_2 = 0;
	    int rightElevatorx_2 = 595;
	    int rightElevatorWidth_2 = 10;

	    //Randomly calls minigames
	    while (true)
	    {
		int randomNumber = (int) (Math.random () * 3);
		if (randomNumber != chooseGame)
		{
		    chooseGame = randomNumber;
		    break;
		}
	    }

	    while (true)
	    {

		backg.setColor (Color.GRAY);
		//Left Elevator Door
		backg.fillRect (200, 70, 200, 500);

		//Right Elevator Door
		backg.fillRect (400, 70, 205, 500);


		//Clearing trails of doors
		backg.setColor (Color.DARK_GRAY);
		backg.fillRect (clearLeftDoor, 71, clearLeftDoor1, 499);
		backg.fillRect (400, 71, clearRightDoor, 499);

		//Elevator Frame
		backg.setColor (Color.LIGHT_GRAY);
		backg.fillRect (150, 25, 225, 40);
		backg.fillRect (430, 25, 225, 40);
		backg.fillRect (150, 21, 505, 4);
		backg.fillRect (150, 65, 505, 5);
		backg.fillRect (150, 70, 50, 500);
		backg.fillRect (605, 70, 50, 500);
		backg.setColor (Color.BLACK);
		backg.drawRect (150, 20, 505, 50);
		backg.drawRect (150, 71, 50, 500);
		backg.drawRect (605, 71, 50, 500);
		backg.drawRect (150, 570, 505, 1);

		//LEVEL TRACKER
		backg.setColor (Color.BLACK);
		backg.fillRect (375, 25, 55, 40);
		backg.setColor (Color.YELLOW);
		backg.setFont (fontLevels);
		levelNumber = "" + levels;
		backg.drawString (levelNumber, 380, 60);

		//Clear Trail animation
		if (clearLeftDoor1 < 210)
		{
		    //Animations of Doors Opening
		    clearLeftDoor -= doorSpeed;
		    clearLeftDoor1 += doorSpeed;
		    clearRightDoor += doorSpeed;
		}

		else if (clearLeftDoor1 >= 210)
		{
		    //minigame3 ();

		    if (chooseGame == 0)
		    {
			minigame1 ();
		    }

		    else if (chooseGame == 1)
		    {
			minigame2 ();
		    }

		    else if (chooseGame == 2)
		    {
			minigame3 ();
		    }

		    /*else if (chooseGame == 3)
		    {
			minigame3 ();
		    }
		    */
		    break;

		}


		//}

		/**
			 * DONT TOUCH THE REST OF THIS METHOD
			 */
		// Now the thread checks to see if it should suspend itself
		if (threadSuspended)
		{
		    synchronized (this)
		    {
			while (threadSuspended)
			{
			    wait ();
			}
		    }
		}
		repaint ();
		t.sleep (17);   // interval given in milliseconds
	    }
	}


	catch (InterruptedException e)
	{
	}
    }





    public void elevatorClose ()
    {

	try
	{
	    //Variables to slide the elevator doors
	    int clearLeftDoor = 400;
	    int clearLeftDoor1 = 2;
	    int clearRightDoor = 4;
	    //CLEARING OPENING DOORS SCREEN
	    backg.setColor (Color.DARK_GRAY);
	    backg.fillRect (0, 0, 800, 600);
	    //Variables to slide the elevator doors
	    int leftElevatorx_2 = 0;
	    int rightElevatorx_2 = 595;
	    int rightElevatorWidth_2 = 10;

	    while (true)
	    {
		// CLOSING DOORS



		backg.setColor (Color.GRAY);
		//Left Elevator Door
		backg.fillRect (200, 70, leftElevatorx_2, 500);

		//Right Elevator Door
		backg.fillRect (rightElevatorx_2, 70, rightElevatorWidth_2, 500);

		//Elevator Frame
		backg.setColor (Color.LIGHT_GRAY);
		backg.fillRect (150, 25, 225, 40);
		backg.fillRect (430, 25, 225, 40);
		backg.fillRect (150, 21, 505, 4);
		backg.fillRect (150, 65, 505, 5);
		backg.fillRect (150, 70, 50, 500);
		backg.fillRect (605, 70, 50, 500);
		backg.setColor (Color.BLACK);
		backg.drawRect (150, 20, 505, 50);
		backg.drawRect (150, 71, 50, 500);
		backg.drawRect (605, 71, 50, 500);
		backg.drawRect (150, 570, 505, 1);


		//Clear Trail animation
		if (count2 < 200)
		{
		    //Animations of Doors
		    leftElevatorx_2 += doorSpeed;
		    rightElevatorx_2 -= doorSpeed;
		    rightElevatorWidth_2 += doorSpeed;
		    count2 += doorSpeed;

		}

		else if (count2 >= 200)
		{
		    count2 = 0;
		    clearLeftDoor = 400;
		    clearLeftDoor1 = 2;
		    clearRightDoor = 4;
		    break;
		}


		//Level Tracker
		backg.setColor (Color.BLACK);
		backg.fillRect (375, 25, 55, 40);
		backg.setColor (Color.YELLOW);
		backg.setFont (fontLevels);
		levelNumber = "" + levels;
		backg.drawString (levelNumber, 380, 60);



		//}

		/**
			 * DONT TOUCH THE REST OF THIS METHOD
			 */
		// Now the thread checks to see if it should suspend itself
		if (threadSuspended)
		{
		    synchronized (this)
		    {
			while (threadSuspended)
			{
			    wait ();
			}
		    }
		}
		repaint ();
		t.sleep (17);   // interval given in milliseconds
	    }
	}


	catch (InterruptedException e)
	{
	}
    }



    //MINIGAME 1
    public void minigame1 ()
    {
	try
	{
	    int dodgeBlocks_1 = 110;
	    int dodgeBlocks_2 = 30;
	    int dodgeBlocks_3 = 120;
	    int dodgeBlocks_4 = 180;
	    int inBasket = 0;
	    int disappearX_1 = 250;
	    int disappearX_2 = 500;
	    int disappearX_3 = 350;
	    int disappearX_4 = 450;
	    xMinigame1 = 375;
	    yMinigame1 = 450;
	    String livesLeft;
	    game1timer = timer;


	    while (true)
	    {

		backg.setColor (Color.DARK_GRAY);
		backg.fillRect (202, 72, 404, 498);
		backg.setFont (fontGame);
		backg.fillRect (350, 120, 120, 32);
		backg.setColor (Color.WHITE);
		backg.drawString ("CATCH!", 350, 150);

		//Dimensions 70 X 50 Pixels
		backg.drawImage (arrowsPlayImg, 325, 160, null);


		//Fruits 50 X 50 Pixels

		//CHECKING IF THE BASKET CAPTURED THE FRUITS

		if (true)
		{

		    backg.drawImage (appleImg, disappearX_1, dodgeBlocks_1, null);
		    dodgeBlocks_1 += dodgeBlocksSpeed;
		    if ((xMinigame1 + 70) >= disappearX_1 && xMinigame1 < (disappearX_1 + 50) && yMinigame1 < (dodgeBlocks_1 + 50))
		    {
			disappearX_1 = 90000;
			inBasket++;
		    }
		    else if (dodgeBlocks_1 >= 520)
		    {
			disappearX_1 = 90000;
			inBasket = 0;
			timesRan++;
			timesRan1++;
			clearLeftDoor = 400;
			clearLeftDoor1 = 2;
			clearRightDoor = 4;
			lives--;
			xMinigame1 = 375;
			yMinigame1 = 450;
			speedX = 0;
			speedY = 0;
			if (lives <= 0)
			{
			    lostGame ();

			}
			break;
		    }
		}


		if (true)
		{

		    backg.drawImage (pearImg, disappearX_2, dodgeBlocks_2, null);
		    dodgeBlocks_2 += dodgeBlocksSpeed;
		    backg.setColor (Color.LIGHT_GRAY);
		    backg.fillRect (450, 22, 200, 48);
		    backg.setColor (Color.BLACK);
		    backg.fillRect (450, 70, 200, 1);
		    if ((xMinigame1 + 70) >= disappearX_2 && xMinigame1 < (disappearX_2 + 50) && yMinigame1 < (dodgeBlocks_2 + 50))
		    {
			disappearX_2 = 90000;
			inBasket++;
		    }
		    else if (dodgeBlocks_2 >= 520)
		    {
			disappearX_2 = 90000;
			inBasket = 0;
			timesRan++;
			timesRan1++;
			clearLeftDoor = 400;
			clearLeftDoor1 = 2;
			clearRightDoor = 4;
			lives--;
			xMinigame1 = 375;
			yMinigame1 = 450;
			speedX = 0;
			speedY = 0;
			if (lives <= 0)
			{
			    lostGame ();

			}
			break;
		    }
		}

		if (true)
		{

		    backg.drawImage (watermelonImg, disappearX_3, dodgeBlocks_3, null);
		    dodgeBlocks_3 += dodgeBlocksSpeed2;
		    if ((xMinigame1 + 70) >= disappearX_3 && xMinigame1 < (disappearX_3 + 50) && yMinigame1 < (dodgeBlocks_3 + 50))
		    {
			disappearX_3 = 90000;
			inBasket++;
		    }
		    else if (dodgeBlocks_3 >= 520)
		    {
			disappearX_3 = 90000;
			inBasket = 0;
			timesRan++;
			timesRan1++;
			clearLeftDoor = 400;
			clearLeftDoor1 = 2;
			clearRightDoor = 4;
			lives--;
			xMinigame1 = 375;
			yMinigame1 = 450;
			speedX = 0;
			speedY = 0;
			if (lives <= 0)
			{
			    lostGame ();

			}
			break;
		    }
		}


		if (true)
		{

		    backg.drawImage (bananaImg, disappearX_4, dodgeBlocks_4, null);
		    dodgeBlocks_4 += dodgeBlocksSpeed2;
		    if ((xMinigame1 + 70) >= disappearX_4 && xMinigame1 <= (disappearX_4 + 50) && yMinigame1 < (dodgeBlocks_4 + 50))
		    {
			disappearX_4 = 90000;
			inBasket++;

		    }
		    else if (dodgeBlocks_4 >= 520)
		    {
			disappearX_4 = 90000;
			inBasket = 0;
			timesRan++;
			timesRan1++;
			clearLeftDoor = 400;
			clearLeftDoor1 = 2;
			clearRightDoor = 4;
			lives--;
			xMinigame1 = 375;
			yMinigame1 = 450;
			speedX = 0;
			speedY = 0;
			if (lives <= 0)
			{
			    lostGame ();

			}
			break;
		    }
		}


		//MOVING CHARACTER
		backg.drawImage (basketImg, xMinigame1, yMinigame1, null);


		if (inBasket >= 4)
		{
		    inBasket = 0;
		    timesRan++;
		    timesRan1++;
		    clearLeftDoor = 400;
		    clearLeftDoor1 = 2;
		    clearRightDoor = 4;
		    xMinigame1 = 375;
		    yMinigame1 = 450;
		    speedX = 0;
		    speedY = 0;
		    if (timesRan % 2 == 0)
		    {
			chooseGame = 0;
		    }

		    else if (timesRan % 2 != 0)
		    {
			chooseGame = 1;
		    }
		    break;
		}

		if (game1timer <= 0)
		{
		    inBasket = 0;
		    timesRan++;
		    timesRan1++;
		    clearLeftDoor = 400;
		    clearLeftDoor1 = 2;
		    clearRightDoor = 4;
		    lives--;
		    xMinigame1 = 375;
		    yMinigame1 = 450;
		    speedX = 0;
		    speedY = 0;
		    if (lives <= 0)
		    {
			lostGame ();

		    }
		    break;
		}


		//SHOW TIMER
		game1timer--;
		showTimerGame1 = "" + game1timer;
		backg.setColor (Color.DARK_GRAY);
		backg.fillRect (700, 480, 30, 20);
		backg.setColor (Color.WHITE);
		backg.setFont (fontTimer);
		backg.drawString ("TIMER:", 680, 470);

		if (game1timer <= 150 && game1timer >= 51)
		{
		    backg.setColor (Color.YELLOW);
		}
		if (game1timer <= 50)
		{
		    backg.setColor (Color.RED);
		}
		backg.drawString (showTimerGame1, 700, 500);



		//This shows how many lives they have left
		backg.setColor (Color.WHITE);
		backg.setFont (fontTimer);
		livesLeft = "" + lives;
		backg.drawString ("Lives", 550, 90);
		backg.setFont (fontTitle);
		backg.drawString (livesLeft, 550, 140);




		/**
			 * DONT TOUCH THE REST OF THIS METHOD
			 */
		// Now the thread checks to see if it should suspend itself
		if (threadSuspended)
		{
		    synchronized (this)
		    {
			while (threadSuspended)
			{
			    wait ();
			}
		    }
		}
		repaint ();
		t.sleep (10);   // interval given in milliseconds
	    }
	}


	catch (InterruptedException e)
	{
	}
    }



    public void minigame2 ()
    {
	try
	{
	    String[] colours = new String [9];
	    colours [0] = "Red";
	    colours [1] = "Blue";
	    colours [2] = "Yellow";
	    colours [3] = "Purple";
	    colours [4] = "Green";
	    colours [5] = "Orange";
	    colours [6] = "White";
	    colours [7] = "Black";
	    colours [8] = "Grey";
	    int r = (int) (Math.random () * 255) + 0;
	    int g = (int) (Math.random () * 255) + 0;
	    int b = (int) (Math.random () * 255) + 0;
	    int x = 0;
	    String livesLeft;
	    String showTimerGame1;
	    game1timer = timer;
	    int select;
	    select = (int) (Math.random () * 8) + 0;
	    String color = colours [select];
	    backspacePressed = false;
	    enterPressed = false;
	    wordEntered = "";


	    while (true)
	    {
		backg.setColor (Color.DARK_GRAY);
		backg.fillRect (202, 72, 402, 499);
		backg.setColor (Color.WHITE);
		backg.setFont (fontInstructions);
		backg.drawString ("TYPE! :", 250, 120);
		backg.setColor (new Color (r, g, b));
		backg.clearRect (380, 90, 150, 35);
		backg.drawString (color, 400, 120);


		//Shows what the player is typing
		backg.setFont (fontInstructions);
		backg.setColor (Color.WHITE);
		backg.fillRect (300, 400, 250, 40);
		backg.setColor (Color.BLACK);

		//if the player presses backspace
		if (backspacePressed)
		{
		    backg.clearRect (300, 400, 250, 40);

		}

		backg.drawString (wordEntered, 320, 430);


		//Word Typed is Correct
		if (enterPressed)
		{
		    if (wordEntered.equals (color))
		    {
			timesRan++;
			timesRan2++;
			clearLeftDoor = 400;
			clearLeftDoor1 = 2;
			clearRightDoor = 4;
			if (timesRan % 2 == 0)
			{
			    chooseGame = 0;

			}

			else if (timesRan % 2 != 0)
			{
			    chooseGame = 1;

			}
			wordEntered = "";
			backspace = "";
			enterPressed = false;
			break;
		    }

		    else
		    {
			wordEntered = "";
			enterPressed = false;
		    }
		}

		//Timer Runs Out

		if (game1timer <= 0)
		{
		    timesRan++;
		    timesRan2++;
		    clearLeftDoor = 400;
		    clearLeftDoor1 = 2;
		    clearRightDoor = 4;
		    lives--;
		    if (lives <= 0)
		    {
			lostGame ();
		    }
		    if (timesRan % 2 == 0)
		    {
			chooseGame = 0;
		    }

		    else if (timesRan % 2 != 0)
		    {
			chooseGame = 1;
		    }
		    wordEntered = "";
		    backspace = "";
		    break;
		}

		//SHOW TIMER
		game1timer--;
		showTimerGame1 = "" + game1timer;
		backg.setColor (Color.DARK_GRAY);
		backg.fillRect (700, 480, 30, 20);
		backg.setColor (Color.WHITE);
		backg.setFont (fontTimer);
		backg.drawString ("TIMER:", 680, 470);
		if (game1timer <= 150 && game1timer >= 51)
		{
		    backg.setColor (Color.YELLOW);
		}
		if (game1timer <= 50)
		{
		    backg.setColor (Color.RED);
		}
		backg.drawString (showTimerGame1, 700, 500);


		//This shows how many lives they have left
		backg.setColor (Color.WHITE);
		backg.setFont (fontTimer);
		livesLeft = "" + lives;
		backg.drawString ("Lives", 550, 90);
		backg.setFont (fontTitle);
		backg.drawString (livesLeft, 550, 140);


		/**

			 * DONT TOUCH THE REST OF THIS METHOD
			 */
		// Now the thread checks to see if it should suspend itself
		if (threadSuspended)
		{
		    synchronized (this)
		    {
			while (threadSuspended)
			{
			    wait ();
			}
		    }
		}
		repaint ();
		t.sleep (10);   // interval given in milliseconds
	    }


	    wordEntered = "";
	}

	catch (InterruptedException e)
	{
	}
    }



    public void minigame3 ()
    {
	try
	{

	    String livesLeft;
	    String showTimerGame1;
	    game1timer = timer;
	    int select;
	    xMinigame2 = 400;
	    yMinigame2 = 500;


	    while (true)
	    {

		backg.setColor (Color.YELLOW);
		backg.fillRect (202, 72, 402, 499);
		//Creating the Maze
		backg.setColor (Color.BLACK);
		//top
		backg.fillRect (202, 72, 402, 40);

		//small left side
		backg.fillRect (202, 112, 50, 459);

		//big left side
		backg.fillRect (252, 350, 120, 221);

		////////////////////////////////////

		//big right side
		backg.fillRect (475, 420, 130, 151);

		//small right side
		backg.fillRect (550, 112, 54, 459);

		//middle block
		backg.fillRect (340, 200, 120, 150);

		backg.setColor (Color.RED);
		backg.setFont (fontInstructions);
		backg.drawString ("FIND THE QUEEN!", 250, 100);

		backg.drawImage (mushroomImg, xMinigame2, yMinigame2, null);


		//Checking if the player hit the sides
		if (xMinigame2 < 604 && yMinigame2 < 100 || xMinigame2 < 238 && yMinigame2 + 50 > 112 && yMinigame2 < 571 || xMinigame2 < (252 + 120) && yMinigame2 < (350 + 207) && (yMinigame2 + 50) > 350
			|| xMinigame2 + 50 > 490 && yMinigame2 > 380 && yMinigame2 < (420 + 151) || xMinigame2 + 50 > 550 || xMinigame2 < 450 && xMinigame2 + 50 > 340 && yMinigame2 < 335 && yMinigame2 + 50 > 200)
		{
		    timesRan++;
		    timesRan2++;
		    clearLeftDoor = 400;
		    clearLeftDoor1 = 2;
		    clearRightDoor = 4;
		    lives--;
		    if (lives <= 0)
		    {
			lostGame ();
		    }

		    break;
		}

		else if (xMinigame2 < 252 + 88 && xMinigame2 + 50 > 252 && yMinigame2 < 275 + 76 && yMinigame2 + 50 > 275)
		{
		    timesRan++;
		    timesRan2++;
		    clearLeftDoor = 400;
		    clearLeftDoor1 = 2;
		    clearRightDoor = 4;
		}


		else if (game1timer <= 0)
		{
		    timesRan++;
		    timesRan2++;
		    clearLeftDoor = 400;
		    clearLeftDoor1 = 2;
		    clearRightDoor = 4;
		    lives--;
		    if (lives <= 0)
		    {
			lostGame ();
		    }

		    break;
		}


		//Queen addition to the game
		backg.setColor (Color.RED);
		backg.fillRect (252, 275, 88, 76);
		backg.drawImage (queenImg, 265, 295, null);

		//SHOW TIMER
		game1timer--;
		showTimerGame1 = "" + game1timer;
		backg.setColor (Color.DARK_GRAY);
		backg.fillRect (700, 480, 30, 20);
		backg.setColor (Color.WHITE);
		backg.setFont (fontTimer);
		backg.drawString ("TIMER:", 680, 470);
		if (game1timer <= 150 && game1timer >= 51)
		{
		    backg.setColor (Color.YELLOW);
		}
		if (game1timer <= 50)
		{
		    backg.setColor (Color.RED);
		}
		backg.drawString (showTimerGame1, 700, 500);


		//This shows how many lives they have left
		backg.setColor (Color.WHITE);
		backg.setFont (fontTimer);
		livesLeft = "" + lives;
		backg.drawString ("Lives", 550, 90);
		backg.setFont (fontTitle);
		backg.drawString (livesLeft, 550, 140);


		/**

			 * DONT TOUCH THE REST OF THIS METHOD
			 */
		// Now the thread checks to see if it should suspend itself
		if (threadSuspended)
		{
		    synchronized (this)
		    {
			while (threadSuspended)
			{
			    wait ();
			}
		    }
		}
		repaint ();
		t.sleep (10);   // interval given in milliseconds
	    }
	}


	catch (InterruptedException e)
	{
	}
    }


    public void ending ()
    {
	try
	{
	    int endingWalk = 5;
	    int endingMovex = 0;
	    int endingMovey = 480;


	    while (end == false)
	    {
		backg.clearRect (0, 0, 800, 600);

		if (endingWalk < 700)
		{
		    endingWalk += 3;
		    backg.drawImage (endingImg, endingWalk, 170, null);
		}

		else if (endingWalk >= 700)
		{
		    backg.clearRect (0, 0, 800, 600);
		    backg.drawImage (endingImg2, 0, 100, null);
		    backg.drawImage (helicopter_Titlescreen, endingMovex, endingMovey, null);


		    if (endingMovex < 800)
		    {
			endingMovex += 2;
			endingMovey -= 1;
		    }
		    else if (endingMovex >= 800)
		    {

			end = true;
			backg.drawImage (backgroundImg, 0, 0, null);
			backg.setFont (fontTitle);
			backg.setColor (Color.CYAN);
			backg.drawString ("YOU WON!", 280, 70);
		    }

		}

		if (end)
		{
		    add (quitButton);
		}







		//DO NOT TOUCH
		if (threadSuspended)
		{
		    synchronized (this)
		    {
			while (threadSuspended)
			{
			    wait ();
			}
		    }
		}
		repaint ();
		t.sleep (17);   // interval given in milliseconds



	    }

	}


	catch (InterruptedException e)
	{
	}


    }



    public void lostGame ()
    {
	try
	{
	    int countLose = 0;
	    boolean lost = false;

	    while (true)
	    {
		backg.setFont (fontTitle);
		backg.clearRect (0, 0, 800, 600);
		backg.setColor (Color.CYAN);
		backg.drawImage (backgroundImg, 0, 0, null);
		backg.drawString ("YOU LOST :(", 260, 70);
		backg.drawImage (lostImg, 200, 130, null);

		if (countLose < 5)
		{

		    countLose++;
		}

		else if (countLose >= 5)
		{
		    lost = true;
		}

		if (lost)
		{
		    add (quitButton);
		}

		//DO NOT TOUCH
		if (threadSuspended)
		{
		    synchronized (this)
		    {
			while (threadSuspended)
			{
			    wait ();
			}
		    }
		}
		repaint ();
		t.sleep (500);   // interval given in milliseconds


	    }


	}


	catch (InterruptedException e)
	{
	}


    }


    /**
     * MouseListener Required methods
     */
    public void mouseClicked (MouseEvent e)
    {

    }


    public void mouseEntered (MouseEvent e)
    {

    }


    public void mouseExited (MouseEvent e)
    {
    }


    public void mousePressed (MouseEvent e)
    {

    }


    public void mouseReleased (MouseEvent e)
    {
    }


    /**
     * MouseMotionListener Required methods
     */


    public void mouseDragged (MouseEvent e)
    {
    }


    public void mouseMoved (MouseEvent e)
    {
    }


    /**
     * Three required methods for KeyListener:
     * keyPressed(), keyReleased(), keyTyped
     */
    public void keyPressed (KeyEvent e)
    {
	if (chooseGame == 0)
	{

	    switch (e.getKeyCode ())
	    {

		case KeyEvent.VK_RIGHT:
		    {
			//GAME 1
			speedX = 14;
			speedY = 0;
			if (xMinigame1 > 510)
			{
			    speedX = 0;
			}
			break;
		    }

		case KeyEvent.VK_LEFT:
		    {
			//GAME 1
			speedX = -14;
			speedY = 0;
			if (xMinigame1 < 220)
			{

			    speedX = 0;
			}
			break;
		    }
		case KeyEvent.VK_UP:
		    {
			//GAME 1
			speedX = 0;
			speedY = 0;
			break;

		    }
		case KeyEvent.VK_DOWN:
		    {
			//GAME 1
			speedX = 0;
			speedY = 0;
			break;

		    }
	    }

	}


	if (chooseGame == 2)
	{
	    switch (e.getKeyCode ())
	    {
		case KeyEvent.VK_RIGHT:
		    {
			//GAME 1
			speedX = 14;
			speedY = 0;
			if (xMinigame2 > 540)
			{

			    speedX = 0;

			}
			break;
		    }

		case KeyEvent.VK_LEFT:
		    {
			//GAME 1
			speedX = -14;
			speedY = 0;
			if (xMinigame2 < 220)
			{

			    speedX = 0;
			}
			break;
		    }
		case KeyEvent.VK_UP:
		    {
			//GAME 1
			speedX = 0;
			speedY = -14;
			if (yMinigame2 < 100)
			{
			    speedY = 0;
			}
			break;

		    }

		case KeyEvent.VK_DOWN:
		    {
			//GAME 1
			speedX = 0;
			speedY = 14;
			if (yMinigame2 > 500)
			{
			    speedY = 0;
			}
			break;

		    }
	    }
	}




	//MINIGAME 1
	xMinigame1 += speedX;

	//MINIGAME 2
	xMinigame2 += speedX;
	yMinigame2 += speedY;





    }


    public void keyReleased (KeyEvent e)
    {
	//System.out.println (e.getKeyCode () == KeyEvent.VK_BACK_SPACE);

	if (chooseGame == 1)
	{

	    char c = e.getKeyChar ();
	    if (c != KeyEvent.CHAR_UNDEFINED)
	    {
		backspace = wordEntered;

		if (e.getKeyCode () == KeyEvent.VK_BACK_SPACE)
		{
		    backspacePressed = true;
		    wordEntered = "";
		}

		else if (e.getKeyCode () == KeyEvent.VK_ENTER)
		{
		    enterPressed = true;
		}
		else
		{
		    wordEntered = wordEntered + c;
		}
		repaint ();
		e.consume ();
	    }
	}

    }


    public void keyTyped (KeyEvent e)
    {

    }


    public void actionPerformed (ActionEvent evt)
    {

	//Checks which button is pressed
	if (evt.getSource () == startButton)
	{
	    remove (startButton);
	    remove (instructionsButton);
	    remove (quitButton);
	    levels = 1;
	    runOnce = 0;
	    dodgeBlocksSpeed = 1;
	    dodgeBlocksSpeed2 = 2;
	    game1timer = timer;
	    startPressed = true;
	}


	else if (evt.getSource () == instructionsButton)
	{
	    remove (startButton);
	    remove (instructionsButton);
	    remove (quitButton);
	    add (backButton);
	    instructionsPressed = true;
	}


	else if (evt.getSource () == backButton)
	{
	    add (startButton);
	    add (instructionsButton);
	    add (quitButton);
	    backButtonPressed = true;
	}


	else if (evt.getSource () == quitButton)
	{
	    quitButtonPressed = true;
	    System.exit (0);
	}


	//MUSIC PLAYING ON/ OFF

	else if (evt.getSource () == musicOnButton)
	{
	    audioClip.play ();
	}


	else if (evt.getSource () == musicOffButton)
	{
	    audioClip.stop ();
	}
    }







    /**
     *  DO NOT MODIFY. This function will start the thread.
     */
    public void start ()
    {
	if (t == null)
	{
	    t = new Thread (this);
	    t.setPriority (Thread.MIN_PRIORITY);
	    threadSuspended = false;
	    t.start ();
	}


	else
	{
	    if (threadSuspended)
	    {
		threadSuspended = false;
		synchronized (this)
		{
		    notify ();
		}
	    }
	}
    }


    /**
     *  DO NOT MODIFY. This function will stop the thread.
     */
    public void stop ()
    {
	threadSuspended = true;
    }


    /**
    *DONOT MODIFY. This is where you do all the drawing. Only the drawing.
    */
    public void update (Graphics g)
    {
	g.drawImage (backbuffer, 0, 0, this);
    }


    /**
    * DO NOT MODIFY. This function will draw all your objects and sprites to the screen
    */
    public void paint (Graphics g)
    {
	update (g);
    }



}


