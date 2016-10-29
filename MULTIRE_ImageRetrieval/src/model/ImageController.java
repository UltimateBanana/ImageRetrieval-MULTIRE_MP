package model;

/*
 * This class is just a cleaner version of Image.java to be used
 * for the project
 */

import java.awt.*;
import java.awt.event.*;
import com.sun.image.codec.jpeg.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.*;
import javax.swing.*;
import java.awt.image.ColorModel;

public class ImageController {
	
	public static ImageObject convertImageCH(String Path, String Filename){
		//loops through every pixel instead of getRGB() being a single pixel
		
		BufferedImage bi1 = null;
	       int RGB1;
	       int i,j;
	       int totalPixels;
	       
	       int[] tempArray = new int[159];
	       
	       try {

	            File file = new File(Path, Filename);
	            FileInputStream in = new FileInputStream(file);

	            // decodes the JPEG data stream into a BufferedImage

	            JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
	            bi1 = decoder.decodeAsBufferedImage();
	            
	        } catch (Exception ex) {
	            /*file error*/
	        	System.out.println("file error");
	        }

	        if (bi1 == null) {
	            /*null file*/
	        	System.out.println("null");
	            return null;
	        }

	        totalPixels = bi1.getHeight() * bi1.getWidth();
	        
	        System.out.println("height is : " + bi1.getHeight());
	        System.out.println("width is : " + bi1.getWidth());
	        
	        for(int y = 0; y<bi1.getHeight();y++){
	        	//System.out.println("~~~~~"+y);
	        	for(int x = 0; x < bi1.getWidth(); x++){
	        		//System.out.println("-----"+x);
	        		ColorModel CM;
	    	        CM = bi1.getColorModel();
	    	        //change this for the loop
	    	        RGB1 = bi1.getRGB(x,y); //get the RGB value at x,y of the image
	    	        
	    	        double R, G, B;

	    	        R = CM.getRed(RGB1);   //get the 8-bit values of RGB (0-255)
	    	        G = CM.getGreen(RGB1);
	    	        B = CM.getBlue(RGB1);	
	    		    cieConvert ColorCIE = new cieConvert();

	    			
	    		    ColorCIE.setValues(R/255.0, G/255.0, B/255.0);
	    		    
	    		   // System.out.println(Integer.toString());
	    		    tempArray[ColorCIE.IndexOf()]++;
	        	}
	        }
	        
	        ImageObject tempImg = new ImageObject(tempArray,totalPixels);

	        return tempImg;
	}
	
	public static CenteringRefinement convertImageCR(String Path, String Filename){
		// loops through every pixel instead of getRGB() being a single pixel
		// Gets RGB->LUV of each pixel for the whole image
		
		BufferedImage bi1 = null;
	       int RGB1;
	       int i,j;
	       int totalPixels;
	       
	       try {

	            File file = new File(Path, Filename);
	            FileInputStream in = new FileInputStream(file);

	            // decodes the JPEG data stream into a BufferedImage

	            JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
	            bi1 = decoder.decodeAsBufferedImage();
	            
	        } catch (Exception ex) {
	            /*file error*/
	        	System.out.println("file error");
	        }

	        if (bi1 == null) {
	            /*null file*/
	        	System.out.println("null");
	            return null;
	        }

	        totalPixels = bi1.getHeight() * bi1.getWidth();
	        
	        System.out.println("height is : " + bi1.getHeight());
	        System.out.println("width is : " + bi1.getWidth());
	        
	        CenteringPixels[][] imageArray = new CenteringPixels[bi1.getWidth()][bi1.getHeight()];
        	int[] LUVArray = new int[159];
        	
        	// Converts each RGB color into LUV color; for the WHOLE image into a 2D ARRAY (depicting the image)
        	for(int y = 0; y<bi1.getHeight();y++){
	        	//System.out.println("~~~~~"+y);
	        	for(int x = 0; x < bi1.getWidth(); x++){
	        		//System.out.println("-----"+x);
	        		ColorModel CM;
	    	        CM = bi1.getColorModel();
	    	        //change this for the loop
	    	        RGB1 = bi1.getRGB(x,y); //get the RGB value at x,y of the image
	    	        
	    	        double R, G, B;

	    	        R = CM.getRed(RGB1);   //get the 8-bit values of RGB (0-255)
	    	        G = CM.getGreen(RGB1);
	    	        B = CM.getBlue(RGB1);	
	    		    cieConvert ColorCIE = new cieConvert();
	    		    ColorCIE.setValues(R/255.0, G/255.0, B/255.0);
	    		    
	    		    CenteringPixels centeringPixel = new CenteringPixels(x, y, ColorCIE.IndexOf());
	    		    imageArray[x][y] = centeringPixel;
	    		    LUVArray[ColorCIE.IndexOf()]++;
	    		    
	    		   // System.out.println(Integer.toString());
//	    		    tempArray[ColorCIE.IndexOf()]++;
	        	}
	        }
        	
        	CenteringRefinement CRImage = new CenteringRefinement(imageArray, LUVArray, bi1.getHeight(), bi1.getWidth());
			return CRImage;
	}
	
	public static void main(String Args[]){

		// COLOR HISTOGRAM
		System.out.println("--- COLOR HISTOGRAM ---");
		System.out.println("Image 1:");
		ImageObject CHimg1 = convertImageCH("images/", "206.jpg");
		System.out.println("\nImage 2:");
		ImageObject CHimg2 = convertImageCH("images/", "218.jpg");
		
		System.out.println("SIM in Color Histogram = " + CHimg1.getSimilarity(CHimg2) + "\n\n");
		
		
		// CENTERING REFINEMENT
		System.out.println("--- COLORING REFINEMENT ---");
		System.out.println("Image 1:");
		CenteringRefinement CRimg1 = convertImageCR("images/", "206.jpg");
		System.out.println("Image 2:");
		CenteringRefinement CRimg2 = convertImageCR("images/", "218.jpg");
		
		CRimg1.getSimilarity(CRimg2);
	}
}
