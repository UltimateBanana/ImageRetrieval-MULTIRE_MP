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
import java.util.ArrayList;

import javax.swing.*;
import java.awt.image.ColorModel;

public class ImageController {
	
	static int[][] matrixArray;

	public static ImageObject convertImage(String Path, String Filename) {
		// loops through every pixel instead of getRGB() being a single pixel

		BufferedImage bi1 = null;
		int RGB1;
		int i, j;
		int totalPixels;
		

		int[] tempArray = new int[159];

		try {

			File file = new File(Path, Filename);
			FileInputStream in = new FileInputStream(file);

			// decodes the JPEG data stream into a BufferedImage

			JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
			bi1 = decoder.decodeAsBufferedImage();

		} catch (Exception ex) {
			/* file error */
			System.out.println("file error");
		}

		if (bi1 == null) {
			/* null file */
			System.out.println("null");
			return null;
		}

		totalPixels = bi1.getHeight() * bi1.getWidth();
		matrixArray = new int[bi1.getHeight()][bi1.getWidth()];
		System.out.println("height is : " + bi1.getHeight());
		System.out.println("width is : " + bi1.getWidth());

		for (int y = 0; y < bi1.getHeight(); y++) {
			// System.out.println("~~~~~"+y);
			for (int x = 0; x < bi1.getWidth(); x++) {
				// System.out.println("-----"+x);
				ColorModel CM;
				CM = bi1.getColorModel();
				// change this for the loop
				RGB1 = bi1.getRGB(x, y); // get the RGB value at x,y of the
											// image

				double R, G, B;

				R = CM.getRed(RGB1); // get the 8-bit values of RGB (0-255)
				G = CM.getGreen(RGB1);
				B = CM.getBlue(RGB1);
				cieConvert ColorCIE = new cieConvert();

				ColorCIE.setValues(R / 255.0, G / 255.0, B / 255.0);

				// System.out.println(Integer.toString());
				matrixArray[y][x] = ColorCIE.IndexOf();
				tempArray[ColorCIE.IndexOf()]++;
			}
		}

		ImageObject tempImg = new ImageObject(tempArray, totalPixels);

		return tempImg;
	}

	public static void main(String Args[]) {

		ImageObject img1 = convertImage("images/", "11.jpg");
		ColorCoherence ccv = new ColorCoherence(matrixArray, 6);
		ArrayList<Pair> pairList = ccv.coherence();
		
		//START OF CCV1
		//for(int i =0; i<pairList.size(); i++){
		//	System.out.println("C: "+pairList.get(i).getCoherent() + " NC: "+pairList.get(i).getNoncoherent());
		//}
		matrixArray = null;
		ImageObject img2 = convertImage("images/", "12.jpg");
		ColorCoherence ccv2 = new ColorCoherence(matrixArray, 6);
		ArrayList<Pair> pairList2 = ccv2.coherence();
		
		//START OF CCV2
		//for(int i =0; i<pairList2.size(); i++){
		//	System.out.println("C: "+pairList.get(i).getCoherent() + " NC: "+pairList.get(i).getNoncoherent());
		//}
		
		int colorcoherence = ccv.computeCCV(pairList, pairList2);
		
		System.out.println(img1.getSimilarity(img2));
		System.out.println("CCV: "+colorcoherence);

	}
}
