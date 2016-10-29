package model;

import java.util.ArrayList;

/* Steps:
 * [?] 1. Get the raw color value of the LUV color (for the D(i,j) part)
 * [x] 2. Get histogram of image (like in Color Histogram)
 * [?] 3. Do the Color Similarity Matrix
 *        a. Get Dmax
 *        b. Get Tcolor
 *        c. Compute for Color Similarity Matrix
 * [ ] 4. Compute for similarity of img 1 & 2
 * 		  a. Compute for SIMcolor
 * 			 i. Compute for SIMExactCol and SIMPerCol
 */
public class PerceptualSimilarity {
	
	float[][] similarityMatrix;
	int[] LUVColorCountOfImage;
//	LuvValues[] LUVRawColorValues;
	ArrayList<Float> percentageOfColors; // contains Histogram of each color in whole image
	int totalPixels; // as a whole image
	int numAcceptedColors; // as a whole image
	
	public PerceptualSimilarity(int[] LUVColorCountOfImage, int totalPixels){
		similarityMatrix = new float[159][159];
		percentageOfColors = new ArrayList<Float>(0);
		numAcceptedColors = 0;
		
		this.LUVColorCountOfImage = LUVColorCountOfImage;
//		this.LUVRawColorValues = LUVRawColorValues;
		this.totalPixels = totalPixels;
		
//		printLUVRaw();
		getAcceptedColors();
		getSimilarityMatrix();
	}
	
	public void printSimMatrix(){
		for(int i = 0; i < 159; i++){
			for(int j = 0; j < 159; j++){
				System.out.print(similarityMatrix[i][j]  + "  ");
			}
			System.out.println();
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
	
	public void getSimilarityMatrix(){
		// Gets the similarity matrix by computing for max of D(i,j), Tcolor, and the similarity between two colors
		// See Lesson 5, slides 24-26
		
		// Get the Dmax
		// HOW DOES THIS EVEN WWORK
		int Dmax = 0;
		for(int i = 0; i < 159; i++){
			for(int j = 0; j < 159; j++){
				/*
				double Dmax = Math.max(LUVRawColorValues[i].getValueL()-LUVRawColorValues[j].getValueL(), 
						Math.max(LUVRawColorValues[i].getValueU()-LUVRawColorValues[j].getValueU(), 
								LUVRawColorValues[i].getValueV()-LUVRawColorValues[j].getValueV()));
				*/
				Dmax = Math.max( i-j, Dmax );
//				System.out.println(Dmax);
			}
		}
		
		// Get Tcolor
		double Tcolor = 0.2 * Dmax; // Tcolor = p*Dmax; p is usually 0.2
		System.out.println(Dmax + "   " + Tcolor);
		
		// Compute for Color Similarity Matrix
		for(int i = 0; i < 159; i++){
			for(int j = 0; j < 159; j++){
				if( (i-j) > Tcolor ){
					similarityMatrix[i][j] = 0;
				}else{
					similarityMatrix[i][j] = (float) (1-((i-j)/Tcolor));
				}
			}
		}
	}
	
	public void getSimilarity(PerceptualSimilarity img2){
		float finalSim = 0;
		
		System.out.println("Getting final similarity...");
		
		for(int i = 0; i < 159; i++){
			if(percentageOfColors.get(i) != 0){
				finalSim += getSimColor(percentageOfColors.get(i), i, img2.percentageOfColors.get(i), img2.percentageOfColors)*
					percentageOfColors.get(i);
			}
		}
		
		System.out.println("SIM in Perceptual Similarity: " + finalSim);
	}
	
	public float getSimExactCol(float histogramImg1, float histogramImg2){
		return 1-( Math.abs(histogramImg1-histogramImg2)/Math.max(histogramImg1, histogramImg2) );
	}
	
	public float getSimPerCol(float histogramImg1, int i, ArrayList<Float> percentageOfColorsImg2){
		float sim = 0;
		
		for(int j = 0; j < 159; j++){
			sim += (1-( Math.abs(histogramImg1-percentageOfColorsImg2.get(j))/
					Math.max(histogramImg1, percentageOfColorsImg2.get(j)) )) * similarityMatrix[i][j] ;
		}
		
		return sim;
	}
	
	public float getSimColor(float histogramImg1, int i, float histogramImg2, ArrayList<Float> percentageOfColorsImg2){
//		System.out.println(getSimExactCol(histogramImg1, histogramImg2));
//		System.out.println((1+getSimPerCol(histogramImg1, i, percentageOfColorsImg2)));
		return getSimExactCol(histogramImg1, histogramImg2)*(1+getSimPerCol(histogramImg1, i, percentageOfColorsImg2));
	}
}
