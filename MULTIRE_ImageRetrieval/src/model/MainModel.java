package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainModel {

	ImageController imgController;
	ArrayList<ImageInfo> imgSimList;
	
	public MainModel(){
		imgController = new ImageController();
		imgSimList = new ArrayList<ImageInfo>(0); //sort with collections.sort
	}
	
	// Color Histogram
	public ArrayList<ImageInfo> methodCH(String path, String filename){
		ImageObject CHimg1 = ImageController.convertImageCH(path, filename);
		
//		System.out.println("Doing COLOR HISTOGRAM...");
		File folder = new File(path);
		for (File fileEntry : folder.listFiles()) {
			String img2Filename = fileEntry.getName();
			String extension = img2Filename.substring(img2Filename.lastIndexOf(".") + 1, img2Filename.length());
			
			if(extension.equals("jpg") && !img2Filename.equals(filename)){
//				System.out.println(fileEntry.getName());
				ImageObject CHimg2 = ImageController.convertImageCH(path, img2Filename);
				
				imgSimList.add(new ImageInfo(path, img2Filename, CHimg1.getSimilarity(CHimg2)));
				
//				System.out.println("\nImage 2 is now " + img2Filename);
			}
	    }
		Collections.sort(imgSimList, new imgDescComparator());
		
//		System.out.println("\n RANKED COLOR HISTOGRAM...");
		for(int i = 0; i < imgSimList.size(); i++){
			System.out.println(imgSimList.get(i).getFilename() + ": " + imgSimList.get(i).getSimilarity());
		}
		
		return imgSimList;
	}
	
	// Perceptual Similarity
	public ArrayList<ImageInfo> methodPS(String path, String filename){
		
		return imgSimList;
	}
	
	// Color Coherence
	public ArrayList<ImageInfo> methodCC(String path, String filename){
		
		ImageController ic = new ImageController();
		ImageObject img1 = ic.convertImageCC(path, filename);
		ColorCoherence ccv = new ColorCoherence(ic.getMatrixArray(), 6);
		ArrayList<Pair> pairList = ccv.coherence();
		
		File folder = new File(path);
		for(File fileEntry : folder.listFiles()) {
			String img2Filename = fileEntry.getName();
			String extension = img2Filename.substring(img2Filename.lastIndexOf(".") + 1, img2Filename.length());
			
			if(extension.equals("jpg") && !img2Filename.equals(filename)){
				ImageObject img2 = ic.convertImageCC(path, img2Filename);
				ColorCoherence ccv2 = new ColorCoherence(ic.getMatrixArray(), 6);
				ArrayList<Pair> pairList2 = ccv2.coherence();
				int colorcoherence = ccv.computeCCV(pairList, pairList2);
				
//				System.out.println(colorcoherence);
				imgSimList.add(new ImageInfo(path, img2Filename, colorcoherence));
			}
		}
		Collections.sort(imgSimList, new imgAscComparator());	
		
		for(int i = 0; i < imgSimList.size(); i++){
			System.out.println(imgSimList.get(i).getFilename() + ": " + imgSimList.get(i).getSimilarity());
		}
		
		return imgSimList;
	}
	
	// Centering Refinement
	public ArrayList<ImageInfo> methodCR(String path, String filename){
		CenteringRefinement CRimg1 = ImageController.convertImageCR(path, filename);
		
		File folder = new File(path);
		for (File fileEntry : folder.listFiles()) {
			String img2Filename = fileEntry.getName();
			String extension = img2Filename.substring(img2Filename.lastIndexOf(".") + 1, img2Filename.length());
			
			if(extension.equals("jpg") && !img2Filename.equals(filename)){
//				System.out.println(fileEntry.getName());
				CenteringRefinement CRimg2 = ImageController.convertImageCR("images/", "218.jpg");
				
				imgSimList.add(new ImageInfo(path, img2Filename, CRimg1.getSimilarity(CRimg2)));
				
//				System.out.println("\nImage 2 is now " + img2Filename);
			}
	    }
		Collections.sort(imgSimList, new imgDescComparator());
		
//		System.out.println("\n RANKED COLOR HISTOGRAM...");
		for(int i = 0; i < imgSimList.size(); i++){
			System.out.println(imgSimList.get(i).getFilename() + ": " + imgSimList.get(i).getSimilarity());
		}
		
		return imgSimList;
	}
	
	public class imgDescComparator implements Comparator<ImageInfo> {
	    @Override
		public int compare(ImageInfo o1, ImageInfo o2) {
			// TODO Auto-generated method stub
			return Float.compare(o2.getSimilarity(), (o1.getSimilarity()));
		}
	}
	
	public class imgAscComparator implements Comparator<ImageInfo> {
	    @Override
		public int compare(ImageInfo o1, ImageInfo o2) {
			// TODO Auto-generated method stub
			return Float.compare(o1.getSimilarity(), (o2.getSimilarity()));
		}
	}
}