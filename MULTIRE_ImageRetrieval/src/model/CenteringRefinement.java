package model;

import java.util.ArrayList;

/* Steps:
 * [x] 1. Get histogram of image as a whole
 * [x] 2. Divide the pixels as center and non-center
 * 	   a. Get the 75% of width and 75% of height, round down the decimal to integer, 
 *        that is the dimension of your center pixels
 *     b. This yields a 54-57% of the image as center image
 * [x] 3. Split histogram bucket to center and non-center -> HOW DO YOU SPLIT IT????
 * 	   a. From the whole image histogram, make 2 arrays: one for histogram of center pixel, 
 * 		  the other for the histogram of non-center histogram;
 * [x] 4. Compare the center bucket and the non-center bucket separately....? DO I USE SIMILARITY???
 */

public class CenteringRefinement {

	int[] LUVColorCountOfImage;
	CenteringPixels[][] imageArray;
	int totalPixels, heightPixel, widthPixel;
	ArrayList<Float> percentageOfColors; // contains Histogram of each color in whole image
	float[] histogramBucketCenter, histogramBucketNonCenter; // contains Histogram of each color in whole image for center and non-center
	int numAcceptedColors; // as a whole image
	
	public CenteringRefinement(CenteringPixels[][] imageArray, int[] LUVArray, int heightPixel, int widthPixel)
	{
		LUVColorCountOfImage = new int[159];
		this.imageArray = new CenteringPixels[widthPixel][heightPixel];
		percentageOfColors = new ArrayList<Float>(); // histogram of pixels
		histogramBucketCenter = new float[159]; // histogram bucket for center image
		histogramBucketNonCenter = new float[159]; // histogram bucket for non-center image
		numAcceptedColors = 0;
		
		LUVColorCountOfImage = LUVArray; // the LUV values
		this.imageArray = imageArray;
		this.heightPixel = heightPixel; // compute for the 75%
		this.widthPixel = widthPixel; // compute for the 75%
		totalPixels = heightPixel * widthPixel;
		
		getAcceptedColors(); // Gets the histogram of image
		dividePixels(); // divides the image to center and non-center
	}
	
	public void initializeBuckets(){
		for(int i = 0; i < 159; i++){
			histogramBucketCenter[i] = 0;
			histogramBucketNonCenter[i] = 0;
		}
	}
	
	public void getAcceptedColors(){
		// Computes for the histogram and counts the colors of Image Q (chosen image) na hindi 0.0
		// Gets histogram as a whole
		
		float checkerHistogram = 0; // to check if the histogram of all colors sum to 1.0
		
		//159 is the total number of LUV colors we have
		for(int x=0; x<159; x++){
			if(LUVColorCountOfImage[x] != 0){
				float ans = (float)LUVColorCountOfImage[x] / (float)totalPixels; // gets the ACTUAL histogram; this is the NH(Q) part
				percentageOfColors.add(ans);
				
				checkerHistogram += ans;
				
				if( ans > 0.005){
					numAcceptedColors++;
					System.out.println("(Accepted) HISTOGRAM Of Color(" + x + ") = " + percentageOfColors.get(x));
				}
				
			}else{ // if null
				percentageOfColors.add((float)0.0);
			}
		}
		System.out.println("numAcceptedColors = " + numAcceptedColors);
		System.out.println("checkerHistogram = " + checkerHistogram);
	}
	
