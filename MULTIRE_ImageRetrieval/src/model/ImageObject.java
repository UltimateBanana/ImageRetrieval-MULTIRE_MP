package model;

import java.util.ArrayList;

public class ImageObject {
	
	//there are only 159 LUV values
	int[] histogramOfImage = new int[159];
	int totalPixels;
	ArrayList<Float> percentageOfColors = new ArrayList<Float>();
	int numAcceptedColors =0 ;
	
	public ImageObject(int[] histogram, int pixels){
		histogramOfImage = histogram;
		totalPixels = pixels;
		getAcceptedColors();
	}
	
	public void printPercentage(){
		for(int x =0; x<percentageOfColors.size();x++){
			System.out.println(percentageOfColors.get(x));
		}
	}
	
	public void printCheck(){
		float ans = 0;
		
		for(int x =0; x<percentageOfColors.size();x++){
			ans+= percentageOfColors.get(x);
		}
		
		System.out.println(ans);
	}
	
	public void getAcceptedColors(){
		// Gets the colors of Image Q (chosen image) that's not 0.0
		
		ArrayList<Integer> temp = new ArrayList<Integer>();
		
		//159 is the total number of LUV colors we have
		for(int x=0; x<159; x++){
			if(histogramOfImage[x] != 0){
				float ans = (float)histogramOfImage[x] / (float)totalPixels;
				percentageOfColors.add(ans);
				
				if( ans >= 0.005){
					numAcceptedColors++;
				}
			}else{
				percentageOfColors.add((float)0.0);
			}
			
//			System.out.println("percentageOfColor(" + x + ") = " + percentageOfColors.get(x));
		}
		
	}
	
	public float getSimilarity(ImageObject img2){
		// Gets similarity of Image Q (chosen image) to another image; SIMexact part
		
		float checkerHistogram = 0; // the sum of all histogram must be 1.00
		float ans= (float)0.0;
		
		System.out.println("\ngetSimilarity()");
		for(int x = 0; x < percentageOfColors.size(); x++ ){
			checkerHistogram += percentageOfColors.get(x);
			
			if(percentageOfColors.get(x) > 0.005){
				
				float numerator = Math.abs(percentageOfColors.get(x)-img2.percentageOfColors.get(x));
				float denominator =  Math.max(percentageOfColors.get(x), img2.percentageOfColors.get(x));
				
				System.out.println("percentageOfColor(" + x + ") = " + percentageOfColors.get(x));
				
				float temp = (float)1-(numerator/denominator);		
				ans+= temp;
			}
		}
		
		float temp1 = (float)1/numAcceptedColors;
		float finalAns = ans * temp1;
		
		System.out.println("numAcceptedColors = " + numAcceptedColors);
		System.out.println("checkerHistogram = " + checkerHistogram);
		
		return finalAns;
	}
}
