import java.awt.Color;
//import java.math.*;
//import java.util.ArrayList;

public class ContourOperation implements ImageOperation {
	public Color[][] doOperation(Color[][] image) {
		int numOfRows = image.length;
		int numOfColumns = image[0].length;

		Color[][] contour = new Color[numOfRows][numOfColumns]; // create a new picture or array called "contour" with
																// same pixel length as original picture

		boolean blackColor = false;
		for (int i = 0; i < numOfRows; i++)
			for (int j = 0; j < numOfColumns; j++) {
				/**
				 * these next if, else if, and else statements will find out where the pixel is
				 * placed and its corresponding neighbors
				 */
				if (i == 0) { // If statements check if the pixel is a corner pixel when x = 0 (3 neighbors)
					if (j == 0) {// Top left corner piece
						if (colorDistanceCalc(image[i][j].getRed(), image[i + 1][j].getRed(), image[i][j].getGreen(),
								image[i + 1][j].getGreen(), image[i][j].getBlue(), image[i + 1][j].getBlue()) > 65) {
							blackColor = true;
						}
						if (colorDistanceCalc(image[i][j].getRed(), image[i][j + 1].getRed(), image[i][j].getGreen(),
								image[i][j + 1].getGreen(), image[i][j].getBlue(), image[i][j + 1].getBlue()) > 65) {
							blackColor = true;
						}
						if (colorDistanceCalc(image[i][j].getRed(), image[i + 1][j + 1].getRed(),
								image[i][j].getGreen(), image[i + 1][j + 1].getGreen(), image[i][j].getBlue(),
								image[i + 1][j + 1].getBlue()) > 65) {
							blackColor = true;
						}
					} else if (j == (numOfColumns - 1)) {// Bottom left corner piece
						if (colorDistanceCalc(image[i][j].getRed(), image[i + 1][j].getRed(), image[i][j].getGreen(),
								image[i + 1][j].getGreen(), image[i][j].getBlue(), image[i + 1][j].getBlue()) > 65) {
							blackColor = true;
						}
						if (colorDistanceCalc(image[i][j].getRed(), image[i][j - 1].getRed(), image[i][j].getGreen(),
								image[i][j - 1].getGreen(), image[i][j].getBlue(), image[i][j - 1].getBlue()) > 65) {
							blackColor = true;
						}
						if (colorDistanceCalc(image[i][j].getRed(), image[i + 1][j - 1].getRed(),
								image[i][j].getGreen(), image[i + 1][j - 1].getGreen(), image[i][j].getBlue(),
								image[i + 1][j - 1].getBlue()) > 65) {
							blackColor = true;
						}
					} else { // Left edge piece
						if (colorDistanceCalc(image[i][j].getRed(), image[i][j - 1].getRed(), image[i][j].getGreen(),
								image[i][j - 1].getGreen(), image[i][j].getBlue(), image[i][j - 1].getBlue()) > 65) {
							blackColor = true;
						}
						if (colorDistanceCalc(image[i][j].getRed(), image[i + 1][j - 1].getRed(),
								image[i][j].getGreen(), image[i + 1][j - 1].getGreen(), image[i][j].getBlue(),
								image[i + 1][j - 1].getBlue()) > 65) {
							blackColor = true;
						}
						if (colorDistanceCalc(image[i][j].getRed(), image[i + 1][j].getRed(), image[i][j].getGreen(),
								image[i + 1][j].getGreen(), image[i][j].getBlue(), image[i + 1][j].getBlue()) > 65) {
							blackColor = true;
						}
						if (colorDistanceCalc(image[i][j].getRed(), image[i + 1][j + 1].getRed(),
								image[i][j].getGreen(), image[i + 1][j + 1].getGreen(), image[i][j].getBlue(),
								image[i + 1][j + 1].getBlue()) > 65) {
							blackColor = true;
						}
						if (colorDistanceCalc(image[i][j].getRed(), image[i][j + 1].getRed(), image[i][j].getGreen(),
								image[i][j + 1].getGreen(), image[i][j].getBlue(), image[i][j + 1].getBlue()) > 65) {
							blackColor = true;
						}
					}
				} else if (i == (numOfRows - 1)) {
					if (j == 0) {// Top Right corner piece
						if (colorDistanceCalc(image[i][j].getRed(), image[i - 1][j].getRed(), image[i][j].getGreen(),
								image[i - 1][j].getGreen(), image[i][j].getBlue(), image[i - 1][j].getBlue()) > 65) {
							blackColor = true;
						}
						if (colorDistanceCalc(image[i][j].getRed(), image[i][j + 1].getRed(), image[i][j].getGreen(),
								image[i][j + 1].getGreen(), image[i][j].getBlue(), image[i][j + 1].getBlue()) > 65) {
							blackColor = true;
						}
						if (colorDistanceCalc(image[i][j].getRed(), image[i - 1][j + 1].getRed(),
								image[i][j].getGreen(), image[i - 1][j + 1].getGreen(), image[i][j].getBlue(),
								image[i - 1][j + 1].getBlue()) > 65) {
							blackColor = true;
						}
					} else if (j == (numOfColumns - 1)) {// Bottom Right corner piece
						if (colorDistanceCalc(image[i][j].getRed(), image[i - 1][j].getRed(), image[i][j].getGreen(),
								image[i - 1][j].getGreen(), image[i][j].getBlue(), image[i - 1][j].getBlue()) > 65) {
							blackColor = true;
						}
						if (colorDistanceCalc(image[i][j].getRed(), image[i][j - 1].getRed(), image[i][j].getGreen(),
								image[i][j - 1].getGreen(), image[i][j].getBlue(), image[i][j - 1].getBlue()) > 65) {
							blackColor = true;
						}
						if (colorDistanceCalc(image[i][j].getRed(), image[i - 1][j - 1].getRed(),
								image[i][j].getGreen(), image[i - 1][j - 1].getGreen(), image[i][j].getBlue(),
								image[i - 1][j - 1].getBlue()) > 65) {
							blackColor = true;
						}
					} else { // Right edge piece
						if (colorDistanceCalc(image[i][j].getRed(), image[i][j - 1].getRed(), image[i][j].getGreen(),
								image[i][j - 1].getGreen(), image[i][j].getBlue(), image[i][j - 1].getBlue()) > 65) {
							blackColor = true;
						}
						if (colorDistanceCalc(image[i][j].getRed(), image[i - 1][j - 1].getRed(),
								image[i][j].getGreen(), image[i - 1][j - 1].getGreen(), image[i][j].getBlue(),
								image[i - 1][j - 1].getBlue()) > 65) {
							blackColor = true;
						}
						if (colorDistanceCalc(image[i][j].getRed(), image[i - 1][j].getRed(), image[i][j].getGreen(),
								image[i - 1][j].getGreen(), image[i][j].getBlue(), image[i - 1][j].getBlue()) > 65) {
							blackColor = true;
						}
						if (colorDistanceCalc(image[i][j].getRed(), image[i - 1][j + 1].getRed(),
								image[i][j].getGreen(), image[i - 1][j + 1].getGreen(), image[i][j].getBlue(),
								image[i - 1][j + 1].getBlue()) > 65) {
							blackColor = true;
						}
						if (colorDistanceCalc(image[i][j].getRed(), image[i][j + 1].getRed(), image[i][j].getGreen(),
								image[i][j + 1].getGreen(), image[i][j].getBlue(), image[i][j + 1].getBlue()) > 65) {
							blackColor = true;
						}
					}
				} else if (j == 0) { // Top edge piece
					if (colorDistanceCalc(image[i][j].getRed(), image[i - 1][j].getRed(), image[i][j].getGreen(),
							image[i - 1][j].getGreen(), image[i][j].getBlue(), image[i - 1][j].getBlue()) > 65) {
						blackColor = true;
					}
					if (colorDistanceCalc(image[i][j].getRed(), image[i - 1][j + 1].getRed(), image[i][j].getGreen(),
							image[i - 1][j + 1].getGreen(), image[i][j].getBlue(),
							image[i - 1][j + 1].getBlue()) > 65) {
						blackColor = true;
					}
					if (colorDistanceCalc(image[i][j].getRed(), image[i][j + 1].getRed(), image[i][j].getGreen(),
							image[i][j + 1].getGreen(), image[i][j].getBlue(), image[i][j + 1].getBlue()) > 65) {
						blackColor = true;
					}
					if (colorDistanceCalc(image[i][j].getRed(), image[i + 1][j + 1].getRed(), image[i][j].getGreen(),
							image[i + 1][j + 1].getGreen(), image[i][j].getBlue(),
							image[i + 1][j + 1].getBlue()) > 65) {
						blackColor = true;
					}
					if (colorDistanceCalc(image[i][j].getRed(), image[i + 1][j].getRed(), image[i][j].getGreen(),
							image[i + 1][j].getGreen(), image[i][j].getBlue(), image[i + 1][j].getBlue()) > 65) {
						blackColor = true;
					}
				} else if (j == (numOfColumns - 1)) { // Bottom edge piece
					if (colorDistanceCalc(image[i][j].getRed(), image[i - 1][j].getRed(), image[i][j].getGreen(),
							image[i - 1][j].getGreen(), image[i][j].getBlue(), image[i - 1][j].getBlue()) > 65) {
						blackColor = true;
					}
					if (colorDistanceCalc(image[i][j].getRed(), image[i - 1][j - 1].getRed(), image[i][j].getGreen(),
							image[i - 1][j - 1].getGreen(), image[i][j].getBlue(),
							image[i - 1][j - 1].getBlue()) > 65) {
						blackColor = true;
					}
					if (colorDistanceCalc(image[i][j].getRed(), image[i][j - 1].getRed(), image[i][j].getGreen(),
							image[i][j - 1].getGreen(), image[i][j].getBlue(), image[i][j - 1].getBlue()) > 65) {
						blackColor = true;
					}
					if (colorDistanceCalc(image[i][j].getRed(), image[i + 1][j - 1].getRed(), image[i][j].getGreen(),
							image[i + 1][j - 1].getGreen(), image[i][j].getBlue(),
							image[i + 1][j - 1].getBlue()) > 65) {
						blackColor = true;
					}
					if (colorDistanceCalc(image[i][j].getRed(), image[i + 1][j].getRed(), image[i][j].getGreen(),
							image[i + 1][j].getGreen(), image[i][j].getBlue(), image[i + 1][j].getBlue()) > 65) {
						blackColor = true;
					}
				} else { // Middle piece (No edge or corner)
					if (colorDistanceCalc(image[i][j].getRed(), image[i - 1][j].getRed(), image[i][j].getGreen(),
							image[i - 1][j].getGreen(), image[i][j].getBlue(), image[i - 1][j].getBlue()) > 65) {
						blackColor = true;
					}
					if (colorDistanceCalc(image[i][j].getRed(), image[i - 1][j - 1].getRed(), image[i][j].getGreen(),
							image[i - 1][j - 1].getGreen(), image[i][j].getBlue(),
							image[i - 1][j - 1].getBlue()) > 65) {
						blackColor = true;
					}
					if (colorDistanceCalc(image[i][j].getRed(), image[i][j - 1].getRed(), image[i][j].getGreen(),
							image[i][j - 1].getGreen(), image[i][j].getBlue(), image[i][j - 1].getBlue()) > 65) {
						blackColor = true;
					}
					if (colorDistanceCalc(image[i][j].getRed(), image[i + 1][j - 1].getRed(), image[i][j].getGreen(),
							image[i + 1][j - 1].getGreen(), image[i][j].getBlue(),
							image[i + 1][j - 1].getBlue()) > 65) {
						blackColor = true;
					}
					if (colorDistanceCalc(image[i][j].getRed(), image[i + 1][j].getRed(), image[i][j].getGreen(),
							image[i + 1][j].getGreen(), image[i][j].getBlue(), image[i + 1][j].getBlue()) > 65) {
						blackColor = true;
					}
					if (colorDistanceCalc(image[i][j].getRed(), image[i - 1][j + 1].getRed(), image[i][j].getGreen(),
							image[i - 1][j + 1].getGreen(), image[i][j].getBlue(),
							image[i - 1][j + 1].getBlue()) > 65) {
						blackColor = true;
					}
					if (colorDistanceCalc(image[i][j].getRed(), image[i][j + 1].getRed(), image[i][j].getGreen(),
							image[i][j + 1].getGreen(), image[i][j].getBlue(), image[i][j + 1].getBlue()) > 65) {
						blackColor = true;
					}
					if (colorDistanceCalc(image[i][j].getRed(), image[i + 1][j + 1].getRed(), image[i][j].getGreen(),
							image[i + 1][j + 1].getGreen(), image[i][j].getBlue(),
							image[i + 1][j + 1].getBlue()) > 65) {
						blackColor = true;
					}

				}

				if (blackColor) {
					contour[i][j] = new Color(0, 0, 0);
					blackColor = false;
				} else {
					contour[i][j] = new Color(255, 255, 255);
				}
			}
		return contour;

	}

	public int colorDistanceCalc(int red1, int red2, int green1, int green2, int blue1, int blue2) {// red1, green1, and
																									// blue1 are RGB
																									// from the pixel
																									// compared to.
																									// red2,green2,and
																									// blue2, are RGB
																									// from neighboring
																									// pixels
		/**
		 * this method calculates the color distance using the formula given and returns
		 * it as an integer when called.
		 */
		int colorDistance = (int) Math.sqrt((double) (((red1 - red2) * (red1 - red2))
				+ ((green1 - green2) * (green1 - green2)) + ((blue1 - blue2) * (blue1 - blue2))));
		// System.out.println(colorDistance);
		return colorDistance; // Returning the distance calculated
	}
}
