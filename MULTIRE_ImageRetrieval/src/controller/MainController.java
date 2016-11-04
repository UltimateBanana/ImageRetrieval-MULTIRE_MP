package controller;

import java.util.ArrayList;

import model.ImageInfo;
import model.MainModel;

public class MainController {

	String path, filename;
	int method;
	MainModel mainModel;
	
	public MainController(String path, String filename, String methodName){
		mainModel = new MainModel();
		
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
	public ArrayList<ImageInfo> getRankedImages(){
		
		if(method == 1){
			return mainModel.methodCH(path, filename);
		} 
		else if(method == 2){
			return mainModel.methodPS(path, filename);
		} 
		else if(method == 3){
			return mainModel.methodCC(path, filename);
		} 
		else if(method == 4){
			return mainModel.methodCR(path, filename);
		}
		return null;
	}
	
	
}
