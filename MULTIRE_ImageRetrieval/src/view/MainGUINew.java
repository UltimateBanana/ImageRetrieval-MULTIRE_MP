package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.MainController;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Enumeration;

public class MainGUINew {

	private JFrame frame;
	public JTextArea textArea;
	public JPanel panel_1;
	
	// Non-GUI stuff
	private String filename = "";
	private String path = "";
	private MainController controller;
	private String output= "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUINew window = new MainGUINew();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainGUINew() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 494, 338);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 329, 304);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblSelectImage = new JLabel("Select Image");
		lblSelectImage.setBounds(6, 6, 79, 16);
		panel.add(lblSelectImage);
		
		JButton btnBrowseImage = new JButton("Browse Image");
		btnBrowseImage.setBounds(98, 1, 117, 29);
		btnBrowseImage.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				getImagePath();
			} 
		});
		panel.add(btnBrowseImage);
		
		panel_1 = new JPanel();
		panel_1.setBounds(16, 34, 204, 130);
		panel.add(panel_1);
		
		JRadioButton rdbtnColorHistogram = new JRadioButton("Color Histogram");
		rdbtnColorHistogram.setBounds(6, 176, 141, 23);
		panel.add(rdbtnColorHistogram);
		
		JRadioButton rdbtnChWithPerceptual = new JRadioButton("CH with Perceptual Similarity");
		rdbtnChWithPerceptual.setBounds(6, 201, 229, 23);
		panel.add(rdbtnChWithPerceptual);
		
		JRadioButton rdbtnHistogramRefinementWith = new JRadioButton("Histogram Refinement with Color Coherence");
		rdbtnHistogramRefinementWith.setBounds(6, 225, 317, 23);
		panel.add(rdbtnHistogramRefinementWith);
		
		JRadioButton rdbtnChWithCentering = new JRadioButton("CH with Centering Refinement");
		rdbtnChWithCentering.setBounds(6, 249, 229, 23);
		panel.add(rdbtnChWithCentering);
		
		ButtonGroup rdbtnGroup = new ButtonGroup();
		rdbtnGroup.add(rdbtnColorHistogram);
		rdbtnGroup.add(rdbtnChWithPerceptual);
		rdbtnGroup.add(rdbtnHistogramRefinementWith);
		rdbtnGroup.add(rdbtnChWithCentering);
		
		JButton btnExecute = new JButton("Execute");
		btnExecute.setBounds(0, 275, 117, 29);
		btnExecute.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				// Goes through the button group to check if a radio button is selected & an image is selected
				// If a radio butt and image is selected, proceeds to do the selected method
				for (Enumeration<AbstractButton> buttons = rdbtnGroup.getElements(); buttons.hasMoreElements();) {
		            AbstractButton button = buttons.nextElement();

		            if (button.isSelected() && !filename.equals("") && !path.equals("")) {
		            	controller = new MainController(path, filename, button.getText());
		            	output = controller.displayRankedImages();
		            	displayOutput();
		            	
		            }
		        }
				
			} 
		});
		panel.add(btnExecute);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(347, 6, 141, 304);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		textArea = new JTextArea(5,5);
		textArea.setEditable(false);
//		panel_2.add(textArea);
		
		JScrollPane scroll = new JScrollPane(textArea);
		panel_2.add(scroll);
		textArea.setText("BANANA");
	}
	
	// Gets the filename and path of the image
	// Displays the input image in the GUI
	public void getImagePath(){
		JFileChooser fileChooser = new JFileChooser("images/");
	    int returnValue = fileChooser.showOpenDialog(null);
	    
	    if (returnValue == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    filename = selectedFile.getName();
		    path = selectedFile.getPath().replace(filename, "");
		    
		    System.out.println(path+ "   " + filename);
		    
		    panel_1.removeAll();
		    
	        BufferedImage myPicture = null;
	        try {
	            myPicture = ImageIO.read(selectedFile);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
	        panel_1.add(picLabel);
	        panel_1.revalidate();
	        panel_1.repaint();
	        panel_1.updateUI();
	    }
	}
	
	public void displayOutput(){
		textArea.setText("");
		textArea.append(output);
    	textArea.revalidate();
    	textArea.repaint();
    	textArea.updateUI();
	}
}
