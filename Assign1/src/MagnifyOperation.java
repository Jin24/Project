import java.awt.Color;
//import java.math.*;
//import java.util.ArrayList;

public class MagnifyOperation implements ImageOperation {
	public Color[][] doOperation(Color[][] image) {
		int numOfRows = image.length;
		int numOfColumns = image[0].length;

		Color[][] magnify = new Color[numOfRows * 2][numOfColumns * 2]; //Creates a new image with double the number of pixels or array size (x,y)

		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfColumns; j++) { //iterate through every pixel in the original picture
				int getRed = image[i][j].getRed();//get the R value of RGB from original picture
				int getGreen = image[i][j].getGreen();//get the G value of RGB from original picture
				int getBlue = image[i][j].getBlue();//get the B value of RGB from original picture

				/**
				 * the next four lines code:
				 * 		the RGB of a respective pixel on the original picture is added to the new picture 'magnify' on four pixels (corresponding pixel with original in the new image and its neighboring pixels)
				 */
				magnify[i*2][j*2] = new Color(getRed, getGreen, getBlue); //corresponding pixel placement of the original picture in the new picture
				magnify[i*2 + 1][j*2] = new Color(getRed, getGreen, getBlue); //corresponding pixel placement neighbor (1 pixel to the right)
				magnify[i*2][j*2 + 1] = new Color(getRed, getGreen, getBlue); //corresponding pixel placement neighbor (1 pixel down)
				magnify[i*2 + 1][j*2 + 1] = new Color(getRed, getGreen, getBlue); //corresponding pixel placement neighbor (1 pixel to the right and down)

			}
		}
		return magnify;
	}
}