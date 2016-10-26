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
		}
		
	}
	
	public float getSimilarity(ImageObject img1){
		
		float ans= (float)0.0;
		for(int x =0; x<percentageOfColors.size();x++ ){
			if(percentageOfColors.get(x)!= 0.0 && img1.percentageOfColors.get(x)!=0.0){
				float numerator = Math.abs(percentageOfColors.get(x)-img1.percentageOfColors.get(x));
				float denominator =  Math.max(percentageOfColors.get(x), img1.percentageOfColors.get(x));
				float temp;
				if(denominator == 0.0){
					temp = (float)1.0;
				}else{
					temp = (float)1-(numerator/denominator);
				}
				
				
				ans+= temp;
			}
		}
		
		float temp1 = (float)1/numAcceptedColors;
		
		ans*=temp1;
		return ans;
	}
}
