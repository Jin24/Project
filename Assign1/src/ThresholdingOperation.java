import java.awt.Color;

public class ThresholdingOperation implements ImageOperation {
	public Color[][] doOperation(Color[][] image) {
		int numOfRows = image.length;
		int numOfColumns = image[0].length;

		Color[][] threshold = new Color[numOfRows][numOfColumns]; //creates a new array with the same length (x,y) of pixels as the original picture
		for (int i = 0; i < numOfRows; i ++) { // iterate through each row of the image
			for (int j = 0; j<numOfColumns; j++) { //iterate through every pixel of the image
				if (BrightScore(image[i][j].getRed(), image[i][j].getGreen(), image[i][j].getBlue()) > 100) { //calls the BrightScore method to receive the score and compares it to 100
					threshold[i][j] = new Color (255, 255, 255);//the pixel placement in the new image 'threshold' is saved as white
							
				}
				else {
					threshold[i][j] = new Color (0,0,0); //the pixel placement in the new image 'threshold' is saved as black
				}
			}
		}
		return threshold;
	}
	
	public int BrightScore (int r, int g, int b) {
		/**
		 * when called, this method calculates the brightness score using the formula given in the document. the 'r','g','b' are RGB values from the corresponding pixel
		 */
		int score = (int)((0.21*(double)r + 0.71*(double)g + 0.07*(double)b));
		return score;
	}
}
