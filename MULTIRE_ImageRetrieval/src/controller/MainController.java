package controller;

import java.util.ArrayList;

import model.ImageInfo;
import model.MainModel;

public class MainController {

	String path, filename;
	int method;
	MainModel mainModel;
	ArrayList<ImageInfo> rankedImgList;
	
	public MainController(String path, String filename, String methodName){
		mainModel = new MainModel();
		rankedImgList = new ArrayList<ImageInfo>(0);
		
		this.path = path;
		this.filename = filename;
		
		if(methodName.equals("Color Histogram")){
			method = 1;
		} else if(methodName.equals("CH with Perceptual Similarity")){
			method = 2;
		} else if(methodName.equals("Histogram Refinement with Color Coherence")){
			method = 3;
		} else if(methodName.equals("CH with Centering Refinement")){
			method = 4;
		}
		
		System.out.println("Method: " + methodName);
		
		long startTime = System.nanoTime();
		getRankedImages();
		long endTime = System.nanoTime();

		long duration = (endTime - startTime)/1000000000;
		System.out.println("Runtime of method: " + duration + " seconds");
	}
	
	// Pass the method to MainModel, MainModel will do the method, 
	// returns sorted ArrayList<ImageInfo>, to be used by MainGUI to display ranked images
	public void getRankedImages(){
		
		if(method == 1){
			rankedImgList = mainModel.methodCH(path, filename);
		} 
		else if(method == 2){
			rankedImgList = mainModel.methodPS(path, filename);
		} 
		else if(method == 3){
			rankedImgList = mainModel.methodCC(path, filename);
		} 
		else if(method == 4){
			rankedImgList = mainModel.methodCR(path, filename);
		}
	}
	
	public String displayRankedImages(){
		String rankedImages = "";
		
		for(int i = 0; i < rankedImgList.size(); i++){
			rankedImages += rankedImgList.get(i).getFilename() + "\n";
		}
		
//		System.out.println("rank: " + rankedImages);
		return rankedImages;
	}
}
