package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import controller.MainController;

import java.awt.Color;
import javax.swing.JRadioButtonMenuItem;

public class MainGUI {

	private JFrame frame;
	private JPanel panel;
	private JPanel panelImg1;
	
	// Labels
	private JLabel lblSelectFile;
	private JLabel lblSelectMethod;
	
	// Buttons
	private JButton btnBrowseFile;
	private JButton btnExecute;
	
	// Radio Buttons
	private JRadioButton rdbtnCH;
	private JRadioButton rdbtnPS;
	private JRadioButton rdbtnCC;
	private JRadioButton rdbtnCR;
	
	// Non-GUI stuff
	private String filename = "";
	private String path = "";
	private MainController controller;
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI window = new MainGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public MainGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 722, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 752, 428);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		panel.setVisible(true);
		
		lblSelectFile = new JLabel("Select File");
		lblSelectFile.setBounds(17, 11, 72, 16);
		panel.add(lblSelectFile);
		
		btnBrowseFile = new JButton("Browse File");
		btnBrowseFile.setBounds(90, 6, 117, 29);
		btnBrowseFile.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				getImagePath();
			} 
		});
		panel.add(btnBrowseFile);
		
		lblSelectMethod = new JLabel("Select Method");
		lblSelectMethod.setBounds(17, 216, 97, 16);
		panel.add(lblSelectMethod);
		
		rdbtnCH = new JRadioButton("Color Histogram");
		rdbtnCH.setBounds(17, 239, 141, 23);
		panel.add(rdbtnCH);
		
		rdbtnPS = new JRadioButton("CH with Perceptual Similarity");
		rdbtnPS.setBounds(17, 266, 225, 23);
		panel.add(rdbtnPS);
		
		rdbtnCC = new JRadioButton("Histogram Refinement with Color Coherence");
		rdbtnCC.setBounds(17, 290, 312, 29);
		panel.add(rdbtnCC);
		
		rdbtnCR = new JRadioButton("CH with Centering Refinement");
		rdbtnCR.setBounds(17, 319, 225, 23);
		panel.add(rdbtnCR);
		
		ButtonGroup rdbtnGroup = new ButtonGroup();
		rdbtnGroup.add(rdbtnCC);
		rdbtnGroup.add(rdbtnCH);
		rdbtnGroup.add(rdbtnCR);
		rdbtnGroup.add(rdbtnPS);
		
		btnExecute = new JButton("JUST DO IT");
		btnExecute.setBounds(27, 354, 117, 29);
		btnExecute.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				// Goes through the button group to check if a radio button is selected & an image is selected
				// If a radio butt and image is selected, proceeds to do the selected method
				for (Enumeration<AbstractButton> buttons = rdbtnGroup.getElements(); buttons.hasMoreElements();) {
		            AbstractButton button = buttons.nextElement();

		            if (button.isSelected() && !filename.equals("") && !path.equals("")) {
		            	controller = new MainController(path, filename, button.getText());
		            }
		        }
				
			} 
		});
		panel.add(btnExecute);
		
		panelImg1 = new JPanel();
		panelImg1.setBackground(Color.WHITE);
		panelImg1.setBounds(17, 39, 240, 172);
		panel.add(panelImg1);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.DARK_GRAY);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(341, 6, 8, 416);
		panel.add(separator);
		panelImg1.setVisible(true);
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
		    
		    panelImg1.removeAll();
		    
	        BufferedImage myPicture = null;
	        try {
	            myPicture = ImageIO.read(selectedFile);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
	        panelImg1.add(picLabel);
	        panelImg1.revalidate();
	        panelImg1.repaint();
	        panelImg1.updateUI();
	    }
	}
}
