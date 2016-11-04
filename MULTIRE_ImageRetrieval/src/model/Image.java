package model;
/*
 * @(#)Image.java 1.0 03/07/08
 *
 * displays a JPEG image on the panel and  
 * gets the RGB value per pixel 
 */

import java.awt.*;
import java.awt.event.*;
import com.sun.image.codec.jpeg.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.*;
import javax.swing.*;
import java.awt.image.ColorModel;

class Image extends JFrame {

    JTextField jTextArea1 = new JTextField();
    JTextField jTextArea2 = new JTextField();

	//shows a JPEG on the screen on the screen at x,y

    public void showJPEG(int x, int y, Graphics2D g2, String path, String filename ) {

        BufferedImage bi = null;        
        String outputFileName =  path +
                                  File.separatorChar + filename;

        try {


            File file = new File(outputFileName);
            FileInputStream in = new FileInputStream(file);

            // decodes the JPEG data stream into a BufferedImage

            JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
            bi = decoder.decodeAsBufferedImage();
            
        } catch (Exception ex) {
            // file read error
        	System.out.println("file error");
            
        }

        if (bi == null) {
            /* null file */
        	System.out.println("null error");
            return;
        }

        g2.drawImage(bi, x, y ,this);
    }
	
	public Image() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});
	}


       public void init(){

	Graphics g = this.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        
        //displays 000.jpg at C:\ in the window
        showJPEG(1,50,g2,"images/", "0.jpg");
        

    }
    
    public void getRGB(int x, int y, String Path, String Filename){

   //gets the RGB and Luv value at x, y    	
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
            /*file arror*/
        	System.out.println("file error");
        }

        if (bi1 == null) {
            /*null file*/
        	System.out.println("null");
            return;
        }

        totalPixels = bi1.getHeight() * bi1.getWidth();
        
        System.out.println("height is : " + bi1.getHeight());
        System.out.println("width is : " + bi1.getWidth());
        

        ColorModel CM;
        CM = bi1.getColorModel();
        
        RGB1 = bi1.getRGB(x,y); //get the RGB value at x,y of the image
        
        double R, G, B;

        R = CM.getRed(RGB1);   //get the 8-bit values of RGB (0-255)
        G = CM.getGreen(RGB1);
        B = CM.getBlue(RGB1);	
	    cieConvert ColorCIE = new cieConvert();

		
	    ColorCIE.setValues(R/255.0, G/255.0, B/255.0);
	    
	    
	    jTextArea2.setText( "RGB:(" +
	    				    Double.toString(R) + "," +
	    				    Double.toString(G) + "," +
	    				    Double.toString(B) + ")" +
	    				    "-> "+Integer.toString(ColorCIE.IndexOf()));
	    jTextArea1.setText("  = LUV:(\n" +
	    				    Double.toString(ColorCIE.L) + "," +
	    				    Double.toString(ColorCIE.u) + "," +
	    				    Double.toString(ColorCIE.v) + ")" );
	    				    //
	    				    
	    				    
	    this.repaint();
    }
    
        
//	public static void main(String args[]) {
//	    JPanel panelCenter = new JPanel();
//   	    System.out.println("Starting Image...");
//	    Image mainFrame = new Image();
//	    
//	    panelCenter.setSize(100,100);
//	    mainFrame.getContentPane().add(panelCenter, BorderLayout.NORTH);
//	    
//	    mainFrame.jTextArea1.setLocation(20,230);
//	    mainFrame.jTextArea1.setSize(400,100);
//	    
//	    mainFrame.getContentPane().add(mainFrame.jTextArea1);
//		
//	    mainFrame.jTextArea2.setLocation(20,331);
//	    mainFrame.jTextArea2.setSize(200,100);
//	    
//	    mainFrame.getContentPane().add(mainFrame.jTextArea2);
//		
//		
//		mainFrame.setSize(600, 400);
//		mainFrame.setTitle("Image");
//		mainFrame.setVisible(true);
//
//                
//        mainFrame.getRGB(1,20,"images/", "0.jpg");		
//		
//		mainFrame.init();
//		
//	}
}
