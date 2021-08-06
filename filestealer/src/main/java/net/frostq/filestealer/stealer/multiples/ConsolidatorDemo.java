package net.frostq.filestealer.stealer.multiples;

import java.awt.event.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;

public class ConsolidatorDemo extends JPanel implements ActionListener {
	private static final long serialVersionUID = -4487732343062917781L;
	JFileChooser fc;
	JButton clear;
	JTextArea dropZone, console;
	JSplitPane childSplitPane, parentSplitPane;
	PrintStream ps;
	
	public ConsolidatorDemo() {
		super(new BorderLayout());
		
		fc = new JFileChooser();;
		fc.setMultiSelectionEnabled(true);
		fc.setDragEnabled(true);
		fc.setControlButtonsAreShown(false);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		JPanel fcPanel = new JPanel(new BorderLayout());
		fcPanel.add(fc, BorderLayout.CENTER);
		
		clear = new JButton("Clear All");
		clear.addActionListener(this);
		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		buttonPanel.add(clear, BorderLayout.LINE_END);
		
		JPanel leftUpperPanel = new JPanel(new BorderLayout());
		leftUpperPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		leftUpperPanel.add(fcPanel, BorderLayout.CENTER);
		leftUpperPanel.add(buttonPanel, BorderLayout.PAGE_END);
		
		JScrollPane leftLowerPanel = new javax.swing.JScrollPane();
		leftLowerPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		dropZone = new JTextArea();
		dropZone.setColumns(20);
		dropZone.setLineWrap(true);
		dropZone.setRows(5);
		dropZone.setDragEnabled(true);
		dropZone.setDropMode(javax.swing.DropMode.INSERT);
		dropZone.setBorder(new TitledBorder("Selected files/folders"));
		leftLowerPanel.setViewportView(dropZone);
		
		childSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				leftUpperPanel, leftLowerPanel);
		childSplitPane.setDividerLocation(400);
		childSplitPane.setPreferredSize(new Dimension(480, 650));
		
		console = new JTextArea();
		console.setColumns(40);
		console.setLineWrap(true);
		console.setBorder(new TitledBorder("Console"));
		
		parentSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				childSplitPane, console);
		parentSplitPane.setDividerLocation(480);
		parentSplitPane.setPreferredSize(new Dimension(800, 650));
		
		add(parentSplitPane, BorderLayout.CENTER);
	}
	
	public void setDefaultButton() {
		getRootPane().setDefaultButton(clear);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == clear) {
			dropZone.setText("");
		}
	}
	
	/**
	 * Create the GUI and show it. For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 */
	private static void createAndShowGUI() {
	    //Make sure we have nice window decorations.
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    try {
	      //UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel");
	        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	            if ("Nimbus".equals(info.getName())) {
	                UIManager.setLookAndFeel(info.getClassName());
	                break;
	            }
	        }
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	
	    //Create and set up the window.
	    JFrame frame = new JFrame("Consolidator!");
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
	    //Create and set up the menu bar and content pane.
	    ConsolidatorDemo demo = new ConsolidatorDemo();
	    demo.setOpaque(true); //content panes must be opaque
	    frame.setContentPane(demo);
	
	    //Display the window.
	    frame.pack();
	    frame.setVisible(true);
	    demo.setDefaultButton();
	}

	public static void main(String[] args) {
	    //Schedule a job for the event-dispatching thread:
	    //creating and showing this application's GUI.
	    javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	            createAndShowGUI();
	        }
	    });
	}
}