	public void dividePixels(){
		// Divides the pixels to Center and Non-center
		// This method just modifies the isCenter of the pixels in imageArray
		
		int centerWidth = (int) (widthPixel * 0.75);
		int centerHeight = (int) (heightPixel * 0.75);
		
		// For checking only
		System.out.println("width: " + widthPixel + "   height: " + heightPixel);
		System.out.println("75% width: " + centerWidth + "   75% height: " + centerHeight);
		float center = centerWidth*centerHeight;
		float whole = widthPixel*heightPixel;
		float centerPercentage = center/whole;
		System.out.printf("Center image is %.4f percent of image\n", centerPercentage*100 );
		
		// Computes for the location of the center image in the image itself
		int centerLeft = centerWidth/2; // this is the left half of the centerWidth
		int centerUp = centerHeight/2; // this is the upper half of the centerHeight
		int wholeLeft = widthPixel/2; // this is the left half of the widthPixel
		int wholeUp = heightPixel/2; // this is the upper half of the heightPixel
		
		int centerWidthStart = wholeLeft - centerLeft; 
		int centerWidthEnd = centerWidthStart + centerWidth;
		int centerHeightStart = wholeUp - centerUp;
		int centerHeightEnd = centerHeightStart + centerHeight;
		
		// For checking only
		System.out.println("Location of the Center Image:");
		System.out.println("centerWidthStart = " + centerWidthStart + "   centerWidthEnd = " + centerWidthEnd);
		System.out.println("centerHeightStart = " + centerHeightStart + "   centerHeightEnd = " + centerHeightEnd);
		System.out.println();
		
		// Divides and identifies the center image
		// Also splits histogram bucket to center and non-center
		for(int x = 0; x < widthPixel; x++ ){
			for(int y = 0; y < heightPixel; y++ ){
				// If within the location of the center image, sets the isCenter to true 
				// thus marking the pixels as center image
				if(x >= centerWidthStart && x <= centerWidthEnd && y >= centerHeightStart && y <= centerHeightEnd){
					imageArray[x][y].setCenter(true);
				}
				
				if( imageArray[x][y].isCenter() == true && histogramBucketCenter[imageArray[x][y].getLUVColor()] == 0.0 ){
					// If imageArray[x][y] is in the center && histogramBucketCenter doesn't yet have 
					// the histogram in percentageOfColors.get(imageArray[x][y].getLUVColor()),
					// then add that histogram in histogramBucketCenter
					
					histogramBucketCenter[imageArray[x][y].getLUVColor()] = percentageOfColors.get(imageArray[x][y].getLUVColor());
				}
				if( imageArray[x][y].isCenter() == false && histogramBucketNonCenter[imageArray[x][y].getLUVColor()] == 0.0 ){
					// If imageArray[x][y] is NOT in the center && histogramBucketNonCenter doesn't yet have 
					// the histogram in percentageOfColors.get(imageArray[x][y].getLUVColor()),
					// then add that histogram in histogramBucketNonCenter
					
					histogramBucketNonCenter[imageArray[x][y].getLUVColor()] = percentageOfColors.get(imageArray[x][y].getLUVColor());
				}
				
			}
		}
		
		/*for(int i = 0; i < histogramBucketCenter.length; i++){
			if(histogramBucketCenter[i] > 0.005){
				System.out.println("Center histogramBucket("+ i + "): " + histogramBucketCenter[i]);
			}
		}
		for(int i = 0; i < histogramBucketNonCenter.length; i++){
			if(histogramBucketNonCenter[i] > 0.005){
				System.out.println("Non-Center histogramBucket("+ i + "): " + histogramBucketNonCenter[i]);
			}
		}*/
	}
	
	public void getSimilarity(CenteringRefinement img2){
		// Gets similarity of Image Q (chosen image) to another image; SIMexact part
		
		float simCenter = (float)0.0;
		float simNonCenter = (float)0.0;
		int numAcceptedColorsCenter = 0;
		int numAcceptedColorsNonCenter = 0;
		
		System.out.println("getSimilarity()");
		
		// Get similarity of center image of img1 and img2
		for(int x = 0; x < histogramBucketCenter.length; x++ ){
			
			if(histogramBucketCenter[x] > 0.005){
				numAcceptedColorsCenter++;
				float numerator = Math.abs(histogramBucketCenter[x]-img2.histogramBucketCenter[x]);
				float denominator =  Math.max(histogramBucketCenter[x], img2.histogramBucketCenter[x]);
				
				System.out.println("percentageOfColor(" + x + ") CENTER = " + histogramBucketCenter[x]);
				
				float temp = (float)1-(numerator/denominator);		
				simCenter += temp;
			}
		}
		
		float temp1 = (float)1/numAcceptedColorsCenter;
		float finalSimCenter = simCenter * temp1;
		System.out.println("numAcceptedColorsCenter = " + numAcceptedColorsCenter);
		
		
		// Get similarity of non-center image of img1 and img2
		for(int x = 0; x < histogramBucketNonCenter.length; x++ ){
			
			if(histogramBucketNonCenter[x] > 0.005){
				numAcceptedColorsNonCenter++;
				float numerator = Math.abs(histogramBucketNonCenter[x]-img2.histogramBucketNonCenter[x]);
				float denominator =  Math.max(histogramBucketNonCenter[x], img2.histogramBucketNonCenter[x]);
				
				System.out.println("percentageOfColor(" + x + ") NON-CENTER= " + histogramBucketNonCenter[x]);
				
				float temp = (float)1-(numerator/denominator);		
				simNonCenter += temp;
			}
		}
		
		float temp2 = (float)1/numAcceptedColorsNonCenter;
		float finalSimNonCenter = simNonCenter * temp2;
		System.out.println("numAcceptedColorsNonCenter = " + numAcceptedColorsNonCenter);
		
		System.out.println("\nCR SIM in Center = " + finalSimCenter);
		System.out.println("CR SIM in Non-Center = " + finalSimNonCenter);
		
//		return finalAns;
	}
}
