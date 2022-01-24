import java.awt.Color;
//import java.math.*;
//import java.util.ArrayList;

public class AdjustmentOperation implements ImageOperation {
	public Color[][] doOperation(Color[][] image) {
		int numOfRows = image.length;
		int numOfColumns = image[0].length;

		double maxDis = Math.sqrt(numOfRows * numOfRows + numOfColumns * numOfColumns); // calculates maximum distance
																						// using the number of rows and
																						// columns of original picture

		Color[][] brightness = new Color[numOfRows][numOfColumns];// create a new picture or array called 'brightness'
																	// using the same lengths (x,y) of the original
																	// picture

		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfColumns; j++) {// iterate through every pixel in original picture
				double distance = Math.sqrt(i * i + j * j);
				double adjustBrightness = distance / maxDis;
				int newRed = (int) (((double) image[i][j].getRed()) * adjustBrightness);
				int newGreen = (int) (((double) image[i][j].getGreen()) * adjustBrightness);
				int newBlue = (int) (((double) image[i][j].getBlue()) * adjustBrightness);

				brightness[i][j] = new Color(newRed, newGreen, newBlue); // adds the new RGB with changed brightness to
																			// its corresponding space in the
																			// 'brightness' images
			}
		}
		return brightness;
	}
}