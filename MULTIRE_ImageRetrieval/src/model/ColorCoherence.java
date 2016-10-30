package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

//STEPS:
//
//[1] Classify pixels as COHERENT or INCOHERENT
//	  Note: a. t is your threshold (input)
//		    b. (4 or 8) and connectedness will determine coherence or incoherence. 
//[2] Compute for the number of coherent and incoherent pixels (Histogram)
//[3] Get the coherence pair. 1 pair for every LUV value. 
//[4] Compute for the distance measure between the CCVs of two images

public class ColorCoherence {
	
	HashMap<Integer, Integer> valueMap;
	public int[][] coherenceMatrix;
	int currentLUV;
	int label;
	int t;
	boolean first;
	ArrayList<Node> nodeList;
	ArrayList<Integer> list;
	ArrayList<Pair> pairList;
	ArrayList<Pixel> parentList;
	
	public ColorCoherence(int[][] coherenceMatrix, int t){
		valueMap = new HashMap<Integer, Integer>();
		this.coherenceMatrix = coherenceMatrix;
		nodeList = new ArrayList(0);
		list = new ArrayList(0);
		pairList = new ArrayList(0);
		parentList = new ArrayList(0);
		this.t= t;
	}
	
	public void coherence(){
		
		Pixel[][] pixelMatrix = initializePixelMatrix(coherenceMatrix);
		for(int luv = 0; luv<159; luv++){
			currentLUV = luv; // 1 for testing purposes; change it back to currentLUV = luv
			label = 1;
			first = false;
			nodeList = new ArrayList(0);
			for(int row = 0; row<pixelMatrix.length; row++){
				for(int column = 0; column<pixelMatrix[0].length; column++){
					if(row == 0 && pixelMatrix[row][column].getValue() == currentLUV && first == false){
						pixelMatrix[row][column].setLabel(label);
						first = true;
					}
					else if(row == 0 && pixelMatrix[row][column].getValue() == currentLUV && first == true){
						if(column != 0 && pixelMatrix[row][column-1].getValue() == pixelMatrix[row][column].getValue()){
							pixelMatrix[row][column].setLabel(pixelMatrix[row][column-1].getLabel());
						}
						else if(column != 0 && pixelMatrix[row][column-1].getValue() != pixelMatrix[row][column].getValue()){
							label++;
							pixelMatrix[row][column].setLabel(label);
						}
					}
					else if(row == 0 && pixelMatrix[row][column].getValue() != currentLUV){
						pixelMatrix[row][column].setLabel(-1);
					}
					else if(row != 0){
						if(pixelMatrix[row-1][column].getValue() == pixelMatrix[row][column].getValue()){
							if(column !=0 && pixelMatrix[row][column-1].getValue() == pixelMatrix[row][column].getValue()){
								if(pixelMatrix[row][column-1].getLabel() < pixelMatrix[row-1][column].getLabel()){
									pixelMatrix[row][column].setLabel(pixelMatrix[row][column-1].getLabel());
									int parent = pixelMatrix[row][column-1].getLabel();
									int child = pixelMatrix[row-1][column].getLabel();
									Node n = new Node(parent, child);
									if(!nodeList.contains(n)){
										nodeList.add(n);
										valueMap.put(parent, 0);
										//parentList.add(new Pixel(0, parent));
										list.add(parent);
									}
								}
								else if(pixelMatrix[row][column-1].getLabel() > pixelMatrix[row-1][column].getLabel()){
									pixelMatrix[row][column].setLabel(pixelMatrix[row-1][column].getLabel());
									int parent = pixelMatrix[row-1][column].getLabel();
									int child = pixelMatrix[row][column-1].getLabel();
									Node n = new Node(parent, child);
									if(!nodeList.contains(n)){
										nodeList.add(n);
										valueMap.put(parent, 0);
										//parentList.add(new Pixel(0, parent));
										list.add(parent);
									}
								}
								else{
									pixelMatrix[row][column].setLabel(pixelMatrix[row-1][column].getLabel());
									if(!list.contains(pixelMatrix[row][column].getLabel())){
										list.add(pixelMatrix[row][column].getLabel());
										valueMap.put(pixelMatrix[row][column].getLabel(), 0);
									}
								}
							}
							else{
								pixelMatrix[row][column].setLabel(pixelMatrix[row-1][column].getLabel());
								if(!list.contains(pixelMatrix[row][column].getLabel())){
									list.add(pixelMatrix[row][column].getLabel());
									valueMap.put(pixelMatrix[row][column].getLabel(), 0);
								}
							}
						}
						else if(pixelMatrix[row-1][column].getValue() != pixelMatrix[row][column].getValue()){
							if(column != 0 && pixelMatrix[row][column-1].getValue() == pixelMatrix[row][column].getValue()){
								pixelMatrix[row][column].setLabel(pixelMatrix[row][column-1].getLabel());
								if(!list.contains(pixelMatrix[row][column].getLabel())){
									list.add(pixelMatrix[row][column].getLabel());
									valueMap.put(pixelMatrix[row][column].getLabel(), 0);
								}
							}
							else if(pixelMatrix[row][column].getValue() == currentLUV){
								label++;
								pixelMatrix[row][column].setLabel(label);
								if(!list.contains(pixelMatrix[row][column].getLabel())){
									list.add(pixelMatrix[row][column].getLabel());
									valueMap.put(pixelMatrix[row][column].getLabel(), 0);
								}
							}
							else{
								pixelMatrix[row][column].setLabel(-1);
							}
						}
						else{
							pixelMatrix[row][column].setLabel(-1);
							
						}
					}
				}
			}
			
			System.out.println("Row: "+pixelMatrix.length);
			System.out.println("Column: "+pixelMatrix[0].length);
			
			for(int x = 0; x<pixelMatrix.length; x++){
				for(int y = 0; y<pixelMatrix[0].length; y++){
					for(int z = 0; z<nodeList.size(); z++){
						if(pixelMatrix[x][y].getLabel() == nodeList.get(z).getChild()){
							pixelMatrix[x][y].setLabel(nodeList.get(z).getParent());
						}
					}
					if(list.contains(pixelMatrix[x][y].getLabel())){
						int value = valueMap.get(pixelMatrix[x][y].getLabel());
						valueMap.remove(pixelMatrix[x][y].getLabel());
						value++;
						//System.out.println("Label: "+pixelMatrix[x][y].getLabel());
						//System.out.println("Count: "+value);
						valueMap.put(pixelMatrix[x][y].getLabel(), value);
					}
				}
			}
			
			int coherent = 0;
			int noncoherent = 0;
			Set<Integer> keys = valueMap.keySet();
			for(Integer i: keys)
			{
				if(valueMap.get(i) != 0 && i != -1){
					System.out.println("Key: "+ i+ " Value: "+ valueMap.get(i));
					if(valueMap.get(i) >= t){
						coherent+=valueMap.get(i);
					}
					else{
						noncoherent+=valueMap.get(i);
					}
				}
			}
			
			Pair pair = new Pair(coherent, noncoherent);
			pairList.add(pair);
			System.out.println("Coherent: "+coherent);
			System.out.println("Nonoherent: "+noncoherent);
			
		}
		
		
		//Testing Purposes
		//for(int i = 0; i<pixelMatrix.length; i++){
		//	for(int j =0; j<pixelMatrix[0].length; j++){
		//		System.out.print(pixelMatrix[i][j].label + " ");
		//	}
		//	System.out.println();
		//}
		

		//Testing Purposes
		//for(int s = 0; s<nodeList.size(); s++){
		//	System.out.println("Parent: " +nodeList.get(s).getParent() + " Child: "+ nodeList.get(s).getChild());
		//}
	}
	
	public Pixel[][] initializePixelMatrix(int[][] coherenceMatrix){
		
		Pixel[][] pixelMatrix = new Pixel[coherenceMatrix.length][coherenceMatrix[0].length];
		
		for(int row = 0; row<coherenceMatrix.length; row++){
			for(int column = 0; column<coherenceMatrix[0].length; column++){
				pixelMatrix[row][column] = new Pixel();
				pixelMatrix[row][column].setValue(coherenceMatrix[row][column]);
				pixelMatrix[row][column].setLabel(-999);
			}
		}
		
		return pixelMatrix;
	}
}
