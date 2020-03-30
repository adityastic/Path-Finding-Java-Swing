package com.aditya.path_visual;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.aditya.path_visual.grid_view.Grid;

public class VisualizerGUI extends JFrame{

	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				VisualizerGUI frame = new VisualizerGUI();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}			
		});		
	}
	
	private static final long serialVersionUID = 1L;
	
	private Grid grid;
	private JPanel container;
	private JPanel controlPanel;
	private JPanel buttonPanel;
	private JPanel optionPanel;
	private JButton playButton;
	private JButton resetButton;
	private JComboBox<?> gridEditorList;
	private JLabel gridEditorListLabel;
	
	public VisualizerGUI(){

		grid = new Grid(600,600,10,10);
		
		
		container = new JPanel(new BorderLayout());
		controlPanel = new JPanel(new BorderLayout());
		
		playButton = new JButton("Start");
		playButton.setMnemonic(KeyEvent.VK_S);
	    playButton.setActionCommand("start");
	    playButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				grid.start();
				playButton.setEnabled(false);
			}
		});
	    
		resetButton = new JButton("Reset");
		resetButton.setMnemonic(KeyEvent.VK_R);
	    resetButton.setActionCommand("reset");
	    resetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				grid.reset();
				playButton.setEnabled(true);
			}
		});
	    
	    String editList[] = {"Start", "Target", "Obstacle"};
	    gridEditorList = new JComboBox<>(editList);
	    gridEditorList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				grid.setPositionable(gridEditorList.getSelectedIndex());
			}
		});
	    gridEditorListLabel = new JLabel("Place on Grid: ");
	    gridEditorListLabel.setLabelFor(gridEditorList);
	    gridEditorListLabel.setHorizontalAlignment(JLabel.CENTER);	
	    
	    buttonPanel = new JPanel(new GridLayout(2, 1, 0, 10));
	    buttonPanel.add(playButton);
	    buttonPanel.add(resetButton);
	    controlPanel.add(buttonPanel, BorderLayout.WEST);
		
	    optionPanel = new JPanel(new GridLayout(3, 2, 0, 5));
		optionPanel.add(gridEditorListLabel);
		optionPanel.add(gridEditorList);
		controlPanel.add(optionPanel,BorderLayout.CENTER);
		
		controlPanel.setPreferredSize(new Dimension(400,75));
		
		container.add(grid,BorderLayout.CENTER);
		container.add(controlPanel,BorderLayout.NORTH);
		
		getContentPane().add(container);
		this.setResizable(true);
		this.pack();		
	}
}
