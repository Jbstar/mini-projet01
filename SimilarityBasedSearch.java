package main;

public class SimilarityBasedSearch {

	/**
	 * Computes the mean value of a gray-scale image given as a 2D array
	 * 
	 * @param image: a 2D double array, the gray-scale Image
	 * Requirement: image has to be an array greater than 1 by 1
	 * @return a double value 
	 */
	public static double mean(double[][] image) {
		
		assert image.length>0 && image[0].length>0;
		
		double sommeImage=0;
		double meanImage;
		
		for(int row = 0 ; row < image.length ; row++) {
			for(int col = 0 ; col < image[0].length; col++) {
				
				sommeImage += image[row][col] ;
			
			}
		}
			meanImage = (sommeImage / (image.length * image[0].length));
			
		return meanImage;
	}

	/**
	 * Computes the Normalized Cross Correlation of a gray-scale pattern if
	 * positioned at the provided row, column-coordinate in a gray-scale image
	 * 
	 * @param row: a integer, the row-coordinate of the upper left corner of the pattern in the image.
	 * Requirement: row must be greater than or equals to 0 and smaller than the difference of the row number of the image +1 and the row number of the pattern 
	 * @param column: a integer, the column-coordinate of the upper left corner of the pattern in the image.
	 * Requirement: column must be greater than or equal to 0 and smaller than the difference of the column number of the image +1 and the column number of the pattern
	 * @param pattern: an 2D array of doubles, the gray-scale pattern to find
	 * Requirement: pattern has to be an 2D  array with a minimal size of 1*1
	 * @param image: an 2D array of double, the gray-scale image where to look for the pattern
	 * Requirement: image has to be an 2D  array with a minimal size of 1*1
	 * @return a double, the Normalized Cross Correlation value at position (row, col) between the pattern and the part of the base image that is 
	 * 		   covered by the pattern, if the pattern is shifted by x and y. should
	 *         return -1 if the denominator is 0
	 *         
	 */
	public static double normalizedCrossCorrelation(int row, int col, double[][] pattern, double[][] image) {
		
		assert pattern.length > 0 && pattern[0].length > 0;
		assert image.length > 0 && image[0].length > 0;
		assert row >= 0 && row <= (image.length-pattern.length+1);
		assert col >= 0 && col <= (image[0].length-pattern[0].length+1);

		double moyenneImage = windowMean(image,row,col,pattern.length,pattern[0].length);
		double moyennePattern = mean(pattern);
		double crossCorrelation;
		double sumPatternImage = 0;
		double sumPatternSquared = 0;
		double sumImageSquared = 0;
		
		for (int i = 0; i < pattern.length; i++) {
			for (int j = 0; j < pattern[0].length; j++) {
				sumPatternImage += (image[row+i][col+j]-moyenneImage)*(pattern[i][j]-moyennePattern);
				sumImageSquared += Math.pow((image[row+i][col+j]-moyenneImage), 2);
				sumPatternSquared += Math.pow((pattern[i][j]-moyennePattern), 2);
			}
		}
		if (sumImageSquared*sumPatternSquared == 0) {
			return -1;
		}
		
		crossCorrelation = sumPatternImage/Math.sqrt(sumImageSquared*sumPatternSquared);
		
	return crossCorrelation;
	}

	/**
	 * Computes the mean value of a gray-scale portion of an image beginning from 
	 * a given row-column coordinate 
	 * 
	 * @param row: a integer, the row-coordinate of the upper left corner of the pattern in the image.
	 * Requirement: row must be greater than or equals to 0 and smaller than the difference of the row number of the matrix +1 and height 
	 * @param column: a integer, the column-coordinate of the upper left corner of the pattern in the image.
	 * Requirement: column must be greater than or equal to 0 and smaller than the difference of the column number of the matrix +1 and width
     * @param matrix : an 2D array
	 * Requirement : a 2D array of double with a minimal size of 1*1.
	 * @param widht : the number of column concern in the matrix.
	 * Requirement : width has to be greater than or equal to 0.
	 * @param height : the number of row concern in the matrix.
	 * Requirement : height has to be greater than or equal to 0.
	 * @return a double, which is the mean value of all elements of the portion of the matrix
	 */
	
	
	public static double windowMean(double [][] matrix , int row , int col , int width , int height) {
		
		assert matrix.length>0 && matrix[0].length>0;
		assert width >0 && width <= matrix.length;
		assert height >0 && height <= matrix[0].length;
		assert row >= 0 && row <= (matrix.length-width+1);
		assert col >= 0 && col <= (matrix[0].length-height+1);
		
		double mean;
		double sum = 0;
		
		for (int i = row; i < row + width; i++) {
			for (int j = col; j < col + height; j++) {
				sum += matrix[i][j];
			}
			
		}
		mean = sum / (height*width);
		
		return mean;
	}
	
		
		
	/**
	 * Compute the similarityMatrix between a gray-scale image and a gray-scale
	 * pattern
	 * 
	 * @param pattern: an 2D array of doubles, the gray-scale pattern to find
	 * @param image: an 2D array of doubles, the gray-scale image where to look for
	 *            the pattern. 
	 *            Requirement : image has to be an 2D  array with a minimal size of 1*1
	 * @return a 2D array of doubles, containing for each pixel of a original
	 *         gray-scale image, the similarity (normalized cross-correlation)
	 *         between the image's window and the pattern placed over this pixel
	 *         (upper-left corner)
	 *         Requirement : pattern has to be an 2D  array with a minimal size of 1*1
	 
	 
	 
	 */
	public static double[][] similarityMatrix(double[][] pattern, double[][] image) {

		assert pattern.length > 0 && pattern[0].length > 0;
		assert image.length > 0 && image[0].length > 0;
		
		double[][] similarityMatrix = new double[image.length - pattern.length + 1][image[0].length - pattern[0].length+ 1];
		for (int row = 0; row < similarityMatrix.length; row++) {
			for (int col = 0; col < similarityMatrix[0].length; col++) {
				similarityMatrix[row][col] = normalizedCrossCorrelation(row, col, pattern, image);
			}
		}
		return similarityMatrix;
	}

}
